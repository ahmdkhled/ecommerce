package com.ahmdkhled.ecommerce.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofetClient {

    private static final String BaseUrl="https://ecommerceg.000webhostapp.com";

    private static Retrofit retrofit;
    private static ApiService ApiService;

    private static Retrofit getInstance(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
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
