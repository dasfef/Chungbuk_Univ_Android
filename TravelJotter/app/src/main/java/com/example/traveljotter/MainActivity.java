package com.example.traveljotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout lilAdd = findViewById(R.id.lilAdd);
        LinearLayout lilChk = findViewById(R.id.lilChk);

        //등록하기 레이아웃 눌렀을 때 이벤트 리스너 만들기
        View.OnClickListener Addclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddIntent = new Intent(getApplicationContext(),Chk_Activity.class);
                startActivity(AddIntent);
            }
        };

        //조회하기 레이아웃 눌렀을 때 이벤트 리스너 만들기
        View.OnClickListener Chkclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChkIntent = new Intent(getApplicationContext(),Map_Activity.class);
                startActivity(ChkIntent);
            }
        };

        //lilAdd, lilChk에 이벤트 등록
        lilAdd.setOnClickListener(Addclick);
        lilChk.setOnClickListener(Chkclick);
    }
}