package org.web.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.web.project.service.BookService;

@Controller
public class BookSearchController {
    @Autowired
    private BookService bookService;
}
