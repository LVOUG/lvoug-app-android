package lv.oug.android.integration.webservice.events;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventsWrapperJSON {

    @SerializedName("events")
    private List<EventJSON> events;

    public List<EventJSON> getEvents() {
        return events;
    }

    public void setEvents(List<EventJSON> events) {
        this.events = events;
    }
}
