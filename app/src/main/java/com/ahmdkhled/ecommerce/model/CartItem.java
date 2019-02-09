package com.ahmdkhled.ecommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CartItem {
    @SerializedName("product_id")
    private int id;
    @SerializedName("product_name")
    private String name ;
    @SerializedName("product_price")
    private double price ;
    @SerializedName("product_quantity")
    private int quantity ;
    @SerializedName("media")
    private ArrayList<Media> image ;
    private int Quantity ;


    public CartItem(int id , String name, double price, int quantity, ArrayList<Media> image , int Quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.Quantity=Quantity;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getquantity() {
        return quantity;
    }

    public void setquantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public ArrayList<Media> getImage() {
        return image;
    }

    public void setImage(ArrayList<Media> image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
