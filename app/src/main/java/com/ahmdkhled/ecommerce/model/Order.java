package com.ahmdkhled.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Order implements Parcelable {

    @SerializedName("order_id")
    private int order_id;
    @SerializedName("order_date")
    private String order_date;
    @SerializedName("order_userId")
    private int userId;
    @SerializedName("order_status")
    private String status;
    @SerializedName("order_addressId")
    private int addressId;
    @SerializedName("address")
    private Address address;
    @SerializedName("orderItems")
    private ArrayList<OrderItem> orderItems;


    public Order(int order_id, String order_date,
                 int userId, String status, int addressId,
                 Address address, ArrayList<OrderItem> orderItems) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.userId = userId;
        this.status = status;
        this.addressId = addressId;
        this.address = address;
        this.orderItems = orderItems;
    }

    protected Order(Parcel in) {
        order_id = in.readInt();
        order_date = in.readString();
        userId = in.readInt();
        status = in.readString();
        addressId = in.readInt();
        address=in.readParcelable(Address.class.getClassLoader());
        orderItems=in.readArrayList(Order.class.getClassLoader());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(order_id);
        dest.writeString(order_date);
        dest.writeInt(userId);
        dest.writeString(status);
        dest.writeInt(addressId);
        dest.writeParcelable(address,flags);
        dest.writeList(orderItems);
    }
}
