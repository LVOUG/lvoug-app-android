package lv.oug.android.application;

import android.os.AsyncTask;
import lv.oug.android.domain.Event;
import lv.oug.android.domain.EventRepository;
import lv.oug.android.infrastructure.common.BeanMapper;
import lv.oug.android.infrastructure.common.ClassLogger;
import lv.oug.android.infrastructure.common.SharedPreferenceService;
import lv.oug.android.integration.webservice.WebServiceIntegration;
import lv.oug.android.integration.webservice.events.EventsWrapperJSON;

import javax.inject.Inject;
import java.util.List;

import static lv.oug.android.domain.EventRepository.EVENTS_TIMESTAMP;

public class EventsApplicationService {

    ClassLogger logger = new ClassLogger(EventsApplicationService.class);

    @Inject
    BeanMapper beanMapper;

    @Inject
    WebServiceIntegration webService;

    @Inject
    EventRepository eventRepository;

    @Inject
    SharedPreferenceService sharedPreference;

    public void loadAndSaveEvents() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    EventsWrapperJSON json = webService.loadEventsWrapper();

                    Long lastUpdatedServer = json.getTimestamp();
                    Long lasUpdatedClient = sharedPreference.loadPreferenceLong(EVENTS_TIMESTAMP);
                    if (lastUpdatedServer > lasUpdatedClient) {
                        List<Event> events = beanMapper.map(json.getEvents());

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
}
