package com.ahmdkhled.ecommerce.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.adapter.DetailsPageAdapter;
import com.ahmdkhled.ecommerce.adapter.ProductsImagesPagerAdapter;
import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.SlideShowAdapter;
import com.ahmdkhled.ecommerce.model.Media;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.utils.CartItemsManger;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.rd.draw.data.Indicator;

import java.util.ArrayList;

public class ProductDetail extends AppCompatActivity {
    TextView name,seller,price;
    Button addToCart;
    Toolbar toolbar;
    ViewPager viewPager,detailsViewpager;
    TabLayout tabLayout;
    SlideShowAdapter slideShowAdapter;
    PageIndicatorView indicator;
    public static final String PRODUCT_KEY="product_key";
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.productImagesPager);
        detailsViewpager = findViewById(R.id.productDetail_viewPager);
        tabLayout=findViewById(R.id.product_tabLayout);
        indicator=findViewById(R.id.productpagerIndicatorView);
        name = findViewById(R.id.product_name);
        seller = findViewById(R.id.seller);
        price = findViewById(R.id.productPrice);
        addToCart = findViewById(R.id.addToCart);

        if (getIntent()!=null &getIntent().hasExtra(PRODUCT_KEY)){
            product=getIntent().getParcelableExtra(PRODUCT_KEY);
            populateData(product);
        }

        slideShowAdapter = new SlideShowAdapter(this,product.getMedia());
        viewPager.setAdapter(slideShowAdapter);
        indicator.setAnimationType(AnimationType.WORM);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product!=null){
                    Toast.makeText(getApplicationContext(),"added to cart ",Toast.LENGTH_SHORT).show();
                    CartItemsManger cartItemsManger=new CartItemsManger(getApplicationContext());
                    cartItemsManger.saveCartItem(product.getId(),1);
                }else{
                    Toast.makeText(getApplicationContext(),"error ",Toast.LENGTH_SHORT).show();
                }
            }
            });
        DetailsPageAdapter detailsPageAdapter=new DetailsPageAdapter(getSupportFragmentManager());
        detailsViewpager.setAdapter(detailsPageAdapter);
        tabLayout.setupWithViewPager(detailsViewpager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    void populateData(Product product){
        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));
        seller.setText(product.getSellerName());
        indicator.setCount(product.getMedia().size());
    }
    void dummyProduct(){
        ArrayList<Media> media=new ArrayList<>();
        media.add(new Media(1,"http://thelondonflowerlover.files.wordpress.com/2012/02/redrose-2.jpg"));
        media.add(new Media(2,"https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/Dahlia_flower_-_colour.jpg/220px-Dahlia_flower_-_colour.jpg"));
        //product=new Product(1,1,50,200,1,"flower","12-12-2012","this is a red flower ",media);

    }


}
