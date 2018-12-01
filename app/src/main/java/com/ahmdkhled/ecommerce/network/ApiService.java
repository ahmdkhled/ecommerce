package com.ahmdkhled.ecommerce.network;

import com.ahmdkhled.ecommerce.Product;
import com.ahmdkhled.ecommerce.model.Response;
import retrofit2.Call;
import retrofit2.http.Field;

import java.util.ArrayList;
import java.util.HashMap;
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
    public Call<String> signup(@FieldMap HashMap<String,String> map);




    @GET ("products.php")
    Call <ArrayList<Product>> getProducts(@Query("category") String category) ;





}
