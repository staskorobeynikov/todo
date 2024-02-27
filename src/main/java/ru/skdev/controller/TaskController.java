package ru.skdev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.skdev.model.Task;
import ru.skdev.model.User;
import ru.skdev.service.category.CategoryService;
import ru.skdev.service.priority.PriorityService;
import ru.skdev.service.task.TaskService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping
    public String getAllTasks(Model model, @SessionAttribute("user") User user) {
        model.addAttribute("tasks", taskService.findAll(user));
        return "tasks/list";
    }

    @GetMapping("/done")
    public String getDoneTasks(Model model, @SessionAttribute("user") User user) {
        model.addAttribute("tasks", taskService.findDoneTasks(user));
        return "tasks/list";
    }

    @GetMapping("/undone")
    public String getUndoneTasks(Model model, @SessionAttribute("user") User user) {
        model.addAttribute("tasks", taskService.findUndoneTasks(user));
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String saveNewTask(@ModelAttribute Task task,
                              @SessionAttribute("user") User user,
                              @RequestParam List<Integer> categoryIds) {
        task.setUser(user);
        task.setCategories(categoryService.findByIds(categoryIds));
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}")
    public String getOneTaskPage(Model model, @PathVariable Integer id) {
        if (findTaskById(model, id)) {
            return "errors/404";
        }
        return "tasks/one";
    }

    @PostMapping("/done/{id}")
    public String markTaskDone(Model model, @PathVariable Integer id) {
        boolean result = taskService.updateDone(id);
        if (!result) {
            String message = String.format("Task with id: %s not mark done", id);
            model.addAttribute("message", message);
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(Model model, @PathVariable Integer id) {
        boolean result = taskService.delete(id);
        if (!result) {
            String message = String.format("Task with id: %s not delete", id);
            model.addAttribute("message", message);
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/update/{id}")
    public String getUpdateTaskPage(Model model, @PathVariable Integer id) {
        if (findTaskById(model, id)) {
            return "errors/404";
        }
        return "tasks/update";
    }

    @PostMapping("/update/{id}")
    public String getUpdateTaskPage(Model model, @ModelAttribute Task task,
                                    @PathVariable Integer id, @SessionAttribute("user") User user) {
        task.setUser(user);
        boolean result = taskService.update(id, task);
        if (!result) {
            String message = String.format("Task with id: %s not edit", id);
            model.addAttribute("message", message);
            return "errors/404";
        }
        return String.format("redirect:/tasks/%s", id);
    }

    private boolean findTaskById(Model model, @PathVariable Integer id) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            String message = String.format("Task with id: %s not found", id);
            model.addAttribute("message", message);
            return true;
        }
        model.addAttribute("task", task.get());
        return false;
    }
}
