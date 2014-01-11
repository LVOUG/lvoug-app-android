package lv.oug.android.androidapp.common;

import android.content.Context;
import dagger.Module;
import lv.oug.android.R;
import lv.oug.android.androidapp.BaseRobolectricTest;
import lv.oug.android.infrastructure.common.StringService;
import lv.oug.android.infrastructure.dagger.DaggerModule;
import org.junit.Ignore;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.junit.Assert.assertThat;

@Ignore
public class StringServiceRoboTest extends BaseRobolectricTest {

    @Inject
    Context context;

    @Inject
    StringService service;

    @Test
    public void shouldFindString() {
        String expected = context.getResources().getString(R.string.app_name);
        String actual = service.loadString("app_name");

        assertThat(actual, equalTo(expected));
    }

    @Override
    public DaggerModule getModule() {
        return new TestModule();
    }

    @Module(includes = BaseTestModule.class, injects = StringServiceRoboTest.class)
    static class TestModule implements DaggerModule {}
}
