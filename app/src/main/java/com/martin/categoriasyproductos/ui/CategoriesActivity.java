package com.martin.categoriasyproductos.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.martin.categoriasyproductos.R;
import com.martin.categoriasyproductos.adapters.CategoryAdapter;
import com.martin.categoriasyproductos.model.Category;
import com.martin.categoriasyproductos.sqlite.CatsAndProdsDataSource;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {

    private Category[] mCategories;
    private  CatsAndProdsDataSource CategoriesProductsDB;

    @BindView(R.id.recycler_view_categories) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        CategoriesProductsDB = new CatsAndProdsDataSource(this);
        ArrayList <Category> categoryArrayList = CategoriesProductsDB.readCategories();
        mCategories = new Category[categoryArrayList.size()];
        mCategories = categoryArrayList.toArray(mCategories);

        //Create the adapter
        CategoryAdapter adapter = new CategoryAdapter(mCategories);
        //set the adapter
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setHasFixedSize(true); //THIS HELPS WITH PERFORMANCE
    }
}
