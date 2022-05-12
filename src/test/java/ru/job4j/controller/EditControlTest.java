package ru.job4j.controller;

import org.junit.Test;
import ru.job4j.service.TaskService;

import static org.mockito.Mockito.*;

public class EditControlTest {
    @Test
    public void whenSave() {
        TaskService taskService = mock(TaskService.class);
        EditControl editControl = new EditControl(taskService);

        editControl.save(1, "task");

        verify(taskService).set(1, "task");
    }
}