package org.web.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web.project.service.BookService;

@RestController
public class BookSearchController {
    @Autowired
    private BookService bookService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/search")
    public String getBooksByFilters(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "author", required = false) String author,
                                    @RequestParam(value = "genre", required = false) String genre,
                                    @RequestParam(value = "price", required = false) String price) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(
                    bookService.getBooksBySearchFilters(name, author, price, genre));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
