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
        itemStore.updateDone(taskId, true);
    }

    public void set(int taskId, String description) {
        if (taskId > 0) {
            itemStore.updateDescription(taskId, description);
        } else {
            itemStore.add(new Item(description));
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
