package com.martin.categoriasyproductos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.martin.categoriasyproductos.R;
import com.martin.categoriasyproductos.model.Category;

/**
 * Created by MartinC on 4/2/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Category[] mCategories;

    public CategoryAdapter(Category[] categories) {
        mCategories = categories;
    }

    @Override
    public int getItemCount() {
        return mCategories.length;
    }

    public Object getItem(int position) {
        return mCategories[position];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        //used to get data from your itemsData at this position
        //used to replace the contents of the view with that itemsData
        viewHolder.categoryLabel.setText(mCategories[position].getTitle());
        viewHolder.sumProductsLabel.setText("# of products");
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryLabel;
        TextView sumProductsLabel;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            categoryLabel = (TextView) itemLayoutView.findViewById(R.id.categoryNameTextView);
            sumProductsLabel = (TextView) itemLayoutView.findViewById(R.id.sumProductsLabel);
        }
    }

}