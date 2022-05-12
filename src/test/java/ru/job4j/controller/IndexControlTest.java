package ru.job4j.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.model.Item;
import ru.job4j.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

public class IndexControlTest {

    @Test
    public void whenGetAll() {
        List<Item> itemList = List.of(
                new Item("task 1", LocalDateTime.now(), false),
                new Item("task 2", LocalDateTime.now(), true)
        );

        Model model = mock(Model.class);
        TaskService taskService = mock(TaskService.class);

        when(taskService.getWithStatus("all")).thenReturn(itemList);
        IndexControl indexControl = new IndexControl(taskService);
        indexControl.index("all", model);

        verify(model).addAttribute("items", itemList);
    }

    @Test
    public void whenGetCompleted() {
        List<Item> itemList = List.of(
                new Item("task 1", LocalDateTime.now(), true)
        );

        Model model = mock(Model.class);
        TaskService taskService = mock(TaskService.class);

        when(taskService.getWithStatus("completed")).thenReturn(itemList);
        IndexControl indexControl = new IndexControl(taskService);
        indexControl.index("completed", model);

        verify(model).addAttribute("items", itemList);
    }

    @Test
    public void whenGetNew() {
        List<Item> itemList = List.of(
                new Item("task 1", LocalDateTime.now(), false)
        );

        Model model = mock(Model.class);
        TaskService taskService = mock(TaskService.class);

        when(taskService.getWithStatus("new")).thenReturn(itemList);
        IndexControl indexControl = new IndexControl(taskService);
        indexControl.index("new", model);

        verify(model).addAttribute("items", itemList);
    }
}