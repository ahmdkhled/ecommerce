package com.ahmdkhled.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable{


    private int id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String state;
    private String city;
    private int zip_code;
    private String address_2;
    private String address_1;
    private int isDefault;


    public Address(String first_name, String last_name, String phone_number, String state, String city,
                   int zip_code, String address2, String address1, int isDefault) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.state = state;
        this.city = city;
        this.zip_code = zip_code;
        this.address_2 = address2;
        this.address_1 = address1;
        this.isDefault = isDefault;
    }

    protected Address(Parcel in) {
        id = in.readInt();
        first_name = in.readString();
        last_name = in.readString();
        phone_number = in.readString();
        state = in.readString();
        city = in.readString();
        zip_code = in.readInt();
        address_2 = in.readString();
        address_1 = in.readString();
        isDefault = in.readInt();
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

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

    public String getAddress_2()
    {
        return address_2;
    }

    public void setAddress_2(String address_2)
    {
        this.address_2 = address_2;
    }

    public String getAddress_1()
    {
        return address_1;
    }

    public void setAddress_1(String address_1)
    {
        this.address_1 = address_1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getisDefault() {
        return isDefault;
    }

    public void setisDefault(int mDefault) {
        this.isDefault = mDefault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(phone_number);
        parcel.writeString(state);
        parcel.writeString(city);
        parcel.writeInt(zip_code);
        parcel.writeString(address_2);
        parcel.writeString(address_1);
        parcel.writeInt(isDefault);
    }
}
