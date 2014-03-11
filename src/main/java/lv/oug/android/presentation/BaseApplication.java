package lv.oug.android.presentation;

import android.app.Application;
import android.os.AsyncTask;
import dagger.ObjectGraph;
import lv.oug.android.application.ServerPullService;
import lv.oug.android.infrastructure.common.ClassLogger;
import lv.oug.android.infrastructure.dagger.DaggerModule;
import lv.oug.android.infrastructure.dagger.MainModule;

import java.util.Arrays;
import java.util.List;

public class BaseApplication extends Application {

    private final ClassLogger logger = new ClassLogger(BaseApplication.class);

    private static ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerModule[] modules = getModules();
        objectGraph = ObjectGraph.create(modules);

        startInBackground();
    }

    public void startInBackground() {
        try {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    ServerPullService serverPullService = objectGraph.get(ServerPullService.class);
                    serverPullService.loadAndSaveEvents();
                    serverPullService.loadAndSaveArticles();
                    return null;
                }
            };
        } catch (Exception e) {
            logger.e("Exception during server connection", e);
        }
    }

    public static <T> void inject(T instance) {
        if(objectGraph != null) objectGraph.inject(instance);
    }

    public DaggerModule[] getModules() {
        List<DaggerModule> modules = Arrays.<DaggerModule>asList(
                new MainModule(this)
        );
        return modules.toArray(new DaggerModule[modules.size()]);
    }
}
