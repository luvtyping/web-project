package org.web.project.service;

import org.web.project.entity.User;

public interface UserService {
    User getUserByLogin(String login);

    boolean addUser(User user);
}
