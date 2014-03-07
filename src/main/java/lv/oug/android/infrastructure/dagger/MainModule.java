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
import lv.oug.android.presentation.articles.ArticlesFragment;
import lv.oug.android.presentation.events.EventsFragment;
import lv.oug.android.presentation.home.HomeFragment;

import javax.inject.Singleton;

@Module(
        injects = {
                BaseApplication.class,
                MainActivity.class,
                HomeFragment.class,
                EventsFragment.class,
                ArticlesFragment.class,
                AboutFragment.class,

                ServerPullService.class
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
