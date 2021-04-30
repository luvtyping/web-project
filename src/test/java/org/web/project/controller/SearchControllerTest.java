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
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private Book bookOne;
    private Book bookTwo;
    private Book bookThree;
    private Book bookFour;

    @Before
    public void setUp() throws Exception {
        bookOne = new Book("NameOne", "AuthorOne", 2001, 100, "DescriptionOne", "Детектив");
        bookTwo = new Book("NameTwo", "AuthorTwo", 2002, 200, "DescriptionTwo", "Драма");
        bookThree = new Book("NameThree", "AuthorThree", 2003, 300, "DescriptionThree", "Роман");
        bookFour = new Book("NameFour", "AuthorThree", 2004, 400, "DescriptionFour", "Драма");

        given(bookService.getBooksBySearchFilters("", "", "", "Драма")).willReturn(Arrays.asList(bookTwo, bookFour));
        given(bookService.getBooksBySearchFilters("", "AuthorThree", "350", "")).willReturn(Collections.singletonList(bookThree));
        given(bookService.getBooksBySearchFilters("unknown", "", "", "")).willReturn(Collections.emptyList());
        given(bookService.getBooksBySearchFilters("", "", "", "")).willReturn(Arrays.asList(bookOne, bookTwo, bookThree, bookFour));
    }

    @Test
    public void givenRequestOnPrivateService_shouldFailWith302() throws Exception {
        mockMvc.perform(get("/search")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mockMvc.perform(get("/search")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void whenGetBooksByFiltersReturnJson_EmptyParams() throws Exception {
        mockMvc.perform(get("/search")
                .param("name", "")
                .param("author", "")
                .param("genre", "")
                .param("price", "")

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].price", is(bookOne.getPrice())))
                .andExpect(jsonPath("$[1].genre", is(bookTwo.getGenre())))
                .andExpect(jsonPath("$[2].author", is(bookThree.getAuthor())))
                .andExpect(jsonPath("$[3].name", is(bookFour.getName())));
    }

    @Test
    @WithMockUser
    public void whenGetBooksByFiltersReturnJsonUnknown_NameParam() throws Exception {
        mockMvc.perform(get("/search")
                .param("name", "unknown")
                .param("author", "")
                .param("genre", "")
                .param("price", "")

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void whenGetBooksByFiltersReturnJson_GenreParam() throws Exception {
        mockMvc.perform(get("/search")
                .param("name", "")
                .param("author", "")
                .param("genre", "Драма")
                .param("price", "")

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].genre", is(bookTwo.getGenre())))
                .andExpect(jsonPath("$[1].genre", is(bookFour.getGenre())));
    }

    @Test
    @WithMockUser
    public void whenGetBooksByFiltersReturnJson_AuthorAndPriceParam() throws Exception {
        mockMvc.perform(get("/search")
                .param("name", "")
                .param("author", "AuthorThree")
                .param("genre", "")
                .param("price", "350")

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].price", is(bookThree.getPrice())));
    }
}