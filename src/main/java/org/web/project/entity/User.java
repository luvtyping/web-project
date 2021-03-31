package org.web.project.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private String login;
    private String password;
    private String name;
    private String role;
    private LocalDate birthdate;
}
