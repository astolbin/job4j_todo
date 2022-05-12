package ru.job4j.persistence;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.model.Item;
import ru.job4j.tools.TestTools;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class ItemStoreTest {
    private final SessionFactory sf = TestTools.instOf().getSf();

    @Before
    public void setUp() {
        TestTools.instOf().clearTables();
    }

    @Test
    public void whenGetAll() {
        ItemStore itemStore = new ItemStore(sf);

        itemStore.add(new Item("task", LocalDateTime.now(), false));
        itemStore.add(new Item("task", LocalDateTime.now(), true));

        assertEquals(2, itemStore.getAll().size());
    }

    @Test
    public void whenGetCompleted() {
        ItemStore itemStore = new ItemStore(sf);

        itemStore.add(new Item("task", LocalDateTime.now(), false));
        itemStore.add(new Item("task", LocalDateTime.now(), true));

        List<Item> rsl = itemStore.getCompleted();

        assertEquals(1, rsl.size());
        assertTrue(rsl.get(0).isDone());
    }

    @Test
    public void whenGetNew() {
        ItemStore itemStore = new ItemStore(sf);

        itemStore.add(new Item("task", LocalDateTime.now(), false));
        itemStore.add(new Item("task", LocalDateTime.now(), true));

        List<Item> rsl = itemStore.getNew();

        assertEquals(1, rsl.size());
        assertFalse(rsl.get(0).isDone());
    }

    @Test
    public void whenUpdate() {
        ItemStore itemStore = new ItemStore(sf);

        Item item = new Item("task", LocalDateTime.now(), false);
        itemStore.add(item);

        item.setDescription("task updated");
        itemStore.update(item.getId(), item);

        assertEquals("task updated", itemStore.getById(item.getId()).getDescription());
    }

    @Test
    public void whenDelete() {
        ItemStore itemStore = new ItemStore(sf);

        Item item = new Item("task", LocalDateTime.now(), false);
        itemStore.add(item);
        itemStore.delete(item.getId());

        assertEquals(0, itemStore.getAll().size());
    }
}