package com.example.shopx;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.BasketItem;
import Model.ServerAddress;

public class Basket extends AppCompatActivity {
    // components
    TextView total;
    RecyclerView orderList;
    AppCompatButton checkout;
    List<BasketItem> basketItemList;
    BasketAdapter orderAdapter;
    //order id that should check out
    String checkoutId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        basketInit();

        settingUpRecycler();
        getOrders();
        onEventHappend();

    }

    private int calculateTotal(int amount , int price ) {
        return amount * price;
    }

    private void onEventHappend() {
            checkout.setOnClickListener(v -> {

                    RequestQueue requestQueue =  Volley.newRequestQueue(Basket.this);
                    StringRequest  stringRequest = new StringRequest(Request.Method.POST, ServerAddress.address+"checkout.php", response -> {
                    if (response.equals("ok")){

                        //send user to see the order road
                        Intent intent=new Intent();
                        setResult(2,intent);
                        finish();//finishing activity

                    }else if (response.equals("fail")){

                        Log.i("checkout",response);

                    }else {

                        Log.i("checkout",response);
                    }

                    }, error -> Log.e("checkout",error.getMessage())){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() {
                           Map<String,String> params = new HashMap<>();
                           params.put("id",checkoutId);
                           return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String,String> params = new HashMap<>();
                            params.put("Content-Type","application/x-www-form-urlencoded");
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
            });
    }

    private void settingUpRecycler() {

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        orderList.setLayoutManager(layoutManager1);
        orderAdapter = new BasketAdapter(this, basketItemList);
        orderList.setAdapter(orderAdapter);
    }

    private void getOrders() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url ="http://192.168.43.154/shopx/getbasket.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            System.out.println(response);
            if (response.equals("noOrder")){
                total.setText("Your Basket Is Empty ");
            }

            try {
                JSONArray jsonArray = new JSONArray(response);
                int totalNum;
                for (int i = 0 ;i<jsonArray.length();i++) {
               int amount =  jsonArray.getJSONObject(i).getInt("amount");
               int price =  jsonArray.getJSONObject(i).getInt("price") ;
               String image =  jsonArray.getJSONObject(i).getString("image");

                    basketItemList.add(new BasketItem(calculateTotal(amount,price),image));
                //get check out (order_list_id)
                    checkoutId= String.valueOf( jsonArray.getJSONObject(i).getInt("order_list_id"));
                //calculate total
                    totalNum =+ calculateTotal(amount,price);
                    System.out.println(totalNum);
                    total.setText("Total: " + totalNum);
                }
                orderAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("id","1");
                //user id
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void basketInit() {
        total = findViewById(R.id.basket_total);
        orderList = findViewById(R.id.basket_recycler);
        checkout = findViewById(R.id.check_out);
        basketItemList = new ArrayList<>();
    }
}