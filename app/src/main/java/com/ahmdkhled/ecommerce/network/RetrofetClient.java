package com.ahmdkhled.ecommerce.network;

import retrofit2.Retrofit;


public class RetrofetClient {



    private static Retrofit retrofit;
    private static ApiService ApiService;

    private static Retrofit getInstance(){
        if (retrofit==null){
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
