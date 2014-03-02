package lv.oug.android.domain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lv.oug.android.infrastructure.common.ClassLogger;

import javax.inject.Inject;
import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static ClassLogger logger = new ClassLogger(DatabaseHelper.class);

    private static final String DATABASE_NAME = "lvoug.db";
    private static final int DATABASE_VERSION = 1;
    private static final int NOT_MAPPED = -1;

    private Dao<Event, Integer> eventDao;
    private Dao<Article, Integer> articleDao;

    @Inject
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            logger.i("onCreate");
            TableUtils.createTable(connectionSource, Event.class);
            TableUtils.createTable(connectionSource, Article.class);

        } catch (SQLException e) {
            logger.e("Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            logger.i("onUpgrade");
            TableUtils.dropTable(connectionSource, Event.class, true);
            TableUtils.dropTable(connectionSource, Article.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            logger.e("Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Event, Integer> getEventDao() throws SQLException {
        if (eventDao == null) {
            eventDao = getDao(Event.class);
        }
        return eventDao;
    }

    public Dao<Article, Integer> getArticleDao() throws SQLException {
        if (articleDao == null) {
            articleDao = getDao(Article.class);
        }
        return articleDao;
    }
}
