package lv.oug.android.presentation.navigation;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import lv.oug.android.R;
import lv.oug.android.infrastructure.common.DrawableService;
import lv.oug.android.infrastructure.common.StringService;
import lv.oug.android.presentation.common.BaseListAdapter;

import javax.inject.Inject;
import java.util.Arrays;

public class NavigationDrawerAdapter extends BaseListAdapter<NavigationItem> {

    @Inject
    StringService stringService;

    @Inject
    DrawableService drawableService;

    public NavigationDrawerAdapter() {
        setData(Arrays.asList(NavigationItem.values()));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.drawer_list_item, parent, false);
        NavigationItem item = get(position);

        TextView titleV = ButterKnife.findById(view, R.id.navigation_title);
        String title = stringService.loadString(item.getTitleId());
        titleV.setText(title);

        ImageView moreButton = ButterKnife.findById(view, R.id.navigation_icon);
        Drawable drawable = drawableService.loadDrawable(item.getImgId());
        moreButton.setImageDrawable(drawable);

        return view;
    }
}
