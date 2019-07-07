package com.ahmdkhled.ecommerce.ui;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.utils.PrefManager;
import com.ahmdkhled.ecommerce.viewmodel.CheckoutViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutPaymentFragment extends Fragment {

    @BindView(R.id.cod_txt)
    TextView mCODTxt;
    @BindView(R.id.cod_detail_txt)
    TextView mCODDetailTxt;
    @BindView(R.id.card_txt)
    TextView mCardTxt;
    @BindView(R.id.card_detail_txt)
    TextView mCardDetailTxt;
    @BindView(R.id.cod_switch)
    Switch mCODSwitch;
    @BindView(R.id.card_switch)
    Switch mCardSwitch;
    @BindView(R.id.coupon_input)
    EditText mCouponEditTxt;
    @BindView(R.id.apply_coupon_btn)
    Button mApplyCouponBtn;
    @BindView(R.id.use_coupon_txt)
    TextView mUseCouponTxt;
    private CheckoutViewModel mCheckoutViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_fragment,container,false);

        ButterKnife.bind(this,view);

        setupFonts();


        mCheckoutViewModel = ViewModelProviders.of(getActivity()).get(CheckoutViewModel.class);


        mCODSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mCardSwitch.setChecked(false);
                    mCheckoutViewModel.setPaymentOption(1);
                }else mCheckoutViewModel.setPaymentOption(-1);
            }
        });

        mCardSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b){
                if(b){
                    mCheckoutViewModel.setPaymentOption(2);
                    mCODSwitch.setChecked(false);
                }else mCheckoutViewModel.setPaymentOption(-1);
            }
        });


        mApplyCouponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mCouponCode = mCouponEditTxt.getText().toString();
                if(!mCouponCode.isEmpty()){
                    if(mCouponCode.equals("mn112")){
                        mCheckoutViewModel.setCouponAmount("10");
                    }else{
                        Toast.makeText(getContext(), "invalid coupon", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Please enter a code", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void setupFonts() {
        mCODTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_bold)));
        mCODDetailTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_regular)));
        mCardTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_bold)));
        mCardDetailTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_regular)));
        mApplyCouponBtn.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_bold)));
        mUseCouponTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_regular)));
    }
}
