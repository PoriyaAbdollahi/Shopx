package com.example.shopx;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import Model.ServerAddress;

public class ProductPage extends AppCompatActivity {
    //we should load this page from everywhere

    // a view pager that hold productImage
    ImageView productImage;
    //a star favorite btn
    ImageView favorite ;

    TextView  productName, productPrice,productDesc;

    AppCompatButton addToCart ,plus, negative,counter;
    int counterValue = 0 ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);


        //what if network goes down ?

        //we should load send request to server with name of product that clicked and get result (better use Progressbar)
        //request info  by id from server
        requestProductInfo();
        Log.i("extrassss", String.valueOf(getIntent().getExtras().getInt("id")));

        productPageInit();
        //on plus button what gonna happen
        onPlusClicked();
        //on negative button what gonna happen
        onNegativeClicked();



        //on add to cart button what gonna happen
        // we should check user login before (server)  if yes  then add the product  to user basket if no then we should send user to login page
        onAddToCartClicked();


        //just setting a title
        setTitle("ProductPage");
        // on favorite button what gonna happen
    }

    private void onAddToCartClicked() {
        addToCart.setOnClickListener(v -> {
            //check if user already login
            //later encrypt
            //here we should read data from datastore
            SharedPreferences sharedpreferences = getSharedPreferences("userData",Context.MODE_PRIVATE);
            String email = /*"javadfakhrian@gmail.com";*/sharedpreferences.getString("email","no");
            String password = /*"123";*/sharedpreferences.getString("password","no");




            if (email.equals("no")||password.equals("no")){
                //this mean there is no userdata in local :)
                startActivity(new Intent(ProductPage.this,Login.class));
            }else if (!email.equals("no")||!password.equals("no")){
                //aut POST
                RequestQueue requestQueue = Volley.newRequestQueue(ProductPage.this);
                StringRequest request = new StringRequest(Request.Method.POST, ServerAddress.address + "auth.php", response -> {
                    //if equals of
                    if (response.contains("yes")){
                        //add product to cart
                        String gettingUserId = response.substring(3);
                        String gettingProductId = String.valueOf(getIntent().getExtras().getInt("id"));
                        Log.i("addtocart",gettingUserId);
                        Log.i("addtocart",gettingProductId);

                        addToCartRequest(gettingUserId,String.valueOf(counterValue),gettingProductId);

                        //here should we make cart in database

                        // we should check if there is availbe orderlist if yess attach to that if not make one
                        Log.i("addtocart","add product to cart");
                    }else if (response.equals("no")){
                        Log.i("addtocart","send him to login page");
                        //this is mean there is difference between the server userdata and local

                    }

                }, error -> {
                //what if network goes down?
                }){
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String,String> params = new HashMap<>();
                        params.put("Content-Type","application/x-www-form-urlencoded");
                        return params;
                    }

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String,String> params = new HashMap<>();
                        params.put("email",email);
                        params.put("password",password);
                        return params;
                    }
                };
            requestQueue.add(request);
            }
            //if need to login
        });
    }

    private void requestProductInfo() {
        //getting from last activity

        String id = String.valueOf(getIntent().getExtras().get("id"));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, ServerAddress.address + ServerAddress.getproductbyid+ id, null, response -> {

            try {
              String n       =       response.getJSONObject(0).getString("product_name");
              String img     =    response.getJSONObject(0).getString("image");
              String desc    =    response.getJSONObject(0).getString("description");
              int price      =     response.getJSONObject(0).getInt("price");
                productName.setText(n);
                productDesc.setText(desc);
                productPrice.setText(String.valueOf(price));
                //we should load the image

                Picasso.get().load(ServerAddress.imageAddress+img).into(productImage);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {


        });
        requestQueue.add(jar);



    }



    private void onNegativeClicked() {
        negative.setOnClickListener(v -> {

       if (counterValue >= 1){
        counter.setText(String.valueOf(--counterValue));

       }
        });
    }

    private void onPlusClicked() {
        plus.setOnClickListener(v -> counter.setText(String.valueOf(++counterValue)));
    }

    private void productPageInit() {
        productImage =findViewById(R.id.view_pager);
        favorite=findViewById(R.id.product_page_favorite);
        productName=findViewById(R.id.product_page_productName);
        productPrice=findViewById(R.id.product_page_price);
        productDesc=findViewById(R.id.product_page_desc);
        addToCart=findViewById(R.id.add_to_cart);
        plus=findViewById(R.id.plus_btn);
        negative=findViewById(R.id.negative);
        counter=findViewById(R.id.counter);
        counter.setText(String.valueOf(counterValue));
    }
    private void addToCartRequest(String id ,String amount,String productId ){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        
        String url = ServerAddress.address+"insertorder.php?id="+id+"&"+"amount="+amount+"&product_id="+productId;
        StringRequest request = new StringRequest(Request.Method.GET,url,response ->{

            Log.i("addtocart",response);
            if(response.equals("order added")){
                Toast.makeText(this, "Product added to basket", Toast.LENGTH_SHORT).show();
                finish();
               // Toast.makeText(this, "Product Added to Basket", Toast.LENGTH_SHORT).show());
               // startActivity( new Intent(ProductPage.this,Basket.class));
            }
        } ,error -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }
}