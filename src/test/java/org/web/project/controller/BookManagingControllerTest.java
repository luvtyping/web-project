package org.web.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.web.project.entity.Book;
import org.web.project.service.BookService;
import org.web.project.service.UserDetailsServiceImpl;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookManagingController.class)
public class BookManagingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book newBook;

    @Before
    public void setUp() throws Exception {
        newBook = new Book("newBook", "newAuthor", 2021, 999, "new description", "Научная фантастика");

        given(bookService.deleteBook(newBook.getName())).willReturn(true);

        given(bookService.addBook(newBook)).willReturn(true);

        given(bookService.getBooks()).willReturn(Collections.singletonList(newBook));

        given(bookService.getBookByName(newBook.getName())).willReturn(newBook);

    }

    @Test
    public void givenRequestOnPrivateService_shouldFailWith302() throws Exception {
        mockMvc.perform(get("/createBook")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mockMvc.perform(get("/createBook")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void createBookPage_NoError() throws Exception {
        mockMvc.perform(get("/createBook"))
                .andExpect(status().isOk())
                .andExpect(view().name("createBook"))
                .andExpect(model().attribute("login", "user"))
                .andExpect(model().attribute("book", is(notNullValue())));
    }

    @Test
    @WithMockUser
    public void createBookPage_Error() throws Exception {
        mockMvc.perform(get("/createBook")
                .param("error", "bookAlreadyExists"))
                .andExpect(status().isOk())
                .andExpect(view().name("createBook"))
                .andExpect(model().attribute("login", "user"))
                .andExpect(model().attribute("book", is(notNullValue())))
                .andExpect(model().attribute("errorMessage", "Книга с таким названием уже существует"));
    }

    @Test
    @WithMockUser
    public void createBookProcess() throws Exception {
        mockMvc.perform(post("/createBook/proceed")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @WithMockUser
    public void deleteBook() throws Exception {
        mockMvc.perform(get("/deleteBook")
                .param("bookName", newBook.getName()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}