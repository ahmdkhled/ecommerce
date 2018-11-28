package com.ahmdkhled.ecommerce.network;

import com.ahmdkhled.ecommerce.model.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<Response> login(@Field("email") String email,
                         @Field("password") String password);
}
