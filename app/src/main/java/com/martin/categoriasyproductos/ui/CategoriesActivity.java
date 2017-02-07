package com.martin.categoriasyproductos.ui;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.martin.categoriasyproductos.R;
import com.martin.categoriasyproductos.adapters.CategoryAdapter;
import com.martin.categoriasyproductos.model.Category;
import com.martin.categoriasyproductos.sqlite.DatabaseOpenHelper;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {

    private Category[] mCategories;
    private DatabaseOpenHelper myDbHelper;

    @BindView(R.id.recycler_view_categories) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ButterKnife.bind(this);

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

        ArrayList <Category> categoryArrayList = myDbHelper.readCategories();
        for(Category category: categoryArrayList){
            category.setProducts(myDbHelper.readProducts(category.getID()));
        }
        mCategories = new Category[categoryArrayList.size()];
        mCategories = categoryArrayList.toArray(mCategories);

        //Create the adapter
        CategoryAdapter adapter = new CategoryAdapter(this,mCategories);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //set the adapter
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setHasFixedSize(true); //THIS HELPS WITH PERFORMANCE
    }

}
