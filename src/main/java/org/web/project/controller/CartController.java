package org.web.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web.project.entity.Cart;
import org.web.project.service.CartService;

import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/addToCart")
    public boolean addToCart(@RequestParam String bookName, @RequestParam String login) {
        return cartService.addToTheCart(bookName, login);
    }

    @GetMapping("/getNumberOfBooks")
    public int getNumberOfBooks(@RequestParam String login) {
        return cartService.getTheCartByLogin(login).size();
    }

    @GetMapping("/deleteBook")
    public boolean deleteBook(@RequestParam String bookName, @RequestParam String login) {
        return cartService.deleteFromTheCart(bookName, login);
    }

    @GetMapping("/buy")
    public boolean buy(@RequestParam String login) {
        List<Cart> cart = cartService.getTheCartByLogin(login);
        for (Cart book : cart) {
            cartService.deleteFromTheCart(book.getBookName(), login);
        }
        return cart.isEmpty();
    }
}
