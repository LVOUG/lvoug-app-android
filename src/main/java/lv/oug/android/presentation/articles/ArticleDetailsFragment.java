package lv.oug.android.presentation.articles;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.InjectView;
import lv.oug.android.R;
import lv.oug.android.domain.Article;
import lv.oug.android.infrastructure.common.DateService;
import lv.oug.android.infrastructure.common.StringUtils;
import lv.oug.android.presentation.BaseFragment;
import lv.oug.android.presentation.common.imageloader.ImageLoader;

import javax.inject.Inject;

public class ArticleDetailsFragment extends BaseFragment {

    public static final String ARTICLE_DETAILS_KEY = "article_details";

    @Inject
    ImageLoader imageLoader;

    @Inject
    DateService dateService;

    @InjectView(R.id.article_title)
    TextView articleTitle;
    @InjectView(R.id.article_description)
    TextView articleDescription;
    @InjectView(R.id.article_date)
    TextView articleDate;
    @InjectView(R.id.article_icon)
    ImageView articleIcon;

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
        articleDate.setText(dateService.format(article.getUpdatedAt()));
        String icon = article.getIcon();
        if (!StringUtils.isEmpty(icon)) {
            articleIcon.setVisibility(View.VISIBLE);
            imageLoader.displayImage(icon, articleIcon);
        }
    }
}
