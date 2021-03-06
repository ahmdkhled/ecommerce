package com.ahmdkhled.ecommerce.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.network.Network;
import com.ahmdkhled.ecommerce.utils.SessionManager;
import com.ahmdkhled.ecommerce.utils.SnackBarUtil;
import com.ahmdkhled.ecommerce.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPass;
    TextInputLayout emailIL;
    TextInputLayout passIL;
    TextView createNewAccount;
    Button loginBu;
    ProgressBar progressBar;
    String source="";
    LoginViewModel loginViewModel;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBu=findViewById(R.id.loginBU);
        loginEmail=findViewById(R.id.loginEmail);
        loginPass=findViewById(R.id.loginPass);
        emailIL=findViewById(R.id.loginEmail_IL);
        passIL=findViewById(R.id.loginPass_IL);
        createNewAccount=findViewById(R.id.createNewAccount);
        progressBar=findViewById(R.id.loginProgressBar);
        constraintLayout=findViewById(R.id.activity_login);

        source=getIntent().getStringExtra("source");
        Log.d("login","source "+source);
        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        loginBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Network.isConnected(getApplicationContext())){
                    SnackBarUtil.showSnackBar(constraintLayout);
                    return;
                }

                String mail=emailIL.getEditText().getText().toString();
                String pass=passIL.getEditText().getText().toString();
                if (validateInput()){
                    loginViewModel.login(mail,pass);
                    observeResponse();
                }

            }
        });

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });


        observeLoading();
        observeError();



    }


    private void observeResponse(){
        loginViewModel.getResponse().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                Log.d("LOGINNN","onResponse "+response.getMessage());

                if (response!=null){
                    if (response.isError()){
                        Toast.makeText(getApplicationContext(),response.getMessage()
                                ,Toast.LENGTH_SHORT)
                                .show();
                    }else {
                        SessionManager sessionManager=new SessionManager(getApplicationContext());
                        sessionManager.saveSession(response.getId(),response.getName(),response.getEmail());
                        if (source!=null&&source.equals("cartItemsActivity")){
                            Intent intent=new Intent();
                            setResult(Activity.RESULT_OK,intent);
                            finish();

                        }else{
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }else {
                    Toast.makeText(getApplicationContext(),
                            "problem while trying to sign in "
                            ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void observeLoading(){
        loginViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean!=null&&aBoolean)
                    progressBar.setVisibility(View.VISIBLE);
                else
                    progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void observeError(){
        loginViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s!=null){
                    Toast.makeText(getApplicationContext(),"error : "+s,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput(){
        String mail=emailIL.getEditText().getText().toString();
        String pass=passIL.getEditText().getText().toString();
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
