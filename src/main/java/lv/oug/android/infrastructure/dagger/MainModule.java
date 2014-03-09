package lv.oug.android.infrastructure.dagger;

import android.content.Context;
import android.view.LayoutInflater;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import lv.oug.android.application.ServerPullService;
import lv.oug.android.presentation.BaseApplication;
import lv.oug.android.presentation.MainActivity;
import lv.oug.android.presentation.about.AboutFragment;
import lv.oug.android.presentation.articles.ArticleDashboardFragment;
import lv.oug.android.presentation.articles.ArticleDetailsFragment;
import lv.oug.android.presentation.common.imageloader.ImageLoader;
import lv.oug.android.presentation.common.imageloader.MemoryCache;
import lv.oug.android.presentation.events.EventDashboardFragment;
import lv.oug.android.presentation.events.EventDetailsFragment;
import lv.oug.android.presentation.home.HomeFragment;

import javax.inject.Singleton;

@Module(
        injects = {
                BaseApplication.class,
                MainActivity.class,
                HomeFragment.class,
                EventDashboardFragment.class,
                EventDetailsFragment.class,
                ArticleDashboardFragment.class,
                ArticleDetailsFragment.class,
                AboutFragment.class,

                ServerPullService.class,
                ImageLoader.class
        }
)
public class MainModule implements DaggerModule {

    private final Context appContext;

    public MainModule(Context appContext) {
        this.appContext = appContext.getApplicationContext();
    }

    @Provides @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    Context provideContext() {
        return appContext;
    }

    @Provides
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(appContext);
    }

    @Provides @Singleton
    MemoryCache provideMemoryCache() {
        return new MemoryCache();
    }

    @Provides @Singleton
    ImageLoader provideImageLoader() {
        ImageLoader imageLoader = new ImageLoader();
        BaseApplication.inject(imageLoader);
        return imageLoader;
    }
}
