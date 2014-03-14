package lv.oug.android.presentation.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.InjectView;
import lv.oug.android.R;
import lv.oug.android.domain.Article;
import lv.oug.android.domain.ArticleRepository;
import lv.oug.android.domain.Event;
import lv.oug.android.domain.EventRepository;
import lv.oug.android.infrastructure.common.DateService;
import lv.oug.android.infrastructure.common.StringUtils;
import lv.oug.android.presentation.BaseFragment;
import lv.oug.android.presentation.common.imageloader.ImageLoader;
import lv.oug.android.presentation.events.EventLayoutGenerator;

import javax.inject.Inject;

import static android.view.View.VISIBLE;

public class HomeFragment extends BaseFragment {

    @Inject
    ImageLoader imageLoader;

    @Inject
    DateService dateService;

    @Inject
    EventLayoutGenerator layoutGenerator;

    @Inject
    ArticleRepository articleRepository;

    @Inject
    EventRepository eventRepository;

    @InjectView(R.id.upcoming_event)
    LinearLayout upcomingEvent;

    @InjectView(R.id.event_title)
    TextView eventTitle;

    @InjectView(R.id.event_description)
    TextView eventDescription;

    @InjectView(R.id.event_date)
    TextView eventDate;

    @InjectView(R.id.event_icon)
    ImageView eventIcon;

    @InjectView(R.id.sponsors_container)
    LinearLayout sponsorsContainer;

    @InjectView(R.id.contacts_container)
    LinearLayout contactsContainer;

    @InjectView(R.id.last_article)
    LinearLayout lastArticle;


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
        return R.layout.home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Event event = eventRepository.loadNextUpcomingEvent();
        if (event == null) {
            Article article = articleRepository.loadLatestArticle();
            if (article != null) {
                showArticle(article);
            }
        } else {
            showEvent(event);
        }
    }

    private void showEvent(Event event) {
        upcomingEvent.setVisibility(VISIBLE);

        eventTitle.setText(event.getTitle());
        eventDescription.setText(event.getDescription());
        eventDate.setText(dateService.format(event.getUpdatedAt()));
        String icon = event.getLogo();
        if (!StringUtils.isEmpty(icon)) {
            eventIcon.setVisibility(View.VISIBLE);
            imageLoader.displayImage(icon, eventIcon);
        }

        layoutGenerator.generateSponsorsLayout(sponsorsContainer, event.getSponsors());
        layoutGenerator.generateContactsLayout(contactsContainer, event.getContacts());
    }

    private void showArticle(Article article) {
        lastArticle.setVisibility(VISIBLE);

        articleTitle.setText(article.getTitle());
        articleDescription.setText(article.getDescription());
        articleDate.setText(dateService.format(article.getUpdatedAt()));
        String icon = article.getIcon();
        if (!StringUtils.isEmpty(icon)) {
            articleIcon.setVisibility(View.VISIBLE);
            imageLoader.displayImage(icon, articleIcon);
        }
        articleTitle.setText(article.getTitle());
    }
}
