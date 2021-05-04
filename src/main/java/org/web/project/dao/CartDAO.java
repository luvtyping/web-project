package org.web.project.dao;

import org.web.project.entity.CartItem;

import java.util.List;

public interface CartDAO {
    List<CartItem> getCartItemsByLogin(String login);

    boolean addToTheCart(String bookName, String login);

    boolean deleteFromTheCart(String bookName, String login);
}
