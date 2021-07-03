package USER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yaga909.contestyard.R;

import Organizer.Organizer_Registration_Form;

public class Registration_Option extends AppCompatActivity {

    CardView participantBTN, organiztionBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__option);

        participantBTN = findViewById(R.id.participantBTN);
        organiztionBTN = findViewById(R.id.organizerBTN);

        participantBTN.setAlpha(0f);
        participantBTN.setTranslationY(60);
        organiztionBTN.setAlpha(0f);
        organiztionBTN.setTranslationY(60);


        //time
        participantBTN.animate().alpha(1f).translationYBy(-60).setDuration(1000);
        organiztionBTN.animate().alpha(1f).translationYBy(-60).setDuration(1300);

    }

    public void participantBTN(View view) {
        startActivity(new Intent(Registration_Option.this, Participant_Registration_Form.class));
    }

    public void organizerBTN(View view) {
        startActivity(new Intent(Registration_Option.this, Organizer_Registration_Form.class));

    }
}