package lv.oug.android.domain;

import android.os.Parcel;
import android.os.Parcelable;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class Article implements Parcelable {

    @DatabaseField(id = true)
    private long id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String description;

    @DatabaseField
    private String icon;

    @DatabaseField
    private Date createdAt;

    @DatabaseField
    private Date updatedAt;

    public Article() {}

    private Article(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        createdAt = new Date(in.readLong());
        updatedAt = new Date(in.readLong());
        icon = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(title);
        out.writeString(description);
        out.writeLong(createdAt.getTime());
        out.writeLong(updatedAt.getTime());
        out.writeString(icon);
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
