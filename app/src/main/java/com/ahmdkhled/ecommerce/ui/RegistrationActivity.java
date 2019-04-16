package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.network.Network;
import com.ahmdkhled.ecommerce.utils.SnackBarUtil;
import com.ahmdkhled.ecommerce.viewmodel.RegistrationViewModel;

public class RegistrationActivity extends AppCompatActivity  {

    @BindView(R.id.fname_edittext_reg)
    AppCompatEditText mFnameTxt;
    @BindView(R.id.lname_edittext_reg)
    AppCompatEditText mLnameTxt;
    @BindView(R.id.email_edittext_reg)
    AppCompatEditText mEmailTxt;
    @BindView(R.id.password_edittext_reg)
    AppCompatEditText mPasswordTxt;
    @BindView(R.id.btn_signup)
    AppCompatButton mRegisterBtn;
    @BindView(R.id.login_txt)
    TextView mLoginTxt;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    RegistrationViewModel mRegistrationViewModel;
    Toast mToast;

    ConstraintLayout constraintLayout;
    public static final String TAG = RegistrationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        constraintLayout = findViewById(R.id.registration_activity);




        // bind views
        ButterKnife.bind(this);

        // link reg view model to activity
        mRegistrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        mRegistrationViewModel.init();


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Network.isConnected(getApplicationContext())) {
                    SnackBarUtil.showSnackBar(constraintLayout);
                    return;
                }
                signUp();
                observeRegistrationResponse();
                observeRegistrationStatus();
            }
        });


    }


    private void signUp() {
        // first check if all required field are fiiled
        String userFname = mFnameTxt.getText().toString();
        String userLname = mLnameTxt.getText().toString();
        String userEmail = mEmailTxt.getText().toString();
        String userPassword = mPasswordTxt.getText().toString();
        if(!TextUtils.isEmpty(userFname) && !TextUtils.isEmpty(userLname)
                && !TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword)){

            // create an account and observe changes in response
            String userName = userFname+" "+userLname;
            mRegistrationViewModel.signUp(userName,userEmail,userPassword);
        }

        // if some or all required field are not filled
        else {
            showToast(getString(R.string.info_lack));
        }
    }


    private void observeRegistrationResponse(){
        /**
         * observe response of sign up process to update UI
         */
        mRegistrationViewModel.getSignUpResponse().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                if(response != null) {
                    showToast(response.getMessage());
                    if(!response.isError()) finish();
                }else {
                    showToast(getString(R.string.error_message));
                }
            }
        });
    }

    private void observeRegistrationStatus() {
        /**
         * observe changes in isProcessing method
         *if isProcessing is true this means that sign up is processing so progress bar should be shown
         * otherwise means that sign up is finished so progress bar should be hidden
         */

        mRegistrationViewModel.isProcessing().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d("reg_mvvm","isProcessing is "+aBoolean);
                if (aBoolean)showProgressBar();
                else hideProgressBar();
            }
        });
    }


    public void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public void showToast(String message){
        if(mToast != null) mToast.cancel();
        mToast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
        mToast.show();
    }



    }



