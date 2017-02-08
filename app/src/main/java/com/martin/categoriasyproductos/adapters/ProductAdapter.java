package com.martin.categoriasyproductos.adapters;

import android.content.Context;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.martin.categoriasyproductos.R;
import com.martin.categoriasyproductos.model.Product;

/**
 * Created by MartinC on 6/2/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Product[] mProducts;
    protected Context mContext;

    public ProductAdapter(Context context, Product[] products) {
        mProducts = products;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mProducts.length;
    }

    public Object getItem(int position) {
        return mProducts[position];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView,mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder viewHolder, int position) {

        //used to get data from my itemsData at this position
        //used to replace the contents of the view with that itemsData
        viewHolder.categoryLabel.setText(mProducts[position].getTitle());
        if(mProducts[position].getPrice()==null) {
            viewHolder.priceProductLabel.setText("No information available");
        }
        else {
            viewHolder.priceProductLabel.setText("$  "+mProducts[position].getPrice());
        }
        viewHolder.id = mProducts[position].getID();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryLabel;
        TextView priceProductLabel;
        String id;

        public ViewHolder(View itemLayoutView, final Context context) {
            super(itemLayoutView);
            categoryLabel = (TextView) itemLayoutView.findViewById(R.id.categoryNameTextView);
            priceProductLabel = (TextView) itemLayoutView.findViewById(R.id.sumProductsLabel);
        }

    }
}
