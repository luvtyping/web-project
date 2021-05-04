package org.web.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.web.project.entity.Book;
import org.web.project.service.BookService;

import javax.validation.Valid;

@Controller
public class BookManagingController {
    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/createBook")
    public String createBook(@RequestParam(required = false) String error, Authentication authentication, Model model) {
        if (error != null) {
            if (error.equals("bookAlreadyExists"))
                model.addAttribute("errorMessage", "Книга с таким названием уже существует");
        }
        model.addAttribute("login", authentication.getName());
        model.addAttribute("book", new Book());
        return "createBook";
    }

    @PostMapping("/createBook/proceed")
    public String createBookProcess(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/createBook";

        if (bookService.getBookByName(book.getName()) != null)
            return "redirect:/createBook?error=bookAlreadyExists";

        bookService.addBook(book);
        return "redirect:/";
    }

    @GetMapping("/deleteBook")
    @ResponseBody
    public boolean deleteBook(@RequestParam String bookName) {
        return bookService.deleteBook(bookName);
    }
}
