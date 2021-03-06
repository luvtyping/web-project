package org.web.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.web.project.entity.CartItem;
import org.web.project.mappers.CartMapper;

import java.util.List;

@Repository
public class CartDAOImpl implements CartDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CartItem> getCartItemsByLogin(String login) {
        return jdbcTemplate.query(
                "SELECT * FROM cart WHERE user_login=?", new CartMapper(), login);
    }

    @Override
    public boolean addToTheCart(String bookName, String login) {
        int updateCart = jdbcTemplate.update(
                "INSERT INTO cart (user_login,book_name) VALUES(?,?)", login, bookName);
        return updateCart != 0;
    }

    @Override
    public boolean deleteFromTheCart(String bookName, String login) {
        Integer id = jdbcTemplate.queryForObject(
                "SELECT id FROM cart WHERE user_login=? AND book_name=? LIMIT 1",
                Integer.class, login, bookName);
        int update = jdbcTemplate.update(
                "DELETE FROM cart WHERE user_login=? AND book_name=? AND id=?", login, bookName, id);
        return update != 0;
    }
}
