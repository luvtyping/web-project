package org.web.project.service;

import org.web.project.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooks();

    Book getBookByName(String name);

    boolean createBook(Book book);

    List<Book> getBooksBySearchFilters(String name, String author, String price, String genre);
}
