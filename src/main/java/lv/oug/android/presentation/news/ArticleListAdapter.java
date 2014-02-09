package lv.oug.android.presentation.news;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import lv.oug.android.R;
import lv.oug.android.domain.Article;

import java.text.SimpleDateFormat;
import java.util.List;

public class ArticleListAdapter extends ArrayAdapter<Article> {

    SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
    Context context;
    int layoutResourceId;
    List<Article> articles = null;

    public ArticleListAdapter(Context context, int viewResourceId, List<Article> articles) {
        super(context, viewResourceId, articles);
        this.context = context;
        this.layoutResourceId = viewResourceId;
        this.articles = articles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        ArticleHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.row_event_entity, null, true);

            holder = new ArticleHolder();
            holder.articleTitle = (TextView) row.findViewById(R.id.news_title);
            holder.articleDate = (TextView) row.findViewById(R.id.news_date);

            row.setTag(holder);
        } else {
            holder = (ArticleHolder) row.getTag();
        }

        Article article = articles.get(position);

        update(holder, article);

        return row;
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
}