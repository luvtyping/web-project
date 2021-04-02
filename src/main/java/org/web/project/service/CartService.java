package org.web.project.service;

import org.web.project.entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getTheCartByLogin(String login);

    boolean addToTheCart(String bookName, String login);

    boolean deleteFromTheCart(String bookName, String login);
}
