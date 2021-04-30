package org.web.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.web.project.entity.User;
import org.web.project.service.UserDetailsServiceImpl;
import org.web.project.service.UserService;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private User newUser;

    @Before
    public void setUp() throws Exception {
        newUser = new User("newLogin", "newPassword", "newName", "USER", LocalDate.of(2021, 1, 1));
        given(userService.addUser(newUser)).willReturn(true);
    }

    @Test
    public void loginPage_NoError() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void loginPage_Error() throws Exception {
        mockMvc.perform(get("/login")
                .param("error", "error"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("errorMessage", "Неверный логин или пароль"));
    }

    @Test
    public void registrationPage_NoError() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attribute("user", is(notNullValue())));
    }

    @Test
    public void registrationPage_Error() throws Exception {
        mockMvc.perform(get("/registration")
                .param("error", "userAlreadyExists"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attribute("user", is(notNullValue())))
                .andExpect(model().attribute("errorMessage", "Логин занят другим пользователем"));
    }

    @Test
    public void processRegistration() throws Exception {
        mockMvc.perform(post("/registration/proceed")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void logout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(redirectedUrl("/login?logout"));
    }

    @Test
    public void error() throws Exception {
        mockMvc.perform(get("/error"))
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}