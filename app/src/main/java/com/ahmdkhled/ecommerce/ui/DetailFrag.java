package com.ahmdkhled.ecommerce.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Product;

public class DetailFrag extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.details_frag,container,false);
        TextView details=v.findViewById(R.id.product_details);

        if (getActivity()!=null){
            Product product=((ProductDetail)getActivity()).product;
            details.setText(product.getDescription());

        }

        return v;
    }
}
