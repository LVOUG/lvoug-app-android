package lv.oug.android.domain;

import android.content.Context;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lv.oug.android.infrastructure.common.ClassLogger;
import lv.oug.android.infrastructure.common.DateService;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class ArticleRepository {

    public static final String ARTICLES_TIMESTAMP = "ARTICLES_TIMESTAMP";

    private static ClassLogger logger = new ClassLogger(EventRepository.class);

    @Inject
    Context context;

    @Inject
    DatabaseHelper db;

    @Inject
    DateService dateService;

    public void saveOrUpdate(List<Article> list) {
        try {
            for (Article article : list) {
                db.getArticleDao().createOrUpdate(article);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Article> load() {
        try {
            return db.getArticleDao().queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        try {
            ConnectionSource source = db.getConnectionSource();
            TableUtils.clearTable(source, Article.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Article, Long> getArticleDao() throws SQLException {
        return db.getArticleDao();
    }

    public AndroidDatabaseResults getRawResults() {
        try {
            QueryBuilder<Article, Long> queryBuilder = getArticleDao().queryBuilder();
            queryBuilder.orderBy("createdAt", false);
            PreparedQuery<Article> query = queryBuilder.prepare();
            return (AndroidDatabaseResults) getArticleDao().iterator(query).getRawResults();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Article loadLatestArticle() {
        try {
            QueryBuilder<Article, Long> queryBuilder = getArticleDao().queryBuilder();
            PreparedQuery<Article> query = queryBuilder.orderBy("createdAt", false).prepare();
            return getArticleDao().queryForFirst(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
