package lv.oug.android.application;

import lv.oug.android.domain.Article;
import lv.oug.android.domain.ArticleRepository;
import lv.oug.android.domain.Event;
import lv.oug.android.domain.EventRepository;
import lv.oug.android.infrastructure.BeanMapper;
import lv.oug.android.infrastructure.common.ClassLogger;
import lv.oug.android.infrastructure.common.NetworkService;
import lv.oug.android.infrastructure.common.SharedPreferenceService;
import lv.oug.android.infrastructure.common.StringUtils;
import lv.oug.android.integration.webservice.WebServiceIntegration;
import lv.oug.android.integration.webservice.articles.ArticleJSON;
import lv.oug.android.integration.webservice.articles.ArticleWrapperJSON;
import lv.oug.android.integration.webservice.events.EventJSON;
import lv.oug.android.integration.webservice.events.EventsWrapperJSON;
import lv.oug.android.integration.webservice.events.SponsorJSON;
import lv.oug.android.presentation.BaseApplication;
import lv.oug.android.presentation.common.imageloader.ImageLoader;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static lv.oug.android.domain.ArticleRepository.ARTICLES_TIMESTAMP;
import static lv.oug.android.domain.EventRepository.EVENTS_TIMESTAMP;

public class ServerPullService {

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

    @Inject
    ImageLoader imageLoader;

    public ServerPullService() {
        BaseApplication.inject(this);
    }

    public void loadAndSaveEvents() {
        try {
            if (!networkService.internetAvailable()) return;

            Date now = new Date();
            Date lastUpdated = new Date(sharedPreference.loadPreferenceLong(EVENTS_TIMESTAMP));

            EventsWrapperJSON json = webService.loadEventsWrapper(lastUpdated);
            List<EventJSON> jsonEvents = json.getEvents();
            if (jsonEvents != null) {
                loadEventImagesInBackground(jsonEvents);
                List<Event> events = beanMapper.mapEvents(jsonEvents);
                List<Long> ids = gatherIds(events);
                eventRepository.deleteForeignCollections(ids);
                eventRepository.saveOrUpdate(events);
                logger.d("Received  " + jsonEvents.size() + " events from server");
            }
            sharedPreference.savePreference(EVENTS_TIMESTAMP, now.getTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAndSaveArticles() {
        try {
            if (!networkService.internetAvailable()) return;

            Date now = new Date();
            Date lastUpdated = new Date(sharedPreference.loadPreferenceLong(ARTICLES_TIMESTAMP));

            ArticleWrapperJSON json = webService.loadArticleWrapper(lastUpdated);
            List<ArticleJSON> jsonArticles = json.getArticles();
            if (jsonArticles != null) {
                loadArticleImagesInBackground(jsonArticles);
                List<Article> articles = beanMapper.mapArticles(jsonArticles);
                articleRepository.saveOrUpdate(articles);
                logger.d("Received  " + articles.size() + " articles from server");
            }
            sharedPreference.savePreference(ARTICLES_TIMESTAMP, now.getTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadEventImagesInBackground(List<EventJSON> jsonEvents) {
        for (EventJSON jsonEvent : jsonEvents) {
            imageLoader.downloadImage(jsonEvent.getLogo());
            for (SponsorJSON sponsor : jsonEvent.getSponsors()) {
                imageLoader.downloadImage(sponsor.getImage());
            }
        }
    }

    private void loadArticleImagesInBackground(List<ArticleJSON> jsonArticles) {
        for (ArticleJSON jsonArticle : jsonArticles) {
            String image = jsonArticle.getImage();
            if (!StringUtils.isEmpty(image)) {
                imageLoader.downloadImage(image);
            }
        }
    }

    private List<Long> gatherIds(List<Event> events) {
        List<Long> ids = new ArrayList<Long>();
        for (Event event : events) {
            ids.add(event.getId());
        }
        return ids;
    }
}
