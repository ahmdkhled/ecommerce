package com.ahmdkhled.ecommerce.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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


    public static final String TAG = RegistrationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // bind views
        ButterKnife.bind(this);

        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_signup){
            String userFname = mFnameTxt.getText().toString();
            String userLname = mLnameTxt.getText().toString();
            String userEmail = mEmailTxt.getText().toString();
            String userPassword = mPasswordTxt.getText().toString();
            if(!TextUtils.isEmpty(userFname) && !TextUtils.isEmpty(userLname)
                    && !TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword)){
                signup(userFname,userLname,userEmail,userPassword);
            }else {
                Toast.makeText(this, R.string.info_lack, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void signup(String fName, String lName, String email, String password){
        HashMap<String,String> map = new HashMap<>();
        map.put("firstname",fName);
        map.put("lastname",lName);
        map.put("email",email);
        map.put("password",password);

        Call<Response> call = RetrofetClient.getApiService().signup(map);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response regesterationResponse = response.body();
                Log.d(TAG,"message is "+regesterationResponse.getMessage());
                if (!regesterationResponse.isError()){
                    Toast.makeText(RegistrationActivity.this,
                            regesterationResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else Toast.makeText(RegistrationActivity.this,
                        regesterationResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(TAG,"onFailure "+t.getMessage());
            }
        });
    }


}
