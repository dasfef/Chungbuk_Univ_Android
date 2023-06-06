package com.example.maptest;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.map.overlay.Marker;

public class PointInfo extends AppCompatActivity {
    static String name;
    static String addr;
    static String content;
    static Marker marker;

    public PointInfo(String name, String addr, String content, Marker marker){
        Intent intent = getIntent();
        this.name = intent.getStringExtra("EditName");
        this.addr = intent.getStringExtra("EditAddr");
        this.content = intent.getStringExtra("EditContent");
        this.marker = marker;
    }
}
