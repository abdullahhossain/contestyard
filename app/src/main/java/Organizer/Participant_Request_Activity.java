package Organizer;

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
import com.yaga909.contestyard.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.ParticipantsRequest_Adapter;
import Adapters.Status_Adapter;
import SlideBar.Requests;
import USER.Status_List;

public class Participant_Request_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ParticipantsRequest_Adapter participant_adapter;
    List<Participant_List> participant_list;
    String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_request);

        recyclerView = findViewById(R.id.recyclerview);
        participant_list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        code = intent.getStringExtra("code");

        loadData(code);



    }

    private void loadData(String code) {
        String url = "http://projecttech.xyz/contest_yard/org_request.php?comp_code="+code;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i =0 ; i<jsonArray.length(); i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        participant_list.add(new Participant_List(data.getInt("id"),
                                data.getString("comp_name"),
                                data.getString("comp_image_name"),
                                data.getString("comp_code"),
                                data.getString("comp_status"),
                                data.getString("un_number"),
                                data.getString("student_name"),
                                data.getString("institution"),
                                data.getString("email")));
                       participant_adapter = new ParticipantsRequest_Adapter(Participant_Request_Activity.this, participant_list);
                       recyclerView.setAdapter(participant_adapter);
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