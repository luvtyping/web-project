package org.web.project.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.web.project.dao.BookDAOImpl;
import org.web.project.entity.Book;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class BookServiceImplTest {

    @TestConfiguration
    static class BookServiceImplTestContextConfiguration {
        @Bean
        public BookService bookService() {
            return new BookServiceImpl();
        }
    }

    @Autowired
    private BookService bookService;

    @MockBean
    private BookDAOImpl bookDAO;

    private Book bookOne;
    private Book bookTwo;
    private Book bookThree;
    private Book bookFour;

    @Before
    public void setUp() {
        bookOne = new Book("NameOne", "AuthorOne", 2001, 100, "DescriptionOne", "Детектив");
        bookTwo = new Book("NameTwo", "AuthorTwo", 2002, 200, "DescriptionTwo", "Драма");
        bookThree = new Book("NameThree", "AuthorThree", 2003, 300, "DescriptionThree", "Роман");
        bookFour = new Book("NameFour", "AuthorThree", 2004, 400, "DescriptionFour", "Драма");

        Mockito.when(bookDAO.getBookByName(bookOne.getName())).thenReturn(bookOne);
        Mockito.when(bookDAO.getBookByName("unknown")).thenReturn(null);

        Mockito.when(bookDAO.getBooks()).thenReturn(Arrays.asList(bookOne, bookTwo, bookThree, bookFour));

        Mockito.when(bookDAO.addBook(bookOne)).thenReturn(true);

        Mockito.when(bookDAO.getBooksBySearchFilters("", "", "", "Драма")).thenReturn(Arrays.asList(bookTwo, bookFour));
        Mockito.when(bookDAO.getBooksBySearchFilters("", "AuthorThree", "350", "")).thenReturn(Collections.singletonList(bookThree));
        Mockito.when(bookDAO.getBooksBySearchFilters("unknown", "", "", "")).thenReturn(Collections.emptyList());
        Mockito.when(bookDAO.getBooksBySearchFilters("", "", "", "")).thenReturn(Arrays.asList(bookOne, bookTwo, bookThree, bookFour));

        Mockito.when(bookDAO.deleteBook("NameOne")).thenReturn(true);
        Mockito.when(bookDAO.deleteBook("unknown")).thenReturn(false);
    }

    @Test
    public void getBooks() {
        List<Book> books = bookService.getBooks();

        assertEquals(4, books.size());
        assertTrue(books.contains(bookOne));
        assertTrue(books.contains(bookTwo));
        assertTrue(books.contains(bookThree));
    }

    @Test
    public void getBookByName() {
        String name = "NameOne";
        Book found = bookService.getBookByName(name);
        assertEquals(found.getName(), name);

        Book unknown = bookService.getBookByName("unknown");
        assertNull(unknown);
    }

    @Test
    public void addBook() {
        assertTrue(bookService.addBook(bookOne));
    }

    @Test
    public void getBooksBySearchFilters() {
        List<Book> booksBySearchFilters1 = bookService.getBooksBySearchFilters("", "", "", "Драма");
        List<Book> booksBySearchFilters2 = bookService.getBooksBySearchFilters("", "AuthorThree", "350", "");
        List<Book> booksBySearchFilters3 = bookService.getBooksBySearchFilters("unknown", "", "", "");
        List<Book> booksBySearchFilters4 = bookService.getBooksBySearchFilters("", "", "", "");

        assertEquals(2, booksBySearchFilters1.size());
        assertTrue(booksBySearchFilters1.containsAll(Arrays.asList(bookTwo, bookFour)));

        assertEquals(1, booksBySearchFilters2.size());
        assertTrue(booksBySearchFilters2.contains(bookThree));

        assertEquals(0, booksBySearchFilters3.size());

        assertEquals(4, booksBySearchFilters4.size());
        assertTrue(booksBySearchFilters4.containsAll(Arrays.asList(bookOne, bookTwo, bookThree, bookFour)));
    }

    @Test
    public void deleteBook() {
        assertTrue(bookService.deleteBook("NameOne"));
        assertFalse(bookService.deleteBook("unknown"));
    }
}