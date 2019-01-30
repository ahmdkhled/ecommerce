package com.ahmdkhled.ecommerce.ui;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.network.ApiService;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

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

        mAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewAddress();
            }
        });
    }

    private void AddNewAddress() {
        if(isInputsValid()){
            Call<Response> call = RetrofetClient.getApiService().addAddress("2",mStateTxt.getText()
                    .toString(),mCityTxt.getText().toString()
            ,Integer.valueOf(mZipCodeTxt.getText().toString()),mAddress1Txt.getText().toString(),mAddress2Txt.getText().toString());
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(AddAddressActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.d(TAG,"failure message : "+t.getMessage());
                }
            });
        }
    }

    private boolean isInputsValid() {
        if(!TextUtils.isEmpty(mFnameTxt.getText()) && !TextUtils.isEmpty(mLnameTxt.getText())
                && !TextUtils.isEmpty(mAddress1Txt.getText()) && !TextUtils.isEmpty(mAddress2Txt.getText())
                && !TextUtils.isEmpty(mStateTxt.getText()) && !TextUtils.isEmpty(mCityTxt.getText())
                && !TextUtils.isEmpty(mZipCodeTxt.getText())){
            return true;
        }

        if(TextUtils.isEmpty(mFnameTxt.getText()))mFnameInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mLnameTxt.getText()))mLnameInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mAddress1Txt.getText()))mAddress1InputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mAddress2Txt.getText()))mAddress2InputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mStateTxt.getText()))mAddress2InputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mCityTxt.getText()))mAddress2InputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mZipCodeTxt.getText()))mAddress2InputLayout.setError(getString(R.string.field_is_required));

        return false;
    }
}
