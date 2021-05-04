package org.web.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web.project.entity.Book;
import org.web.project.entity.CartItem;
import org.web.project.service.BookService;
import org.web.project.service.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CartController {
    private CartService cartService;
    private BookService bookService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/cart")
    public String cart(Authentication authentication, Model model) {
        List<String> bookNames = cartService.getCartItemsByLogin(authentication.getName())
                .stream().map(CartItem::getBookName).collect(Collectors.toList());

        List<Book> books = new ArrayList<>();
        for (String name : bookNames)
            books.add(bookService.getBookByName(name));

        model.addAttribute("login", authentication.getName());
        model.addAttribute("cartItem", books);
        return "cart";
    }

    @GetMapping("/addToCart")
    @ResponseBody
    public boolean addToCart(@RequestParam String bookName, @RequestParam String login) {
        return cartService.addToTheCart(bookName, login);
    }

    @GetMapping("/getNumberOfBooks")
    @ResponseBody
    public int getNumberOfBooks(@RequestParam String login) {
        return cartService.getCartItemsByLogin(login).size();
    }

    @GetMapping("/deleteBookFromTheCart")
    @ResponseBody
    public boolean deleteBook(@RequestParam String bookName, @RequestParam String login) {
        return cartService.deleteFromTheCart(bookName, login);
    }

    @GetMapping("/buy")
    @ResponseBody
    public boolean buy(@RequestParam String login) {
        List<CartItem> cart = cartService.getCartItemsByLogin(login);
        for (CartItem book : cart)
            cartService.deleteFromTheCart(book.getBookName(), login);
        return cart.isEmpty();
    }
}
