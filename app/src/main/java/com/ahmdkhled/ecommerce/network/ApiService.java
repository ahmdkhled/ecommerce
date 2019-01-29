package com.ahmdkhled.ecommerce.network;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Ad;
import com.ahmdkhled.ecommerce.model.Category;
import com.ahmdkhled.ecommerce.model.Response;


import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<Response> login(@Field("email") String email,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST(Constants.SIGNUP_URL)
    public Call<Response> signup(@FieldMap HashMap<String,String> map);

    @GET(Constants.CATEGORY_URL)
    Call<ArrayList<Category>> getCategories();

    @GET("products.php")
    Call<ArrayList<CartItem>> getCartItems(@Query("id") String ids);

    @GET ("products.php")
    Call <ArrayList<Product>> getProducts(@Query("categoryId") String category) ;

    @GET("ads.php")
    Call<ArrayList<Ad>> getAds();

    @GET("recentlyAdded.php")
    Call<ArrayList<Product>> getRecentlyAdedProducts();

    @FormUrlEncoded
    @POST(Constants.GET_ADDRESSES_URL)
    Call<ArrayList<Address>> getAddresses(@Field("id") String userId);

}
