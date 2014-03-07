package lv.oug.android.presentation.articles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.j256.ormlite.android.AndroidDatabaseResults;
import lv.oug.android.R;
import lv.oug.android.domain.Article;
import lv.oug.android.domain.ArticleRepository;
import lv.oug.android.infrastructure.common.DrawableService;

import javax.inject.Inject;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ArticleORMAdapter extends BaseAdapter {

    @Inject
    LayoutInflater inflater;

    @Inject
    ArticleRepository repository;

    @Inject
    DrawableService drawableService;

    private SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy HH:mm");

    private AndroidDatabaseResults dbResults;

    @Override
    public int getCount() {
        return getDbResults().getCount();
    }

    @Override
    public Article getItem(final int position) {
        try {
            getDbResults().moveAbsolute(position);
            return repository.getArticleDao().mapSelectStarRow(getDbResults());
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

        ArticleHolder holder;
        if (row == null) {
            row = inflater.inflate(R.layout.row_news_entity, null, true);

            holder = new ArticleHolder();
            holder.articleTitle = (TextView) row.findViewById(R.id.news_title);
            holder.articleDate = (TextView) row.findViewById(R.id.news_date);

            row.setTag(holder);
        } else {
            holder = (ArticleHolder) row.getTag();
        }

        Article article = getItem(position);

        update(holder, article);

        return row;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        dbResults = repository.getRawResults();
    }

    private void update(ArticleHolder holder, Article a) {
        holder.articleTitle.setText(a.getTitle());

        if (a.getCreatedAt() != null) {
            holder.articleDate.setText(df.format(a.getCreatedAt()));
        }

    }

    static class ArticleHolder {
        public TextView articleTitle;
        public TextView articleDate;
    }

    public AndroidDatabaseResults getDbResults() {
        if (dbResults == null) {
            dbResults = repository.getRawResults();
        }
        return dbResults;
    }
}
