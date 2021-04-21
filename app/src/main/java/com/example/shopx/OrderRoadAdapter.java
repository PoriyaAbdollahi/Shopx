package com.example.shopx;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Model.ServerAddress;

public class OrderRoadAdapter extends RecyclerView.Adapter<OrderRoadAdapter.RoadHolder> {
    List<Integer> orderStatuses;
    Context context ;
    public OrderRoadAdapter(List<Integer> orderStatuses, Context context){
        this.orderStatuses=orderStatuses;
        this.context=context;
    }

    @NonNull
    @Override
    public RoadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_road_item,parent,false);
        return new RoadHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoadHolder holder, int position) {

            bindOrderRoad(holder,position);
    }

    private void bindOrderRoad(RoadHolder holder, int position) {
        //1 inProgress
        //2 Packing
        //3 InTheWay
        //4 Delivered
        if (orderStatuses.get(position)==1){
            //1 inProgress

            holder.inProgress.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.order_road_back_status_red,null));
        }else if (orderStatuses.get(position)==2){
            //2 Packing
            holder.inProgress.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.order_road_back_status_red,null));
            holder.recOne.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.packing.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.order_road_back_status_red,null));

        }else if (orderStatuses.get(position)==3){
            //3 InTheWay

            holder.inProgress.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.order_road_back_status_red,null));
            holder.recOne.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.packing.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.order_road_back_status_red,null));
            holder.recTwo.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.inTheWay.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.order_road_back_status_red,null));

        }else if (orderStatuses.get(position)==4){

            //4 Delivered

            holder.inProgress.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.order_road_back_status_red,null));
            holder.recOne.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.packing.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.order_road_back_status_red,null));
            holder.recTwo.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.inTheWay.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.order_road_back_status_red,null));
            holder.recThree.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.delivered.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.order_road_back_status_red,null));
        }
        //an example photo of order
        Picasso.get().load(ServerAddress.imageAddress+"iphone11.jpeg").into(holder.firstProduct);
    }

    @Override
    public int getItemCount() {
        return orderStatuses.size();
    }

    public class RoadHolder extends RecyclerView.ViewHolder{
        ImageView firstProduct ;
        View inProgress,recOne,packing,recTwo,inTheWay,recThree,delivered;
        public RoadHolder(@NonNull View itemView) {
            super(itemView);
            // initializing the component order_road_item.xml
            inProgress=itemView.findViewById(R.id.view2);
            packing=itemView.findViewById(R.id.view);
            inTheWay=itemView.findViewById(R.id.view3);
            delivered=itemView.findViewById(R.id.view4);
            recOne=itemView.findViewById(R.id.view8);
            recTwo=itemView.findViewById(R.id.view5);
            recThree=itemView.findViewById(R.id.view6);
            firstProduct=itemView.findViewById(R.id.imageView2);

        }
    }
}
