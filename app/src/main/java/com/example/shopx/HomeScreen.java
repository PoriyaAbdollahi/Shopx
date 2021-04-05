package com.example.shopx;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import Model.Product;
import Model.ProductCatItem;


public class HomeScreen extends AppCompatActivity {
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
    //test
    ProductAdapter productAdapter;


    //order mode objects
    ImageView orderRoadBack ,orderRoadOne,orderRoadTwo,orderRoadThree,orderRoadFour;
    RecyclerView orderStatusList;
    //main buttons
    ImageButton shop , order , basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
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

        setupCatRecycler();

        // mode 1   category mode  (when user first time enter  || click on shop category )  we should disable mode 2 and 3
        //first should automatic  all category  load when user enter the app
        // mode 2   search mode     (by searching we will go to search mode)  we should disable mode 1 and 3
        // mode 3   order mode       (by clicking on order road icon)   we should disable mode 2 and 1'
        OnEventHappend();
        // when user search
        performingSearch();

    }

    private void performingSearch() {
        String a = "https://www.apple.com/newsroom/images/product/iphone/standard/Apple_iphone_11-rosette-family-lineup-091019_big.jpg.medium.jpg";
        List<Product> products =new ArrayList<>();
        products.add(new Product("iphone 11", 9999,a,"blahblahblah"));
        products.add(new Product("iphone 14", 9999,a,"blahblahblah"));
        products.add(new Product("iphone 16", 9999,a,"blahblahblah"));
        products.add(new Product("iphone 15", 9999,a,"blahblahblah"));
        products.add(new Product("iphone 14", 9999,a,"blahblahblah"));
        products.add(new Product("iphone 13", 9999,a,"blahblahblah"));
        products.add(new Product("iphone 12", 9999,a,"blahblahblah"));

        RecyclerView.LayoutManager searchlayoutmanager = new GridLayoutManager(this,2);
        searchList.setLayoutManager(searchlayoutmanager);
        productAdapter= new ProductAdapter(products,this);
        searchList.setAdapter(productAdapter);



    }

    private void OnEventHappend() {
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        onShopClicked();

            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        onOrderRoadClicked();
            }
        });
        // when search happend
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
             onUserSearched();
             productAdapter.getFilter().filter(query);
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
        startActivity(new Intent(this,Basket.class));
    }

    private void onShopClicked() {
        //change color of shop nad order
        order.setBackgroundColor(Color.parseColor("#C5D7BD"));
        shop.setBackgroundColor(Color.parseColor("#007580"));

        // ET the search box
        search.setQuery("",false);
        search.clearFocus();
        //invisible serach list to user
        searchList.setVisibility(View.GONE);
        //make order road invisible to user
        orderRoadBack.setVisibility(View.GONE);
        orderRoadOne.setVisibility(View.GONE);
        orderRoadTwo.setVisibility(View.GONE);
        orderRoadThree.setVisibility(View.GONE);
        orderRoadFour.setVisibility(View.GONE);
        // make cat visible to user
        categoryOne.setVisibility(View.VISIBLE);
        categoryTwo.setVisibility(View.VISIBLE);
        categoryThree.setVisibility(View.VISIBLE);
        categoryOneText.setVisibility(View.VISIBLE);
        categoryTwoText.setVisibility(View.VISIBLE);
        categoryThreeText.setVisibility(View.VISIBLE);
        Log.i("mode","mode 1");


    }

    private void onOrderRoadClicked() {
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
        orderRoadBack.setVisibility(View.VISIBLE);
        orderRoadOne.setVisibility(View.VISIBLE);
        orderRoadTwo.setVisibility(View.VISIBLE);
        orderRoadThree.setVisibility(View.VISIBLE);
        orderRoadFour.setVisibility(View.VISIBLE);
        Log.i("mode","mode 2");
    }

    private void onUserSearched() {
        //make order road invisilbe to user
        orderRoadBack.setVisibility(View.GONE);
        orderRoadOne.setVisibility(View.GONE);
        orderRoadTwo.setVisibility(View.GONE);
        orderRoadThree.setVisibility(View.GONE);
        orderRoadFour.setVisibility(View.GONE);
        //make category invisilbe to user
        categoryOne.setVisibility(View.GONE);
        categoryTwo.setVisibility(View.GONE);
        categoryThree.setVisibility(View.GONE);
        categoryOneText.setVisibility(View.GONE);
        categoryTwoText.setVisibility(View.GONE);
        categoryThreeText.setVisibility(View.GONE);
        //make search list visible to user
        searchList.setVisibility(View.VISIBLE);
        Log.i("mode","mode 3");
    }

    private void setupCatRecycler() {
        //categoryOne a recycler that init
        // a is image photo address from internet
        String a = "https://www.apple.com/newsroom/images/product/iphone/standard/Apple_iphone_11-rosette-family-lineup-091019_big.jpg.medium.jpg";
        List<ProductCatItem> productCatItems = new ArrayList<>();
        productCatItems.add(new ProductCatItem(a,"iphone1",9999));
        productCatItems.add(new ProductCatItem(a,"iphone2",9998));
        productCatItems.add(new ProductCatItem(a,"iphone3",9997));
        productCatItems.add(new ProductCatItem(a,"iphone4",9996));

        //cat1
        categoryOneText.setText("category1");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this ,RecyclerView.HORIZONTAL,false);
        categoryOne.setLayoutManager(layoutManager);
        adapter=new CatAdapter(this, productCatItems);
        categoryOne.setAdapter(adapter);
        //cat2
        categoryTwoText.setText("category2");
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this ,RecyclerView.HORIZONTAL,false);
        categoryTwo.setLayoutManager(layoutManager1);
        adapter1= new CatAdapter(this,productCatItems);
        categoryTwo.setAdapter(adapter1);

        //cat3
        categoryThreeText.setText("category3");
        RecyclerView.LayoutManager layoutManager2= new LinearLayoutManager(this ,RecyclerView.HORIZONTAL,false);
        categoryThree.setLayoutManager(layoutManager2);
        adapter2=new CatAdapter(this,productCatItems);
        categoryThree.setAdapter(adapter2);
    }

    private void mainButtonInit() {
        shop=findViewById(R.id.shop);
        order=findViewById(R.id.order_btn);
        basket=findViewById(R.id.basket);
    }

    private void orderModeInit() {
    orderRoadBack=findViewById(R.id.imageView2);
    orderRoadOne=findViewById(R.id.imageView4);
    orderRoadTwo=findViewById(R.id.imageView5);
    orderRoadThree=findViewById(R.id.imageView7);
    orderRoadFour=findViewById(R.id.imageView6);
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

}