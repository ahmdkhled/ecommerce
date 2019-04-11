package com.ahmdkhled.ecommerce.utils;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartItemsManger {
    private static final String PREFS="prefs";
    private static final String CART_ITEMS_LIST_KEY="cart_items";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private MutableLiveData<Integer> cartItemsSize;
    String TAG="BADGEEE";
    static CartItemsManger cartItemsManger;

    public static CartItemsManger getInstance(Context context){
        if(cartItemsManger == null)cartItemsManger = new CartItemsManger(context);

        return cartItemsManger;
    }

    private CartItemsManger(Context context) {
        if(cartItemsSize == null) {
            cartItemsSize = new MutableLiveData<>();
            sharedPreferences=context.getSharedPreferences(PREFS,Context.MODE_PRIVATE);
            cartItemsSize.setValue(getCartItems().size());
        }
        Log.d("BADGEEE", "sp cons null");
    }

    public ArrayList<CartItem> getCartItems(){
        if(sharedPreferences == null){
            Log.d("BADGEEE","sp null");
            return new ArrayList<>();
        }else {
            String json = sharedPreferences.getString(CART_ITEMS_LIST_KEY, null);
            Log.d("JSONN", "json is" + json);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<CartItem>>() {
            }.getType();
            return gson.fromJson(json, type);
        }
    }

    public void saveCartItem(int ProductId,int quantity){
        CartItem cartItem=new CartItem(ProductId,quantity);
        ArrayList<CartItem> cartItems=getCartItems();
        if (cartItems==null){
            cartItems=new ArrayList<>();
        }
        cartItems.add(cartItem);
        Gson gson=new Gson();
        String json=gson.toJson(cartItems);
        editor=sharedPreferences.edit();
        editor.putString(CART_ITEMS_LIST_KEY,json);
        editor.apply();
        cartItemsSize.setValue(cartItems.size());
        Log.d(TAG, "saveCartItem: ");
    }
    private void deleteCartItems(){
        editor=sharedPreferences.edit();
        editor.putString(CART_ITEMS_LIST_KEY,"");
        editor.apply();

    }

    public void updateQuantity(int newQuantity,int pos){
        ArrayList<CartItem> cartItems=getCartItems();
        cartItems.get(pos).setQuantity(newQuantity);
        deleteCartItems();
        Gson gson=new Gson();
        String json=gson.toJson(cartItems);
        editor=sharedPreferences.edit();
        editor.putString(CART_ITEMS_LIST_KEY,json);
        editor.apply();
    }

    public void deleteCartItem(int pos){
        ArrayList<CartItem> cartItems=getCartItems();
        cartItems.remove(pos);
        deleteCartItems();
        Gson gson=new Gson();
        String json=gson.toJson(cartItems);
        editor=sharedPreferences.edit();
        editor.putString(CART_ITEMS_LIST_KEY,json);
        editor.apply();
        cartItemsSize.setValue(cartItems.size());

    }

    public MutableLiveData<Integer> getCartItemsSize(){
        Log.d(TAG, "getCartItemsSize: "+cartItemsSize.getValue());
        return cartItemsSize;
    }


}
