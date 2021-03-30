package org.web.bookShop.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.web.bookShop.entity.Cart;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartMapper implements RowMapper<Cart> {
    @Override
    public Cart mapRow(ResultSet resultSet, int i) throws SQLException {
        Cart cart = new Cart();
        cart.setBookName(resultSet.getString("book_name"));
        cart.setUserLogin(resultSet.getString("user_login"));
        return cart;
    }
}
