package ru.job4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.model.Item;
import ru.job4j.service.TaskService;

import java.util.List;

@Controller
public class EditControl {
    private final TaskService taskService;

    public EditControl(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/edit")
    public String add(Model model) {
        model.addAttribute("actions", List.of(new Action("Сохранить", "", "btn-primary")));
        model.addAttribute("menus", List.of(new Menu("Отменить", "/index")));
        model.addAttribute("item", new Item(null));

        return "edit";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("actions", List.of(new Action("Сохранить", "", "btn-primary")));
        model.addAttribute("menus", List.of(new Menu("Отменить", "/detail/" + id)));
        model.addAttribute("item", taskService.get(id));

        return "edit";
    }

    @PostMapping("/save")
    public String save(
            @ModelAttribute("id") int id,
            @ModelAttribute("description") String description
    ) {
        taskService.set(id, description);
        return "redirect:/index";
    }
}
