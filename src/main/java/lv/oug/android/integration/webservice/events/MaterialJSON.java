package lv.oug.android.integration.webservice.events;

import com.google.gson.annotations.SerializedName;

public class MaterialJSON {

    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;

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
}
