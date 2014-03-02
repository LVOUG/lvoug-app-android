package lv.oug.android.presentation.news;

import android.os.Bundle;
import android.widget.ListView;
import butterknife.InjectView;
import lv.oug.android.R;
import lv.oug.android.domain.Article;
import lv.oug.android.domain.ArticleRepository;
import lv.oug.android.presentation.BaseFragment;

import javax.inject.Inject;
import java.util.List;

public class NewsFragment extends BaseFragment {

    @Inject
    ArticleRepository articleRepository;
    @InjectView(R.id.list_articles)
    ListView listArticles;

    @Override
    protected int contentViewId() {
        return R.layout.news;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        List<Article> articles = articleRepository.load();
        listArticles.setAdapter(new ArticleListAdapter(this.getActivity(), R.layout.row_news_entity, articles));
    }
}
