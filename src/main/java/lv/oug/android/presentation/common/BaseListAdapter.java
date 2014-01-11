package lv.oug.android.presentation.common;

import android.content.Context;
import android.widget.BaseAdapter;
import javax.inject.Inject;
import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {

    @Inject
    Context context;

    private List<T> data;

    @Override
    public int getCount() {
        return getData().size();
    }

    @Override
    public Object getItem(int position) {
        return getData().get(position);
    }

    public T get(int position) {
        return getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    public Context getContext() {
        return context;
    }
}
