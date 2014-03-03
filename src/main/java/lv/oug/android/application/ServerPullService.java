package lv.oug.android.application;

import android.app.IntentService;
import android.content.Intent;
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
import lv.oug.android.integration.webservice.articles.ArticleJSON;
import lv.oug.android.integration.webservice.articles.ArticleWrapperJSON;
import lv.oug.android.integration.webservice.events.EventJSON;
import lv.oug.android.integration.webservice.events.EventsWrapperJSON;
import lv.oug.android.presentation.BaseApplication;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import static lv.oug.android.domain.ArticleRepository.ARTICLES_TIMESTAMP;
import static lv.oug.android.domain.EventRepository.EVENTS_TIMESTAMP;

public class ServerPullService extends IntentService {

    private static final ClassLogger logger = new ClassLogger(ServerPullService.class);

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

    public ServerPullService() {
        super("ServerPullService");
        BaseApplication.inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        loadAndSaveEvents();
        loadAndSaveArticles();
    }

    public void loadAndSaveEvents() {
        try {
            if (!networkService.internetAvailable()) return;
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    Date now = new Date();
                    Date lastUpdated = new Date(sharedPreference.loadPreferenceLong(EVENTS_TIMESTAMP));

                    EventsWrapperJSON json = webService.loadEventsWrapper(lastUpdated);
                    List<EventJSON> jsonEvents = json.getEvents();
                    if(jsonEvents != null) {
                        List<Event> events = beanMapper.mapEvents(jsonEvents);
                        eventRepository.saveOrUpdate(events);
                        logger.d("Received  " + jsonEvents.size() + " events from server");
                    }
                    sharedPreference.savePreference(EVENTS_TIMESTAMP, now.getTime());
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
                    Date now = new Date();
                    Date lastUpdated = new Date(sharedPreference.loadPreferenceLong(ARTICLES_TIMESTAMP));

                    ArticleWrapperJSON json = webService.loadArticleWrapper(lastUpdated);
                    List<ArticleJSON> jsonArticles = json.getArticles();
                    if(jsonArticles != null) {
                        List<Article> articles = beanMapper.mapArticles(jsonArticles);
                        articleRepository.saveOrUpdate(articles);
                        logger.d("Received  " + articles.size() + " articles from server");
                    }
                    sharedPreference.savePreference(ARTICLES_TIMESTAMP, now.getTime());
                    return null;
                }
            }.execute();
        } catch (Exception e) {
            logger.e("Exception", e);
        }
    }
}