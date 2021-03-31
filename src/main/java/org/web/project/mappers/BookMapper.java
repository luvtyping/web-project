package org.web.project.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.web.project.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        book.setAuthor(resultSet.getString("author"));
        book.setName(resultSet.getString("book_name"));
        book.setPublishYear(resultSet.getInt("publishYear"));
        book.setGenre(resultSet.getString("genre"));
        book.setDescription(resultSet.getString("description"));
        book.setPrice(resultSet.getInt("price"));
        return book;
    }
}
