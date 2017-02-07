package com.martin.categoriasyproductos.model;

import java.util.ArrayList;

/**
 * Created by MartinC on 3/2/2017.
 */

public class Category {

    public String mID;
    public String mTitle;
    public ArrayList<Product> mProducts;

    //constructor
    public Category(String id, String title){
        this.mID = id;
        this.mTitle = title;
        mProducts = new ArrayList<>();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getID() {
        return mID;
    }

    public void addProduct (Product product){
        mProducts.add(product);
    }

    public void removeProduct (Product product){ }

    public ArrayList<Product> getProducts(){
        return mProducts;
    }

    public void setProducts (ArrayList<Product> products){
        mProducts = products;
    }
}
