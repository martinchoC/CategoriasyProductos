package com.martin.categoriasyproductos.sqlite;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.martin.categoriasyproductos.model.Category;
import com.martin.categoriasyproductos.model.Product;

/**
 * Created by MartinC on 6/2/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CatsAndProds.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CATEGORIES = "CATEGORIES";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TITLE = "TITLE";

    public static final String PRODUCTS_TABLE = "PRODUCTS";
    public static final String COLUMN_PRODUCT_ID = "ID";
    public static final String COLUMN_PRODUCT_TITLE = "TITLE";
    public static final String COLUMN_PRODUCT_PRICE = "PRICE";
    public static final String COLUMN_PRODUCT_STOCK = "STOCK";
    public static final String COLUMN_FOREIGN_KEY_CATEGORY = "CATEGORYID";
    public static final String COLUMN_PRODUCT_CREATION = "CREATION";
    public static final String COLUMN_PRODUCT_EXPIRATION = "EXPIRATION";

    private SQLiteDatabase database;

    private final Context context;

    // database path
    private static String DATABASE_PATH;

    /** constructor */
    public DatabaseOpenHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
        DATABASE_PATH = context.getFilesDir().getParentFile().getPath()
                + "/databases/";

    }

    /**
     * Creates an empty database on the system and rewrites it with my own
     * database.
     * */
    public void create() throws IOException {
        boolean check = checkDataBase();

        SQLiteDatabase db_Read = null;

        // Creates empty database default system path
        db_Read = this.getWritableDatabase();
        db_Read.close();
        try {
            if (!check) {
                copyDataBase();
            }
        } catch (IOException e) {
            throw new Error("Error copying database");
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time I open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            // database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies my database from my local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    // open the database
    public void open() throws SQLException {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        database = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    // close the database
    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }

    // insert a product into the product table
    public void createProduct(Product product, int categoryId) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_ID, product.getID());
        initialValues.put(COLUMN_PRODUCT_TITLE, product.getTitle());
        initialValues.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        initialValues.put(COLUMN_PRODUCT_STOCK, product.getStock());
        initialValues.put(COLUMN_FOREIGN_KEY_CATEGORY, categoryId);
        initialValues.put(COLUMN_PRODUCT_CREATION, product.getCreationDate());
        initialValues.put(COLUMN_PRODUCT_EXPIRATION, product.getExpirationDate());
        database.insert(PRODUCTS_TABLE, null, initialValues);
    }

    // updates a product
    public boolean updateProduct(Product product, int categoryId) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_PRODUCT_ID, product.getID());
        args.put(COLUMN_PRODUCT_TITLE, product.getTitle());
        args.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        args.put(COLUMN_PRODUCT_STOCK, product.getStock());
        args.put(COLUMN_FOREIGN_KEY_CATEGORY, categoryId);
        args.put(COLUMN_PRODUCT_CREATION, product.getCreationDate());
        args.put(COLUMN_PRODUCT_EXPIRATION, product.getExpirationDate());
        return database.update(PRODUCTS_TABLE, args, COLUMN_PRODUCT_ID + "=?", new String[] {product.getID()}) > 0;
    }

    //returns the Category with the id rowId
    public Category getCategory(int rowId){
        Cursor cursor = getCursorCategory(rowId);
        Category category = null;
        if(cursor.getCount() != 0) {
            category = new Category(new Integer(getIntFromColumnName(cursor, COLUMN_ID)),
                    getStringFromColumnName(cursor, COLUMN_TITLE));
        }while(cursor.moveToNext());
        return category;
    }

    // retrieves a particular category from the DB
    private Cursor getCursorCategory(Integer rowId) throws SQLException {
        Cursor mCursor = database.query(true, TABLE_CATEGORIES, new String[] {
                        COLUMN_ID, COLUMN_TITLE},
                COLUMN_ID + " = " + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //returns an arraylist of Products from a particular category
    public ArrayList<Product> readProducts(int idCategory) {
        ArrayList<Product> products = readProds(idCategory);
        return products;
    }

    //retrieves an arraylist of products
    private ArrayList<Product> readProds(int idCategory) {
        String[] tableColumns = new String[] {COLUMN_PRODUCT_ID,COLUMN_PRODUCT_TITLE,COLUMN_PRODUCT_STOCK,COLUMN_PRODUCT_PRICE,COLUMN_PRODUCT_CREATION,COLUMN_PRODUCT_EXPIRATION};
        String whereClause = COLUMN_FOREIGN_KEY_CATEGORY+" = ?";
        String[] whereArgs = new String[] {idCategory+""};
        Cursor cursor = database.query(PRODUCTS_TABLE, tableColumns, whereClause, whereArgs,null, null, null);
        ArrayList<Product> products = new ArrayList<Product>();
        if(cursor.moveToFirst()) {
            do {
                Category category = this.getCategory(idCategory);
                Product product = new Product(getStringFromColumnName(cursor, COLUMN_PRODUCT_ID),
                        getStringFromColumnName(cursor, COLUMN_PRODUCT_TITLE),
                        getStringFromColumnName(cursor,COLUMN_PRODUCT_STOCK),
                        getStringFromColumnName(cursor,COLUMN_PRODUCT_CREATION),
                        getStringFromColumnName(cursor,COLUMN_PRODUCT_CREATION));
                product.setPrice(getStringFromColumnName(cursor, COLUMN_PRODUCT_PRICE));
                product.setCategory(category);
                products.add(product);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    //returns a product
    public Product getProduct(String productId){
        Cursor cursor = getCursorProduct(productId);
        Product product = null;
        if(cursor.getCount() != 0) {
            product = new Product(getStringFromColumnName(cursor, COLUMN_PRODUCT_ID),
                    getStringFromColumnName(cursor, COLUMN_PRODUCT_TITLE),
                    getStringFromColumnName(cursor, COLUMN_PRODUCT_STOCK),
                    getStringFromColumnName(cursor,COLUMN_PRODUCT_CREATION),
                    getStringFromColumnName(cursor,COLUMN_PRODUCT_EXPIRATION));
            Category category = this.getCategory(getIntFromColumnName(cursor, COLUMN_FOREIGN_KEY_CATEGORY));
            product.setCategory(category);
            product.setPrice(getStringFromColumnName(cursor, COLUMN_PRODUCT_PRICE));
        }while(cursor.moveToNext());
        return product;
    }

    //retrieves a product that contains a certain id
    private Cursor getCursorProduct(String productId) throws SQLException {
        String[] tableColumns = new String[] {COLUMN_PRODUCT_ID,COLUMN_PRODUCT_TITLE,COLUMN_PRODUCT_STOCK,COLUMN_PRODUCT_PRICE,COLUMN_FOREIGN_KEY_CATEGORY,COLUMN_PRODUCT_CREATION,COLUMN_PRODUCT_EXPIRATION};
        String whereClause = COLUMN_PRODUCT_ID+" = ?";
        String[] whereArgs = new String[] {productId};
        Cursor mCursor = database.query(PRODUCTS_TABLE, tableColumns, whereClause, whereArgs,null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // delete a particular product
    public boolean deleteProduct(String rowId) {
        String [] args = new String[]{rowId+""};
        return database.delete(PRODUCTS_TABLE, COLUMN_PRODUCT_ID+"=?",args) > 0;
    }

    //returns an arraylist of categories
    public ArrayList<Category> readCategories(){
        Cursor cursor = getAllCategories();
        ArrayList<Category> categories = new ArrayList<Category>();
        if(cursor.moveToFirst()) {
            do {
                Category category = new Category(Integer.valueOf(getIntFromColumnName(cursor, COLUMN_ID)),
                        getStringFromColumnName(cursor, COLUMN_TITLE));
                categories.add(category);
            }while(cursor.moveToNext());
        }
        return categories;
    }

    // retrieves all categories
    private Cursor getAllCategories() {
        return database.query(TABLE_CATEGORIES, new String[] { COLUMN_ID,
                        COLUMN_TITLE}, null, null,
                null, null, null);
    }

    //returns the string contained in a column row
    private String getStringFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    //returns the integer contained in a column row
    private int getIntFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

}