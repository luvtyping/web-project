package org.web.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Size(min = 2, max = 10, message = "Длина логина 2-10 символов")
    private String login;

    @Size(min = 5, message = "Длина пароля не менее 5 символов")
    private String password;

    @Size(min = 2, max = 10, message = "Длина имени 2-10 символов")
    @Pattern(regexp = "[A-Za-zА-Яа-я]+", message = "Должны быть только буквы")
    private String name;

    private String role;

    @NotNull(message = "Выберите дату рождения")
    @Past(message = "Некорректная дата")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
}
