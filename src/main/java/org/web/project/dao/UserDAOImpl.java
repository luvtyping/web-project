package org.web.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.web.project.entity.User;
import org.web.project.mappers.UserMapper;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query(
                "SELECT * FROM users JOIN authorized_data ON users.login=authorized_data.login", new UserMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        return jdbcTemplate.query(
                "SELECT * FROM users JOIN authorized_data ON users.login=authorized_data.login WHERE users.login=?",
                new UserMapper(), login).stream().findFirst().orElse(null);
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
        return (updateUsers != 0 && updateAuthorizedData != 0);
    }
}
