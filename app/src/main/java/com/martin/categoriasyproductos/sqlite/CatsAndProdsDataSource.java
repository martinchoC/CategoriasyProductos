package com.martin.categoriasyproductos.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.martin.categoriasyproductos.model.Category;
import com.martin.categoriasyproductos.model.Product;

import java.util.ArrayList;

/**
 * Created by MartinchoC on 2/4/17.
 */

public class CatsAndProdsDataSource {

    private Context mContext;
    private CatsAndProdsSQLiteHelper mCatsAndProdsSqliteHelper;

    public CatsAndProdsDataSource(Context context) {

        mContext = context;
        mCatsAndProdsSqliteHelper = new CatsAndProdsSQLiteHelper(context);
    }

    private SQLiteDatabase open() {
        return mCatsAndProdsSqliteHelper.getWritableDatabase();
    }

    private void close(SQLiteDatabase database) {
        database.close();
    }

    public ArrayList<Category> readCategories() {
        ArrayList<Category> categories = readCats();
        return categories;
    }

    public ArrayList<Category> readCats() {
        SQLiteDatabase database = open();
        Cursor cursor = database.query(
                CatsAndProdsSQLiteHelper.CATEGORIES_TABLE,
                new String [] {CatsAndProdsSQLiteHelper.COLUMN_CATEGORY_TITLE, BaseColumns._ID},
                null, //selection
                null, //selection args
                null, //group by
                null, //having
                null); //order
        ArrayList<Category> categories = new ArrayList<Category>();
        if(cursor.moveToFirst()) {
            do {
                Category category = new Category(getIntFromColumnName(cursor, BaseColumns._ID)+"",
                        getStringFromColumnName(cursor, CatsAndProdsSQLiteHelper.COLUMN_CATEGORY_TITLE));
                categories.add(category);
            }while(cursor.moveToNext());
        }
        cursor.close();
        close(database);
        return categories;
    }

    public ArrayList<Product> readProducts() {
        ArrayList<Product> products = readProds();
        return products;
    }

    public ArrayList<Product> readProds() {
        SQLiteDatabase database = open();
        Cursor cursor = database.query(
                CatsAndProdsSQLiteHelper.PRODUCTS_TABLE,
                new String [] {BaseColumns._ID,
                                CatsAndProdsSQLiteHelper.COLUMN_CATEGORY_TITLE,
                                CatsAndProdsSQLiteHelper.COLUMN_PRODUCT_PRICE,
                                CatsAndProdsSQLiteHelper.COLUMN_PRODUCT_STOCK,
                                CatsAndProdsSQLiteHelper.COLUMN_FOREIGN_KEY_CATEGORY,
                                CatsAndProdsSQLiteHelper.COLUMN_PRODUCT_CREATION,
                                CatsAndProdsSQLiteHelper.COLUMN_PRODUCT_EXPIRATION,},
                null, //selection
                null, //selection args
                null, //group by
                null, //having
                null); //order
        ArrayList<Product> products = new ArrayList<Product>();
        if(cursor.moveToFirst()) {
            do {
                Product product = new Product(getIntFromColumnName(cursor, BaseColumns._ID)+"",
                                                 getStringFromColumnName(cursor, CatsAndProdsSQLiteHelper.COLUMN_CATEGORY_TITLE)+"",
                                                 getStringFromColumnName(cursor,CatsAndProdsSQLiteHelper.COLUMN_PRODUCT_STOCK)+"",
                                                 getStringFromColumnName(cursor,CatsAndProdsSQLiteHelper.COLUMN_PRODUCT_CREATION)+"",
                                                 getStringFromColumnName(cursor,CatsAndProdsSQLiteHelper.COLUMN_PRODUCT_EXPIRATION));
                products.add(product);
            }while(cursor.moveToNext());
        }
        cursor.close();
        close(database);
        return products;
    }

    private int getIntFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private String getStringFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    public void createCategory(Category category) {
        SQLiteDatabase database = open();
        database.beginTransaction();
        ContentValues CategoryValues = new ContentValues();
        CategoryValues.put(CatsAndProdsSQLiteHelper.COLUMN_CATEGORY_TITLE, category.getTitle());
        long categoryID = database.insert(CatsAndProdsSQLiteHelper.CATEGORIES_TABLE, null, CategoryValues);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public void createProduct(Product product) {
        SQLiteDatabase database = open();
        database.beginTransaction();
        ContentValues CategoryValues = new ContentValues();
        CategoryValues.put(CatsAndProdsSQLiteHelper.COLUMN_PRODUCT_TITLE, product.getTitle());
        CategoryValues.put(CatsAndProdsSQLiteHelper.COLUMN_PRODUCT_PRICE, product.getPrice());
        CategoryValues.put(CatsAndProdsSQLiteHelper.COLUMN_PRODUCT_STOCK, product.getStock());
        CategoryValues.put(CatsAndProdsSQLiteHelper.COLUMN_FOREIGN_KEY_CATEGORY, product.getCategory().getID());
        CategoryValues.put(CatsAndProdsSQLiteHelper.COLUMN_PRODUCT_CREATION, product.getCreationDate());
        CategoryValues.put(CatsAndProdsSQLiteHelper.COLUMN_PRODUCT_EXPIRATION, product.getExpirationDate());

        long categoryID = database.insert(CatsAndProdsSQLiteHelper.CATEGORIES_TABLE, null, CategoryValues);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

}













