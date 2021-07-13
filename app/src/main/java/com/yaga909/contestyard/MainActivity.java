package com.yaga909.contestyard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yaga909.contestyard.session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Organizer.Organizer_Login;
import USER.Registration_Option;
import USER.user_profile;

public class MainActivity extends AppCompatActivity {
    EditText emailET, passwordET;
    String emailST, passwordST, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emailET = findViewById(R.id.postFeesET);
        passwordET = findViewById(R.id.estdTV);
        emailST = emailET.getText().toString();
        passwordST = passwordET.getText().toString();

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void registrationForm(View view) {
        startActivity(new Intent(MainActivity.this, Registration_Option.class));
    }

    public void forgetPassword(View view) {
        startActivity(new Intent(MainActivity.this, Organizer_Login.class));
    }

    public void logInBTN(View view) {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        userLogin(email, password);

    }

    private void userLogin(String userEmail, String userPassword) {


        String url = "http://projecttech.xyz/contest_yard/user_login.php?student_email=" + userEmail + "&password=" + userPassword;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject data = jsonArray.getJSONObject(0);
                    if (!data.getString("error").equalsIgnoreCase("no")) {
                        Toast.makeText(MainActivity.this, "Invalid Email Or Password!", Toast.LENGTH_SHORT).show();
                    } else {
                        SessionManager sessionManager = new SessionManager(MainActivity.this);
                        sessionManager.createSession(userEmail, userPassword);

                        code = data.getString("un_number");

                        Intent intent = new Intent(MainActivity.this, user_profile.class);
                        intent.putExtra("email", userEmail);
                        intent.putExtra("password", userPassword);
                        intent.putExtra("un_number", code);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);


    }

    public void TermsandCondition(View view) {
        startActivity(new Intent(MainActivity.this, terms_and_conditions.class));
    }
}