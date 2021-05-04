package org.web.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web.project.service.BookService;

@RestController
public class PopupController {
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

    @GetMapping("/popup")
    public String getBookPopup(@RequestParam String name) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(bookService.getBookByName(name));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
