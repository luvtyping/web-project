package org.web.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web.project.service.BookService;

@RestController
public class SearchController {
    private BookService bookService;
    private ObjectMapper objectMapper;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/search")
    public String getBooksByFilters(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String author,
                                    @RequestParam(required = false) String genre,
                                    @RequestParam(required = false) String price) {
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
