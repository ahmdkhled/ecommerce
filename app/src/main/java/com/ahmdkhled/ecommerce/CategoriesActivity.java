package com.ahmdkhled.ecommerce;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class CategoriesActivity  extends AppCompatActivity {

    ArrayList<Categories> categoriesArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesArrayList.add(new Categories("https://ecommerceg.000webhostapp.com/media/p.jpg"));
        categoriesArrayList.add(new Categories("https://ecommerceg.000webhostapp.com/media/p2.jpg"));
        categoriesArrayList.add(new Categories("https://ecommerceg.000webhostapp.com/media/p3.jpg"));
        categoriesArrayList.add(new Categories("https://ecommerceg.000webhostapp.com/media/p4.jpg"));
        categoriesArrayList.add(new Categories("https://ecommerceg.000webhostapp.com/media/p5.jpg"));


        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        CategoryAdapter adapter = new CategoryAdapter(categoriesArrayList, this);


        RecyclerView.LayoutManager layoutManage = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManage);

        recyclerView.setAdapter(adapter);


    }
}
