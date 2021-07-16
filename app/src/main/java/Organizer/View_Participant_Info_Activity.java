package Organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaga909.contestyard.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class View_Participant_Info_Activity extends AppCompatActivity {
    TextView nameTV, compNameTV, instituteTV, emailTV;
    CircleImageView compImageIV;
    String imagename, name, compname, institute, email;

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


        String url = "http://projecttech.xyz/contest_yard/comp_banners/";
        Glide.with(this).load(url+imagename).into(compImageIV);
        nameTV.setText(name);
        compNameTV.setText(compname);
        instituteTV.setText(institute);
        emailTV.setText(email);






    }
}