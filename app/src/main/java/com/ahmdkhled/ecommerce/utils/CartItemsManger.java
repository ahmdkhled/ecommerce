package com.ahmdkhled.ecommerce.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ahmdkhled.ecommerce.model.CartItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartItemsManger {
    private static final String PREFS="prefs";
    public static final String CART_ITEMS_LIST_KEY="cart_items";
    private SharedPreferences sharedPreferences;

    public CartItemsManger(Context context) {
        sharedPreferences=context.getSharedPreferences(PREFS,Context.MODE_PRIVATE);
    }

    public ArrayList<CartItem> getCartItems(){
        String json=sharedPreferences.getString(CART_ITEMS_LIST_KEY,null);
        Gson gson=new Gson();
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        return gson.fromJson(json,type);
    }





}
