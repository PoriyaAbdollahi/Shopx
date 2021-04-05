package com.example.shopx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductPage extends AppCompatActivity {
    // a view pager that hold productImage
    ViewPager productImages;
    //a star favorite btn
    ImageView favorite ;

    TextView  productName, productPrice,productDesc;

    AppCompatButton addToCart ,plus, negative,counter;
    int counterValue = 0 ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        getClickedName();
    }

    private void getClickedName() {
        String a = String.valueOf(getIntent().getExtras().get("productName"));
        Log.i("nameLog",a);


        productPageInit();
        //on plus button what gonna happen
        onPlusClicked();
        //on negative button what gonna happen
        onNegativeClicked();
        //we should load send request to server with name of product that clicked and get result (better use Progressbar)



        //on add to cart button what gonna happen
        // we should check user login before (server)  if yes  then add the product  to user basket if no then we should send user to login page




        // on favorite button what gonna happen
    }

    private void onNegativeClicked() {
        negative.setOnClickListener(v -> {

       if (counterValue >= 1){
        counter.setText(String.valueOf(--counterValue));

       }
        });
    }

    private void onPlusClicked() {
        plus.setOnClickListener(v -> {
            counter.setText(String.valueOf(++counterValue));
        });
    }

    private void productPageInit() {
        productImages=findViewById(R.id.view_pager);
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
}