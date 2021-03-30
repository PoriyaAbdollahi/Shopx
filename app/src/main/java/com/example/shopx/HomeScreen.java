package com.example.shopx;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


public class HomeScreen extends AppCompatActivity {
    DrawerLayout drawer;
    NavigationView nv;
    ImageView drawerTrigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        drawer = findViewById(R.id.home_screen_drawer);
        nv = findViewById(R.id.navigation_view);
        drawerTrigger = findViewById(R.id.drawer_trigger);

        drawerTrigger.setOnClickListener(v -> {
            Toast.makeText(HomeScreen.this, "clicked", Toast.LENGTH_SHORT).show();
            drawer.openDrawer(GravityCompat.END);
        });
    }

}