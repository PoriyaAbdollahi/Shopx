package com.example.shopx;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Model.ProductCatItem;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.productHolder> {
    List<ProductCatItem> productCatItems;
    Context context;

    public CatAdapter(Context context , List<ProductCatItem> productCatItems){
    this.productCatItems=productCatItems;
    this.context=context;
    }

    @NonNull
    @Override
    public productHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_home_screen_item,parent,false);
        return  new productHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productHolder holder, int position) {
        String price = productCatItems.get(position).getPrice()+"$";
        holder.price.setText(price);
        holder.name.setText(productCatItems.get(position).getName());
        //we should load image using picasso here
        Picasso.get().load(productCatItems.get(position).getProductImage()).into(holder.productImage);
        holder.productImage.setOnClickListener(v -> context.startActivity(new Intent(context,ProductPage.class).putExtra("id",productCatItems.get(position).getId())));
    }



    @Override
    public int getItemCount() {
        return productCatItems.size();
    }


    public static final class productHolder extends RecyclerView.ViewHolder{
            ImageView productImage;
            TextView name , price ;
        public productHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.cat_img_item);
            name=itemView.findViewById(R.id.cat_name_item);
            price=itemView.findViewById(R.id.cat_price_item);
        }
    }
}
