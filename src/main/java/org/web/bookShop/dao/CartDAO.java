package org.web.bookShop.dao;

import org.web.bookShop.entity.Cart;

import java.util.List;

public interface CartDAO {
    // TODO: 30.03.2021 delete???
//    List<BookInCart> getBooksInTheCart();

    List<Cart> getTheCartByLogin(String login);

    boolean addToTheCart(String bookName, String login);

    boolean deleteFromTheCart(String bookName, String login);
}
