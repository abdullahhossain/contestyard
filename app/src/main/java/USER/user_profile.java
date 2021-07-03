package USER;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import Adapters.User_Profile_Adapter;
import SlideBar.About_Us;
import SlideBar.Category;
import SlideBar.Notification;
import SlideBar.Posts;
import de.hdodenhof.circleimageview.CircleImageView;


public class user_profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView userNameTV, institutionNameTV;
    CircleImageView circleImageView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    RecyclerView recyclerView;
    User_Profile_Adapter user_profile_adapter;
    List<User_Profile_List> user_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        userNameTV = findViewById(R.id.userNameTV);
        institutionNameTV = findViewById(R.id.institutionTV);
        circleImageView = findViewById(R.id.profilePicture);
        toolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);



        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close

        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.newsFeed);


        loadData();
        loadPostData();


        //recycler view:
        recyclerView = findViewById(R.id.recyclerview);
        user_list = new ArrayList<>();
        // user_list2 = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


    }


    public void loadPostData() {
        String url = "http://projecttech.xyz/contest_yard/get_data_comp_post.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        user_list.add(new User_Profile_List(data.getInt("id"),
                                data.getString("name"),
                                data.getString("location"),
                                data.getString("logo_name"),
                                data.getString("comp_name"),
                                data.getString("comp_date"),
                                data.getString("comp_fees"),
                                data.getString("comp_image_name")));
                        Log.d("TAG", "onResponse: " + data);


                        // loadCompPost(postedBy);
                        user_profile_adapter = new User_Profile_Adapter(user_profile.this, user_list);
                        recyclerView.setAdapter(user_profile_adapter);

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


    private void loadPosts() {
    }


    private void loadData() {
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        String url = "http://projecttech.xyz/contest_yard/get_user_data.php?student_email=" + email + "&password=" + password;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("contest_yard_student");
                    JSONObject data = jsonArray.getJSONObject(0);


                    userNameTV.setText(data.getString("student_name"));
                    institutionNameTV.setText(data.getString("institution_name"));

                    String urlOfPhoto = "http://projecttech.xyz/contest_yard/upload/";
                    Glide.with(getApplicationContext()).load(urlOfPhoto + data.getString("dp_name")).into(circleImageView);

                    NavigationView navigationView = findViewById(R.id.navView);
                    View headerView = navigationView.getHeaderView(0);
                    CircleImageView headerImage = headerView.findViewById(R.id.organizerLogoCV);
                    TextView headerText = headerView.findViewById(R.id.heading_userName);

                    headerText.setText(data.getString("student_name"));
                    Glide.with(getApplicationContext()).load(urlOfPhoto + data.getString("dp_name")).into(headerImage);


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
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.newsFeed:
                break;

            case R.id.all_posts:
                startActivity(new Intent(this, Posts.class));
                break;

            case R.id.category:
                startActivity(new Intent(this, Category.class));
                break;

            case R.id.notification:
                startActivity(new Intent(this, Notification.class));
                break;


            case R.id.aboutUs:
                startActivity(new Intent(this, About_Us.class));
                break;

            case R.id.logout:
//
//
                SharedPreferences.Editor editor = getSharedPreferences("LOGIN_TRACK", MODE_PRIVATE).edit();
                editor.clear().apply();
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

        }


        return true;
    }
}