package com.example.shopx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import Model.ServerAddress;

public class MainActivity extends AppCompatActivity {
    ImageView logo;
    EditText username,email,password,confirmPassword;
    AppCompatButton next;
    TextView youAlreadyHaveAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUpInit();
        onNextClicked();
        onAlreadyHaveAccount();

    }

    private void onAlreadyHaveAccount() {
        finish();
        startActivity(new Intent(MainActivity.this,Login.class));
    }

    private void onNextClicked() {

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting inputs

                String userName =username.getText().toString().toLowerCase().trim();
                String input_email =email.getText().toString().toLowerCase().trim();
                String input_password =password.getText().toString().toLowerCase().trim();
                String input_confirmPassword =confirmPassword.getText().toString().toLowerCase().trim();
                //check email from database


                //checking password and not EMT
                if (userName.isEmpty()||input_email.isEmpty()||input_password.isEmpty()||input_confirmPassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }else
                    // checking password matching
                if (input_password.equals(input_confirmPassword)){
                  checkEmail(userName,input_email,input_password);
                }else {
                    Toast.makeText(MainActivity.this, "Password its not match", Toast.LENGTH_SHORT).show();
                }


            }


        });
    }

    private void signUpInit() {
        next=findViewById(R.id.sign_up_next);
        logo=findViewById(R.id.imageView);
        username=findViewById(R.id.sign_up_username);
        email=findViewById(R.id.sign_up_email);
        password=findViewById(R.id.sign_up_password);
        confirmPassword=findViewById(R.id.sign_up_confirm_password);
        youAlreadyHaveAccount =findViewById(R.id.textView);
    }
    private void checkEmail(String userName , String input_email ,String input_password) {

     String checking =   email.getText().toString().toLowerCase().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, ServerAddress.address + "checkemail.php?email=" + checking, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
          if (response.equals("positive")){
              //closing this activity
              finish();

              //sending data and move to InfoGetter
              startActivity(new Intent(MainActivity.this,InfoGetter.class).putExtra("username",userName).putExtra("email",input_email).putExtra("password",input_password));
          }else if (response.equals("negative")){
              Toast.makeText(MainActivity.this, "Account already exist with this email", Toast.LENGTH_SHORT).show();
          }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue.add(request);
    }
}