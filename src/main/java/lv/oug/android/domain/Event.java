package lv.oug.android.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {

    private Event(Parcel in) {

    }

	@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
