package com.ahmdkhled.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OrderItem implements Parcelable {

    @SerializedName("orderItem_id")
    private int id;
    @SerializedName("orderItem_quantity")
    private int orderItem_quantity;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private float price;
    @SerializedName("description")
    private String description;


    protected OrderItem(Parcel in) {
        id = in.readInt();
        orderItem_quantity = in.readInt();
        name = in.readString();
        price = in.readFloat();
        description = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(orderItem_quantity);
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeString(description);
    }


    public int getId() {
        return id;
    }

    public int getOrderItem_quantity() {
        return orderItem_quantity;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
