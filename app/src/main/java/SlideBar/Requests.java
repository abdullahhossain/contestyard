package SlideBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.yaga909.contestyard.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.Status_Adapter;
import USER.Status_List;

public class Requests extends AppCompatActivity {
    RecyclerView recyclerView;
    Status_Adapter status_adapter;
    List<Status_List> status_list;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        recyclerView = findViewById(R.id.recyclerview);
        status_list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        Intent intent = getIntent();
        code = intent.getStringExtra("code");

        loadData(code);


    }

    private void loadData(String code) {
        String url = "http://projecttech.xyz/contest_yard/request_status.php?un_number="+code;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject data =jsonArray.getJSONObject(i);
                        status_list.add(new Status_List(data.getInt("id"),
                                data.getString("comp_name"),
                                data.getString("comp_image_name"),
                                data.getString("comp_code"),
                                data.getString("comp_status"),
                                data.getString("un_number")));

                        status_adapter = new Status_Adapter(Requests.this, status_list);
                        recyclerView.setAdapter(status_adapter);
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