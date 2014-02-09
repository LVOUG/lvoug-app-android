package lv.oug.android.domain;

import lv.oug.android.infrastructure.common.ClassLogger;

import javax.inject.Inject;
import java.util.List;

public class EventRepository {

    private static ClassLogger logger = new ClassLogger(EventRepository.class);

    @Inject
    DatabaseHelper db;

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
}
