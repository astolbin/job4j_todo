package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Item;
import ru.job4j.persistence.ItemStore;

import java.util.List;

@Service
public class TaskService {
    private final ItemStore itemStore;

    public TaskService(ItemStore store) {
        this.itemStore = store;
    }

    public void success(int taskId) {
        Item item = itemStore.getById(taskId);
        if (item != null) {
            item.setDone(true);
            itemStore.update(item.getId(), item);
        }
    }

    public void set(int taskId, String description) {
        Item item = itemStore.getById(taskId);
        if (item == null) {
            itemStore.add(new Item(description));
        } else {
            item.setDescription(description);
            itemStore.update(taskId, item);
        }
    }

    public void delete(int taskId) {
        itemStore.delete(taskId);
    }

    public Item get(int taskId) {
        return itemStore.getById(taskId);
    }

    public List<Item> getWithStatus(String status) {
        return switch (status) {
            case "completed" -> itemStore.getCompleted();
            case "new" -> itemStore.getNew();
            case "all" -> itemStore.getAll();
            default -> throw new IllegalArgumentException("Wrong status code.");
        };
    }
}
