package lv.oug.android.presentation.news;

import android.os.AsyncTask;
import android.os.Bundle;
import butterknife.InjectView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import lv.oug.android.R;
import lv.oug.android.presentation.BaseFragment;

import javax.inject.Inject;

public class NewsFragment extends BaseFragment implements PullToRefreshListView.OnRefreshListener {

    @Inject
    ArticleORMAdapter adapter;

    @InjectView(R.id.list_articles)
    PullToRefreshListView listArticles;

    @Override
    protected int contentViewId() {
        return R.layout.news;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        listArticles.setAdapter(adapter);
        listArticles.setOnRefreshListener(this);
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
                listArticles.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }
        }.execute();

    }
}
