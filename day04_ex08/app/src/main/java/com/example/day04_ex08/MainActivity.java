package com.example.day04_ex08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button button;
    ImageView imageView, imageView2;
    int imgIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
    }

    public void onButtonClicked(View v){
        if(imgIndex == 0) {
            imageView.setVisibility(v.VISIBLE);
            imageView2.setVisibility(v.INVISIBLE);
            imgIndex = 1;
        }else {
            imageView.setVisibility(v.INVISIBLE);
            imageView2.setVisibility(v.VISIBLE);
            imgIndex = 0;
        }
    }
}