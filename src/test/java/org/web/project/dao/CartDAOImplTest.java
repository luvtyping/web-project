package org.web.project.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

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
        Assertions.assertEquals(2, cartDAO.getTheCartByLogin("user1login").size());
    }

    @Test
    void addToTheCart() {
        Assertions.assertTrue(cartDAO.addToTheCart("book 1", "user1login"));
    }

    @Test
    void deleteFromTheCart() {
        Assertions.assertTrue(cartDAO.deleteFromTheCart("book 3", "user1login"));
    }
}