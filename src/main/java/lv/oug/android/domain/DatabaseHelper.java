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

    private static final String DATABASE_NAME = "lvoug.db";
    private static final int DATABASE_VERSION = 1;
    private static final int NOT_MAPPED = -1;
    private static ClassLogger logger = new ClassLogger(DatabaseHelper.class);

    private Dao<Event, Long> eventDao;
    private Dao<Article, Long> articleDao;
    private Dao<Sponsor, Long> sponsorDao;
    private Dao<Material, Long> materialDao;
    private Dao<Contact, Long> contactDao;

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
            TableUtils.createTable(connectionSource, Contact.class);
            TableUtils.createTable(connectionSource, Material.class);
            TableUtils.createTable(connectionSource, Sponsor.class);

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
            TableUtils.dropTable(connectionSource, Contact.class, true);
            TableUtils.dropTable(connectionSource, Material.class, true);
            TableUtils.dropTable(connectionSource, Sponsor.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            logger.e("Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Event, Long> getEventDao() throws SQLException {
        if (eventDao == null) {
            eventDao = getDao(Event.class);
        }
        return eventDao;
    }

    public Dao<Article, Long> getArticleDao() throws SQLException {
        if (articleDao == null) {
            articleDao = getDao(Article.class);
        }
        return articleDao;
    }

    public Dao<Sponsor, Long> getSponsorDao() throws SQLException {
        if (sponsorDao == null) {
            sponsorDao = getDao(Sponsor.class);
        }
        return sponsorDao;
    }

    public Dao<Material, Long> getMaterialDao() throws SQLException {
        if (materialDao == null) {
            materialDao = getDao(Material.class);
        }
        return materialDao;
    }

    public Dao<Contact, Long> getContactDao() throws SQLException {
        if (contactDao == null) {
            contactDao = getDao(Contact.class);
        }
        return contactDao;
    }
}
