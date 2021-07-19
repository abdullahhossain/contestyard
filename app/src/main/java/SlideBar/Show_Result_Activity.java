package SlideBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
import Adapters.Show_Result_Adapter;
import Organizer.Participant_List;
import USER.Show_Result_List;

public class Show_Result_Activity extends AppCompatActivity {


    RecyclerView recyclerView;
    Show_Result_Adapter show_result_adapter;
    List<Show_Result_List> show_list;
    String un_number;
    TextView msg;
    //SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        msg = findViewById(R.id.noItems);
     //   swipeRefreshLayout = findViewById(R.id.swipeRefresh);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadData(un_number);
//                show_list.clear();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });


        recyclerView = findViewById(R.id.recyclerView);
        show_list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        un_number = intent.getStringExtra("un_number");

        loadData(un_number);

    }

    public void loadData(String un_number) {

        String url = "http://projecttech.xyz/contest_yard/show_result1.php?un_number="+un_number;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i =0 ; i<jsonArray.length(); i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        show_list.add(new Show_Result_List(data.getInt("id"),
                                data.getString("comp_name"),
                                data.getString("comp_image_name"),
                                data.getString("comp_code"),
                                data.getString("comp_status"),
                                data.getString("un_number"),
                                data.getString("student_name"),
                                data.getString("institution"),
                                data.getString("email"),
                                data.getString("comp_id")));

                        show_result_adapter = new Show_Result_Adapter(Show_Result_Activity.this, show_list);
                        recyclerView.setAdapter(show_result_adapter);
                        msg.setText("");

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Show_Result_Activity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }
}