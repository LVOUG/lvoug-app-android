package lv.oug.android.integration.webservice.events;

import com.google.gson.annotations.SerializedName;

public class SponsorJSON {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String logo;
    @SerializedName("image")
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
