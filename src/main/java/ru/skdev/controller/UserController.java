package ru.skdev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skdev.model.User;
import ru.skdev.service.SimpleUserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final SimpleUserService service;

    @GetMapping("/register")
    public String getRegisterPage() {
        return "users/register";
    }

    @PostMapping("/register")
    public String saveNewUser(Model model, @ModelAttribute User user) {
        Optional<User> result = service.save(user);
        if (result.isEmpty()) {
            String message = String.format("User with login: %s already exists", user.getLogin());
            model.addAttribute("error", message);
            return "users/register";
        }
        return "users/register";
    }
}
