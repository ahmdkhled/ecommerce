package com.ahmdkhled.ecommerce.model;

import java.util.List;

public class Checkout {
    private List<Address> mAddresses;
    private User mUser;

    public List<Address> getmAddress() {
        return mAddresses;
    }

    public void setmAddress(List<Address> mAddress) {
        this.mAddresses = mAddress;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }
}
