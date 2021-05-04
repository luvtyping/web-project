package org.web.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.web.project.entity.Book;
import org.web.project.mappers.BookMapper;

import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> getBooks() {
        return jdbcTemplate.query(
                "SELECT * FROM books JOIN books_genres " +
                        "ON books.book_name=books_genres.book_name", new BookMapper());
    }

    @Override
    public Book getBookByName(String name) {
        return jdbcTemplate.query(
                "SELECT * FROM books JOIN books_genres " +
                        "ON books.book_name=books_genres.book_name WHERE books_genres.book_name=?",
                new BookMapper(), name).stream().findFirst().orElse(null);
    }

    @Override
    public boolean addBook(Book book) {
        int updateBooks = jdbcTemplate.update("INSERT INTO books VALUES(?,?,?,?,?)",
                book.getName(),
                book.getPublishYear(),
                book.getDescription(),
                book.getPrice(),
                book.getAuthor()
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
                        "WHERE books.book_name LIKE ? AND genre LIKE ? AND author LIKE ? AND price < ?",
                new BookMapper(), bookName, bookGenre, bookAuthor, bookPrice);
    }

    @Override
    public boolean deleteBook(String name) {
        int deleteFromCart = jdbcTemplate.update("DELETE FROM cart WHERE book_name=?", name);
        int deleteFromBooksGenres = jdbcTemplate.update("DELETE FROM books_genres WHERE book_name=?", name);
        int deleteFromBooks = jdbcTemplate.update("DELETE FROM books WHERE book_name=?", name);
        return (deleteFromCart != 0 && deleteFromBooksGenres != 0 && deleteFromBooks != 0);
    }
}
