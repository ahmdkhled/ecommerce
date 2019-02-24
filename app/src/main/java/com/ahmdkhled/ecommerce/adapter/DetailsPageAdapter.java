package com.ahmdkhled.ecommerce.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ahmdkhled.ecommerce.ui.DetailFrag;
import com.ahmdkhled.ecommerce.ui.ReviewsFrag;

public class DetailsPageAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments={new DetailFrag(),new ReviewsFrag()};
    String[] tabs={"Overview","Reviews"};

    public DetailsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
