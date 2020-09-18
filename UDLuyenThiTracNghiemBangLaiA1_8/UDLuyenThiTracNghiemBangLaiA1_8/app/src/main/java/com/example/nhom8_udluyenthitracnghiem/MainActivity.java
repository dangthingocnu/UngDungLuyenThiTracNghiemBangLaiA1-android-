package com.example.nhom8_udluyenthitracnghiem;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_welcome);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_welcome);
//Set animation to elements
        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);


    }
}
