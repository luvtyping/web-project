package org.web.bookShop.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorizedUser {
    private String login;
    private String password;
    private String name;
    private String role;
    private LocalDate birthdate;
}
