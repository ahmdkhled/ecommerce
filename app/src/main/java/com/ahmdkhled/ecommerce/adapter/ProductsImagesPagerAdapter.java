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

public class ProductsImagesPagerAdapter extends PagerAdapter {
    private Context context;
    LayoutInflater inflater;
    public int[] images = {
            R.drawable.picture,
//            R.drawable.picture2,
    };
    public ProductsImagesPagerAdapter(Context context){
        this.context = context;

    }
    @Override
    public int getCount(){
        return images.length;

    }
    @Override
    public boolean isViewFromObject(View view, Object object){
        return (view==(LinearLayout)object);
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        inflater =inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_images_layout,container,false);
        ImageView image =  view.findViewById(R.id.productSliderImg);
        image.setImageResource(images[position]);
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);

    }

}
