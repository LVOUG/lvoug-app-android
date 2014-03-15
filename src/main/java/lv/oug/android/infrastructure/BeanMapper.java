package lv.oug.android.infrastructure;

import lv.oug.android.domain.*;
import lv.oug.android.integration.webservice.articles.ArticleJSON;
import lv.oug.android.integration.webservice.events.ContactJSON;
import lv.oug.android.integration.webservice.events.EventJSON;
import lv.oug.android.integration.webservice.events.MaterialJSON;
import lv.oug.android.integration.webservice.events.SponsorJSON;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeanMapper {

    @Inject
    EventRepository eventRepository;

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
        event.setLogo(eventJSON.getLogo());
        event.setTitle(eventJSON.getTitle());
        event.setDescription(eventJSON.getDescription());
        event.setAddress(eventJSON.getAddress());
        event.setAddressLatitude(eventJSON.getAddressLatitude());
        event.setAddressLongitude(eventJSON.getAddressLongitude());
        event.setEventPage(eventJSON.getEventPage());
        event.setEventDate(eventJSON.getEventDate());
        event.setCreatedAt(eventJSON.getCreatedAt());
        event.setUpdatedAt(eventJSON.getUpdatedAt());

        try {
            event.setContacts(eventRepository.getEventDao().<Contact>getEmptyForeignCollection("contacts"));
            event.setMaterials(eventRepository.getEventDao().<Material>getEmptyForeignCollection("materials"));
            event.setSponsors(eventRepository.getEventDao().<Sponsor>getEmptyForeignCollection("sponsors"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mapContacts(eventJSON, event);
        mapMaterials(eventJSON, event);
        mapSponsors(eventJSON, event);

        return event;
    }

    private void mapSponsors(EventJSON eventJSON, Event event) {
        List<SponsorJSON> sponsors = eventJSON.getSponsors();
        for (SponsorJSON s : sponsors) {
            Sponsor sponsor = map(s);
            sponsor.setEvent(event);
            event.getSponsors().add(sponsor);
        }
    }

    private void mapMaterials(EventJSON eventJSON, Event event) {
        List<MaterialJSON> materials = eventJSON.getMaterials();
        for (MaterialJSON m : materials) {
            Material material = map(m);
            material.setEvent(event);
            event.getMaterials().add(material);
        }
    }

    private void mapContacts(EventJSON eventJSON, Event event) {
        List<ContactJSON> contacts = eventJSON.getContacts();
        for (ContactJSON c : contacts) {
            Contact contact = map(c);
            contact.setEvent(event);
            event.getContacts().add(contact);
        }
    }

    public List<Article> mapArticles(List<ArticleJSON> json) {
        List<Article> result = new ArrayList<Article>();
        for (ArticleJSON jsonItem : json) {
            result.add(map(jsonItem));
        }
        return result;
    }

    private Material map(MaterialJSON m) {
        Material material = new Material();
        material.setId(m.getId());
        material.setTitle(m.getTitle());
        material.setUrl(m.getUrl());
        return material;
    }

    private Sponsor map(SponsorJSON s) {
        Sponsor sponsor = new Sponsor();
        sponsor.setId(s.getId());
        sponsor.setName(s.getName());
        sponsor.setImage(s.getImage());
        return sponsor;
    }

    private Contact map(ContactJSON c) {
        Contact contact = new Contact();
        contact.setId(c.getId());
        contact.setName(c.getName());
        contact.setSurname(c.getSurname());
        contact.setEmail(c.getEmail());
        contact.setPhone(c.getPhone());
        return contact;
    }

    private Article map(ArticleJSON articleJSON) {
        Article article = new Article();
        article.setId(articleJSON.getId());
        article.setTitle(articleJSON.getTitle());
        article.setDescription(articleJSON.getDescription());
        article.setCreatedAt(articleJSON.getCreatedAt());
        article.setUpdatedAt(articleJSON.getUpdatedAt());
        article.setIcon(articleJSON.getImage());
        return article;
    }
}
