package lv.oug.android.infrastructure.common;

import lv.oug.android.domain.Event;
import lv.oug.android.integration.webservice.events.EventJSON;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class BeanMapper {

    @Inject
    public BeanMapper() {}

    public List<Event> map(List<EventJSON>  json) {
        List<Event> events = new ArrayList<Event>();
        for (EventJSON eventJSON : json) {
            events.add(map(eventJSON));
        }
        return events;
    }

    private Event map(EventJSON eventJSON) {
        Event event = new Event();
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

}
