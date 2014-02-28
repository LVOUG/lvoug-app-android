package lv.oug.android.presentation.news;

import android.os.Bundle;
import android.widget.ListView;
import butterknife.InjectView;
import lv.oug.android.R;
import lv.oug.android.domain.Article;
import lv.oug.android.presentation.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends BaseFragment {

    @InjectView(R.id.list_articles)
    ListView listArticles;

    @Override
    protected int contentViewId() {
        return R.layout.news;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        List<Article> articles = new ArrayList<Article>();
        listArticles.setAdapter(new ArticleListAdapter(this.getActivity(), R.layout.row_news_entity, articles));
    }
}
