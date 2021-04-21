package com.example.shopx;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import Model.ServerAddress;

public class InfoGetter extends AppCompatActivity {
    AppCompatButton submit;
    EditText phone, address;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_getter);

        infoInit();
        onSubmitClicked();


    }

    private void onSubmitClicked() {
        submit.setOnClickListener(v -> {
            String fullName = getIntent().getExtras().get("username").toString();
            String email = getIntent().getExtras().get("email").toString();
            String password = getIntent().getExtras().get("password").toString();
            String phone_number = phone.getText().toString().toLowerCase().trim();
            String input_address = address.getText().toString().toLowerCase().trim();
            if (phone_number.isEmpty() || input_address.isEmpty()) {
                Toast.makeText(this, "fields are empty", Toast.LENGTH_SHORT).show();
                return;
            } else {
                RequestQueue requestQueue = Volley.newRequestQueue(InfoGetter.this);
                StringRequest request = new StringRequest(Request.Method.POST, ServerAddress.address + "signup.php", response -> {
                    Log.i("infoinfo", response);
                    if (response.length() >= 1 && !response.equals("0")) {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                        // we should save the data on local storage realm later
                        Log.i("infoinfo", response);
                        SharedPreferences sharedpreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("email",email);
                        editor.putString("password",password);

                        editor.putString("id", response);
                        editor.apply();
                        finish();
                    } else if (response.equals("0")) {

                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
                    // what happend if netwoork goes down
                }) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("password", password);
                        params.put("full_name", fullName);
                        params.put("email", email);
                        params.put("phone", phone_number);
                        params.put("address", input_address);
                        params.put("status", "1");

                        return params;
                    }
                };

                requestQueue.add(request);
            }

        });
    }

    private void infoInit() {
        submit = findViewById(R.id.sign_up_submit);
        phone = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);
        logo = findViewById(R.id.imageView);
    }
}