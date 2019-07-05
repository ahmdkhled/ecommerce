package com.ahmdkhled.ecommerce.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Category;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterDialogFragment extends DialogFragment {

    Spinner spinner;
    CrystalRangeSeekbar crystalRangeSeekbar;
    EditText minVal;
    EditText maxVal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.filter_dialog,container,false);
        spinner=v.findViewById(R.id.categoriesSpinner);
        crystalRangeSeekbar =v.findViewById(R.id.products_seekbar);
        minVal =v.findViewById(R.id.min_value);
        maxVal=v.findViewById(R.id.max_value);
        getCategories();



        crystalRangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minVal.setText(String.valueOf(minValue));
                maxVal.setText(String.valueOf(maxValue));


            }
        });

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
                            ArrayList<String> c=new ArrayList<>();
                            for (int i = 0; i < categories.size(); i++) {
                                c.add(categories.get(i).getName());
                            }
                            ArrayAdapter<String> aa = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,c);
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
