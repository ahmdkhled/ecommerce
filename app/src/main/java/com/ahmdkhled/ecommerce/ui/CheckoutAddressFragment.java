package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.viewmodel.CheckoutViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutAddressFragment extends Fragment {


    private static final String TAG = "ADDRESS_FRAGMENT";
    @BindView(R.id.checkout_address_name)
    TextView mAddressNameTxt;
    @BindView(R.id.checkout_address_address_1)
    TextView mAddress1Text;
    @BindView(R.id.checkout_address_address_2)
    TextView mAddress2Text;
    @BindView(R.id.checkout_address_mobile_number)
    TextView mPhoneNumberTxt;
    @BindView(R.id.edit_address_image_btn)
    ImageButton mEditAddressBtn;
    @BindView(R.id.delete_address_image_btn)
    ImageButton mDeleteAddressBtn;
    @BindView(R.id.add_address_txt)
    TextView mAddAddressTxt;
    @BindView(R.id.address_fragment_layout)
    ConstraintLayout mAddressFragmentLayout;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;



    CheckoutViewModel mCheckoutViewModel;
    private String userId = "37";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_fragment,container,false);

        // bind views
        ButterKnife.bind(this,view);

        // set Roboto font to text view
        setupViewFont();

        // view model
        mCheckoutViewModel = ViewModelProviders.of(this).get(CheckoutViewModel.class);

        // load address
        mCheckoutViewModel.loadAddress(userId);

        // observe loading address response
        mCheckoutViewModel.getAddress().observe(this, new Observer<List<Address>>() {
            @Override
            public void onChanged(@Nullable List<Address> addresses) {
                Log.d(TAG,"get address");
                if(addresses != null && addresses.size() > 0){
                    fillAddressFields(addresses.get(0));
                }else Log.d(TAG,"null address list");
            }
        });


        // observe loading address status
        mCheckoutViewModel.getIsAddressLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    showProgressBar();
                    hideLayout();
                }else {
                    hideProgressBar();
                    showLayout();
                }
            }
        });





        return view;
    }

    private void fillAddressFields(Address address) {
        mAddressNameTxt.setText(address.getFirst_name()+" "+address.getLast_name());
        mAddress1Text.setText(address.getAddress_1());
        mAddress2Text.setText(address.getAddress_2());
        mPhoneNumberTxt.setText(address.getPhone_number());
    }

    private void setupViewFont() {
        mAddressNameTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_medium)));
        mAddress1Text.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_light)));
        mAddress2Text.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_light)));
        mPhoneNumberTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_black)));
        mAddAddressTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_black)));





    }


    public void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public void showLayout(){
        mAddressFragmentLayout.setVisibility(View.VISIBLE);
    }

    public void hideLayout(){
        mAddressFragmentLayout.setVisibility(View.INVISIBLE);
    }
}
