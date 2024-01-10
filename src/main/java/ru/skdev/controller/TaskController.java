package ru.skdev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skdev.model.Task;
import ru.skdev.service.TaskService;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "tasks/list";
    }

    @GetMapping("/done")
    public String getDoneTasks(Model model) {
        model.addAttribute("tasks", service.findDoneTasks());
        return "tasks/list";
    }

    @GetMapping("/undone")
    public String getUndoneTasks(Model model) {
        model.addAttribute("tasks", service.findUndoneTasks());
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreatePage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String saveNewTask(@ModelAttribute Task task) {
        service.save(task);
        return "redirect:/tasks";
    }
}
