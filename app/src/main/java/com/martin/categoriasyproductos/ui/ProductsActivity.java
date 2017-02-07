package com.martin.categoriasyproductos.ui;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity {

    private Product[] mProducts;
    private Category mCategory;
    private String mCategoryId;

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
        ArrayList<Product> productArrayList = myDbHelper.readProducts(mCategoryId);
        mProducts = new Product[productArrayList.size()];
        mProducts = productArrayList.toArray(mProducts);

        //Create the adapter
        ProductAdapter adapter = new ProductAdapter(this, mProducts);
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
                break;
            // action with ID action_settings was selected
            case R.id.action_add:
                Toast.makeText(this, "Add selected" + mCategoryId, Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(this,DetailedProductActivity.class);
                intent.putExtra("CATEGORYID",mCategoryId);
                intent.putExtra("PRODID",UUID.randomUUID().toString());
                intent.putExtra("NEW","true");
                this.startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }

}
