package lv.oug.android.application;

import android.os.AsyncTask;
import lv.oug.android.domain.Article;
import lv.oug.android.domain.ArticleRepository;
import lv.oug.android.domain.Event;
import lv.oug.android.domain.EventRepository;
import lv.oug.android.infrastructure.common.BeanMapper;
import lv.oug.android.infrastructure.common.ClassLogger;
import lv.oug.android.infrastructure.common.NetworkService;
import lv.oug.android.infrastructure.common.SharedPreferenceService;
import lv.oug.android.integration.webservice.WebServiceIntegration;
import lv.oug.android.integration.webservice.articles.ArticleWrapperJSON;
import lv.oug.android.integration.webservice.events.EventsWrapperJSON;

import javax.inject.Inject;
import java.util.List;

import static lv.oug.android.domain.ArticleRepository.ARTICLES_TIMESTAMP;
import static lv.oug.android.domain.EventRepository.EVENTS_TIMESTAMP;

public class EventsApplicationService {

    ClassLogger logger = new ClassLogger(EventsApplicationService.class);

    @Inject
    BeanMapper beanMapper;

    @Inject
    NetworkService networkService;

    @Inject
    EventRepository eventRepository;

    @Inject
    ArticleRepository articleRepository;

    @Inject
    WebServiceIntegration webService;

    @Inject
    SharedPreferenceService sharedPreference;

    public void loadAndSaveEvents() {
        try {
            if (!networkService.internetAvailable()) return;
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    EventsWrapperJSON json = webService.loadEventsWrapper();

                    Long lastUpdatedServer = json.getTimestamp();
                    Long lasUpdatedClient = sharedPreference.loadPreferenceLong(EVENTS_TIMESTAMP);
                    if (lastUpdatedServer > lasUpdatedClient) {
                        List<Event> events = beanMapper.mapEvents(json.getEvents());

                        eventRepository.clearEvents();
                        eventRepository.saveEvents(events);
                        sharedPreference.savePreference(EVENTS_TIMESTAMP, lastUpdatedServer);
                        logger.d("Updated " + EVENTS_TIMESTAMP);
                    }
                    return null;
                }
            }.execute();
        } catch (Exception e) {
            logger.e("Exception", e);
        }
    }

    public void loadAndSaveArticles() {
        try {
            if (!networkService.internetAvailable()) return;
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    ArticleWrapperJSON json = webService.loadArticleWrapper();

                    Long lastUpdatedServer = json.getTimestamp();
                    Long lasUpdatedClient = sharedPreference.loadPreferenceLong(ARTICLES_TIMESTAMP);
                    if (lastUpdatedServer > lasUpdatedClient) {
                        List<Article> events = beanMapper.mapArticles(json.getArticles());

                        articleRepository.clear();
                        articleRepository.save(events);
                        sharedPreference.savePreference(ARTICLES_TIMESTAMP, lastUpdatedServer);
                        logger.d("Updated " + ARTICLES_TIMESTAMP);
                    }
                    return null;
                }
            }.execute();
        } catch (Exception e) {
            logger.e("Exception", e);
        }
    }
}
