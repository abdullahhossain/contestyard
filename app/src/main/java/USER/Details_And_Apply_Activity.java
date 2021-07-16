package USER;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.yaga909.contestyard.Payment_Activity;
import com.yaga909.contestyard.R;

public class Details_And_Apply_Activity extends AppCompatActivity {
    String name, photoname, fee, details, comp_code, un_number, username, institution, email;
    TextView postName, postFee, postDetails;
    ImageView postImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_and_apply);
        postName = findViewById(R.id.postNameTV);
        postFee = findViewById(R.id.feeTV);
        postDetails = findViewById(R.id.DescriptionTV);
        postImage = findViewById(R.id.bannerIV);

        Intent intent = getIntent();

        name = intent.getStringExtra("comp_name");
        fee = intent.getStringExtra("comp_fee");
        details = intent.getStringExtra("comp_details");
        photoname = intent.getStringExtra("comp_image");
        comp_code = intent.getStringExtra("comp_code");
        un_number = intent.getStringExtra("un_code");
        username = intent.getStringExtra("username");
        institution = intent.getStringExtra("institution");
        email = intent.getStringExtra("email");




        postName.setText(name);
        postFee.setText(fee);
        postDetails.setText(details);

        String url = "http://projecttech.xyz/contest_yard/comp_banners/";

        Glide.with(this).load(url + photoname).into(postImage);


    }

    public void ApplyBTN(View view) {
        Intent intent = new Intent(Details_And_Apply_Activity.this, Payment_Activity.class);
        intent.putExtra("comp_name", name);
        intent.putExtra("comp_image", photoname);
        intent.putExtra("comp_code", comp_code);
        intent.putExtra("un_number", un_number);
        intent.putExtra("username", username);
        intent.putExtra("institution", institution);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}