package Organizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.yaga909.contestyard.R;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class View_Participant_Info_Activity extends AppCompatActivity {
    TextView nameTV, compNameTV, instituteTV, emailTV;
    CircleImageView compImageIV;
    String imagename, name, compname, institute, email, comp_code, un_number;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_participant_info);

        nameTV = findViewById(R.id.nameTV);
        compNameTV = findViewById(R.id.contestNameTV);
        instituteTV = findViewById(R.id.instituteTV);
        emailTV = findViewById(R.id.emailTV);
        compImageIV = findViewById(R.id.circleImageIV);

        Intent intent = getIntent();

        imagename = intent.getStringExtra("image_name");
        name = intent.getStringExtra("username");
        compname = intent.getStringExtra("comp_name");
        institute = intent.getStringExtra("institution");
        email = intent.getStringExtra("email");
        id = intent.getIntExtra("id", 0);
        comp_code = intent.getStringExtra("comp_code");
        un_number = intent.getStringExtra("un_code");

        Log.d("TAG", "accept: "+id);


        String url = "http://projecttech.xyz/contest_yard/comp_banners/";
        Glide.with(this).load(url + imagename).into(compImageIV);
        nameTV.setText(name);
        compNameTV.setText(compname);
        instituteTV.setText(institute);
        emailTV.setText(email);


    }

    public void acceptBTN(View view) {
        accept(String.valueOf(id));
    }

    private void accept(String id) {
        String url = "http://projecttech.xyz/contest_yard/accept_participant.php?id="+id;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(View_Participant_Info_Activity.this, "Request Accepted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(View_Participant_Info_Activity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("comp_status", "Accepted");

                return stringStringMap;
            }
        };
        requestQueue.add(stringRequest);


    }
}