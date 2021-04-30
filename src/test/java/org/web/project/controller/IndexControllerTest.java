package org.web.project.controller;

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

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(IndexController.class)
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private Book bookOne;
    private Book bookTwo;
    private Book bookThree;

    @Before
    public void setUp() throws Exception {
        bookOne = new Book("NameOne", "AuthorOne", 2001, 100, "DescriptionOne", "Детектив");
        bookTwo = new Book("NameTwo", "AuthorTwo", 2002, 200, "DescriptionTwo", "Драма");
        bookThree = new Book("NameThree", "AuthorThree", 2003, 300, "DescriptionThree", "Роман");

        given(bookService.getBooks()).willReturn(Arrays.asList(bookOne, bookTwo, bookThree));
    }

    @Test
    public void givenRequestOnPrivateService_shouldFailWith302() throws Exception {
        mockMvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mockMvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "manager", authorities = "MANAGER")
    public void indexPageForManager() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("login", "manager"))
                .andExpect(model().attribute("books", hasSize(3)))
                .andExpect(model().attribute("role", is("MANAGER")))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("name", is(bookOne.getName())),
                                hasProperty("description", is(bookOne.getDescription()))
                        )
                )))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("name", is(bookTwo.getName())),
                                hasProperty("description", is(bookTwo.getDescription()))
                        )
                )))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("name", is(bookThree.getName())),
                                hasProperty("description", is(bookThree.getDescription()))
                        )
                )));
    }

    @Test
    @WithMockUser
    public void indexPageForUser() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("login", "user"))
                .andExpect(model().attribute("books", hasSize(3)))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("name", is(bookOne.getName())),
                                hasProperty("description", is(bookOne.getDescription()))
                        )
                )))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("name", is(bookTwo.getName())),
                                hasProperty("description", is(bookTwo.getDescription()))
                        )
                )))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("name", is(bookThree.getName())),
                                hasProperty("description", is(bookThree.getDescription()))
                        )
                )));
    }
}