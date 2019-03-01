package com.ahmdkhled.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable{


    private int id;
    private String state;
    private String city;
    private int zip_code;
    private String address2;
    private String address1;


    public Address(String state, String city, int zip_code, String address1, String address2) {
        this.state = state;
        this.city = city;
        this.zip_code = zip_code;
        this.address2 = address2;
        this.address1 = address1;
    }

    protected Address(Parcel in) {
        id = in.readInt();
        state = in.readString();
        city = in.readString();
        zip_code = in.readInt();
        address2 = in.readString();
        address1 = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getState() {

        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public String getAddress2 ()
    {
        return address2;
    }

    public void setAddress2 (String address2)
    {
        this.address2 = address2;
    }

    public String getAddress1 ()
    {
        return address1;
    }

    public void setAddress1 (String address1)
    {
        this.address1 = address1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(state);
        parcel.writeString(city);
        parcel.writeString(address1);
        parcel.writeString(address2);
        parcel.writeInt(id);
        parcel.writeInt(zip_code);
    }
}
