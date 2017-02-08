package com.martin.categoriasyproductos.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.martin.categoriasyproductos.R;
import com.martin.categoriasyproductos.adapters.ProductAdapter;
import com.martin.categoriasyproductos.model.Category;
import com.martin.categoriasyproductos.model.Product;
import com.martin.categoriasyproductos.sqlite.DatabaseOpenHelper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity {

    private Product[] mProducts;
    private Category mCategory;
    private String mCategoryId;
    private ArrayList<Product> productArrayList;
    private ProductAdapter adapter;

    @BindView(R.id.recycler_view_products) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        mCategoryId = intent.getStringExtra("IDCATEGORY");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DatabaseOpenHelper myDbHelper = new DatabaseOpenHelper(this);
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

        mCategory = myDbHelper.getCategory(mCategoryId);
        productArrayList = myDbHelper.readProducts(mCategoryId);
        mProducts = new Product[productArrayList.size()];
        mProducts = productArrayList.toArray(mProducts);

        //Create the adapter
        adapter = new ProductAdapter(this, mProducts);
        //set the adapter
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setHasFixedSize(true); //THIS HELPS WITH PERFORMANCE

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_filter:
                Toast.makeText(this, "Filter selected", Toast.LENGTH_SHORT)
                        .show();
                showOptionsFilter();
                break;
            // action with ID action_settings was selected
            case R.id.action_add:
                Toast.makeText(this, "Add selected" + mCategoryId, Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(this,DetailedProductActivity.class);
                intent.putExtra("CATEGORYID",mCategoryId);
                intent.putExtra("PRODID",UUID.randomUUID().toString());
                intent.putExtra("NEW","true");
                this.finish();
                this.startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    private void showOptionsFilter(){
        AlertDialog.Builder UnitSelection = new AlertDialog.Builder(this);
        UnitSelection.setTitle("Select filter");
        final String [] options = new String[] {"Expired products","Products available","Products created in the last 90 days"};
        UnitSelection.setSingleChoiceItems(options, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(getApplicationContext(), options[item], Toast.LENGTH_SHORT).show();
                switch (item){
                    //Expired products
                    case 0:
                        showExpiredProducts();
                        break;
                    //Products available
                    case 1:
                        break;
                    //Products created in the last 90 days
                    case 2:
                        break;
                }
                dialog.dismiss();
            }
        });
        AlertDialog alert = UnitSelection.create();
        alert.show();
    }

    private void showExpiredProducts() {
        ArrayList<Product> expired = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter  = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        Date toCompare;
        for(Product product: productArrayList){
            try {
                toCompare = formatter.parse(product.getExpirationDate());
                System.out.println("EXPIRATION vs TODAY: "+toCompare+" vs "+today+" isbefore? "+ (toCompare.getTime()<today.getTime()));
                if (toCompare.before(today)){
                    expired.add(product);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        mProducts = new Product[expired.size()];
        mProducts = expired.toArray(mProducts);

        //Create the adapter
        adapter = new ProductAdapter(this, mProducts);
        //set the adapter
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setHasFixedSize(true);
    }

    private Date getDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateToReturn = new Date();
        try {
            dateToReturn = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToReturn;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,CategoriesActivity.class);
        this.finish();
        startActivity(intent);
    }
}