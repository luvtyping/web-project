package org.web.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.web.project.entity.Book;
import org.web.project.mappers.BookMapper;

import java.util.List;
import java.util.stream.Collectors;

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
    public boolean addBook(Book book) {
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

    @Override
    public List<Book> getBooksBySearchFilters(String name, String author, String price, String genre) {
        String bookName = name.equals("") ? "%" : name;
        String bookAuthor = author.equals("") ? "%" : author;
        String bookGenre = genre.equals("") ? "%" : genre;
        int bookPrice = price.equals("") ? Integer.MAX_VALUE : Integer.parseInt(price);

        return jdbcTemplate.query(
                "SELECT * FROM books JOIN books_genres ON books.book_name=books_genres.book_name " +
                        "WHERE books.book_name LIKE ? AND genre LIKE ? AND author LIKE ?",
                bookMapper, bookName, bookGenre, bookAuthor, bookPrice);
    }


}
