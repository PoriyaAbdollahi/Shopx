package com.example.shopx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.productItemHolder> implements Filterable {
    private List<Product> products;
    private List<Product> productsFull;

    public ProductAdapter(List<Product> products) {
        this.products = products;
        productsFull = new ArrayList<>(products);

    }

    @NonNull
    @Override
    public productItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_search, parent, false);
        return new productItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull productItemHolder holder, int position) {
        holder.name.setText(products.get(position).getName());
        holder.price.setText(String.valueOf(products.get(position).getPrice()));
        Picasso.get().load(products.get(position).getImageAddress()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(productsFull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Product item : productsFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            products.clear();
            products.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static final class productItemHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView price, name;

        public productItemHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_img_item);
            price = itemView.findViewById(R.id.product_price_item);
            name = itemView.findViewById(R.id.product_name_item);
        }
    }
}
