package com.ahmdkhled.ecommerce.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String SHIPPING_OPTION = "shipping_method";
    private static final String PAYMENT_OPTION = "payment_method";
    private static final String SHIPPING_OPTOPN_VALUE = "shipping_value";
    private static final String PAYMENT_OPTOPN_VALUE = "payment_value";
    private  SharedPreferences shippingOptionPref,paymentOptionPref;
    private  Context context;

    public PrefManager(Context context) {
        this.context = context;
        shippingOptionPref = context.getSharedPreferences(SHIPPING_OPTION,Context.MODE_PRIVATE);
        paymentOptionPref = context.getSharedPreferences(PAYMENT_OPTION,Context.MODE_PRIVATE);

    }

    public void setShippingOption(String option){
        SharedPreferences.Editor editor1 = shippingOptionPref.edit();
        editor1.putString(SHIPPING_OPTOPN_VALUE,option);
        editor1.apply();
    }

    public void setPaymentOption(String option){
        SharedPreferences.Editor editor1 = paymentOptionPref.edit();
        editor1.putString(PAYMENT_OPTOPN_VALUE,option);
        editor1.apply();
    }


    public String readShippingOption(){
        return shippingOptionPref.getString(SHIPPING_OPTOPN_VALUE,null);
    }

    public String readPaymentOption(){
        return paymentOptionPref.getString(PAYMENT_OPTOPN_VALUE,null);
    }

}
