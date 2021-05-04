package org.web.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.project.dao.CartDAO;
import org.web.project.entity.CartItem;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private CartDAO cartDAO;

    @Autowired
    public void setCartDAO(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @Override
    public List<CartItem> getCartItemsByLogin(String login) {
        return cartDAO.getCartItemsByLogin(login);
    }

    @Override
    public boolean addToTheCart(String bookName, String login) {
        return cartDAO.addToTheCart(bookName, login);
    }

    @Override
    public boolean deleteFromTheCart(String bookName, String login) {
        return cartDAO.deleteFromTheCart(bookName, login);
    }
}
