package org.web.project.dao;

import org.web.project.entity.User;

import java.util.List;

public interface UserDAO {
    List<User> getUsers();

    User getUserByLogin(String login);

    boolean addUser(User user);
}
