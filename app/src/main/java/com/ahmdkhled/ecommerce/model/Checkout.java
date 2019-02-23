package com.ahmdkhled.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Checkout implements Parcelable{
    private List<Address> mAddresses;
    private User mUser;

    public Checkout(List<Address> mAddresses, User mUser) {
        this.mAddresses = mAddresses;
        this.mUser = mUser;
    }

    protected Checkout(Parcel in) {
        mUser = in.readParcelable(User.class.getClassLoader());
        mAddresses=in.readArrayList(Address.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mUser, flags);
        dest.writeList(mAddresses);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Checkout> CREATOR = new Creator<Checkout>() {
        @Override
        public Checkout createFromParcel(Parcel in) {
            return new Checkout(in);
        }

        @Override
        public Checkout[] newArray(int size) {
            return new Checkout[size];
        }
    };

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
