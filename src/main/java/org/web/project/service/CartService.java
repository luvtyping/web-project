package org.web.project.service;

import org.web.project.entity.CartItem;

import java.util.List;

public interface CartService {
    List<CartItem> getCartItemsByLogin(String login);

    boolean addToTheCart(String bookName, String login);

    boolean deleteFromTheCart(String bookName, String login);
}
