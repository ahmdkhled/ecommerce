package com.ahmdkhled.ecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ahmdkhled.ecommerce.R;

import java.util.ArrayList;

public class ProductsActivity  extends AppCompatActivity {

    ArrayList<Product> productsList=new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler_view);
        productsList=new ArrayList<>();


        Product p=new Product("","15","50","general");
        Product p1=new Product("","20","20","general");
        Product p2=new Product("","30","40","general");
        Product p3=new Product("","60","50","general");
        Product p4=new Product("","60","70","general");
        Product p5=new Product("","25","40","general");
        Product p6=new Product("","60","70","general");


        productsList.add(p);
        productsList.add(p1);
        productsList.add(p2);
        productsList.add(p3);
        productsList.add(p4);
        productsList.add(p5);
        productsList.add(p6);


        ProductAdapter productAdapter=new ProductAdapter(productsList,this);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));






    }


}
