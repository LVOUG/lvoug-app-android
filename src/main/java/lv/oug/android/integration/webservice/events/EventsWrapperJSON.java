package lv.oug.android.integration.webservice.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventsWrapperJSON {

    @SerializedName("events")
    private List<EventJSON> events;

    @Expose
    private Long timestamp = 20000L;

    public List<EventJSON> getEvents() {
        return events;
    }

    public void setEvents(List<EventJSON> events) {
        this.events = events;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
