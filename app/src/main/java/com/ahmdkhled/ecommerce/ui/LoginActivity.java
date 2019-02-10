package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.ahmdkhled.ecommerce.R;
import retrofit2.Call;
import retrofit2.Callback;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPass;
    TextInputLayout emailIL;
    TextInputLayout passIL;
    Button loginBu;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBu=findViewById(R.id.loginBU);
        loginEmail=findViewById(R.id.loginEmail);
        loginPass=findViewById(R.id.loginPass);
        emailIL=findViewById(R.id.loginEmail_IL);
        passIL=findViewById(R.id.loginPass_IL);
        progressBar=findViewById(R.id.loginProgressBar);

        loginBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=loginEmail.getText().toString();
                String password=loginPass.getText().toString();
                if (validateInput(loginEmail,loginPass,emailIL,passIL)){
                    login(email,password);
                }

            }
        });

    }

    private void login(String email,String password){
        progressBar.setVisibility(View.VISIBLE);
        RetrofetClient.getApiService()
                .login(email,password)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Response r =response.body();
                        if (r!=null){
                            progressBar.setVisibility(View.GONE);
                            if (r.isError()){
                                Toast.makeText(getApplicationContext(),r.getMessage()
                                        ,Toast.LENGTH_SHORT)
                                        .show();
                            }else {
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "problem while trying to sign in "
                                    ,Toast.LENGTH_SHORT).show();
                        }
                        Log.d("LOGINN","message: "+r.getMessage());
                    }
                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Log.d("LOGINN","failed: ");
                    }
                });
    }

    private boolean validateInput(EditText Email, EditText password
                                , TextInputLayout emailIL,TextInputLayout passwordIL){
        String mail=Email.getText().toString();
        String pass=password.getText().toString();
        if (!TextUtils.isEmpty(mail)&&!TextUtils.isEmpty(pass)){
            return true;
        }
        if (TextUtils.isEmpty(mail)){
            emailIL.setError("required");
        }
        if (TextUtils.isEmpty(pass)){
            passIL.setError("required");
        }
        return false;

    }




}