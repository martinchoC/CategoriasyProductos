package com.martin.categoriasyproductos.model;

import java.util.LinkedList;

/**
 * Created by MartinC on 3/2/2017.
 */

public class Category {

    public String mID;
    public String mTitle;
    public LinkedList<Product> mProducts;

    //constructor
    public Category(String id, String title){
        this.mID = id;
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void addProduct (Product product){

    }

    public void removeProduct (Product product){

    }
}
