package com.ahmdkhled.ecommerce.ui;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.utils.PrefManager;
import com.ahmdkhled.ecommerce.viewmodel.CheckoutViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutShippingFragment extends Fragment {

    @BindView(R.id.feature_one_txt)
    TextView mFeatureOneTxt;
    @BindView(R.id.feature_one_value)
    TextView mFeatureOneValueTxt;
    @BindView(R.id.feature_two_txt)
    TextView mFeatureTwoTxt;
    @BindView(R.id.feature_two_value)
    TextView mFeatureTwoValueTxt;
    @BindView(R.id.feature_one_switch)
    Switch mFeatureOneSwitch;
    @BindView(R.id.feature_two_switch)
    Switch mFeatureTwoSwitch;



    private CheckoutViewModel mCheckoutViewModel;
    private PrefManager manager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shipping_fragment,container,false);

        // bind views
        ButterKnife.bind(this,view);

        // setup fonts
        setFonts();

        manager = new PrefManager(getContext());

        // link to checkout view model
        mCheckoutViewModel = ViewModelProviders.of(getActivity()).get(CheckoutViewModel.class);
//        mCheckoutViewModel.init();


        mFeatureOneSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mFeatureTwoSwitch.setChecked(false);
                    mCheckoutViewModel.setShippingComplete(true);
                    manager.setShippingOption("1");
                }
            }
        });

        mFeatureTwoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b){
                if(b){
                    mCheckoutViewModel.setShippingComplete(true);
                    mFeatureOneSwitch.setChecked(false);
                    manager.setShippingOption("2");
                }
            }
        });









        return view;
    }

    private void setFonts() {
        mFeatureOneTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_bold)));
        mFeatureOneValueTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_regular)));
        mFeatureTwoTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_bold)));
        mFeatureTwoValueTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_regular)));
    }




}
