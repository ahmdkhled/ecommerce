package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.adapter.DetailsPageAdapter;
import com.ahmdkhled.ecommerce.adapter.ProductsImagesPagerAdapter;
import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.SlideShowAdapter;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Media;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.utils.CartItemsManger;
import com.ahmdkhled.ecommerce.utils.SessionManager;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.rd.draw.data.Indicator;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetail extends AppCompatActivity {
    TextView name,seller,price;
    Button addToCart;
    Toolbar toolbar;
    ViewPager viewPager,detailsViewpager;
    TabLayout tabLayout;
    SlideShowAdapter slideShowAdapter;
    PageIndicatorView indicator;
    FloatingActionButton favorite;
    public static final String PRODUCT_KEY="product_key";
    public Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white)
                , PorterDuff.Mode.SRC_ATOP);
        viewPager = findViewById(R.id.productImagesPager);
        detailsViewpager = findViewById(R.id.productDetail_viewPager);
        tabLayout=findViewById(R.id.product_tabLayout);
        indicator=findViewById(R.id.productpagerIndicatorView);
        name = findViewById(R.id.product_name);
        seller = findViewById(R.id.seller);
        price = findViewById(R.id.productPrice);
        addToCart = findViewById(R.id.addToCart);
        favorite= findViewById(R.id.fav_fab);


        if (getIntent()!=null && getIntent().hasExtra(PRODUCT_KEY)){
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
                    CartItemsManger cartItemsManger=new CartItemsManger(getApplicationContext());
                    ArrayList<CartItem> cartItems=cartItemsManger.getCartItems();
                    if (cartItems!=null&&cartItems.contains(new CartItem(product,1))){
                        Toast.makeText(ProductDetail.this, "already exist", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"added to cart ",Toast.LENGTH_SHORT).show();
                        cartItemsManger.saveCartItem(product.getId(),1);
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"error ",Toast.LENGTH_SHORT).show();
                }
            }
            });
        DetailsPageAdapter detailsPageAdapter=new DetailsPageAdapter(getSupportFragmentManager());
        detailsViewpager.setAdapter(detailsPageAdapter);
        tabLayout.setupWithViewPager(detailsViewpager);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product!=null){
                    SessionManager sessionManager=new SessionManager(getApplicationContext());
                    addToFavorite(product.getId(),sessionManager.getId());
                }
            }
        });

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

    void addToFavorite(int productId,long userId){
        RetrofetClient.getApiService()
                .addToFavorite(productId,userId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(ProductDetail.this, "added to favorite",
                                    Toast.LENGTH_SHORT).show();
                            favorite.setBackgroundResource(R.drawable.ic_star_black_24dp);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ProductDetail.this, "error adding to favorite"
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void populateData(Product product){
        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));
        seller.setText(product.getSellerName());
        if (product.getMedia()!=null&&!product.getMedia().isEmpty())
        indicator.setCount(product.getMedia().size());
    }

    void dummyProduct(){
        ArrayList<Media> media=new ArrayList<>();
        media.add(new Media(1,"http://thelondonflowerlover.files.wordpress.com/2012/02/redrose-2.jpg"));
        media.add(new Media(2,"https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/Dahlia_flower_-_colour.jpg/220px-Dahlia_flower_-_colour.jpg"));
        //product=new Product(1,1,50,200,1,"flower","12-12-2012","this is a red flower ",media);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        final MenuItem menuItem = menu.findItem(R.id.detail_cart);
        View actionView = menuItem.getActionView();
        TextView badgeTV=actionView.findViewById(R.id.cartCount);
        setupBadge(badgeTV);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.detail_cart){
            Intent intent=new Intent(this,CartActivity.class);
            startActivity(intent);
        }
        return true;
    }

    void setupBadge(TextView countTV){
        CartItemsManger cartItemsManger=new CartItemsManger(this);
        ArrayList<CartItem> cartItems=cartItemsManger.getCartItems();
        if (cartItems==null||cartItems.isEmpty()){
            countTV.setVisibility(View.GONE);
        }else{
            countTV.setVisibility(View.VISIBLE);
            countTV.setText(String.valueOf(cartItems.size()));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
