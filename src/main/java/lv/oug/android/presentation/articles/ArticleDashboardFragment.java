package lv.oug.android.presentation.articles;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.InjectView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import lv.oug.android.R;
import lv.oug.android.application.ServerPullService;
import lv.oug.android.domain.Article;
import lv.oug.android.infrastructure.common.ClassLogger;
import lv.oug.android.infrastructure.common.NetworkService;
import lv.oug.android.infrastructure.common.SharedPreferenceService;
import lv.oug.android.presentation.BaseFragment;

import javax.inject.Inject;

import static android.widget.AdapterView.OnItemClickListener;
import static com.handmark.pulltorefresh.library.PullToRefreshListView.OnRefreshListener;
import static lv.oug.android.presentation.articles.ArticleDetailsFragment.ARTICLE_DETAILS_KEY;

public class ArticleDashboardFragment extends BaseFragment implements OnRefreshListener<ListView>, OnItemClickListener {

    private static final ClassLogger logger = new ClassLogger(ArticleDashboardFragment.class);

    @Inject
    ArticleORMAdapter adapter;

    @Inject
    NetworkService networkService;

    @Inject
    ServerPullService serverPullService;

    @Inject
    SharedPreferenceService sharedPreference;

    @InjectView(R.id.list_articles)
    PullToRefreshListView listArticles;

    @Override
    protected int contentViewId() {
        return R.layout.article_dashboard;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        listArticles.setAdapter(adapter);
        listArticles.setOnRefreshListener(this);
        listArticles.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        try {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    serverPullService.loadAndSaveArticles();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if (!networkService.internetAvailable()) {
                        Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                    }
                    listArticles.onRefreshComplete();
                    adapter.notifyDataSetChanged();
                }
            }.execute();
        } catch (Exception e) {
            logger.e("Exception during server connection", e);
            Toast.makeText(getActivity(), R.string.failed_to_connect_to_server, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        listArticles.setRefreshing();
        onRefresh(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        position = position - 1; // hack to Pull to Refresh silly problem
        Article article = adapter.getItem(position);

        Bundle data = new Bundle();
        data.putParcelable(ARTICLE_DETAILS_KEY, article);

        ArticleDetailsFragment fragment = new ArticleDetailsFragment();
        fragment.setArguments(data);

        getMainActivity().changeFragment(fragment);
    }
}
