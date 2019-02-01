package com.ahmdkhled.ecommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Product  {

    @SerializedName("product_id")
    private int id;
    @SerializedName("product_name")
    private String name;
    @SerializedName("product_marketId")
    private int marketId;
    @SerializedName("product_quantity")
    private int quantity;
    @SerializedName("product_price")
    private int price;
    @SerializedName("product_categoryId")
    private int categoryId;
    @SerializedName("product_date")
    private String date;
    @SerializedName("product_description")
    private String description;
    @SerializedName("media")
    private ArrayList<Media> media;

    public Product(int id, int marketId, int quantity, int price, int categoryId,
                   String name, String date, String description,ArrayList<Media> media) {
        this.id = id;
        this.marketId = marketId;
        this.quantity = quantity;
        this.price = price;
        this.categoryId = categoryId;
        this.name = name;
        this.date = date;
        this.description = description;
        this.media=media;
    }

    public Product(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null) return false;
        if (!(obj instanceof Product)) return false;
        Product p= (Product) obj;
        return p.getId() == this.getId();
    }
}
