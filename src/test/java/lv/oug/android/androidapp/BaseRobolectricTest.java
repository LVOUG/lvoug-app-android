package lv.oug.android.androidapp;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import dagger.Module;
import dagger.ObjectGraph;
import lv.oug.android.infrastructure.dagger.DaggerModule;
import lv.oug.android.infrastructure.dagger.MainModule;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;


@Ignore
@RunWith(RobolectricTestRunner.class)
public abstract class BaseRobolectricTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ObjectGraph.create(new MainModule(Robolectric.application), new BaseTestModule(), getModule()).inject(this);
    }

    public abstract DaggerModule getModule();

    @Module(includes = MainModule.class, overrides = true, injects = BaseRobolectricTest.class)
    public static class BaseTestModule implements DaggerModule {}
}
