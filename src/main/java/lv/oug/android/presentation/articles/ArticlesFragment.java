package lv.oug.android.presentation.articles;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import butterknife.InjectView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import lv.oug.android.R;
import lv.oug.android.application.ServerPullService;
import lv.oug.android.presentation.BaseFragment;

import javax.inject.Inject;

public class ArticlesFragment extends BaseFragment implements PullToRefreshListView.OnRefreshListener<ListView> {

    @Inject
    ArticleORMAdapter adapter;

    @Inject
    ServerPullService serverPullService;

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

        listArticles.setRefreshing();
        onRefresh(null);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                serverPullService.loadAndSaveArticles();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                listArticles.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        listArticles.onRefreshComplete();
    }
}
