package com.ahmdkhled.ecommerce.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String SHIPPING_OPTION = "shipping_method";
    private static final String PAYMENT_OPTION = "payment_method";
    private static final String SHIPPING_OPTOPN_VALUE = "shipping_value";
    private static final String PAYMENT_OPTOPN_VALUE = "payment_value";
    private static final String SHIPPING_ADDRESS_PREF = "shipping_address";
    private static final String SHIPPING_ADDRESS = "shipping_address_id";
    private  SharedPreferences shippingOptionPref,paymentOptionPref;
    private  Context context;
    private SharedPreferences addressPref;

    public PrefManager(Context context) {
        this.context = context;
        shippingOptionPref = context.getSharedPreferences(SHIPPING_OPTION,Context.MODE_PRIVATE);
        paymentOptionPref = context.getSharedPreferences(PAYMENT_OPTION,Context.MODE_PRIVATE);
        addressPref = context.getSharedPreferences(SHIPPING_ADDRESS_PREF,Context.MODE_PRIVATE);

    }

    public void setShippingOption(String option){
        SharedPreferences.Editor editor = shippingOptionPref.edit();
        editor.putString(SHIPPING_OPTOPN_VALUE,option);
        editor.apply();
    }

    public void setPaymentOption(String option){
        SharedPreferences.Editor editor = paymentOptionPref.edit();
        editor.putString(PAYMENT_OPTOPN_VALUE,option);
        editor.apply();
    }


    public String readShippingOption(){
        return shippingOptionPref.getString(SHIPPING_OPTOPN_VALUE,null);
    }

    public String readPaymentOption(){
        return paymentOptionPref.getString(PAYMENT_OPTOPN_VALUE,null);
    }

    public void saveAddressId(int id) {
        SharedPreferences.Editor editor = addressPref.edit();
        editor.putInt(SHIPPING_ADDRESS,id);
        editor.apply();
    }

    public int readShippingAddress(){
        return addressPref.getInt(SHIPPING_ADDRESS,-1);
    }
}
