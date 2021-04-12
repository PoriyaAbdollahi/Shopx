package com.example.shopx;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import Model.ServerAddress;

public class Login extends AppCompatActivity {

AppCompatButton login;
ImageView logo;
EditText email, password;
TextView doNotHaveAccount,forgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginInit();

        onForgetPassword();
        OnDoNotHaveAccount();
        OnEventHappened();
    }

    private void OnEventHappened() {
        login.setOnClickListener(l-> onLogin());
    }

    private void OnDoNotHaveAccount() {
    doNotHaveAccount.setOnClickListener(l->{
        finish();
        startActivity(new Intent(Login.this,MainActivity.class));
    });
    }

    private void onForgetPassword() {
            // we need api for that
    }

    private void onLogin() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, ServerAddress.address+"login.php", response -> {
            if (response.equals("status updated")){
            // if login it was ok we should save login information into shared data
                SharedPreferences sharedpreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("email",email.getText().toString().toLowerCase().trim());
                editor.putString("password",password.getText().toString().toLowerCase().trim());
                editor.apply();
                // send user to last activity
                finish();

            }else
            // if login it was not ok we should show error
                if (response.equals("status update failed password or user incorrect")){
                    email.setError("password or email incorrect");
                    //try again
                }



        }, error -> {

        }){
            @Override
            public Map<String, String> getHeaders()  {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("email",email.getText().toString().toLowerCase().trim());
                params.put("password",password.getText().toString().toLowerCase().trim());

                return params;
            }
        };
        requestQueue.add(request);
    }

    private void loginInit() {
        logo=findViewById(R.id.imageView);
        login=findViewById(R.id.login_submit);
        email =findViewById(R.id.login_username);
        password=findViewById(R.id.login_password);
        doNotHaveAccount=findViewById(R.id.textView);
        forgetPassword=findViewById(R.id.textView3);
    }
}