package com.ahmdkhled.ecommerce.network;

import com.ahmdkhled.ecommerce.model.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import java.util.HashMap;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<Response> login(@Field("email") String email,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST(Constants.SIGNUP_URL)
    public Call<String> signup(@FieldMap HashMap<String,String> map);



}
