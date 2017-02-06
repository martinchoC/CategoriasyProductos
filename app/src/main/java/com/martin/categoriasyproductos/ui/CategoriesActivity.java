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
import java.util.UUID;

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
        createCategories();
        ArrayList <Category> categoryArrayList = CategoriesProductsDB.readCategories();
        mCategories = new Category[categoryArrayList.size()];
        mCategories = categoryArrayList.toArray(mCategories);

        //Create the adapter
        CategoryAdapter adapter = new CategoryAdapter(this,mCategories);
        //set the adapter
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setHasFixedSize(true); //THIS HELPS WITH PERFORMANCE

    }

    private void createCategories(){
        Category category;
        String title = "";
        String id = "";

        {
            title = "Meats and Sausages";
            id = UUID.randomUUID().toString();
            category = new Category(id,title);
            CategoriesProductsDB.createCategory(this,category);
        }
        {
            title = "Fruits and vegetables";
            id = UUID.randomUUID().toString();
            category = new Category(id,title);
            CategoriesProductsDB.createCategory(this,category);
        }
        {
            title = "Bakery and Sweets";
            id = UUID.randomUUID().toString();
            category = new Category(id,title);
            CategoriesProductsDB.createCategory(this,category);
        }
        {
            title = "Eggs, Dairy & Coffee";
            id = UUID.randomUUID().toString();
            category = new Category(id,title);
            CategoriesProductsDB.createCategory(this,category);
        }
        {
            title = "Oil, Pasta and Pulses";
            id = UUID.randomUUID().toString();
            category = new Category(id,title);
            CategoriesProductsDB.createCategory(this,category);
        }
        {
            title = "Canned and Prepared Food";
            id = UUID.randomUUID().toString();
            category = new Category(id,title);
            CategoriesProductsDB.createCategory(this,category);
        }
        {
            title = "Juices and Drinks";
            id = UUID.randomUUID().toString();
            category = new Category(id,title);
            CategoriesProductsDB.createCategory(this,category);
        }
        {
            title = "Appetizers";
            id = UUID.randomUUID().toString();
            category = new Category(id,title);
            CategoriesProductsDB.createCategory(this,category);
        }
        {
            title = "Algae, Tofu and Preparations";
            id = UUID.randomUUID().toString();
            category = new Category(id,title);
            CategoriesProductsDB.createCategory(this,category);
        }
        {
            title = "Childish";
            id = UUID.randomUUID().toString();
            category = new Category(id,title);
            CategoriesProductsDB.createCategory(this,category);
        }
        {
            title = "Cosmetics and Personal Care";
            id = UUID.randomUUID().toString();
            category = new Category(id,title);
            CategoriesProductsDB.createCategory(this,category);
        }
        {
            title = "Home and Cleaning";
            id = UUID.randomUUID().toString();
            category = new Category(id,title);
            CategoriesProductsDB.createCategory(this,category);
        }
    }
}
