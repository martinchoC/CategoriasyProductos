package com.martin.categoriasyproductos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.martin.categoriasyproductos.R;
import com.martin.categoriasyproductos.adapters.CategoryAdapter;
import com.martin.categoriasyproductos.adapters.ProductAdapter;
import com.martin.categoriasyproductos.model.Category;
import com.martin.categoriasyproductos.model.Product;
import com.martin.categoriasyproductos.sqlite.CatsAndProdsDataSource;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity {

    private Product[] mProducts;
    private  CatsAndProdsDataSource mPoductsDB;
    private Category mCategory;

    @BindView(R.id.recycler_view_products) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("IDCATEGORY");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mPoductsDB = new CatsAndProdsDataSource(this);
        mCategory = mPoductsDB.findCategory(id);
        ArrayList<Product> productArrayList = mPoductsDB.readProducts();
        mProducts = new Product[productArrayList.size()];
        mProducts = productArrayList.toArray(mProducts);

        //Create the adapter
        ProductAdapter adapter = new ProductAdapter(this, mProducts);
        //set the adapter
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setHasFixedSize(true); //THIS HELPS WITH PERFORMANCE

    }

}
