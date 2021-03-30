package org.web.bookShop.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.web.bookShop.entity.AuthorizedUser;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthorizedUserMapper implements RowMapper<AuthorizedUser> {
    @Override
    public AuthorizedUser mapRow(ResultSet resultSet, int i) throws SQLException {
        AuthorizedUser authorizedUser = new AuthorizedUser();
        authorizedUser.setName(resultSet.getString("user_name"));
        authorizedUser.setBirthdate(resultSet.getDate("birthdate").toLocalDate());
        authorizedUser.setLogin(resultSet.getString("login"));
        authorizedUser.setPassword(resultSet.getString("password"));
        authorizedUser.setRole(resultSet.getString("role"));
        return authorizedUser;
    }
}
