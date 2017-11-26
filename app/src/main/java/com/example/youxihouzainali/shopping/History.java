package com.example.youxihouzainali.shopping;

/**
 * Created by youxihouzainali on 2017/11/25.
 */

public class History {
    private String name;
    private int number;
    private String price;
    private String payflag;
    private String image;

    public History(String name, int number, String price, String payflag, String image) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.payflag = payflag;
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
    public String getPayflag() {
        return payflag;
    }

    public String getImage() {
        return image;
    }
}
