package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Media;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SlideShowAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Media> media;
    public SlideShowAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount(){
        return media.size();

    }

    @NonNull
    @Override
    public Object  instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.slider_layout, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        Glide.with(context).load(media.get(position)) .into(imageView);
        container.addView(view);
        return view;
    }
    @Override
    public void  destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((LinearLayout)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }


}

