package com.ahmdkhled.ecommerce.model;

public class Address {
    private String address2;

    private String address1;

    public Address(String address2, String address1) {
        this.address2 = address2;
        this.address1 = address1;
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
}
