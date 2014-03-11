package lv.oug.android.presentation.home;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.InjectView;
import lv.oug.android.R;
import lv.oug.android.domain.Article;
import lv.oug.android.domain.ArticleRepository;
import lv.oug.android.domain.Event;
import lv.oug.android.domain.EventRepository;
import lv.oug.android.presentation.BaseFragment;

import javax.inject.Inject;

import static android.view.View.VISIBLE;

public class HomeFragment extends BaseFragment {

    @Inject
    ArticleRepository articleRepository;

    @Inject
    EventRepository eventRepository;

    @InjectView(R.id.upcoming_event)
    LinearLayout upcomingEvent;

    @InjectView(R.id.last_article)
    LinearLayout lastArticle;

    @InjectView(R.id.event_title)
    TextView eventTitle;

    @InjectView(R.id.article_title)
    TextView articleTitle;

    @Override
    protected int contentViewId() {
        return R.layout.home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Event event = eventRepository.loadNextUpcomingEvent();
        if (event == null) {
            Article article = articleRepository.loadLatestArticle();
            showArticle(article);
        } else {
            showEvent(event);
        }
    }

    private void showEvent(Event event) {
        upcomingEvent.setVisibility(VISIBLE);
        eventTitle.setText(event.getTitle());
    }

    private void showArticle(Article article) {
        lastArticle.setVisibility(VISIBLE);
        articleTitle.setText(article.getTitle());
    }
}
