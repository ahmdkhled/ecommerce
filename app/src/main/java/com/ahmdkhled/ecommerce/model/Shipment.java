package com.ahmdkhled.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Shipment implements Parcelable {
    private CartItem cartItem;
    private int total;

    public Shipment(CartItem cartItem, int total) {
        this.cartItem = cartItem;
        this.total = total;
    }

    protected Shipment(Parcel in) {
        cartItem = in.readParcelable(CartItem.class.getClassLoader());
        total = in.readInt();
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(cartItem, i);
        parcel.writeInt(total);
    }
}
