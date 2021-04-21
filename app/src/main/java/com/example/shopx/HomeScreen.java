package com.example.shopx;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
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
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Product;
import Model.ProductCatItem;
import Model.ServerAddress;


public class HomeScreen extends AppCompatActivity {
    //Logo
    ImageView logo;
    //drawer objects
    DrawerLayout drawer;
    NavigationView nv;
    ImageView drawerTrigger;

    //category mode objects
    TextView categoryOneText,categoryTwoText,categoryThreeText;
    RecyclerView categoryOne, categoryTwo ,categoryThree;

    CatAdapter adapter;
    CatAdapter adapter1;
    CatAdapter adapter2;
    //search mode objects
    SearchView search;
    RecyclerView searchList;

    ProductAdapter productAdapter;


    //order mode objects
    RecyclerView orderStatusList;
    OrderRoadAdapter orderRoadAdapter;

    //main buttons
    ImageButton shop , order , basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //initializing logo
        logoInit();
        //initializing the drawer
        drawerInit();
        //initializing the  category objects
        categoryModeInit();
        //initializing the search  objects
        searchModeInit();
        //initializing the orderRoad Objects
        orderModeInit();
        //initializing main buttons
        mainButtonInit();

        //geting  productItems from server
        List<ProductCatItem> productCatItems = getCatItems();


        setupCatRecycler(productCatItems);

        // mode 1   category mode  (when user first time enter  || click on shop category )  we should disable mode 2 and 3
        //first should automatic  all category  load when user enter the app
        // mode 2   search mode     (by searching we will go to search mode)  we should disable mode 1 and 3
        // mode 3   order mode       (by clicking on order road icon)   we should disable mode 2 and 1'

