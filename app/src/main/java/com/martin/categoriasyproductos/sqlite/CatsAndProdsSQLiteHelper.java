package com.martin.categoriasyproductos.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by MartinchoC on 2/4/17.
 */
public class CatsAndProdsSQLiteHelper extends SQLiteOpenHelper  {

    private static final String DB_NAME = "CatsAndProds.db";
    private static final int DB_VERSION = 1;

    //Categories Table functionality
    public static final String CATEGORIES_TABLE = "CATEGORIES";
    public static final String COLUMN_CATEGORY_TITLE = "TITLE";
    private static String CREATE_CATEGORIES =
            "CREATE TABLE " + CATEGORIES_TABLE +
            "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_CATEGORY_TITLE + " TEXT)";

    //Products Table functionality
    public static final String PRODUCTS_TABLE = "PRODUCTS";
    public static final String COLUMN_PRODUCT_TITLE = "TITLE";
    public static final String COLUMN_PRODUCT_PRICE = "PRICE";
    public static final String COLUMN_PRODUCT_STOCK = "STOCK";
    public static final String COLUMN_FOREIGN_KEY_CATEGORY = "CATEGORY";
    public static final String COLUMN_PRODUCT_CREATION = "CREATION";
    public static final String COLUMN_PRODUCT_EXPIRATION = "EXPIRATION";

    private static final String CREATE_PRODUCTS = "CREATE TABLE " + PRODUCTS_TABLE + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_PRODUCT_TITLE + " TEXT, " +
            COLUMN_PRODUCT_PRICE + " DOUBLE, " +
            COLUMN_PRODUCT_STOCK + " INTEGER, " +
            COLUMN_FOREIGN_KEY_CATEGORY + " INTEGER, " +
            COLUMN_PRODUCT_CREATION + " DATE, " +
            COLUMN_PRODUCT_EXPIRATION + " DATE, " +
            "FOREIGN KEY(" + COLUMN_FOREIGN_KEY_CATEGORY + ") REFERENCES CATEGORIES(_ID))";

    public CatsAndProdsSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CATEGORIES);
        sqLiteDatabase.execSQL(CREATE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
