package com.martin.categoriasyproductos.ui;

import com.martin.categoriasyproductos.R;
import com.martin.categoriasyproductos.model.Product;
import com.martin.categoriasyproductos.sqlite.DatabaseOpenHelper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedProductActivity extends AppCompatActivity {

    @BindView(R.id.editTextName) EditText mName;
    @BindView(R.id.editTextPrice) EditText mPrice;
    @BindView(R.id.editTextStock) EditText mStock;
    @BindView(R.id.editTextCreation) EditText mCreation;
    @BindView(R.id.editTextExpiration) EditText mExpiration;

    private String mProductID;
    private int mCategoryId;
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
        mCategoryId = intent.getIntExtra("CATEGORYID",0);
        mProductID = intent.getStringExtra("PRODID");
        mNewProduct = intent.getStringExtra("NEW");

        myDbHelper = new DatabaseOpenHelper(this);
        manageDatabase();

        hideKeyboard(mCreation);
        dateClickListener(mCreation);

        hideKeyboard(mExpiration);
        dateClickListener(mExpiration);

        if(mNewProduct.equals("false")){
            Product product = myDbHelper.getProduct(mProductID);
            populateFields(product);
        }
    }

    private void manageDatabase() {
        try {
            // check if database exists in app path, if not copy it from assets
            myDbHelper.create();
        }
        catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            // open the database
            myDbHelper.open();
            myDbHelper.getWritableDatabase();
        }
        catch (SQLException sqle) {
            throw sqle;
        }
    }

    private void hideKeyboard(EditText editText){
        editText.setInputType(InputType.TYPE_NULL);
        editText.setShowSoftInputOnFocus(false);
        editText.requestFocus();
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    private void populateFields(Product product) {
        mName.setText(product.getTitle());
        mStock.setText(product.getStock());
        mCreation.setText(product.getCreationDate());
        mExpiration.setText(product.getExpirationDate());
        if(product.getPrice() != null) {
            mPrice.setText(product.getPrice());
        }
    }

    private void dateClickListener(final EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(DetailedProductActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        editText.setText(new StringBuilder().append(selectedday).append("/").append(selectedmonth + 1).append("/")
                                .append(selectedyear).append(""));
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            if(mName.getText().length()==0 || mStock.getText().length()==0 || mCreation.getText().length()==0 || mExpiration.getText().length()==0) {
                Toast.makeText(this, "You should complete the required fields to save the product", Toast.LENGTH_SHORT).show();
            }
            else {
                Product product = new Product(mProductID, mName.getText().toString(),mStock.getText().toString(),
                        mCreation.getText().toString(), mExpiration.getText().toString());
                {
                    if (mPrice.getText().length() > 0) {
                        product.setPrice(mPrice.getText().toString());
                    }
                    else {
                        product.setPrice(null);
                    }
                }
                {
                    if (mNewProduct.equals("true")) {
                        myDbHelper.createProduct(product, mCategoryId);
                        Toast.makeText(this, "Product created successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        myDbHelper.updateProduct(product, mCategoryId);
                        Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            Intent intent = new Intent(this,ProductsActivity.class);
            intent.putExtra("IDCATEGORY",mCategoryId);
            this.finish();
            startActivity(intent);
        }
        else{
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

    @Override
    public void onRestart() {
        super.onRestart();
    }

}


