package org.web.project.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.web.project.dao.CartDAOImpl;
import org.web.project.entity.Book;
import org.web.project.entity.CartItem;
import org.web.project.entity.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class CartServiceImplTest {

    @TestConfiguration
    static class CartServiceImplTestContextConfiguration {
        @Bean
        public CartService cartService() {
            return new CartServiceImpl();
        }
    }

    @Autowired
    private CartService cartService;

    @MockBean
    private CartDAOImpl cartDAO;

    private CartItem item1;
    private CartItem item2;
    private CartItem item3;

    private User user;

    private Book bookOne;
    private Book bookTwo;
    private Book bookThree;

    @Before
    public void setUp() {
        user = new User("loginOne", "passwordOne", "UserOne", "USER", LocalDate.of(2000, 1, 1));

        bookOne = new Book("NameOne", "AuthorOne", 2001, 100, "DescriptionOne", "Детектив");
        bookTwo = new Book("NameTwo", "AuthorTwo", 2002, 200, "DescriptionTwo", "Драма");
        bookThree = new Book("NameThree", "AuthorThree", 2003, 300, "DescriptionThree", "Роман");

        item1 = new CartItem(user.getLogin(), bookOne.getName());
        item2 = new CartItem(user.getLogin(), bookTwo.getName());
        item3 = new CartItem(user.getLogin(), bookThree.getName());

        Mockito.when(cartDAO.getCartItemsByLogin(user.getLogin())).thenReturn(Arrays.asList(item1, item2, item3));

        Mockito.when(cartDAO.addToTheCart(bookOne.getName(), user.getLogin())).thenReturn(true);

        Mockito.when(cartDAO.deleteFromTheCart(bookOne.getName(), user.getLogin())).thenReturn(true);
        Mockito.when(cartDAO.deleteFromTheCart(bookTwo.getName(), user.getLogin())).thenReturn(true);
        Mockito.when(cartDAO.deleteFromTheCart(bookThree.getName(), user.getLogin())).thenReturn(true);
    }

    @Test
    public void getCartItemsByLogin() {
        List<CartItem> cartItems = cartService.getCartItemsByLogin(user.getLogin());
        assertEquals(3, cartItems.size());
        assertTrue(cartItems.containsAll(Arrays.asList(item1, item2, item3)));
    }

    @Test
    public void addToTheCart() {
        assertTrue(cartService.addToTheCart(bookOne.getName(), user.getLogin()));
    }

    @Test
    public void deleteFromTheCart() {
        assertTrue(cartService.deleteFromTheCart(bookOne.getName(), user.getLogin()));
        assertTrue(cartService.deleteFromTheCart(bookTwo.getName(), user.getLogin()));
        assertTrue(cartService.deleteFromTheCart(bookThree.getName(), user.getLogin()));
    }
}