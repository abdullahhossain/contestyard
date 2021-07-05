package Organizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yaga909.contestyard.R;

public class Update_Post_ORG extends AppCompatActivity {
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__post__o_r_g);
        Intent intent = getIntent();
       id = intent.getStringExtra("id");
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();

    }

    public void CreatePost(View view) {
    }
}