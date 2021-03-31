package org.web.project.dao;

import org.web.project.entity.Book;

import java.util.List;

public interface BookDAO {
    List<Book> getBooks();

    Book getBookByName(String name);

    boolean addBook(Book book);

    List<Book> getBooksBySearchFilters(String name, String author, String price, String genre);
}
