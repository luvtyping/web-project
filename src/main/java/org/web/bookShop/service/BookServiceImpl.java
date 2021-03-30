package org.web.bookShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.web.bookShop.dao.BookDAOImpl;
import org.web.bookShop.entity.Book;

import java.util.List;

public class BookServiceImpl implements BookService {
    @Autowired
    private BookDAOImpl bookDAO;

    @Override
    public List<Book> getBooks() {
        return bookDAO.getBooks();
    }

    @Override
    public Book getBookByName(String name) {
        return bookDAO.getBookByName(name);
    }

    @Override
    public boolean createBook(Book book) {
        return bookDAO.createBook(book);
    }
}
