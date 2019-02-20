package com.ahmdkhled.ecommerce.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.viewmodel.RegistrationViewModel;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

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


    public static final String TAG = RegistrationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // bind views
        ButterKnife.bind(this);

        // link reg view model to activity
        mRegistrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        mRegistrationViewModel.init();



        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_signup){

            // first check if all required field are fiiled
            String userFname = mFnameTxt.getText().toString();
            String userLname = mLnameTxt.getText().toString();
            String userEmail = mEmailTxt.getText().toString();
            String userPassword = mPasswordTxt.getText().toString();
            if(!TextUtils.isEmpty(userFname) && !TextUtils.isEmpty(userLname)
                    && !TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword)){

                // create an account and observe changes in response
                String userName = userFname+" "+userLname;
                mRegistrationViewModel.signUp(userName,userEmail,userPassword)
                        .observe(this, new Observer<Response>() {
                            @Override
                            public void onChanged(@Nullable Response response) {
                                Toast.makeText(RegistrationActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            // if some or all required field are not filled
            else {
                Toast.makeText(this, R.string.info_lack, Toast.LENGTH_SHORT).show();
            }
        }
    }


    }



