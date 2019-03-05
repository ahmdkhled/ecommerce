package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder>{

    Context context;
    private ArrayList<Review> reviewsList;

    public ReviewsAdapter(Context context, ArrayList<Review> reviewsList) {
        this.context = context;
        this.reviewsList = reviewsList;
    }

    @NonNull
    @Override
    public ReviewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.review_row,parent,false);
        return new ReviewsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsHolder holder, int position) {
        Review review=reviewsList.get(position);
        holder.author.setText(review.getUserName());
        holder.comment.setText(review.getComment());
        holder.ratingBar.setRating(review.getRating());
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    class ReviewsHolder extends RecyclerView.ViewHolder{

        TextView author,comment;
        RatingBar ratingBar;
        public ReviewsHolder(@NonNull View itemView) {
            super(itemView);
            author=itemView.findViewById(R.id.review_author);
            comment=itemView.findViewById(R.id.review_content);
            ratingBar=itemView.findViewById(R.id.review_rating);
            //Drawable stars =  ratingBar.getProgressDrawable();
            //stars.setColorFilter(Color.parseColor("#FFEBDB24"), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
