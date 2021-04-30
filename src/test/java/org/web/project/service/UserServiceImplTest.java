package org.web.project.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.web.project.dao.UserDAOImpl;
import org.web.project.entity.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestontextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserDAOImpl userDAO;

    private User user;

    @Before
    public void setUp() {
        user = new User("loginOne", "passwordOne", "UserOne", "USER", LocalDate.of(2000, 1, 1));

        Mockito.when(userDAO.getUserByLogin(user.getLogin())).thenReturn(user);
        Mockito.when(userDAO.getUserByLogin("unknown")).thenReturn(null);
        Mockito.when(userDAO.addUser(user)).thenReturn(true);
    }

    @Test
    public void getUserByLogin() {
        String login = "loginOne";
        User found = userService.getUserByLogin(login);
        assertEquals(found.getLogin(), login);

        User unknown = userService.getUserByLogin("unknown");
        assertNull(unknown);
    }

    @Test
    public void addUser() {
        assertTrue(userService.addUser(user));
    }
}