package org.web.project.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.web.project.entity.Book;

@JdbcTest
@Sql({"classpath:dbSchema.sql", "classpath:data.sql"})
class BookDAOImplTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private BookDAOImpl bookDAO;

    @BeforeEach
    void setUp() {
        bookDAO = new BookDAOImpl();
        bookDAO.setJdbcTemplate(jdbcTemplate);
    }

    @Test
    void getBooks() {
        Assertions.assertEquals(4, bookDAO.getBooks().size());
    }

    @Test
    void getBookByName() {
        Assertions.assertEquals("Book one", bookDAO.getBookByName("Book one").getName());
        Assertions.assertEquals("Book three", bookDAO.getBookByName("Book three").getName());
        Assertions.assertNull(bookDAO.getBookByName("unknown"));
    }

    @Test
    void addBook() {
        Assertions.assertTrue(bookDAO.addBook(
                new Book("testName", "testAuthor", 2020, 999,
                        "simple test book description", "Драма")
        ));
    }

    @Test
    void getBooksBySearchFilters() {
        Assertions.assertEquals(2, bookDAO.getBooksBySearchFilters("", "", "", "Роман").size());
        Assertions.assertEquals(2, bookDAO.getBooksBySearchFilters("", "Author A.", "", "").size());
        Assertions.assertEquals(1, bookDAO.getBooksBySearchFilters("", "Author A.", "1500", "").size());
        Assertions.assertEquals(0, bookDAO.getBooksBySearchFilters("", "", "1000", "").size());
        Assertions.assertEquals(4, bookDAO.getBooksBySearchFilters("", "", "", "").size());
    }

    @Test
    void deleteBook() {
        Assertions.assertTrue(bookDAO.deleteBook("Book three"));
        Assertions.assertNull(bookDAO.getBookByName("Book three"));
        Assertions.assertFalse(bookDAO.deleteBook("unknown"));
    }
}