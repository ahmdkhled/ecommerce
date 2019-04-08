package com.ahmdkhled.ecommerce.model;

import com.google.gson.annotations.SerializedName;

public class OrderItem {
    private int id;
    @SerializedName(value="quantity", alternate={"orderItem_quantity"})
    private int orderItem_quantity;

    public OrderItem(int id, int quantity) {
        this.id = id;
        this.orderItem_quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderItem_quantity() {
        return orderItem_quantity;
    }

    public void setOrderItem_quantity(int orderItem_quantity) {
        this.orderItem_quantity = orderItem_quantity;
    }
}
