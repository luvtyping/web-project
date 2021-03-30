package org.web.bookShop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.web.bookShop.entity.Cart;
import org.web.bookShop.mappers.CartMapper;

import java.util.List;

@Repository
public class CartDAOImpl implements CartDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> getTheCartByLogin(String login) {
        return jdbcTemplate.query(
                "SELECT * FROM cart WHERE user_login=?", cartMapper, login);
    }

    @Override
    public boolean addToTheCart(String bookName, String login) {
        int updateCart = jdbcTemplate.update(
                "INSERT INTO cart VALUES(?,?)", cartMapper, login, bookName);
        return updateCart != 0;
    }

    @Override
    public boolean deleteFromTheCart(String bookName, String login) {
        return false;
    }
}
