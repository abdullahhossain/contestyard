package Organizer;

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
import com.yaga909.contestyard.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Organizer_Login extends AppCompatActivity {

    EditText userNameET, pinET;
    SharedPreferences sharedPreferences;
    String pref_name = "TRACK";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer__login);
        userNameET = findViewById(R.id.postFeesET);
        pinET = findViewById(R.id.estdTV);





    }

    public void logInBTN(View view) {
        String name = userNameET.getText().toString();
        String code = pinET.getText().toString();
        orgLogIn(name, code);
    }

    private void orgLogIn(String userName, String pin) {
        String url = "http://projecttech.xyz/contest_yard/org_login.php?user_name=" + userName + "&request_code=" + pin;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject data = jsonArray.getJSONObject(0);
                    if (!data.getString("error").equalsIgnoreCase("no")) {
                        Toast.makeText(Organizer_Login.this, "Invalid Access!", Toast.LENGTH_SHORT).show();
                    } else {




                        Intent intent = new Intent(Organizer_Login.this, Organizer_Newsfeed.class);
                        intent.putExtra("username", userName);
                        intent.putExtra("code", pin);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Organizer_Login.this, "Login Approved", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(stringRequest);


    }
}