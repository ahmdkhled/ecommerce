package com.ahmdkhled.ecommerce.ui;

import android.app.Activity;
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
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.utils.SessionManager;
import com.ahmdkhled.ecommerce.viewmodel.AddAddressViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddressActivity extends AppCompatActivity {

    private static final String TAG = "ADD_ADDRESS_TAG";
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

    @BindView(R.id.phone_textInputLayout)
    TextInputLayout mPhoneInputLayout;
    @BindView(R.id.phone_edittext)
    AppCompatEditText mPhoneTxt;

    @BindView(R.id.zip_code_textInputLayout)
    TextInputLayout mZipCodeInputLayout;
    @BindView(R.id.add_address_btn)
    AppCompatButton mAddAddressBtn;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;


    AddAddressViewModel mAddAddressViewModel;
    private long userId = 2;
    private Address newAddress,addressEdited;
    String target = "Add new address";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        // bind views
        ButterKnife.bind(this);

        // get user id
        userId = new SessionManager(this).getId();

        // check if user wanna edit an address.
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("edit_address")){
            Log.d(TAG,"user wanna edit address");
            if(intent.getParcelableExtra("edit_address") != null){
                addressEdited = intent.getParcelableExtra("edit_address");
                fillViewsWithAddress(addressEdited);
                target = "Edit the address";
            }
        }

//        if(intent != null && intent.hasExtra("user_id")){
//            userId = intent.getLongExtra("user_id",0);
//            Log.d(TAG,"user id "+userId);
//        }

        // setup activity
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(target);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //link view model to the activity
        mAddAddressViewModel = ViewModelProviders.of(this).get(AddAddressViewModel.class);
        mAddAddressViewModel.init();


        mAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(target.equals("Edit the address")) editAddress();
                else addNewAddress();
            }
        });
    }


    private void editAddress() {
        if(inputsAreValid()){
            String firstName = mFnameTxt.getText().toString();
            String lastName = mLnameTxt.getText().toString();
            String phoneNumber = mPhoneTxt.getText().toString();
            String state = mStateTxt.getText().toString();
            String city = mCityTxt.getText().toString();
            String address_1 = mAddress1Txt.getText().toString();
            String address_2 = mAddress2Txt.getText().toString();
            int zip_code = Integer.valueOf(mZipCodeTxt.getText().toString());
            int isDefault = addressEdited.getisDefault();
            int id = addressEdited.getId();
            addressEdited = new Address(firstName,lastName,phoneNumber,state,city,zip_code,address_1,address_2,0);
            addressEdited.setId(id);
            addressEdited.setisDefault(isDefault);
            mAddAddressViewModel.editAddress(addressEdited);
            observeEditingAddressResponse();
            observeEditingAddressStatus();
        }
    }

    private void observeEditingAddressStatus() {
        mAddAddressViewModel.getIsEditing().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean)showProgressBar();
                else hideProgressBar();
            }
        });
    }

    private void observeEditingAddressResponse() {
        mAddAddressViewModel.getEditResponse().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                if(response != null) {
                    Toast.makeText(AddAddressActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    if (!response.isError()) {
                        returnToAddressActivity(addressEdited);
                    }


                }
            }
        });
    }

    // fill views with address that user want to edit.
    private void fillViewsWithAddress(Address mAddress) {
        Log.d(TAG,"first_name "+mAddress.getFirst_name());
        mFnameTxt.setText(mAddress.getFirst_name());
        mLnameTxt.setText(mAddress.getLast_name());
        mPhoneTxt.setText(mAddress.getPhone_number());
        mStateTxt.setText(mAddress.getState());
        mCityTxt.setText(mAddress.getCity());
        mAddress1Txt.setText(mAddress.getAddress_1());
        mAddress2Txt.setText(mAddress.getAddress_2());
        mZipCodeTxt.setText(mAddress.getZip_code()+"");
    }


    private void addNewAddress() {

        // first check if all field is filled out
        if(!TextUtils.isEmpty(mFnameTxt.getText()) && !TextUtils.isEmpty(mLnameTxt.getText())
                && !TextUtils.isEmpty(mAddress1Txt.getText()) && !TextUtils.isEmpty(mAddress2Txt.getText())
                && !TextUtils.isEmpty(mStateTxt.getText()) && !TextUtils.isEmpty(mCityTxt.getText())
                && !TextUtils.isEmpty(mZipCodeTxt.getText()) && !TextUtils.isEmpty(mPhoneTxt.getText())){

            newAddress = new Address(mFnameTxt.getText().toString(),mLnameTxt.getText().toString()
                    ,mPhoneTxt.getText().toString(),mStateTxt.getText().toString(),mCityTxt.getText().toString(),
                    Integer.valueOf(mZipCodeTxt.getText().toString()),mAddress1Txt.getText().toString(),mAddress2Txt.getText().toString(),0);

            /*
                obserce add address function to make an action when this process is done
             */
            Log.d(TAG,"id "+userId);
            mAddAddressViewModel.addAddress(newAddress,String.valueOf(userId));
            observeAddressAddingResponse();
            observeAddingAddressStatus();
        }

        if(TextUtils.isEmpty(mFnameTxt.getText()))mFnameInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mLnameTxt.getText()))mLnameInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mAddress1Txt.getText()))mAddress1InputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mAddress2Txt.getText()))mAddress2InputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mStateTxt.getText()))mStateInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mCityTxt.getText()))mCityInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mZipCodeTxt.getText()))mZipCodeInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mPhoneTxt.getText()))mPhoneInputLayout.setError(getString(R.string.field_is_required));



    }

    // observe response of adding new address
    private void observeAddressAddingResponse() {
        mAddAddressViewModel.getAddResponse().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                if(response != null) {
                    Log.d(TAG,"message "+response.getMessage());
                    Toast.makeText(AddAddressActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    if (!response.isError()) {
                        newAddress.setId(response.getAddress_id());
                        returnToAddressActivity(newAddress);
                    }
                }else{
//                    Log.d("address_tag","adding address response "+response.getMessage());
                }


            }
        });
    }


    // return to address activity with address added
    private void returnToAddressActivity(Address newAddress) {
        Log.d("ADDRESS_ACTIVITY_TAG","address_1 add "+newAddress.getAddress_1());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("new_address",newAddress);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    /**
     * observe adding address process status.
     * To display progress bar until address is added.
     */
    private void observeAddingAddressStatus() {
        mAddAddressViewModel.getIsAdding().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean)showProgressBar();
                else hideProgressBar();
            }
        });
    }


    private boolean inputsAreValid(){
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
        if(TextUtils.isEmpty(mStateTxt.getText()))mStateInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mCityTxt.getText()))mCityInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mZipCodeTxt.getText()))mZipCodeInputLayout.setError(getString(R.string.field_is_required));
        if(TextUtils.isEmpty(mPhoneTxt.getText()))mPhoneInputLayout.setError(getString(R.string.field_is_required));


        return false;
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        finish();

    }
}
