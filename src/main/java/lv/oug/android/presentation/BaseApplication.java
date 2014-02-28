package lv.oug.android.presentation;

import android.app.Application;
import dagger.ObjectGraph;
import lv.oug.android.application.EventsApplicationService;
import lv.oug.android.infrastructure.dagger.DaggerModule;
import lv.oug.android.infrastructure.dagger.MainModule;

import java.util.Arrays;
import java.util.List;

public class BaseApplication extends Application {

    private static ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerModule[] modules = getModules();
        objectGraph = ObjectGraph.create(modules);

        EventsApplicationService eventsApplicationService = objectGraph.get(EventsApplicationService.class);
        eventsApplicationService.loadAndSaveEvents();
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
