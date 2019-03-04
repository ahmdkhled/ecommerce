package com.ahmdkhled.ecommerce.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Checkout;
import com.ahmdkhled.ecommerce.model.User;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    TextInputLayout fullName;
    TextInputLayout emailAddress;
    TextInputLayout phoneNumber;
    TextInputLayout country;
    TextInputLayout city;
    TextInputLayout Address1;
    TextInputLayout Address2;
    Button next;
    EditText name;
    EditText email;
    EditText phonenum;
    EditText countryEditText;
    EditText cityEditText;
    EditText add1;
    EditText add2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        fullName = findViewById(R.id.full_name);
        emailAddress = findViewById(R.id.emailaddress);
        phoneNumber = findViewById(R.id.phone_num);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        Address1 = findViewById(R.id.address1);
        Address2 = findViewById(R.id.address2);
        next =  findViewById(R.id.next_button);
         name = (EditText) findViewById(R.id.name_Edittext);
         email = (EditText) findViewById(R.id.email_Edittext);
         phonenum = (EditText) findViewById(R.id.phone_num_edittext);
         countryEditText = (EditText) findViewById(R.id.country_edittext);
         cityEditText = (EditText) findViewById(R.id.city_edittext);
         add1 = (EditText) findViewById(R.id.add1_edittext);
         add2 = (EditText) findViewById(R.id.add2_edittext);


        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyboard();

                String fullname = fullName.getEditText().getText().toString();
                String emailaddress = emailAddress.getEditText().getText().toString();
                String phonenumber = phoneNumber.getEditText().getText().toString();
                String Country = country.getEditText().getText().toString();
                String City = city.getEditText().getText().toString();
                String address1 = Address1.getEditText().getText().toString();

                if(validateInput(fullname,emailaddress,phonenumber,Country,City,address1)){
                    Intent intent = new Intent(getApplicationContext(),OrderSummaryActivity.class);
                    Address address=new Address("daqahlia","mansoura",12345,"123 code academy","");
                    List<Address> addresses=new ArrayList<>();
                    addresses.add(address);
                    User user= new User("amribrahim","amribrahim@gmail.com","01092277653");
                    Checkout checkout = new Checkout(addresses,user);
                    intent.putExtra("add_key",checkout);
                    startActivity(intent);
                }

            }
        });

        getCheckoutDetails();
    }

    private void getCheckoutDetails(){
        SessionManager sessionManager=new SessionManager(this);
        long userId=sessionManager.getId();
        if (userId==-1){
            Intent intent=new Intent(this,LoginActivity.class);
            intent.putExtra("source",this.getClass().getSimpleName());
            startActivity(intent);
            finish();
            return;
        }
        RetrofetClient.getApiService()
                .getCheckoutInfo(String.valueOf(userId))
                .enqueue(new Callback<Checkout>() {
                    @Override
                    public void onResponse(Call<Checkout> call, Response<Checkout> response) {
                        Checkout checkout =response.body();
                        Log.d("string",checkout.getmUser().getName());
                        if (checkout!=null){
                            populatecheckout(checkout);
                        }else{
                            Toast.makeText(CheckoutActivity.this, "there is no address", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Checkout> call, Throwable t) {
                        Toast.makeText(CheckoutActivity.this, "error getting address", Toast.LENGTH_SHORT).show();.
                    }
                });
    }

    private void populatecheckout(Checkout checkout) {
        name.setText(checkout.getmUser().getName());
        email.setText(checkout.getmUser().getEmail());
        if(checkout.getmAddress().size() == 0){
            Toast.makeText(CheckoutActivity.this,"there is no address",Toast.LENGTH_SHORT).show();
        }else {
            countryEditText.setText(checkout.getmAddress().get(0).getState());
            cityEditText.setText(checkout.getmAddress().get(0).getCity());
            add1.setText(checkout.getmAddress().get(0).getAddress1());
            add2.setText(checkout.getmAddress().get(0).getAddress2());
        }

    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public boolean validateInput (String fullname,String emailaddress,String phonenumber,String Country,String City,String address1 ){
        boolean ok = true;
        Log.d("EMAIL",emailaddress+"   "+validateEmail(emailaddress));
        if (!validateEmail(emailaddress)) {
            emailAddress.setError("Not a valid email address!");
            ok=false;
        } else {
            emailAddress.setErrorEnabled(false);
        }
        if (fullname.matches("")){
            fullName.setError("plz enter your name");
            ok=false;
        } else {
            fullName.setErrorEnabled(false);
        }
        if (phonenumber.equals("")){
            phoneNumber.setError("plz enter your phone number");
            ok=false;
        } else {
            phoneNumber.setErrorEnabled(false);
        }
        if(Country.matches("")){
            country.setError("plz enter your country");
            ok=false;
        } else {
            country.setErrorEnabled(false);
        }
        if(City.matches("")){
            city.setError("plz enter your city");
            ok=false;
        } else {
            city.setErrorEnabled(false);
        }
        if(address1.matches("")){
            Address1.setError("plz enter your Address 1");
            ok=false;
        } else {
            Address1.setErrorEnabled(false);
        }
        return ok;
    }
}
