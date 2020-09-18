package com.example.nhom8_udluyenthitracnghiem;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    LinearLayout finale, hocbai, bienbao, meothi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        finale= findViewById(R.id.finale);
        finale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                Intent intent = new Intent(Main2Activity.this, ContestDetail.class);
                startActivityForResult(intent, 100);

            }
        });
        hocbai= findViewById(R.id.hocbai);
        hocbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, LearningActivity.class);
                startActivityForResult(intent, 100);

            }
        });
        bienbao= findViewById(R.id.bienbao);
        bienbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, BienBaoActivity.class);
                startActivityForResult(intent, 100);

            }
        });
        meothi= findViewById(R.id.meothi);
        meothi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMeoGhiNho = new Intent(Main2Activity.this,MeoGhiNhoActivity.class);
                startActivity(intentMeoGhiNho);
            }
        });
    }
}
