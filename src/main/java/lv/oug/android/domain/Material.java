package lv.oug.android.domain;

import com.j256.ormlite.field.DatabaseField;

public class Material {

    @DatabaseField(id = true)
    private long id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String url;

    @DatabaseField(foreign = true, columnName = "event_id")
    private Event event;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
