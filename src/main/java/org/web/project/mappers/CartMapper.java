package org.web.project.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.web.project.entity.CartItem;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CartMapper implements RowMapper<CartItem> {
    @Override
    public CartItem mapRow(ResultSet resultSet, int i) throws SQLException {
        CartItem cart = new CartItem();
        cart.setBookName(resultSet.getString("book_name"));
        cart.setUserLogin(resultSet.getString("user_login"));
        return cart;
    }
}
