package com.martin.categoriasyproductos.ui;

import com.martin.categoriasyproductos.R;
import com.martin.categoriasyproductos.model.Category;
import com.martin.categoriasyproductos.model.NumberTextWatcher;
import com.martin.categoriasyproductos.model.Product;
import com.martin.categoriasyproductos.sqlite.DatabaseOpenHelper;

import android.content.Intent;
import android.database.SQLException;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedProductActivity extends AppCompatActivity {

    @BindView(R.id.editTextName) EditText mName;
    @BindView(R.id.editTextPrice) EditText mPrice;
    @BindView(R.id.editTextStock) EditText mStock;
    @BindView(R.id.editTextCreation) EditText mCreation;
    @BindView(R.id.editTextExpiration) EditText mExpiration;

    private Category mCategory;
    private String mProductID;
    private String mCategoryId;
    boolean doubleBackToExitPressedOnce;
    String mNewProduct;
    private DatabaseOpenHelper myDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_product);
        doubleBackToExitPressedOnce = false;
        ButterKnife.bind(this);


        Intent intent = getIntent();
        mCategoryId = intent.getStringExtra("CATEGORYID");
        Log.d("CATEGORYID",mCategoryId);
        mProductID = intent.getStringExtra("PRODID");
        Log.d("PRODID",mProductID);
        mNewProduct = intent.getStringExtra("NEW");
        Log.d("NEWPRODUCT",mNewProduct);




        myDbHelper = new DatabaseOpenHelper(this);
        try {
            // check if database exists in app path, if not copy it from assets
            myDbHelper.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            // open the database
            myDbHelper.open();
            myDbHelper.getWritableDatabase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        mPrice.addTextChangedListener(new NumberTextWatcher(mPrice,"#,##"));

    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            if(mName.getText().length()==0 || mStock.getText().length()==0 || mCreation.getText().length()==0 || mExpiration.getText().length()==0) {
                Toast.makeText(this, "Please complete the required fields", Toast.LENGTH_SHORT).show();
            }
            else {
                Product product = new Product(mProductID, mName.getText().toString(), mStock.getText().toString(),
                        mCreation.getText().toString(), mExpiration.getText().toString());
                product.setPrice(mPrice.getText().toString());
                if(mNewProduct.equals("true")) {
                    myDbHelper.createProduct(product,mCategoryId);
                }
                /*else{
                    myDbHelper.updateProduct(product);
                }*/
            }
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to save the product", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}


