package com.ahmdkhled.ecommerce.model;

import java.util.ArrayList;

public class CartResponse {
    private int total;
    private ArrayList<Product> products;

    public CartResponse(int total, ArrayList<Product> products) {
        this.total = total;
        this.products = products;
    }



    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
