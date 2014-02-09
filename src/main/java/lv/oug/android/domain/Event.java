package lv.oug.android.domain;

import android.os.Parcel;
import android.os.Parcelable;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class Event implements Parcelable {

    @DatabaseField
    private long id;

    @DatabaseField
    private String logo;

    @DatabaseField
    private String title;

    @DatabaseField
    private String description;

    @DatabaseField
    private String address;

    @DatabaseField
    private double addressLatitude;

    @DatabaseField
    private double addressLongitude;

    @DatabaseField
    private String eventPage;

    @DatabaseField
    private Date eventDate;

    @DatabaseField
    private Date createdAt;

    @DatabaseField
    private Date updatedAt;

    private Event(Parcel in) {
        id = in.readLong();
        logo = in.readString();
        title = in.readString();
        description = in.readString();
        address = in.readString();
        addressLatitude = in.readDouble();
        addressLongitude = in.readDouble();
        eventPage = in.readString();
        eventDate = new Date(in.readLong());
        createdAt = new Date(in.readLong());
        updatedAt = new Date(in.readLong());
    }

	@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(logo);
        out.writeString(title);
        out.writeString(description);
        out.writeString(address);
        out.writeDouble(addressLatitude);
        out.writeDouble(addressLongitude);
        out.writeString(eventPage);
        out.writeLong(eventDate.getTime());
        out.writeLong(createdAt.getTime());
        out.writeLong(updatedAt.getTime());
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(double addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public double getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(double addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public String getEventPage() {
        return eventPage;
    }

    public void setEventPage(String eventPage) {
        this.eventPage = eventPage;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
