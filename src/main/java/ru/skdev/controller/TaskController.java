package ru.skdev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.skdev.model.Task;
import ru.skdev.model.User;
import ru.skdev.service.TaskService;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    @GetMapping
    public String getAllTasks(Model model, @SessionAttribute("user") User user) {
        model.addAttribute("tasks", service.findAll(user));
        return "tasks/list";
    }

    @GetMapping("/done")
    public String getDoneTasks(Model model, @SessionAttribute("user") User user) {
        model.addAttribute("tasks", service.findDoneTasks(user));
        return "tasks/list";
    }

    @GetMapping("/undone")
    public String getUndoneTasks(Model model, @SessionAttribute("user") User user) {
        model.addAttribute("tasks", service.findUndoneTasks(user));
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreatePage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String saveNewTask(@ModelAttribute Task task, @SessionAttribute("user") User user) {
        task.setUser(user);
        service.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}")
    public String getOneTaskPage(Model model, @PathVariable Integer id) {
        Optional<Task> task = service.findById(id);
        if (task.isEmpty()) {
            String message = String.format("Task with id: %s not found", id);
            model.addAttribute("message", message);
            return "errors/404";
        }
        model.addAttribute("task", task.get());
        return "tasks/one";
    }

    @PostMapping("/done/{id}")
    public String markTaskDone(Model model, @PathVariable Integer id) {
        boolean result = service.updateDone(id);
        if (!result) {
            String message = String.format("Task with id: %s not mark done", id);
            model.addAttribute("message", message);
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(Model model, @PathVariable Integer id) {
        boolean result = service.delete(id);
        if (!result) {
            String message = String.format("Task with id: %s not delete", id);
            model.addAttribute("message", message);
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/update/{id}")
    public String getUpdateTaskPage(Model model, @PathVariable Integer id) {
        Optional<Task> task = service.findById(id);
        if (task.isEmpty()) {
            String message = String.format("Task with id: %s not found", id);
            model.addAttribute("message", message);
            return "errors/404";
        }
        model.addAttribute("task", task.get());
        return "tasks/update";
    }

    @PostMapping("/update/{id}")
    public String getUpdateTaskPage(Model model, @ModelAttribute Task task,
                                    @PathVariable Integer id, @SessionAttribute("user") User user) {
        task.setUser(user);
        boolean result = service.update(id, task);
        if (!result) {
            String message = String.format("Task with id: %s not edit", id);
            model.addAttribute("message", message);
            return "errors/404";
        }
        return String.format("redirect:/tasks/%s", id);
    }
}
