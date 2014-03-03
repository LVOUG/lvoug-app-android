package lv.oug.android.presentation.events;

import android.os.AsyncTask;
import android.os.Bundle;
import butterknife.InjectView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import lv.oug.android.R;
import lv.oug.android.domain.EventRepository;
import lv.oug.android.presentation.BaseFragment;

import javax.inject.Inject;


public class PastEventsFragment extends BaseFragment implements PullToRefreshListView.OnRefreshListener {

    @Inject
    EventRepository eventsRepository;

    @Inject
    EventsORMAdapter adapter;

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
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                getMainActivity().refreshFromServer();
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                listEvents.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
