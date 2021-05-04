package org.web.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @NotEmpty(message = "Введите название книги")
    private String name;

    @Pattern(regexp = "^[a-zA-Zа-яА-Я\\s]+$", message = "Должны быть только буквы")
    private String author;

    @Range(min = 1900, max = 2021, message = "Требуемая дата '1900-2021'")
    private int publishYear;

    @Range(min = 1, max = 99999, message = "Некорректная цена")
    private int price;

    private String description;

    @NotEmpty(message = "Выберите жанр")
    private String genre;
}
