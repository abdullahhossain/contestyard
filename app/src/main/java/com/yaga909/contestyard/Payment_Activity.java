package com.yaga909.contestyard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Payment_Activity extends AppCompatActivity {
    EditText creditNumberET, validNumberET, digitNumberET, nameET;
    String name, code, image, un_number, username, institution,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        creditNumberET = findViewById(R.id.creditNumberET);
        validNumberET = findViewById(R.id.validNumberET);
        digitNumberET = findViewById(R.id.digitNumberET);
        nameET = findViewById(R.id.nameET);

        Intent intent = getIntent();
        name = intent.getStringExtra("comp_name");
        image = intent.getStringExtra("comp_image");
        code = intent.getStringExtra("comp_code");
        un_number = intent.getStringExtra("un_number");
        username = intent.getStringExtra("username");
        institution = intent.getStringExtra("institution");
        email = intent.getStringExtra("email");

        Log.d("username", "onCreate: "+username);




    }

    public void ProcedBTN(View view) {

        String url = "http://projecttech.xyz/contest_yard/comp_request.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Payment_Activity.this, "Request Has Been Sent", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Payment_Activity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("comp_name", name);
                stringStringMap.put("comp_code", code);
                stringStringMap.put("comp_image_name", image);
                stringStringMap.put("comp_status", "Pending");
                stringStringMap.put("un_number", un_number);
                stringStringMap.put("student_name", username);
                stringStringMap.put("institution",institution);
                stringStringMap.put("email", email);

                return stringStringMap;
            }
        };
        requestQueue.add(stringRequest);


    }
}