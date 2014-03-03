package lv.oug.android.domain;

import android.content.Context;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lv.oug.android.infrastructure.common.ClassLogger;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class EventRepository {

    public static final String EVENTS_TIMESTAMP = "EVENTS_TIMESTAMP";

    private static ClassLogger logger = new ClassLogger(EventRepository.class);

    @Inject
    Context context;

    @Inject
    DatabaseHelper db;

    public void saveOrUpdate(List<Event> list) {
        try {
            for (Event event : list) {
                getEventDao().createOrUpdate(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearEvents() {
        try {
            ConnectionSource source = db.getConnectionSource();
            TableUtils.clearTable(source, Event.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Event, Integer> getEventDao() throws SQLException {
        return db.getEventDao();
    }

    public AndroidDatabaseResults getRawResults() {
        try {
            QueryBuilder<Event, Integer> queryBuilder = getEventDao().queryBuilder();
            queryBuilder.orderBy("createdAt", false);
            PreparedQuery<Event> query = queryBuilder.prepare();
            return (AndroidDatabaseResults) getEventDao().iterator(query).getRawResults();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
