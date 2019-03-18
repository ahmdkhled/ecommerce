package com.ahmdkhled.ecommerce.model;

import com.google.gson.annotations.SerializedName;

public class OrderItem {
    private int id;
    @SerializedName("orderItem_quantity")
    private int quantity;

    public OrderItem(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
