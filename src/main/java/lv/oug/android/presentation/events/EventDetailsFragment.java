package lv.oug.android.presentation.events;

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


public class EventDetailsFragment extends BaseFragment {

    @Override
    protected int contentViewId() {
        return R.layout.event_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
