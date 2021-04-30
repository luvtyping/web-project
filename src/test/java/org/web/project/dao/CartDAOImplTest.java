package org.web.project.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import org.web.project.entity.CartItem;
import org.web.project.mappers.CartMapper;

@JdbcTest
@Sql({"classpath:dbSchema.sql", "classpath:data.sql"})
class CartDAOImplTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CartDAOImpl cartDAO;

    @BeforeEach
    void setUp() {
        cartDAO = new CartDAOImpl();
        cartDAO.setJdbcTemplate(jdbcTemplate);
    }

    @Test
    void getTheCartByLogin() {
        Assertions.assertEquals(5, cartDAO.getCartItemsByLogin("user1login").size());
    }

    @Test
    void addToTheCart() {
        Assertions.assertTrue(cartDAO.addToTheCart("Book one", "user1login"));
    }

    @Test
    void deleteFromTheCart() {
        Assertions.assertTrue(cartDAO.deleteFromTheCart("Book three", "user1login"));
        Assertions.assertTrue(cartDAO.deleteFromTheCart("Book two", "user1login"));
        Assertions.assertEquals(3, cartDAO.getCartItemsByLogin("user1login").size());
    }
}