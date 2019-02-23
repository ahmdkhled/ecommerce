package com.ahmdkhled.ecommerce;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ahmdkhled.ecommerce.model.Category;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterDialogFragment extends DialogFragment {

    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.filter_dialog,container,false);
        spinner=v.findViewById(R.id.categoriesSpinner);
        getCategories();
        return v;
    }

    void getCategories(){
        RetrofetClient.getApiService()
                .getCategories()
                .enqueue(new Callback<ArrayList<Category>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                        ArrayList<Category> categories=response.body();
                        if (categories!=null){
                            ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,categories);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(aa);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                        Log.d("tag","msg");

                    }
                });

    }

}
