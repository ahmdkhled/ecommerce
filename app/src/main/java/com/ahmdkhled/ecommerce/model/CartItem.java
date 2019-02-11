package com.ahmdkhled.ecommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CartItem {
    private Product product;
    private int quantity ;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(int id,int quantity) {
        this.product= new Product(id);
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
