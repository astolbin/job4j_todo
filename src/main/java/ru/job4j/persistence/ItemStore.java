package ru.job4j.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Item;

import java.util.List;

@Repository
public class ItemStore {
    private static final Logger LOG = LoggerFactory.getLogger(ItemStore.class.getName());

    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    private List<Item> getByFilter(String filter) {
        final Session session = sf.openSession();
        List result = null;
        try (session) {
            session.beginTransaction();
            result = session.createQuery("from ru.job4j.model.Item" + filter).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
        return result;
    }

    public List<Item> getAll() {
        return getByFilter("");
    }

    public List<Item> getNew() {
        return getByFilter(" where done = false");
    }

    public List<Item> getCompleted() {
        return getByFilter(" where done = true");
    }

    public Item getById(int id) {
        final Session session = sf.openSession();
        Item result = null;
        try (session) {
            session.beginTransaction();
            result = session.get(Item.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
        return result;
    }

    public void updateDescription(int id, String description) {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            String sql = "update Item i set i.description = :description where i.id = :id";
            session.createQuery(sql)
                    .setParameter("description", description)
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
    }

    public void updateDone(int id, boolean done) {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            String sql = "update Item i set i.done = :done where i.id = :id";
            session.createQuery(sql)
                    .setParameter("done", done)
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
    }

    public void add(Item item) {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
    }

    public void delete(int id) {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            Item item = new Item();
            item.setId(id);
            session.delete(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
    }
}
