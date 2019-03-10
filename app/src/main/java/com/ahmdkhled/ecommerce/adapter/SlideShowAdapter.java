package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Media;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.util.ArrayList;

public class SlideShowAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Media> media;

    public SlideShowAdapter(Context context, ArrayList<Media> media) {
        this.context = context;
        this.media = media;
    }

    @Override
    public int getCount(){
        if (media==null)
            return 0;
        return media.size();

    }

    @NonNull
    @Override
    public Object  instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d("countt ","cont "+media.get(position).getUrl());
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.slider_layout, container, false);
        ImageView imageView = view.findViewById(R.id.mainSliderImage);
        Glide.with(context).load(media.get(position).getUrl()).into(imageView);
        container.addView(view);
        return view;
    }
    @Override
    public void  destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }


}

