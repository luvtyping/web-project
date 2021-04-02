package org.web.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.project.dao.CartDAO;
import org.web.project.entity.Cart;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDAO cartDAO;

    @Override
    public List<Cart> getTheCartByLogin(String login) {
        return cartDAO.getTheCartByLogin(login);
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
