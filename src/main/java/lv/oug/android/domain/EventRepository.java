package lv.oug.android.domain;

import android.content.Context;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lv.oug.android.infrastructure.common.ClassLogger;
import lv.oug.android.infrastructure.common.DateService;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class EventRepository {

    public static final String EVENTS_TIMESTAMP = "EVENTS_TIMESTAMP";

    private static ClassLogger logger = new ClassLogger(EventRepository.class);

    @Inject
    Context context;

    @Inject
    DatabaseHelper db;

    @Inject
    DateService dateService;

    public void saveOrUpdate(List<Event> list) {
        try {
            for (Event event : list) {
                getEventDao().createOrUpdate(event);

                for (Contact contact : event.getContacts()) {
                    db.getContactDao().create(contact);
                }

                for (Material material : event.getMaterials()) {
                    db.getMaterialDao().create(material);
                }

                for (Sponsor sponsor : event.getSponsors()) {
                    db.getSponsorDao().create(sponsor);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Event createEmpty() {
        Event event = new Event();
        try {
            event.setContacts(getEventDao().<Contact>getEmptyForeignCollection("contacts"));
            event.setMaterials(getEventDao().<Material>getEmptyForeignCollection("materials"));
            event.setSponsors(getEventDao().<Sponsor>getEmptyForeignCollection("sponsors"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    public void clearEvents() {
        try {
            ConnectionSource source = db.getConnectionSource();
            TableUtils.clearTable(source, Event.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearForeignCollections(Event event) {
        event.getContacts().clear();
        event.getSponsors().clear();
        event.getMaterials().clear();
        try {
            db.getEventDao().update(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Event, Long> getEventDao() throws SQLException {
        return db.getEventDao();
    }

    public AndroidDatabaseResults getRawResults() {
        try {
            QueryBuilder<Event, Long> queryBuilder = getEventDao().queryBuilder();
            queryBuilder.orderBy("createdAt", false);
            PreparedQuery<Event> query = queryBuilder.prepare();
            return (AndroidDatabaseResults) getEventDao().iterator(query).getRawResults();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Event loadNextUpcomingEvent() {
        try {
            Date now = dateService.currentDate();
            QueryBuilder<Event, Long> queryBuilder = getEventDao().queryBuilder();
            queryBuilder.where().ge("eventDate", now);
            QueryBuilder<Event, Long> query = queryBuilder.orderBy("eventDate", true);
            return getEventDao().queryForFirst(query.prepare());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteForeignCollections(List<Long> ids) {
        deleteContactsForEvents(ids);
        deleteSponsorsForEvents(ids);
        deleteMaterialsForEvents(ids);
    }

    private void deleteContactsForEvents(List<Long> ids) {
        try {
            Dao<Contact, Long> dao = db.getContactDao();
            DeleteBuilder<Contact,Long> builder = dao.deleteBuilder();
            builder.where().in(Contact.EVENT_ID, ids);
            dao.delete(builder.prepare());
        } catch (Exception e) {
            logger.e("Failed to delete Contacts", e);
        }
    }

    private void deleteSponsorsForEvents(List<Long> ids) {
        try {
            Dao<Sponsor, Long> dao = db.getSponsorDao();
            DeleteBuilder<Sponsor,Long> builder = dao.deleteBuilder();
            builder.where().in(Sponsor.EVENT_ID, ids);
            dao.delete(builder.prepare());
        } catch (Exception e) {
            logger.e("Failed to delete Sponsors", e);
        }
    }

    private void deleteMaterialsForEvents(List<Long> ids) {
        try {
            Dao<Material, Long> dao = db.getMaterialDao();
            DeleteBuilder<Material,Long> builder = dao.deleteBuilder();
            builder.where().in(Material.EVENT_ID, ids);
            dao.delete(builder.prepare());
        } catch (Exception e) {
            logger.e("Failed to delete Materials", e);
        }
    }
}
