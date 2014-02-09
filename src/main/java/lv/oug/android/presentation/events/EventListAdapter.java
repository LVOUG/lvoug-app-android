package lv.oug.android.presentation.events;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import lv.oug.android.R;
import lv.oug.android.domain.Event;

import java.text.SimpleDateFormat;
import java.util.List;

public class EventListAdapter extends ArrayAdapter<Event> {

    SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy HH:mm");
    Context context;
    int layoutResourceId;
    List<Event> events = null;

    public EventListAdapter(Context context, int viewResourceId, List<Event> events) {
        super(context, viewResourceId, events);
        this.context = context;
        this.layoutResourceId = viewResourceId;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        EventHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
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

        Event event = events.get(position);

        update(holder, event);

        return row;
    }

    private void update(EventHolder holder, Event e) {
        holder.eventTitle.setText(e.getTitle());
        holder.eventDescription.setText(e.getDescription());

        if (e.getEventDate() != null) {
            holder.eventDate.setText(df.format(e.getEventDate()));
        }

        holder.eventHeaderImage.setImageDrawable(context.getResources().getDrawable(R.drawable.test_image));
    }

    static class EventHolder {
        public ImageView eventHeaderImage;
        public TextView eventTitle;
        public TextView eventDescription;
        public TextView eventDate;
    }
}
