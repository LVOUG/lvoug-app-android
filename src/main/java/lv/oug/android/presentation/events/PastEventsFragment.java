package lv.oug.android.presentation.events;

import android.os.Bundle;
import android.widget.ListView;
import butterknife.InjectView;
import lv.oug.android.R;
import lv.oug.android.domain.Event;
import lv.oug.android.domain.EventRepository;
import lv.oug.android.presentation.BaseFragment;

import javax.inject.Inject;
import java.util.List;


public class PastEventsFragment extends BaseFragment {

    @Inject
    EventRepository eventsRepository;
    @InjectView(R.id.list_events)
    ListView listEvents;

    @Override
    protected int contentViewId() {
        return R.layout.events;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        List<Event> events = eventsRepository.loadEvents();
        listEvents.setAdapter(new EventListAdapter(this.getActivity(), R.layout.row_event_entity, events));
    }
}
