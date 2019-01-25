package com.ahmdkhled.ecommerce.model;

import com.google.gson.annotations.SerializedName;

public class Media {

    @SerializedName("image_id")
    private long id;
    @SerializedName("image_url")
    private String url;

    public Media(long id, String url) {
        this.id = id;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
