package com.ahmdkhled.ecommerce.model;

public class AddressItem {

    private Address mAddress;
    private int position;

    public AddressItem(Address mAddress, int position) {
        this.mAddress = mAddress;
        this.position = position;
    }

    public Address getmAddress() {

        return mAddress;
    }

    public int getPosition() {
        return position;
    }
}
