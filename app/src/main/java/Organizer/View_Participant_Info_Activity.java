package Organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.yaga909.contestyard.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class View_Participant_Info_Activity extends AppCompatActivity {
    TextView nameTV, compNameTV, instituteTV, emailTV;
    CircleImageView compImageIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_participant_info);

        nameTV = findViewById(R.id.nameTV);
        compNameTV = findViewById(R.id.contestNameTV);
        instituteTV = findViewById(R.id.instituteTV);
        emailTV = findViewById(R.id.emailTV);
        compImageIV = findViewById(R.id.circleImageIV);




    }
}