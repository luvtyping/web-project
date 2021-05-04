package org.web.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.project.entity.User;
import org.web.project.service.UserService;

import javax.validation.Valid;

@Controller
public class AuthenticationController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null)
            model.addAttribute("errorMessage", "Неверный логин или пароль");
        return "login";
    }

    @GetMapping("/registration")
    public String registration(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            if (error.equals("userAlreadyExists"))
                model.addAttribute("errorMessage", "Логин занят другим пользователем");
        }
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration/proceed")
    public String processRegistration(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "registration";

        if (userService.getUserByLogin(user.getLogin()) != null)
            return "redirect:/registration?error=userAlreadyExists";

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("user", null);
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
