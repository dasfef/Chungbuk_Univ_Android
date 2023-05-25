package com.example.day06_activitycycle;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("액티비티 테스트 예제");
        Log.d("액티비티 테스트", "onCreate()");

        Button btnDial = (Button) findViewById(R.id.btnDial);
        btnDial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:010-1234-5678");
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });

        Button btnFinish = (Button) findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("액티비티 테스트", "onDestroy() 호출됨");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("액티비티 테스트", "onPause() 호출됨");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("액티비티 테스트", "onRestart() 호출됨");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("액티비티 테스트", "onResume() 호출됨");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("액티비티 테스트", "onStart() 호출됨");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("액티비티 테스트", "onStop() 호출됨");
    }

}