package com.example.day04_ex04;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        LinearLayout baseLayout = new LinearLayout(this);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setBackgroundColor(Color.rgb(0, 255, 0));
        setContentView(baseLayout, params);

        setTitle("Project5_1");

        EditText edit = new EditText(this);
        TextView textview = new TextView(this);
        Button button = new Button(this);

        button.setText("버튼");
        edit.setText("여기에 작성");

        baseLayout.addView(edit);
        baseLayout.addView(button);
        baseLayout.addView(textview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textview.setText(edit.getText());
            }
        });
    }
}