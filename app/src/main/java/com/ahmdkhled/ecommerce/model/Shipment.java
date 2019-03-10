package com.ahmdkhled.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Shipment implements Parcelable {
    private CartItem cartItem;
    private String expectedDelivery ;

    public Shipment(CartItem cartItem, String expectedDelivery) {
        this.cartItem = cartItem;
        this.expectedDelivery = expectedDelivery;
    }

    protected Shipment(Parcel in) {
        cartItem = in.readParcelable(CartItem.class.getClassLoader());
        expectedDelivery = in.readString();
    }

    public static final Creator<Shipment> CREATOR = new Creator<Shipment>() {
        @Override
        public Shipment createFromParcel(Parcel in) {
            return new Shipment(in);
        }

        @Override
        public Shipment[] newArray(int size) {
            return new Shipment[size];
        }
    };

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public String getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(String expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(cartItem, i);
        parcel.writeString(expectedDelivery);
    }
}
