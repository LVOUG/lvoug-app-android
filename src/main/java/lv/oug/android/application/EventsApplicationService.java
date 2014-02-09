package lv.oug.android.application;

import lv.oug.android.domain.Event;
import lv.oug.android.infrastructure.common.ClassLogger;
import lv.oug.android.integration.webservice.WebServiceIntegration;
import lv.oug.android.integration.webservice.events.EventsWrapperJSON;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class EventsApplicationService {

    ClassLogger logger = new ClassLogger(EventsApplicationService.class);

    @Inject
    WebServiceIntegration webService;

    public List<Event> loadEvents() {
        try {
            EventsWrapperJSON eventJSONs = webService.loadEventsWrapper();

            logger.e(eventJSONs.getEvents().size() + "");
        } catch (Exception e) {
            logger.e("Exception", e);
        }

        return getMockEvents();
    }

    @Deprecated
    private List<Event> getMockEvents() {
        List<Event> mockEvents = new ArrayList<Event>();


        for (int i = 0; i < 10; i++) {
            Event e = new Event();
            e.setTitle("Mock title " + i);
            e.setDescription("Mock Description " + i);
            e.setAddress("Riga, Latvia, Mock street " + i);

            mockEvents.add(e);
        }

        return mockEvents;
    }
}
