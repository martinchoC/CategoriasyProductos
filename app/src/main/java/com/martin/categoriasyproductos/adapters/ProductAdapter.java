package com.martin.categoriasyproductos.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.martin.categoriasyproductos.R;
import com.martin.categoriasyproductos.model.Product;
import com.martin.categoriasyproductos.sqlite.DatabaseProdsAndCats;
import com.martin.categoriasyproductos.ui.DetailedProductActivity;
import com.martin.categoriasyproductos.ui.ProductsActivity;

/**
 * Created by MartinC on 6/2/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Product[] mProducts;
    protected Context mContext;
    private DatabaseProdsAndCats mDatabaseProdsAndCats;

    public ProductAdapter(Context context, Product[] products, DatabaseProdsAndCats databaseProdsAndCats) {
        mProducts = products;
        mContext = context;
        mDatabaseProdsAndCats = databaseProdsAndCats;
    }

    @Override
    public int getItemCount() {
        return mProducts.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView,mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder viewHolder, int position) {

        //used to get data from my itemsData at this position
        //used to replace the contents of the view with that itemsData
        viewHolder.mcategoryLabel.setText(mProducts[position].getTitle());
        if(mProducts[position].getPrice()==null) {
            viewHolder.mpriceProductLabel.setText("No price information available");
        }
        else {
            viewHolder.mpriceProductLabel.setText("$  "+mProducts[position].getPrice());
        }
        viewHolder.idProduct = mProducts[position].getID();
        viewHolder.idCategory = mProducts[position].getCategory().getID();
        viewHolder.dbOpenHelper = mDatabaseProdsAndCats;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mcategoryLabel;
        TextView mpriceProductLabel;
        Integer idCategory;
        String idProduct;
        DatabaseProdsAndCats dbOpenHelper;

        public ViewHolder(View itemLayoutView, final Context context) {
            super(itemLayoutView);
            mcategoryLabel = (TextView) itemLayoutView.findViewById(R.id.categoryNameTextView);
            mpriceProductLabel = (TextView) itemLayoutView.findViewById(R.id.sumProductsLabel);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showOptions(context,idCategory,idProduct,dbOpenHelper);
                    return true;
                }
            });
        }

        private static void showOptions(final Context context, final Integer idCategory, final String idProduct, final DatabaseProdsAndCats databaseProdsAndCats) {
            final AlertDialog.Builder UnitSelection = new AlertDialog.Builder(context);
            String options[] ={"Edit","Remove",};
            UnitSelection.setItems(options, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    switch (item){
                        //Edit product
                        case 0:
                            editProduct(context,idCategory,idProduct);
                            break;
                        //Remove product
                        case 1:
                            removeProduct(context,idProduct,idCategory, databaseProdsAndCats);
                            break;
                    }
                    dialog.dismiss();
                }
            });
            AlertDialog alert = UnitSelection.create();
            alert.show();

        }

        private static void editProduct(final Context context, final Integer idCategory, final String idProduct){
            Intent intent = new Intent(context, DetailedProductActivity.class);
            intent.putExtra("CATEGORYID",idCategory);
            intent.putExtra("PRODID",idProduct);
            intent.putExtra("NEW","false");
            ((Activity)context).finish();
            context.startActivity(intent);
        }

        private static void removeProduct(Context context, final String idProduct, final Integer idCategory, DatabaseProdsAndCats databaseProdsAndCats){
            databaseProdsAndCats.deleteProduct(idProduct);
            Intent intent = new Intent(context, ProductsActivity.class);
            intent.putExtra("IDCATEGORY",idCategory);
            ((Activity) context).recreate();
        }
    }

}
