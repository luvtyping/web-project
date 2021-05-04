package org.web.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.web.project.service.BookService;

@Controller
public class IndexController {
    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        String role = authentication.getAuthorities().toArray()[0].toString();
        if (role.equals("MANAGER"))
            model.addAttribute("role", "MANAGER");
        model.addAttribute("login", authentication.getName());
        model.addAttribute("books", bookService.getBooks());
        return "index";
    }
}
