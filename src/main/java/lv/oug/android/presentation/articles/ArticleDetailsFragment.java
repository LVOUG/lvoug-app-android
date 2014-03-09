package lv.oug.android.presentation.articles;

import android.os.Bundle;
import android.widget.TextView;
import butterknife.InjectView;
import lv.oug.android.R;
import lv.oug.android.domain.Article;
import lv.oug.android.presentation.BaseFragment;

import java.text.SimpleDateFormat;

public class ArticleDetailsFragment extends BaseFragment {

    public static final String ARTICLE_DETAILS_KEY = "article_details";
    @InjectView(R.id.article_title)
    TextView articleTitle;
    @InjectView(R.id.article_description)
    TextView articleDescription;
    @InjectView(R.id.article_date)
    TextView articleDate;

    SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yy");

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
        articleTitle.setText(article.getTitle());
        articleDescription.setText(article.getDescription());
        articleDate.setText(df.format(article.getUpdatedAt()));
    }
}
