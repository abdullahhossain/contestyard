package com.yaga909.contestyard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class Splash_Screen extends AppCompatActivity {
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        imageView = findViewById(R.id.imageView);

        imageView.setAlpha(0f);
        imageView.setTranslationY(60);


        //time
        imageView.animate().alpha(1f).translationYBy(-60).setDuration(1000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Screen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}