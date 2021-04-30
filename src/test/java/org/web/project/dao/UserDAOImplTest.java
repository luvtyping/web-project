package org.web.project.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.web.project.entity.User;

import java.time.LocalDate;

@JdbcTest
@Sql({"classpath:dbSchema.sql", "classpath:data.sql"})
class UserDAOImplTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private UserDAOImpl userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAOImpl();
        userDAO.setJdbcTemplate(jdbcTemplate);
    }

    @Test
    void getUsers() {
        Assertions.assertEquals(1, userDAO.getUsers().size());
    }

    @Test
    void getUserByLogin() {
        Assertions.assertEquals("name1", userDAO.getUserByLogin("user1login").getName());
        Assertions.assertNull(userDAO.getUserByLogin("unknownLogin"));
    }

    @Test
    void addUser() {
        Assertions.assertTrue(userDAO.addUser(
                new User("userlogin1", "userpassword1", "username1", "MANAGER",
                        LocalDate.of(2021, 6, 5))));
    }
}