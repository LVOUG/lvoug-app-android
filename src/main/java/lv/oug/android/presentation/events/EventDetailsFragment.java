package lv.oug.android.presentation.events;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.InjectView;
import lv.oug.android.R;
import lv.oug.android.domain.Event;
import lv.oug.android.infrastructure.common.DateService;
import lv.oug.android.infrastructure.common.StringUtils;
import lv.oug.android.presentation.BaseFragment;
import lv.oug.android.presentation.common.imageloader.ImageLoader;

import javax.inject.Inject;

public class EventDetailsFragment extends BaseFragment {

    public static final String EVENT_DETAILS_KEY = "event_details";
    @Inject
    ImageLoader imageLoader;

    @Inject
    DateService dateService;

    @Inject
    EventLayoutGenerator layoutGenerator;

    @InjectView(R.id.event_title)
    TextView eventTitle;

    @InjectView(R.id.event_description)
    TextView eventDescription;

    @InjectView(R.id.event_date)
    TextView eventDate;

    @InjectView(R.id.event_icon)
    ImageView eventIcon;

    @InjectView(R.id.sponsors_container)
    LinearLayout sponsorsContainer;

    @InjectView(R.id.contacts_container)
    LinearLayout contactsContainer;

    @Override
    protected int contentViewId() {
        return R.layout.event_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Event event = getArguments().getParcelable(EVENT_DETAILS_KEY);
        showEvent(event);
    }

    private void showEvent(Event event) {
        eventTitle.setText(event.getTitle());
        eventDescription.setText(event.getDescription());
        eventDate.setText(dateService.format(event.getUpdatedAt()));
        String icon = event.getLogo();
        if (!StringUtils.isEmpty(icon)) {
            eventIcon.setVisibility(View.VISIBLE);
            imageLoader.displayImage(icon, eventIcon);
        }

        layoutGenerator.generateSponsorsLayout(sponsorsContainer, event.getSponsors());
        layoutGenerator.generateContactsLayout(contactsContainer, event.getContacts());
    }


}
