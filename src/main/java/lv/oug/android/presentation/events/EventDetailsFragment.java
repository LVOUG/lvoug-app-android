package lv.oug.android.presentation.events;

import android.os.Bundle;
import lv.oug.android.R;
import lv.oug.android.domain.Event;
import lv.oug.android.presentation.BaseFragment;

public class EventDetailsFragment extends BaseFragment {

    public static final String EVENT_DETAILS_KEY = "event_details";

    @Override
    protected int contentViewId() {
        return R.layout.event_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Event event = getArguments().getParcelable(EVENT_DETAILS_KEY);
        showEvent(event);
    }

    private void showEvent(Event event) {

    }
}
