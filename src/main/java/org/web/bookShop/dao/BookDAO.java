package org.web.bookShop.dao;

import org.web.bookShop.entity.Book;

import java.util.List;

public interface BookDAO {
    List<Book> getBooks();

    Book getBookByName(String name);

    boolean createBook(Book book);


//    List<Book> getBooksByParameters();// TODO: 30.03.2021 доделать метод
}
