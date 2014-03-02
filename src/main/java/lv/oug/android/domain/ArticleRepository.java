package lv.oug.android.domain;

import android.content.Context;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lv.oug.android.infrastructure.common.ClassLogger;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class ArticleRepository {
    public static final String EVENTS_TIMESTAMP = "EVENTS_TIMESTAMP";

    private static ClassLogger logger = new ClassLogger(EventRepository.class);

    @Inject
    Context context;

    @Inject
    DatabaseHelper db;

    public void save(List<Article> list) {
        try {
            for (Article article : list) {
                db.getArticleDao().create(article);
            }
        } catch (SQLException e) {
            logger.e(e.getLocalizedMessage());
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

}
