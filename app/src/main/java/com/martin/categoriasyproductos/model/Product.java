package com.martin.categoriasyproductos.model;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MartinC on 3/2/2017.
 */
public class Product {

    private String mID;
    private String mTitle;
    private String mPrice;
    private String  mStock;
    private String mCreationDate;
    private String mExpirationDate;
    private Category mCategory;

    //constructor
    public Product(String id, String title, String stock, String creationDate, String expirationDate){
        this.mID = id;
        this.mTitle = title;
        this.mStock = stock;
        this.mCreationDate = creationDate;
        this.mExpirationDate = expirationDate;
    }

    public void setCategory(Category category){
        mCategory = category;
    }

    public Category getCategory(){
        return mCategory;
    }

    public String getID(){
        return mID;
    }

    public Boolean isExpired(){
        Boolean expiredProduct= false;
        SimpleDateFormat formatter  = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date(); //Actual date
        Date toCompare;
        try {
            toCompare = formatter.parse(this.getExpirationDate());
            expiredProduct= toCompare.before(today);
            Log.d("EXPIRED", getTitle()+" expired? "+expiredProduct);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return expiredProduct;
    }

    public Boolean hasStock(){
        return (Integer.valueOf(mStock)>0);
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getStock() {
        return mStock;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public String getExpirationDate() {
        return mExpirationDate;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

}
