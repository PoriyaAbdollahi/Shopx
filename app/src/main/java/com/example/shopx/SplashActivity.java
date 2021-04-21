package com.example.shopx;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar ;
    ImageView logo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar=findViewById(R.id.progressBar);
        logo=findViewById(R.id.imageView5);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                moveToHomeScreen();
            }
        },5000);


    }
    private void moveToHomeScreen() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            //  for lollipop and above versions
            Pair[] pair = new Pair[1];
            pair[0] =new Pair<View, String>(logo,"logo");
            ActivityOptions activityOptions =  ActivityOptions.makeSceneTransitionAnimation( SplashActivity.this,pair);
            startActivity(new Intent(SplashActivity.this, HomeScreen.class),activityOptions.toBundle());
        } else{
            // for phones running an SDK before lollipop
            startActivity(new Intent(SplashActivity.this, HomeScreen.class));
        }


    }
}