package com.martin.categoriasyproductos.model;

import java.util.ArrayList;

/**
 * Created by MartinC on 3/2/2017.
 */

public class Category {

    private Integer mID;
    private String mTitle;
    private ArrayList<Product> mProducts;

    //constructor
    public Category(Integer id, String title){
        this.mID = id;
        this.mTitle = title;
        mProducts = new ArrayList<>();
    }

    public String getTitle() {
        return mTitle;
    }

    public Integer getID() {
        return mID;
    }

    public ArrayList<Product> getProducts(){
        return mProducts;
    }

    public void setProducts (ArrayList<Product> products){
        mProducts = products;
    }
}
