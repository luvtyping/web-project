package org.web.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.project.dao.UserDAO;
import org.web.project.entity.User;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getUserByLogin(String login) {
        return userDAO.getUserByLogin(login);
    }

    @Override
    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }
}
