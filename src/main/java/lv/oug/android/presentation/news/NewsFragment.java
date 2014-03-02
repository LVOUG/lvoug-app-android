package lv.oug.android.presentation.news;

import android.os.Bundle;
import android.widget.ListView;
import butterknife.InjectView;
import lv.oug.android.R;
import lv.oug.android.presentation.BaseFragment;

import javax.inject.Inject;

public class NewsFragment extends BaseFragment {

    @Inject
    ArticleORMAdapter adapter;

    @InjectView(R.id.list_articles)
    ListView listArticles;

    @Override
    protected int contentViewId() {
        return R.layout.news;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        listArticles.setAdapter(adapter);
    }
}
