package com.example.day05_tabhostpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecIMG1 = tabHost.newTabSpec("IMG1").setIndicator("1번 이미지");
        tabSpecIMG1.setContent(R.id.image1);
        tabHost.addTab(tabSpecIMG1);

        TabHost.TabSpec tabSpecIMG2 = tabHost.newTabSpec("IMG2").setIndicator("2번 이미지");
        tabSpecIMG2.setContent(R.id.image2);
        tabHost.addTab(tabSpecIMG2);

        TabHost.TabSpec tabSpecIMG3 = tabHost.newTabSpec("IMG3").setIndicator("3번 이미지");
        tabSpecIMG3.setContent(R.id.image3);
        tabHost.addTab(tabSpecIMG3);

        TabHost.TabSpec tabSpecIMG4 = tabHost.newTabSpec("IMG4").setIndicator("4번 이미지");
        tabSpecIMG4.setContent(R.id.image4);
        tabHost.addTab(tabSpecIMG4);

        tabHost.setCurrentTab(0);
    }
}