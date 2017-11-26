package com.example.youxihouzainali.shopping;

/**
 * Created by youxihouzainali on 2017/11/26.
 */

    public class Details {
        private String name;
        private String describe;
        private String price;
        private String image;

        public Details(String name, String describe, String price, String image) {
            this.name = name;
            this.describe = describe;
            this.price = price;
            this.image = image;
        }
        public String getName() {
            return name;
        }
        public String getDescribe() {
            return describe;
        }
        public String getPrice() {
            return price;
        }
        public String getImage() {
            return image;
        }
    }
