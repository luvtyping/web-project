package org.web.bookShop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.web.bookShop.entity.Book;
import org.web.bookShop.mappers.BookMapper;

import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> getBooks() {
        return jdbcTemplate.query(
                "SELECT * FROM books JOIN books_genres " +
                        "ON books.book_name=books_genres.book_name", bookMapper);
    }

    @Override
    public Book getBookByName(String name) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM books JOIN books_genres " +
                        "ON books.book_name=books_genres.book_name WHERE books_genres.book_name=?", bookMapper, name);

    }

    @Override
    public boolean createBook(Book book) {
        int updateBooks = jdbcTemplate.update("INSERT INTO books VALUES(?,?,?,?,?)",
                book.getName(),
                book.getAuthor(),
                book.getPublishYear(),
                book.getPrice(),
                book.getDescription()
        );
        int updateBooksGenres = jdbcTemplate.update("INSERT INTO books_genres VALUES(?,?)",
                book.getName(),
                book.getGenre()
        );
        return (updateBooks != 0 && updateBooksGenres != 0);
    }


}
