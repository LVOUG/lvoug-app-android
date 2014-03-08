package lv.oug.android.presentation.articles;

import android.os.Bundle;
import lv.oug.android.R;
import lv.oug.android.domain.Article;
import lv.oug.android.domain.Event;
import lv.oug.android.presentation.BaseFragment;

public class ArticleDetailsFragment extends BaseFragment {

    public static final String ARTICLE_DETAILS_KEY = "article_details";

    @Override
    protected int contentViewId() {
        return R.layout.article_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Article article = getArguments().getParcelable(ARTICLE_DETAILS_KEY);
        showArticle(article);
    }

    private void showArticle(Article article) {

    }
}
