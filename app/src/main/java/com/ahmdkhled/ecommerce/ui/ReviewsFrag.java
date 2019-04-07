package com.ahmdkhled.ecommerce.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
    ProgressBar progressBar;
    int productId=-1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.reviews_frag,container,false);
        recyclerView=v.findViewById(R.id.reviews_RecyclerView);
        progressBar=v.findViewById(R.id.reviews_progressbar);

        productId=((ProductDetail)getActivity()).product.getId();

        getReviews(productId);
        return v;
    }

    void getReviews(int productId){
        if (productId==-1){
            Toast.makeText(getContext(),
                    "error getting revies for this product"
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        RetrofetClient.getApiService()
                .getReviews(String.valueOf(productId))
                .enqueue(new Callback<ArrayList<Review>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                        ArrayList<Review> reviews= response.body();
                        Log.d("REVIEWS", "onResponse: "+reviews.size());
                        reviewsAdapter=new ReviewsAdapter(getContext(),reviews);
                        recyclerView.setAdapter(reviewsAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                        Log.d("REVIEWS", "onFailure: "+t.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }

                });
    }
}
