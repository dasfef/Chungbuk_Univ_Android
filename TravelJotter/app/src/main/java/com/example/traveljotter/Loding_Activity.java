package com.example.traveljotter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Loding_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);

//        타이틀바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //로딩 화면 만들기, 3초 후 메인 화면으로 전환ㄴ
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
    protected void onPause(){
        super.onPause();
        finish();
    }
}