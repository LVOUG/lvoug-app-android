package lv.oug.android.presentation.common.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;
import lv.oug.android.R;

import javax.inject.Inject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {
    private static final int STUB_IMAGE = R.drawable.abs__progress_primary_holo_dark;

    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

    @Inject
    Context context;

    @Inject
    FileCache fileCache;

    @Inject
    MemoryCache memoryCache;

    Handler handler;

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    public void displayImage(String url, ImageView imageView) {
        if (url == null) {
            imageView.setImageResource(STUB_IMAGE);
            return;
        }

        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);
        else {
            queuePhoto(url, imageView);
            imageView.setImageResource(STUB_IMAGE);
        }
    }

    public void downloadImage(String url) {
        if (url == null) return;

        Bitmap bitmap = memoryCache.get(url);
        if (bitmap == null) {
            downloadPhoto(url);
        }
    }

    private void downloadPhoto(final String url) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = getBitmap(url);
                memoryCache.put(url, bmp);
            }
        });
    }

    private void queuePhoto(final String url, final ImageView imageView) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    if (imageViewReused(imageView, url))
                        return;
                    Bitmap bmp = getBitmap(url);
                    memoryCache.put(url, bmp);
                    if (imageViewReused(imageView, url))
                        return;
                    BitmapDisplayer bd = new BitmapDisplayer(bmp, imageView, url);
                    if (handler == null) {
                        handler = new Handler();
                    }
                    handler.post(bd);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    private Bitmap getBitmap(String url) {
        File f = fileCache.getFile(url);

        //from SD cache
        Bitmap b = decodeFile(f);
        if (b != null)
            return b;

        //from web
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            copyStream(is, os);
            os.close();
            conn.disconnect();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Throwable ex) {
            ex.printStackTrace();
            if (ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    boolean imageViewReused(ImageView imageView, String url) {
        String tag = imageViews.get(imageView);
        if (tag == null || !tag.equals(url))
            return true;
        return false;
    }

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        ImageView imageView;
        String url;

        public BitmapDisplayer(Bitmap b, ImageView i, String u) {
            bitmap = b;
            imageView = i;
            url = u;
        }

        public void run() {
            if (imageViewReused(imageView, url))
                return;
            if (bitmap != null)
                imageView.setImageBitmap(bitmap);
            else
                imageView.setImageResource(STUB_IMAGE);
        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

    public static void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ignored) {}
    }
}
