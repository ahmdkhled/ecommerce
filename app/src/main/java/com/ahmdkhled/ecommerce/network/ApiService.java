package com.ahmdkhled.ecommerce.network;

import com.ahmdkhled.ecommerce.model.Response;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {


    @FormUrlEncoded
    @POST(Constants.SIGNUP_URL)
    public Call<Response> signup(@FieldMap HashMap<String,String> map);



}
