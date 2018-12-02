package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Ad;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainSliderAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Ad> ads;

    public MainSliderAdapter(Context context, ArrayList<Ad> ads) {
        this.context = context;
        this.ads = ads;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v= LayoutInflater.from(context).inflate(R.layout.main_slider_layout,container,false);
        ImageView image=v.findViewById(R.id.mainSliderImage);
        Glide.with(context).load(ads.get(position).getImage())
                .into(image);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return ads.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}
