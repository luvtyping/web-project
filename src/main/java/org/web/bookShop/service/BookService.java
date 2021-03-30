package org.web.bookShop.service;

import org.web.bookShop.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooks();

    Book getBookByName(String name);

    boolean createBook(Book book);
}
