package lv.oug.android.infrastructure.dagger;

import android.content.Context;
import android.view.LayoutInflater;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import lv.oug.android.application.EventsApplicationService;
import lv.oug.android.presentation.BaseApplication;
import lv.oug.android.presentation.MainActivity;
import lv.oug.android.presentation.about.AboutFragment;
import lv.oug.android.presentation.events.PastEventsFragment;
import lv.oug.android.presentation.home.HomeFragment;
import lv.oug.android.presentation.news.NewsFragment;

import javax.inject.Singleton;

@Module(
        injects = {
                BaseApplication.class,
                MainActivity.class,
                HomeFragment.class,
                PastEventsFragment.class,
                NewsFragment.class,
                AboutFragment.class,

                EventsApplicationService.class
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
}
