package lv.oug.android.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "sponsors")
public class Sponsor {

    public static final java.lang.String EVENT_ID = "event_id";

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String image;

    @DatabaseField(foreign = true, columnName = EVENT_ID)
    private Event event;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
