package org.web.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.web.project.entity.User;
import org.web.project.mappers.UserMapper;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query(
                "SELECT * FROM users JOIN authorized_data ON users.login=authorized_data.login", userMapper);
    }

    @Override
    public User getUserByLogin(String login) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users JOIN authorized_data ON users.login=authorized_data.login WHERE login=?", userMapper, login);
    }

    @Override
    public boolean addUser(User user) {
        int updateUsers = jdbcTemplate.update("INSERT INTO users VALUES(?,?,?,?)",
                user.getName(),
                user.getBirthdate(),
                user.getLogin(),
                user.getRole()
        );
        int updateAuthorizedData = jdbcTemplate.update("INSERT INTO authorized_data VALUES(?,?)",
                user.getLogin(),
                user.getPassword()
        );

        String role = user.getRole().equals("MANAGER") ? "managers" : "customers";
        int updateRole = jdbcTemplate.update("INSERT INTO " + role + " VALUES(?)",
                user.getLogin());

        return (updateUsers != 0 && updateAuthorizedData != 0 && updateRole != 0);
    }
}
