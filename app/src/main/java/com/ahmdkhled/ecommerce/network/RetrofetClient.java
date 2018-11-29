package com.ahmdkhled.ecommerce.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofetClient {

    private static Retrofit retrofit;
    private static ApiService ApiService;

    private static Retrofit getInstance(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL).build();

        }
        return retrofit;
    }

    public static ApiService getApiService(){
        if (ApiService==null){
            ApiService=getInstance().create(ApiService.class);
        }
        return ApiService;
    }

}
