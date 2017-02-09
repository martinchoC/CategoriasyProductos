package com.martin.categoriasyproductos.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.martin.categoriasyproductos.R;
import com.martin.categoriasyproductos.model.Category;
import com.martin.categoriasyproductos.ui.ProductsActivity;

/**
 * Created by MartinC on 4/2/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Category[] mCategories;
    protected Context mContext;

    public CategoryAdapter(Context context, Category[] categories) {
        mCategories = categories;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mCategories.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item,parent, false);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView,mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        //used to get data from your itemsData at this position
        //used to replace the contents of the view with that itemsData
        viewHolder.categoryLabel.setText(mCategories[position].getTitle());
        viewHolder.priceProductLabel.setText("Contains "+mCategories[position].getProducts().size()+" products");
        viewHolder.id = mCategories[position].getID();
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductsActivity.class);
                    intent.putExtra("IDCATEGORY",id);
                    context.startActivity(intent);
                    ((Activity)context).finish();

                }
            });
        }
    }

}