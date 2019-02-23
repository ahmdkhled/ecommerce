package com.ahmdkhled.ecommerce.model;

public class Category {
    private int id ;
    private String name;
    private String image;
    private String icon;

    public Category(int id, String name, String image, String icon) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}