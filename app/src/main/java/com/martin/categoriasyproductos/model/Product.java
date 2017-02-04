package com.martin.categoriasyproductos.model;


import java.util.Calendar;

/**
 * Created by MartinC on 3/2/2017.
 */
public class Product {

    public String mID;
    public String mTitle;
    public String mPrice;
    public String mStock;
    public Category mCategory;
    public String mCreationDate;
    public String mExpirationDate;

    //constructor
    public Product(String id, String title, String stock, String creationDate, String expirationDate){
        this.mID = id;
        this.mTitle = title;
        this.mStock = stock;
        this.mCreationDate = creationDate;
        this.mExpirationDate = expirationDate;
    }

    public boolean isExpired(){
        String actualDate = Calendar.getInstance().getTime().toString();
        return (actualDate.equals(mExpirationDate));
    }

    public boolean hasStock(){
        return (mStock != "0");
    }

}
