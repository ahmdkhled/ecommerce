package com.ahmdkhled.ecommerce.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;

public class ReviewsFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.reviews_frag,container,false);
        TextView author=v.findViewById(R.id.review_author);
        TextView details=v.findViewById(R.id.review_content);
        RatingBar ratingBar=v.findViewById(R.id.review_rating);
        return v;
    }
}
