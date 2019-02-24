package com.ahmdkhled.ecommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.repository.FavProductsRepository;

import java.util.ArrayList;

public class FavProductsViewModel extends ViewModel{

    private MutableLiveData<ArrayList<Product>> favProducts;
    private MutableLiveData<Boolean> isLoading;


    public MutableLiveData<ArrayList<Product>> getFavProducts(Context context) {
        if (favProducts==null){
            favProducts= FavProductsRepository.getInstance().getFavProducts(context);
        }
        return favProducts;
    }

    public MutableLiveData<Boolean> isLoading() {
        return FavProductsRepository.getInstance().isLoading();
    }
}
