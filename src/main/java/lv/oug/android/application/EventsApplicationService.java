package lv.oug.android.application;

import lv.oug.android.domain.Event;
import lv.oug.android.infrastructure.common.ClassLogger;
import lv.oug.android.integration.webservice.events.EventJSON;
import lv.oug.android.integration.webservice.WebServiceIntegration;
import lv.oug.android.integration.webservice.events.EventsWrapperJSON;

import javax.inject.Inject;
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

        return null;
    }
}
