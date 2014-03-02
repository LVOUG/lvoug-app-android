package lv.oug.android.infrastructure.common;

import lv.oug.android.domain.Article;
import lv.oug.android.domain.Event;
import lv.oug.android.integration.webservice.articles.ArticleJSON;
import lv.oug.android.integration.webservice.events.EventJSON;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class BeanMapper {

    @Inject
    public BeanMapper() {}

    public List<Event> mapEvents(List<EventJSON> json) {
        List<Event> result = new ArrayList<Event>();
        for (EventJSON jsonItem : json) {
            result.add(map(jsonItem));
        }
        return result;
    }

    private Event map(EventJSON eventJSON) {
        Event event = new Event();
        event.setId(eventJSON.getId());
        event.setTitle(eventJSON.getTitle());
        event.setDescription(eventJSON.getDescription());
        event.setAddress(eventJSON.getAddress());
        event.setAddressLatitude(eventJSON.getAddressLatitude());
        event.setAddressLongitude(eventJSON.getAddressLongitude());
        event.setEventPage(eventJSON.getEventPage());
        event.setEventDate(eventJSON.getEventDate());
        event.setCreatedAt(eventJSON.getCreatedAt());
        event.setUpdatedAt(eventJSON.getUpdatedAt());
        return event;
    }

    public List<Article> mapArticles(List<ArticleJSON> json) {
        List<Article> result = new ArrayList<Article>();
        for (ArticleJSON jsonItem : json) {
            result.add(map(jsonItem));
        }
        return result;
    }

    private Article map(ArticleJSON articleJSON) {
        Article article = new Article();
        article.setId(articleJSON.getId());
        article.setTitle(articleJSON.getTitle());
        article.setDescription(articleJSON.getDescription());
        article.setCreatedAt(articleJSON.getCreatedAt());
        article.setUpdatedAt(articleJSON.getUpdatedAt());
        return article;
    }
}
