package ru.job4j.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Item;

import java.util.List;
import java.util.function.Function;

@Repository
public class ItemStore {
    private static final Logger LOG = LoggerFactory.getLogger(ItemStore.class.getName());
    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Item> getAll() {
        return runWithSession(session -> session.createQuery("from Item").list());
    }

    public List<Item> getNew() {
        return runWithSession(
                session -> session.createQuery("from Item i where i.done = false").list()
        );
    }

    public List<Item> getCompleted() {
        return runWithSession(
                session -> session.createQuery("from Item i where i.done = true").list()
        );
    }

    public Item getById(int id) {
        return runWithSession(session -> session.get(Item.class, id));
    }

    public void updateDescription(int id, String description) {
        String sql = "update Item i set i.description = :description where i.id = :id";
        runWithSession(session -> session.createQuery(sql)
                .setParameter("description", description)
                .setParameter("id", id)
                .executeUpdate());
    }

    public void updateDone(int id, boolean done) {
        String sql = "update Item i set i.done = :done where i.id = :id";
        runWithSession(session -> session.createQuery(sql)
                .setParameter("done", done)
                .setParameter("id", id)
                .executeUpdate());
    }

    public void add(Item item) {
        runWithSession(session -> session.save(item));
    }

    public void delete(int id) {
        runWithSession(session -> session.createQuery("delete from Item i WHERE i.id = :id")
                .setParameter("id", id)
                .executeUpdate());
    }

    private <T> T runWithSession(final Function<Session, T> command) {
        final Session session = sf.openSession();
        T rsl = null;
        try (session) {
            session.beginTransaction();
            rsl = command.apply(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
        return rsl;
    }
}
