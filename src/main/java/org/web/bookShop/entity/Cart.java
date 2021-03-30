package org.web.bookShop.entity;

import lombok.Data;

@Data
public class Cart {//BookInCart -> Cart
    private String userLogin;
    private String bookName;
}
