package com.yaga909.contestyard;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Organizer.Organizer_Login;
import USER.Registration_Option;
import USER.user_profile;

public class MainActivity extends AppCompatActivity {

    String emailST, passwordST;
    SharedPreferences sharedPreferences;
    String PREF_NAME = "LOGIN_TRACK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false);
      if (isLoggedIn){
          Intent intent=new Intent(this, user_profile.class);
          intent.putExtra("email",sharedPreferences.getString("email",""));
          intent.putExtra("password",sharedPreferences.getString("password",""));
          startActivity(intent);
          finish();
      }



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

                        SharedPreferences.Editor editor=getSharedPreferences("LOGIN_TRACK",MODE_PRIVATE).edit();
                        editor.putBoolean("IS_LOGGED_IN",true);
                        editor.putString("email",userEmail);
                        editor.putString("password",userPassword);
                        editor.apply();

                        Intent intent = new Intent(MainActivity.this, user_profile.class);
                        intent.putExtra("email", userEmail);
                        intent.putExtra("password", userPassword);
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