package com.ahmdkhled.ecommerce;

public class Product  {

    private String image;
    private String discount;
    private String price;
    private String category;

    public Product(String image, String discount, String price, String category) {
        this.image = image;
        this.discount = discount;
        this.price = price;
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
