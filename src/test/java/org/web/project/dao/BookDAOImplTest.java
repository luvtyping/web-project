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
        Assertions.assertEquals("book 1", bookDAO.getBookByName("book 1").getName());
        Assertions.assertEquals("book 2", bookDAO.getBookByName("book 2").getName());
        Assertions.assertEquals("book 3", bookDAO.getBookByName("book 3").getName());
        Assertions.assertEquals("book 4", bookDAO.getBookByName("book 4").getName());
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
        Assertions.assertEquals(2, bookDAO.getBooksBySearchFilters("", "Author R.T.", "", "").size());
        Assertions.assertEquals(1, bookDAO.getBooksBySearchFilters("", "Author R.T.", "1500", "").size());
        Assertions.assertEquals(0, bookDAO.getBooksBySearchFilters("", "", "1000", "").size());
    }
}