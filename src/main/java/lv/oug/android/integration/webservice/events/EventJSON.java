package lv.oug.android.integration.webservice.events;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class EventJSON {

    @SerializedName("id")
    private long id;

    @SerializedName("logo")
    private String logo;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("address")
    private String address;

    @SerializedName("address_latitude")
    private double addressLatitude;

    @SerializedName("address_longitude")
    private double addressLongitude;

    @SerializedName("event_page")
    private String eventPage;

    @SerializedName("event_date")
    private Date eventDate;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("updated_at")
    private Date updatedAt;

    @SerializedName("event_materials")
    private List<MaterialJSON> materials;

    @SerializedName("sponsors")
    private List<SponsorJSON> sponsors;

    @SerializedName("contacts")
    private List<ContactJSON> contacts;

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

    public List<MaterialJSON> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialJSON> materials) {
        this.materials = materials;
    }

    public List<SponsorJSON> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<SponsorJSON> sponsors) {
        this.sponsors = sponsors;
    }

    public List<ContactJSON> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactJSON> contacts) {
        this.contacts = contacts;
    }
}
