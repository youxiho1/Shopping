package com.example.youxihouzainali.shopping;

/**
 * Created by youxihouzainali on 2017/11/25.
 */

public class Cart {
    private String name;
    private int number;
    private String price;
    private String image;

    public Cart(String name, int number, String price, String image) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.image = image;
    }
    public String getName() {
        return name;
    }
    public int getNumber() {
        return number;
    }
    public String getPrice() {
        return price;
    }
    public String getImage() {
        return image;
    }
}
