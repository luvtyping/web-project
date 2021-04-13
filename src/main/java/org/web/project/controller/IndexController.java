package org.web.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.web.project.dao.CartDAO;
import org.web.project.service.BookService;

@Controller
public class IndexController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CartDAO cartDAO;

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        model.addAttribute("name", authentication.getName());
        model.addAttribute("books", bookService.getBooks());

        String role = authentication.getAuthorities().toArray()[0].toString();
        if (role.equals("MANAGER"))
            model.addAttribute("manager", "MANAGER");

        return "index";
    }
}
