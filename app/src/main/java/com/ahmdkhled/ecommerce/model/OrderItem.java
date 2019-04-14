package com.ahmdkhled.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OrderItem implements Parcelable {
    private int id;
    @SerializedName(value="quantity", alternate={"orderItem_quantity"})
    private int orderItem_quantity;

    public OrderItem(int id, int quantity) {
        this.id = id;
        this.orderItem_quantity = quantity;
    }

    protected OrderItem(Parcel in) {
        id = in.readInt();
        orderItem_quantity = in.readInt();
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(orderItem_quantity);
    }
}
