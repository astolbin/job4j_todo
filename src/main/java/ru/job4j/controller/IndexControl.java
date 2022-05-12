package ru.job4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.service.TaskService;

import java.util.List;

@Controller
public class IndexControl {
    private final TaskService taskService;

    public IndexControl(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/index/{status}")
    public String index(@PathVariable("status") String status, Model model) {
        List<Menu> menus = List.of(
                new Menu("Все", "/index/all"),
                new Menu("Выполненные", "/index/completed"),
                new Menu("Новые", "/index/new")
        );
        menus.forEach(menu -> menu.setCurrent(menu.getPath().contains(status)));

        model.addAttribute("menus", menus);
        model.addAttribute("items", taskService.getWithStatus(status));
        model.addAttribute(
                "actions",
                List.of(new Action("Добавить задание", "/edit", "btn-primary"))
        );
        return "index";
    }

    @GetMapping("/index")
    public String indexDefault(Model model) {
        return index("all", model);
    }
}
