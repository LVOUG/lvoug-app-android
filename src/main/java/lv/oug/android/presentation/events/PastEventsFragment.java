package lv.oug.android.presentation.events;

import android.os.Bundle;
import android.util.Log;
import lv.oug.android.R;
import lv.oug.android.application.EventsApplicationService;
import lv.oug.android.presentation.BaseFragment;

import javax.inject.Inject;

public class PastEventsFragment extends BaseFragment {

    @Inject
    EventsApplicationService eventsService;

    @Override
    protected int contentViewId() {
        return R.layout.events;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        eventsService.loadEvents();
    }
}
