package ru.job4j.tools;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTools {
    private static final Logger LOG = LoggerFactory.getLogger(TestTools.class.getName());

    private final SessionFactory sf;

    public TestTools() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
    }

    private static final class Lazy {
        private static final TestTools INST = new TestTools();
    }

    public static TestTools instOf() {
        return TestTools.Lazy.INST;
    }

    public void clearTables() {
        Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM items").executeUpdate();
            session.createSQLQuery("ALTER TABLE items ALTER COLUMN id RESTART WITH 1")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
    }

    public SessionFactory getSf() {
        return sf;
    }
}
