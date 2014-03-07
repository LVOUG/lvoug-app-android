package lv.oug.android.presentation.events;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import butterknife.InjectView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import lv.oug.android.R;
import lv.oug.android.application.ServerPullService;
import lv.oug.android.domain.EventRepository;
import lv.oug.android.presentation.BaseFragment;

import javax.inject.Inject;


public class EventsFragment extends BaseFragment implements PullToRefreshListView.OnRefreshListener<ListView> {

    @Inject
    EventRepository eventsRepository;

    @Inject
    EventsORMAdapter adapter;

    @Inject
    ServerPullService serverPullService;

    @InjectView(R.id.list_events)
    PullToRefreshListView listEvents;

    @Override
    protected int contentViewId() {
        return R.layout.events;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        listEvents.setAdapter(adapter);
        listEvents.setOnRefreshListener(this);

        listEvents.setRefreshing();
        onRefresh(null);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                serverPullService.loadAndSaveEvents();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                listEvents.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        listEvents.onRefreshComplete();
    }
}
