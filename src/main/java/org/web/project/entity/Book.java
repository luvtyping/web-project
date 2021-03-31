package org.web.project.entity;

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
