package com.example.shopx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Model.BasketItem;
import Model.ServerAddress;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketHolder> {
    private Context context;
    private List<BasketItem> basketItems;
    public BasketAdapter(Context context , List<BasketItem> basketItems){
    this.context=context;
    this.basketItems=basketItems;

    }

    @NonNull
    @Override
    public BasketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.basket_item,parent,false);
        return new BasketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketHolder holder, int position) {
        holder.price.setText(basketItems.get(position).getPrice()+"$");
        Picasso.get().load(ServerAddress.imageAddress+basketItems.get(position).getImg()).into(holder.basketPhoto);

    }

    @Override
    public int getItemCount() {
        return basketItems.size();
    }

    public static final class BasketHolder extends RecyclerView.ViewHolder{
        ImageView basketPhoto ;
        TextView price;
    public BasketHolder(@NonNull View itemView) {
        super(itemView);
        basketPhoto=itemView.findViewById(R.id.item_img_basket);
        price=itemView.findViewById(R.id.basket_item_price);
    }
}
}
