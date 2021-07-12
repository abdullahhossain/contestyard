package USER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaga909.contestyard.R;

public class Details_And_Apply_Activity extends AppCompatActivity {
    String name, photoname, fee, details;
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

        postName.setText(name);
        postFee.setText(fee);
        postDetails.setText(details);

        String url = "http://projecttech.xyz/contest_yard/comp_banners/";

        Glide.with(this).load(url+photoname).into(postImage);





    }
}