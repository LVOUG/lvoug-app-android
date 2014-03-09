package lv.oug.android.presentation.events;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.j256.ormlite.android.AndroidDatabaseResults;
import lv.oug.android.R;
import lv.oug.android.domain.Event;
import lv.oug.android.domain.EventRepository;
import lv.oug.android.infrastructure.common.DrawableService;
import lv.oug.android.presentation.common.imageloader.ImageLoader;

import javax.inject.Inject;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class EventsORMAdapter extends BaseAdapter {

    @Inject
    LayoutInflater inflater;

    @Inject
    EventRepository repository;

    @Inject
    DrawableService drawableService;

    @Inject
    ImageLoader imageLoader;

    private SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy HH:mm");

    private AndroidDatabaseResults dbResults;

    @Override
    public int getCount() {
        return getDbResults().getCount();
    }

    @Override
    public Event getItem(final int position) {
        try {
            getDbResults().moveAbsolute(position);
            return repository.getEventDao().mapSelectStarRow(getDbResults());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public long getItemId(final int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        EventHolder holder;
        if (row == null) {
            row = inflater.inflate(R.layout.row_event_entity, null, true);

            holder = new EventHolder();
            holder.eventHeaderImage = (ImageView) row.findViewById(R.id.event_header_image);
            holder.eventTitle = (TextView) row.findViewById(R.id.event_title);
            holder.eventDescription = (TextView) row.findViewById(R.id.event_description);
            holder.eventDate = (TextView) row.findViewById(R.id.event_date);

            row.setTag(holder);
        } else {
            holder = (EventHolder) row.getTag();
        }

        Event event = getItem(position);

        update(holder, event);

        return row;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        dbResults = repository.getRawResults();
    }

    private void update(EventHolder holder, Event e) {
        holder.eventTitle.setText(e.getTitle());
        holder.eventDescription.setText(e.getDescription());

        if (e.getEventDate() != null) {
            holder.eventDate.setText(df.format(e.getEventDate()));
        }

        imageLoader.DisplayImage(e.getLogo(), holder.eventHeaderImage);
    }

    static class EventHolder {
        public ImageView eventHeaderImage;
        public TextView eventTitle;
        public TextView eventDescription;
        public TextView eventDate;
    }

    public AndroidDatabaseResults getDbResults() {
        if (dbResults == null) {
            dbResults = repository.getRawResults();
        }
        return dbResults;
    }
}
