package ru.job4j.controller;

import org.junit.Test;
import ru.job4j.model.Item;
import ru.job4j.service.TaskService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DetailControlTest {
    @Test
    public void whenSuccess() {
        Item item = new Item("task");
        item.setId(1);
        TaskService taskService = mock(TaskService.class);
        DetailControl detailControl = new DetailControl(taskService);

        detailControl.success(1);

        verify(taskService).success(1);
    }

    @Test
    public void whenDelete() {
        Item item = new Item("task");
        item.setId(1);
        TaskService taskService = mock(TaskService.class);
        DetailControl detailControl = new DetailControl(taskService);

        detailControl.delete(1);

        verify(taskService).delete(1);
    }
}