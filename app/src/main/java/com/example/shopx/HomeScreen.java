package com.example.shopx;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

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

    //search mode objects
    SearchView search;
    RecyclerView searchList;

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
        searchMode();
        //initializing the orderRoad Objects
        orderModeInit();
        //initializing main buttons
        mainButtonInit();

        // mode 1   category mode  (when user first time enter  || click on shop category )  we should disable mode 2 and 3
        //first should automatic  all category  load when user enter the app
        setupCatRecycler();

        // mode 2   search mode     (by searching we will go to search mode)  we should disable mode 1 and 3
        // mode 3   order mode       (by clicking on order road icon)   we should disable mode 2 and 1'




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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this ,RecyclerView.HORIZONTAL,false);
        categoryOne.setLayoutManager(layoutManager);
        adapter=new CatAdapter(this, productCatItems);
        categoryOne.setAdapter(adapter);

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

    private void searchMode() {
        search=findViewById(R.id.searchView2);
        searchList=findViewById(R.id.recycler_search);
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