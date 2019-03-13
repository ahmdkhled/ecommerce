package com.ahmdkhled.ecommerce.network;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.CartResponse;
import com.ahmdkhled.ecommerce.model.Checkout;
import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.model.Ad;
import com.ahmdkhled.ecommerce.model.Category;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.model.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<Response> login(@Field("email") String email,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST(Constants.SIGNUP_URL)
    public Call<Response> signup(@Field("name") String name, @Field("email") String email,
                                 @Field("password") String password);

    @GET(Constants.CATEGORY_URL)
    Call<ArrayList<Category>> getCategories();

    @GET("cart.php")
    Call<CartResponse> getCartItems(@Query("ids") String ids, @Query("q") String q, @Query("page") String page);

    @GET ("products.php")
    Call <ArrayList<Product>> getProducts(@Query("categoryId") String categoryId,@Query("page") int page,
                                        @Query("orderBy") String sortBy,@Query("order") String sort) ;

    @GET("ads.php")
    Call<ArrayList<Ad>> getAds();

    @GET("recentlyAdded.php")
    Call<ArrayList<Product>> getRecentlyAddedProducts(@Query("page") int page);

    @GET("favorite.php")
    Call<ArrayList<Product>> getFavoriteProducts(@Query("userId") long userId);

    @FormUrlEncoded
    @POST("favorite.php")
    Call<ResponseBody> addToFavorite(@Field("productId") int productId,@Field("userId") long userId);

    @FormUrlEncoded
    @HTTP(method = "DELETE",path="favorite.php", hasBody = true)
    Call<ResponseBody> deleteFavoriteProduct(@Field("productId")long productId,
                                             @Field("userId") long userId );

    @FormUrlEncoded
    @POST(Constants.GET_ADDRESSES_URL)
    Call<List<Address>> getAddresses(@Field("id") String userId);

    @FormUrlEncoded
    @POST(Constants.ADD_ADDRESS_URL)
    Call<Response> addAddress(@Field("user_id") String userId, @Field("state") String state,
                              @Field("city") String city, @Field("zip_code") int zipCode,
                              @Field("address_1") String address1, @Field("address_2") String address2);

    @FormUrlEncoded
    @POST(Constants.DELETE_ADDRESS)
    Call<Response> deleteAddress(@Field("id") int addressId);


    @GET(Constants.GET_CHECKOUT_INFO)
    Call<Checkout> getCheckoutInfo(@Query("user_id") String userId);
    @GET("reviews.php")
    Call<ArrayList<Review>> getReviews();

    @POST("orders.php")
    @FormUrlEncoded
    Call<Order> makeOrder(@Field("orderItems") String orderItems,@Field("quantity") String quantity
            ,@Field("userId") int userId,@Field("address_id") int addressId);

    @GET("orders.php")
    Call<ArrayList<Order>> getOrders(@Query("userId") String userId);

}
