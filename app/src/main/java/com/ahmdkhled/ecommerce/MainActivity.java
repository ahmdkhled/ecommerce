package com.ahmdkhled.ecommerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.image);

        Picasso.get().load("https://ecommerceg.000webhostapp.com/media/p.jpg").into(imageView);


        //Intent intent = new Intent(this,CategoriesActivity.class);
        //startActivity(intent);

    }
}
