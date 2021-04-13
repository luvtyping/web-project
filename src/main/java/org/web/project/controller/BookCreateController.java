package org.web.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.project.entity.Book;
import org.web.project.service.BookService;
import org.web.project.service.UserService;

import javax.validation.Valid;

@Controller
public class BookCreateController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @GetMapping("/createBook")
    public String createBook(@RequestParam(required = false) String error, Authentication authentication, Model model) {
        model.addAttribute("name", authentication.getName());
        model.addAttribute("book", new Book());
        if (error != null) {
            if (error.equals("bookAlreadyExists"))
                model.addAttribute("errorMessage", "Книга с таким названием уже существует");
        }
        return "createBook";
    }

    @PostMapping("/createBook/proceed")
    public String createBookProcess(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/createBook";

        if (bookService.getBookByName(book.getName()).getName() != null)
            return "redirect:/createBook?error=bookAlreadyExists";

        bookService.addBook(book);
        return "redirect:/";
    }
}
