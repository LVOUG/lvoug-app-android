package lv.oug.android.domain;

import android.content.Context;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.Dao;
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

    @Deprecated
    public List<Event> loadByRoom(int id) {
//        try {
//            Dao<Event, Integer> dao = db.getEventDao();
//            QueryBuilder<Event, Integer> builder = dao.queryBuilder();
//            builder.where().eq(Event.ROOM_ID_ID, id);
//            PreparedQuery<Event> preparedQuery = builder.prepare();
//
//            return dao.query(preparedQuery);
//        } catch (SQLException e){
//            logger.e(e.getLocalizedMessage());
//        }

        return null;
    }

    public void saveEvents(List<Event> list) {
        try {
            for (Event event : list) {
                getEventDao().create(event);
            }
        } catch (SQLException e) {
            logger.e(e.getLocalizedMessage());
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
            return (AndroidDatabaseResults) getEventDao().iterator().getRawResults();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
