package org.web.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.project.dao.BookDAO;
import org.web.project.entity.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private BookDAO bookDAO;

    @Autowired
    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public List<Book> getBooks() {
        return bookDAO.getBooks();
    }

    @Override
    public Book getBookByName(String name) {
        return bookDAO.getBookByName(name);
    }

    @Override
    public boolean addBook(Book book) {
        return bookDAO.addBook(book);
    }

    @Override
    public List<Book> getBooksBySearchFilters(String name, String author, String price, String genre) {
        return bookDAO.getBooksBySearchFilters(name, author, price, genre);
    }

    @Override
    public boolean deleteBook(String name) {
        return bookDAO.deleteBook(name);
    }
}
