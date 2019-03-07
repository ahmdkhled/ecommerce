package com.ahmdkhled.ecommerce.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.ReviewsAdapter;
import com.ahmdkhled.ecommerce.model.Review;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsFrag extends Fragment {


    ReviewsAdapter reviewsAdapter;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.reviews_frag,container,false);
        recyclerView=v.findViewById(R.id.reviews_RecyclerView);

        getReviews();
        return v;
    }

    void getReviews(){
        RetrofetClient.getApiService()
                .getReviews()
                .enqueue(new Callback<ArrayList<Review>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                        ArrayList<Review> reviews= response.body();
                        reviewsAdapter=new ReviewsAdapter(getContext(),reviews);
                        recyclerView.setAdapter(reviewsAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Review>> call, Throwable t) {

                    }

                });
    }
}
