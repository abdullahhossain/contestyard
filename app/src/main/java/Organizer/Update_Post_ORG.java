package Organizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.yaga909.contestyard.R;

public class Update_Post_ORG extends AppCompatActivity {
    int id;
    String post_name, post_fees, post_date, post_details, post_banner;
    TextView datechooser;
    EditText name, fees, description;
    ImageView banner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__post__o_r_g);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        post_name = intent.getStringExtra("post_name");
        post_fees = intent.getStringExtra("post_fees");
        post_date = intent.getStringExtra("post_date");
        post_details = intent.getStringExtra("post_details");
        post_banner = intent.getStringExtra("post_banner");

        datechooser = findViewById(R.id.dateChooser);
        name = findViewById(R.id.postNameET);
        fees = findViewById(R.id.postFeesET);
        description = findViewById(R.id.postDescriptionET);
        banner = findViewById(R.id.bannerViewIV);

        datechooser.setText(post_date);
        name.setText(post_name);
        fees.setText(post_fees);
        description.setText(post_details);

        String url = "http://projecttech.xyz/contest_yard/comp_banners/";
        Glide.with(this).load(url + post_banner).into(banner);




    }


    public void ChooseBanner(View view) {
    }

    public void DatePicker(View view) {
    }

    public void Update(View view) {
    }
}