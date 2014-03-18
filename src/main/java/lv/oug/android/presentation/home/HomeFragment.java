package lv.oug.android.presentation.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import butterknife.InjectView;
import lv.oug.android.R;
import lv.oug.android.domain.Article;
import lv.oug.android.domain.ArticleRepository;
import lv.oug.android.domain.Event;
import lv.oug.android.domain.EventRepository;
import lv.oug.android.infrastructure.common.DateService;
import lv.oug.android.presentation.BaseFragment;
import lv.oug.android.presentation.about.AboutFragment;
import lv.oug.android.presentation.articles.ArticleDetailsFragment;
import lv.oug.android.presentation.common.imageloader.ImageLoader;
import lv.oug.android.presentation.events.EventDetailsFragment;
import lv.oug.android.presentation.events.EventLayoutGenerator;

import javax.inject.Inject;

import static lv.oug.android.presentation.articles.ArticleDetailsFragment.ARTICLE_DETAILS_KEY;
import static lv.oug.android.presentation.events.EventDetailsFragment.EVENT_DETAILS_KEY;

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

    @InjectView(R.id.home_container)
    FrameLayout homeContainer;

    @Override
    protected int contentViewId() {
        return R.layout.home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Fragment fragment;
        Event event = eventRepository.loadNextUpcomingEvent();
        if (event == null) {
            Article article = articleRepository.loadLatestArticle();
            if (article != null) {
                Bundle data = new Bundle();
                data.putParcelable(ARTICLE_DETAILS_KEY, article);

                fragment = new ArticleDetailsFragment();
                fragment.setArguments(data);
            } else {
                fragment = new AboutFragment();
            }
        } else {
            Bundle data = new Bundle();
            data.putParcelable(EVENT_DETAILS_KEY, event);
            fragment = new EventDetailsFragment();
            fragment.setArguments(data);
        }

        getFragmentManager().beginTransaction()
                .add(R.id.home_container, fragment)
                .commit();
    }
}
