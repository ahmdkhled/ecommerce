package com.ahmdkhled.ecommerce.adapter;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;

public class CheckoutViewPagerAdapter  extends FragmentPagerAdapter{

    private Fragment[] fragments;
    private Context context;
    private String[] pageTitles;
    private int tabPosition;

    public CheckoutViewPagerAdapter(Context context, FragmentManager fm, Fragment[]fragments,
                                    String[] pageTitles) {
        super(fm);
        this.fragments = fragments;
        this.context = context;
        this.pageTitles = pageTitles;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab,null,false);
        TextView tabTitle = (TextView) view.findViewById(R.id.tab_title);
        tabTitle.setText(pageTitles[position]);
        tabTitle.setTypeface(Typeface.createFromAsset(context.getAssets()
                ,context.getString(R.string.roboto_bold)));
        if(position == 0)
            tabTitle.setBackgroundResource(R.drawable.selected_custom_tab);
        else tabTitle.setBackgroundResource(R.drawable.unselected_custom_tab);

        return view;
    }

    public View getTabView(int position, boolean forward){
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab,null,false);
        TextView tabTitle = (TextView) view.findViewById(R.id.tab_title);
        tabTitle.setText(pageTitles[position]);
        tabTitle.setTypeface(Typeface.createFromAsset(context.getAssets()
                ,context.getString(R.string.roboto_bold)));
        if(forward)
            tabTitle.setBackgroundResource(R.drawable.selected_custom_tab);
        else tabTitle.setBackgroundResource(R.drawable.unselected_custom_tab);

        return view;
    }




}
