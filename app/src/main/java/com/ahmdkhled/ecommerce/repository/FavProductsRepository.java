package com.ahmdkhled.ecommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavProductsRepository {


    private MutableLiveData<ArrayList<Product>> favProducts=new MutableLiveData<>();
    private static FavProductsRepository favProductsRepository;

    public static FavProductsRepository getInstance(){
        if (favProductsRepository==null)
            favProductsRepository=new FavProductsRepository();
        return favProductsRepository;
    }

    public MutableLiveData<ArrayList<Product>> getFavProducts(Context context){
        Log.d("FAVORITEE","quering products");
        long userId=new SessionManager(context).getId();
        if (userId>-1) {
            RetrofetClient.getApiService()
                    .getFavoriteProducts(userId)
                    .enqueue(new Callback<ArrayList<Product>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                            if (response.isSuccessful()){
                                ArrayList<Product> products = response.body();
                                favProducts.setValue(products);
                            }

                        }

                        @Override
                        public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                            Log.d("FAVORITEE", "error " + t.getMessage());
                        }
                    });
        }
        return favProducts;
    }
}
