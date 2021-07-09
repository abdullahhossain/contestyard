package Organizer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.yaga909.contestyard.MainActivity;
import com.yaga909.contestyard.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.Org_Profile_Adapter;
import de.hdodenhof.circleimageview.CircleImageView;

public class Organizer_Newsfeed extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView orgNameTV;
   // SwipeRefreshLayout swipeRefreshLayout;
    CircleImageView circleImageView;
    String username, username1, code;
  int identity;


    //recyclerview:

    RecyclerView recyclerView;
    Org_Profile_Adapter org_profile_adapter;
    List<OrgProfile_List> org_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer__newsfeed);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolBar);
        orgNameTV = findViewById(R.id.userNameTV);
        circleImageView = findViewById(R.id.profilePicture);
     //  swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.homeORG);

        Intent intent = getIntent();
        username1 = intent.getStringExtra("username");
        code = intent.getStringExtra("code");

        recyclerView = findViewById(R.id.recyclerview);
        org_list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                loadNewsFeed(code);
//               swipeRefreshLayout.setRefreshing(false);
//
//            }
//        });


        loadData();
        loadNewsFeed(code);


    }


    public void loadNewsFeed(String code) {
        String url = "http://projecttech.xyz/contest_yard/org_post_newsfeed.php?comp_code=" + code;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        org_list.add(new OrgProfile_List(data.getInt("id"),
                                data.getString("comp_name"),
                                data.getString("comp_date"),
                                data.getString("comp_fees"),
                                data.getString("comp_image_name"),
                                data.getString("comp_description"),
                                data.getString("comp_code")));
                        identity = data.getInt("id");
                        Log.d("TAG", "onResponse: "+identity);

                        org_profile_adapter = new Org_Profile_Adapter(Organizer_Newsfeed.this, org_list);
                        recyclerView.setAdapter(org_profile_adapter);

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

    public void deletePost(Context context) {


        String url = "http://projecttech.xyz/contest_yard/delete_post_ORG.php?id=" + identity;
        Log.d("TAG", "deletePost: "+identity);


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Post Has Been Deleted: "+identity, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);


    }


    private void loadData() {
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        String code = intent.getStringExtra("code");

        String url = "http://projecttech.xyz/contest_yard/get_org_data.php?user_name=" + username + "&request_code=" + code;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("contest_yard_student");
                    JSONObject data = jsonArray.getJSONObject(0);

                    orgNameTV.setText(data.getString("name"));


                    String urlOfPhoto = "http://projecttech.xyz/contest_yard/org_upload/";
                    Glide.with(getApplicationContext()).load(urlOfPhoto + data.getString("logo_name")).into(circleImageView);

                    NavigationView navigationView = findViewById(R.id.navView);
                    View headerView = navigationView.getHeaderView(0);
                    CircleImageView headerImage = headerView.findViewById(R.id.organizerLogoCV);
                    TextView headerText = headerView.findViewById(R.id.heading_userName);

                    headerText.setText(data.getString("name"));
                    Glide.with(getApplicationContext()).load(urlOfPhoto + data.getString("logo_name")).into(headerImage);


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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.homeORG:
                break;

            case R.id.createPostORG:
                Intent intent = new Intent(Organizer_Newsfeed.this, Create_Post_ORG.class);
                intent.putExtra("posted_by", username1);
                intent.putExtra("code1", code);
                startActivity(intent);
                break;


            case R.id.logout:
                Intent intent1 = new Intent(Organizer_Newsfeed.this, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}