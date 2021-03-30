package org.web.bookShop.entity;

import lombok.Data;

@Data
public class Book {
    private String name;
    private String author;
    private int publishYear;
    private int price;
    private String description;
    private String genre;
}