        // will change mode
        OnEventHappend();
        // when user search
        performingSearch();
        //just setting a title
        setTitle("Homescreen");



    }

    private void logoInit() {
        logo=findViewById(R.id.imageView3);
    }



    private List<ProductCatItem> getCatItems() {
       final List<ProductCatItem> productCatItems = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, ServerAddress.address + "getproduct.php?id=1", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.i("aa",response.getJSONObject(1).getString("product_name"));
                for (int i = 0 ;i<response.length();i++){
                   int price = response.getJSONObject(i).getInt("price");
                   String product_name = response.getJSONObject(i).getString("product_name");
                   String image = ServerAddress.imageAddress + response.getJSONObject(i).getString("image");
                   String description = response.getJSONObject(i).getString("description");
                   ProductCatItem productCatItem = new ProductCatItem(image,product_name,price);
                   productCatItem.setId(response.getJSONObject(i).getInt("id"));
                   productCatItems.add( productCatItem);
                   Log.i("aaa",productCatItems.get(0).getName());

                }
                }catch (JSONException e){
                    e.getMessage();
                }

                //when response receveid we should call the recycleres to update
                adapter.notifyDataSetChanged();
                // and show the category Title
                categoryOneText.setText("Category A");
                categoryTwoText.setText("Category B");
                categoryThreeText.setText("Category C");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    requestQueue.add(jar);
    return  productCatItems;
    }

    private void performingSearch() {
     //   String a = "https://www.apple.com/newsroom/images/product/iphone/standard/Apple_iphone_11-rosette-family-lineup-091019_big.jpg.medium.jpg";
        List<Product> products =new ArrayList<>();


        RecyclerView.LayoutManager searchlayoutmanager = new GridLayoutManager(this,2);
        searchList.setLayoutManager(searchlayoutmanager);
        productAdapter= new ProductAdapter(products,this);
        searchList.setAdapter(productAdapter);
        //test
        // Get the search close button image view



    }

    private void OnEventHappend() {
        //mode 1
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onShopClicked();

            }
        });
        //mode 3
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        onOrderRoadClicked();
            }
        });
        //mode 2
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
             onUserSearched();
           // productAdapter.getFilter().filter(query);

                //this query should send to server and get the result and set the result in recycler view
                String URl = ServerAddress.address+"search.php?search="+query;
                RequestQueue requestQueue = Volley.newRequestQueue(HomeScreen.this);
                JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, URl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            try {
                                List<Product> products = new ArrayList<>();
                                for (int i=0;i<response.length();i++){

                            String name =    response.getJSONObject(i).getString("product_name");
                            int price =    response.getJSONObject(i).getInt("price");
                            String image = ServerAddress.imageAddress+ response.getJSONObject(i).getString("image");
                            String desc = response.getJSONObject(i).getString("description");
                            Product product = new Product(name,price,image,desc);
                            product.setId(response.getJSONObject(i).getInt("id"));
                            products.add(product);



                            Log.i("searchresult",name);


                                }
                                productAdapter.setProducts(products);
                            }catch (JSONException e){

                            }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                                Log.i("searchresult",error.getMessage());
                    }
                });
                requestQueue.add(jar);

                return false;

            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBasketClicked();
            }
        });
    }

    private void onBasketClicked() {
       // startActivity(new Intent(this,Basket.class));
        startActivityForResult(new Intent(this,Basket.class),2);
    }

    private void onShopClicked() {


        //change color of shop nad order
        order.setBackgroundColor(Color.parseColor("#C5D7BD"));
        shop.setBackgroundColor(Color.parseColor("#007580"));

        // EmT the search box
        search.setQuery("",false);
        search.clearFocus();
        //invisible serach list to user
        searchList.setVisibility(View.GONE);
        //make order road invisible to user
        orderStatusList.setVisibility(View.GONE);
        // make cat visible to user
        categoryOne.setVisibility(View.VISIBLE);
        categoryTwo.setVisibility(View.VISIBLE);
        categoryThree.setVisibility(View.VISIBLE);
        categoryOneText.setVisibility(View.VISIBLE);
        categoryTwoText.setVisibility(View.VISIBLE);
        categoryThreeText.setVisibility(View.VISIBLE);
        Log.i("mode","mode 1");


    }

    public void onOrderRoadClicked() {
        //change color back

       shop.setBackgroundColor(Color.parseColor("#C5D7BD"));
       order.setBackgroundColor(Color.parseColor("#007580"));

        // ET the search box
        search.setQuery("",false);
        search.clearFocus();
        //make category invisilbe to user
        categoryOne.setVisibility(View.GONE);
        categoryTwo.setVisibility(View.GONE);
        categoryThree.setVisibility(View.GONE);
        categoryOneText.setVisibility(View.GONE);
        categoryTwoText.setVisibility(View.GONE);
        categoryThreeText.setVisibility(View.GONE);
        //invisible serach list to user
        searchList.setVisibility(View.GONE);
        //make road visible to user
       orderStatusList.setVisibility(View.VISIBLE);
        //makes the status list
        List<Integer> order_statuses = new ArrayList<>();
        //get data from shared p
        SharedPreferences sharedpreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        String id = sharedpreferences.getString("id","no");
        if (id.equals("no"))return ;

       //send request for orders
        RequestQueue orderqueue = Volley.newRequestQueue(this);
        JsonArrayRequest getRoad = new JsonArrayRequest(Request.Method.GET, ServerAddress.address + "getroad.php?id="+id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                        Log.i("order_status", String.valueOf(response.length()));

                    try {
                        for (int i = 0 ; i< response.length();i++){
                        int status = response.getJSONObject(i).getInt("order_status");
                            order_statuses.add(status);
                            System.out.println(status);
                        }
                        // this makes the order road adapter understand that we changed some value :)
                            orderRoadAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("order_status",e.getMessage());
                    }
                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                        Log.i("order_status",error.getMessage());
            }
        });
        orderqueue.add(getRoad);
        //make the adapter

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this ,RecyclerView.VERTICAL,false);
        orderStatusList.setLayoutManager(layoutManager);
        orderRoadAdapter =new OrderRoadAdapter(order_statuses,this);
        orderStatusList.setAdapter(orderRoadAdapter);
        //manage the road

        Log.i("mode","mode 2");
    }

    private void onUserSearched() {
        //make order road invisilbe to user

        //make category invisilbe to user
        categoryOne.setVisibility(View.GONE);
        categoryTwo.setVisibility(View.GONE);
        categoryThree.setVisibility(View.GONE);
        categoryOneText.setVisibility(View.GONE);
        categoryTwoText.setVisibility(View.GONE);
        categoryThreeText.setVisibility(View.GONE);
        //make search list visible to user
        searchList.setVisibility(View.VISIBLE);
        //invisble the order list
        orderStatusList.setVisibility(View.INVISIBLE);
        Log.i("mode","mode 3");
    }

    private void setupCatRecycler(List<ProductCatItem> products) {
        //categoryOne a recycler that init
        // a is image photo address from internet
       // String a = "https://www.apple.com/newsroom/images/product/iphone/standard/Apple_iphone_11-rosette-family-lineup-091019_big.jpg.medium.jpg";
        //List<ProductCatItem> productCatItems = new ArrayList<>();
        //productCatItems.add(new ProductCatItem(a,"iphone1",9999));
       // productCatItems.add(new ProductCatItem(a,"iphone2",9998));
       // productCatItems.add(new ProductCatItem(a,"iphone3",9997));
       // productCatItems.add(new ProductCatItem(a,"iphone4",9996));

        //cat1

       // categoryOneText.setText("category1");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this ,RecyclerView.HORIZONTAL,false);
        categoryOne.setLayoutManager(layoutManager);
        adapter=new CatAdapter(this, products);
        categoryOne.setAdapter(adapter);
        //cat2
       // categoryTwoText.setText("category2");
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this ,RecyclerView.HORIZONTAL,false);
        categoryTwo.setLayoutManager(layoutManager1);
        adapter1= new CatAdapter(this,products);
        categoryTwo.setAdapter(adapter1);

        //cat3
      //  categoryThreeText.setText("category3");
        RecyclerView.LayoutManager layoutManager2= new LinearLayoutManager(this ,RecyclerView.HORIZONTAL,false);
        categoryThree.setLayoutManager(layoutManager2);
        adapter2=new CatAdapter(this,products);
        categoryThree.setAdapter(adapter2);
    }

    private void mainButtonInit() {
        shop=findViewById(R.id.shop);
        order=findViewById(R.id.order_btn);
        basket=findViewById(R.id.basket);
    }

    private void orderModeInit() {

    orderStatusList=findViewById(R.id.order_status_recycler);


    }

    private void searchModeInit() {
        search=findViewById(R.id.searchView2);
        searchList=findViewById(R.id.recycler_search);

        //this will make the search view text white
        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) search.findViewById(id);
        textView.setTextColor(Color.WHITE);
    }

    private void categoryModeInit() {
        //init text
        categoryOneText = findViewById(R.id.text_category_one);
        categoryTwoText =findViewById(R.id.text_category_two);
        categoryThreeText =findViewById(R.id.text_category_three);
        //init recycler
        categoryOne=findViewById(R.id.category_one);
        categoryTwo=findViewById(R.id.category_two);
        categoryThree=findViewById(R.id.category_three);

    }


    private void drawerInit() {
        drawer = findViewById(R.id.home_screen_drawer);
        nv = findViewById(R.id.navigation_view);
        drawerTrigger = findViewById(R.id.drawer_trigger);

        drawerTrigger.setOnClickListener(v -> {
            Toast.makeText(HomeScreen.this, "clicked", Toast.LENGTH_SHORT).show();
            drawer.openDrawer(GravityCompat.END);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 878787 ){
            onOrderRoadClicked();
        }
    }
}