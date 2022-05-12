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
public class DetailControl {
    private final TaskService taskService;

    public DetailControl(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        Item item = taskService.get(id);

        List<Action> actions;
        if (item.isDone()) {
            actions = List.of(new Action("Удалить", "/delete", "btn-danger"));
        } else {
            actions = List.of(
                    new Action("Выполнено", "/success", "btn-success"),
                    new Action("Отредактировать", "/to-edit", "btn-primary"),
                    new Action("Удалить", "/delete", "btn-danger")
            );
        }

        model.addAttribute("menus", List.of(new Menu("Назад", "/index")));
        model.addAttribute("actions", actions);
        model.addAttribute("item", item);

        return "detail";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("id") int id) {
        taskService.delete(id);
        return "redirect:/index";
    }

    @PostMapping("/success")
    public String success(@ModelAttribute("id") int id) {
        taskService.success(id);
        return "redirect:/index";
    }

    @PostMapping("/to-edit")
    public String toEdit(@ModelAttribute("id") int id) {
        return "redirect:/edit/" + id;
    }
}
