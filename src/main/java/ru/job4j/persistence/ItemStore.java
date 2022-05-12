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

    public boolean update(int id, Item item) {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            item.setId(id);
            session.update(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
            return false;
        }
        return true;
    }

    public boolean add(Item item) {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
            return false;
        }
        return true;
    }

    public boolean delete(int id) {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            Item item = new Item(null);
            item.setId(id);
            session.delete(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
            return false;
        }
        return true;
    }
}
