package org.web.project.controller;

import org.junit.Before;
import org.junit.Ignore;
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
import org.web.project.entity.CartItem;
import org.web.project.service.BookService;
import org.web.project.service.CartService;
import org.web.project.service.UserDetailsServiceImpl;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

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

        CartItem itemOne = new CartItem("user", bookOne.getName());
        CartItem itemTwo = new CartItem("user", bookTwo.getName());
        CartItem itemThree = new CartItem("user", bookThree.getName());

        given(cartService.getCartItemsByLogin("user")).willReturn(Arrays.asList(itemOne, itemTwo, itemThree));

        given(bookService.getBookByName(bookOne.getName())).willReturn(bookOne);
        given(bookService.getBookByName(bookTwo.getName())).willReturn(bookTwo);
        given(bookService.getBookByName(bookThree.getName())).willReturn(bookThree);

        given(cartService.addToTheCart(bookOne.getName(), "user")).willReturn(true);
        given(cartService.addToTheCart(bookTwo.getName(), "user")).willReturn(true);
        given(cartService.addToTheCart(bookThree.getName(), "user")).willReturn(true);

        given(cartService.deleteFromTheCart(bookOne.getName(), "user")).willReturn(true);
        given(cartService.deleteFromTheCart(bookTwo.getName(), "user")).willReturn(true);
        given(cartService.deleteFromTheCart(bookThree.getName(), "user")).willReturn(true);
    }

    @Test
    public void givenRequestOnPrivateService_shouldFailWith302() throws Exception {
        mockMvc.perform(get("/cart")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mockMvc.perform(get("/cart")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void cartPage() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attribute("login", "user"))
                .andExpect(model().attribute("cartItem", hasSize(3)))
                .andExpect(model().attribute("cartItem", hasItem(
                        allOf(
                                hasProperty("name", is(bookOne.getName()))
                        )
                )))
                .andExpect(model().attribute("cartItem", hasItem(
                        allOf(
                                hasProperty("name", is(bookTwo.getName()))
                        )
                )))
                .andExpect(model().attribute("cartItem", hasItem(
                        allOf(
                                hasProperty("name", is(bookThree.getName()))
                        )
                )));
    }

    @Test
    @WithMockUser
    public void addToCart() throws Exception {
        mockMvc.perform(get("/addToCart")
                .param("bookName", bookOne.getName())
                .param("login", "user"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @WithMockUser
    public void getNumberOfBooks() throws Exception {
        mockMvc.perform(get("/getNumberOfBooks")
                .param("login", "user"))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }

    @Test
    @WithMockUser
    public void deleteBook() throws Exception {
        mockMvc.perform(get("/deleteBookFromTheCart")
                .param("bookName", bookThree.getName())
                .param("login", "user"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @WithMockUser
    @Ignore
    public void buy() throws Exception {
        mockMvc.perform(get("/buy")
                .param("login", "user"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}