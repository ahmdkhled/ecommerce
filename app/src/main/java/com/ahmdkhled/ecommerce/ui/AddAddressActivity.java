package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.viewmodel.AddAddressViewModel;
import com.ahmdkhled.ecommerce.viewmodel.AddressViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddressActivity extends AppCompatActivity {

    private static final String TAG = AddAddressActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fname_edittext)
    AppCompatEditText mFnameTxt;
    @BindView(R.id.fname_textInputLayout)
    TextInputLayout mFnameInputLayout;
    @BindView(R.id.lname_edittext)
    AppCompatEditText mLnameTxt;
    @BindView(R.id.lname_textInputLayout)
    TextInputLayout mLnameInputLayout;
    @BindView(R.id.address1_edittext)
    AppCompatEditText mAddress1Txt;
    @BindView(R.id.address1_textInputLayout)
    TextInputLayout mAddress1InputLayout;
    @BindView(R.id.address2_edittext)
    AppCompatEditText mAddress2Txt;
    @BindView(R.id.address2_textInputLayout)
    TextInputLayout mAddress2InputLayout;
    @BindView(R.id.state_edittext)
    AppCompatEditText mStateTxt;
    @BindView(R.id.state_textInputLayout)
    TextInputLayout mStateInputLayout;
    @BindView(R.id.city_edittext)
    AppCompatEditText mCityTxt;
    @BindView(R.id.city_textInputLayout)
    TextInputLayout mCityInputLayout;
    @BindView(R.id.zip_code_edittext)
    AppCompatEditText mZipCodeTxt;
    @BindView(R.id.zip_code_textInputLayout)
    TextInputLayout mZipCodeInputLayout;
    @BindView(R.id.add_address_btn)
    AppCompatButton mAddAddressBtn;


    AddAddressViewModel mAddAddressViewModel;
    private String userId = "2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        // bind views
        ButterKnife.bind(this);

        // setup toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.add_address_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //link view model to the activity
        mAddAddressViewModel = ViewModelProviders.of(this).get(AddAddressViewModel.class);
        mAddAddressViewModel.init();


        mAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewAddress();
            }
        });
    }



    private void addNewAddress() {

        // first check if all field is filled out
        if(!TextUtils.isEmpty(mFnameTxt.getText()) && !TextUtils.isEmpty(mLnameTxt.getText())
                && !TextUtils.isEmpty(mAddress1Txt.getText()) && !TextUtils.isEmpty(mAddress2Txt.getText())
                && !TextUtils.isEmpty(mStateTxt.getText()) && !TextUtils.isEmpty(mCityTxt.getText())
                && !TextUtils.isEmpty(mZipCodeTxt.getText())){

//            Address address = new Address(mStateTxt.getText().toString(),mCityTxt.getText().toString(),
//                    Integer.valueOf(mZipCodeTxt.getText().toString()),mAddress1Txt.getText().toString(),mAddress2Txt.getText().toString());

            /*
                obserce add address function to make an action when this process is done
             */
//            mAddAddressViewModel.addAddress(address,userId).observe(this, new Observer<Response>() {
//                @Override
//                public void onChanged(@Nullable Response response) {
//                    Toast.makeText(AddAddressActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            });
        }

        if(TextUtils.isEmpty(mFnameTxt.getText()))mFnameInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mLnameTxt.getText()))mLnameInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mAddress1Txt.getText()))mAddress1InputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mAddress2Txt.getText()))mAddress2InputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mStateTxt.getText()))mStateInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mCityTxt.getText()))mCityInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mZipCodeTxt.getText()))mZipCodeInputLayout.setError(getString(R.string.field_is_required));



    }


}
