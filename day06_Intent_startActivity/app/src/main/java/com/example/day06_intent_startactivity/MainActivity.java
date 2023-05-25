package com.example.day06_intent_startactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("메인 액티비티");

        Button btnNewActivity = (Button) findViewById(R.id.btnNewActivity);

        btnNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editNum1 = (EditText)findViewById(R.id.editNum1);
                EditText editNum2 = (EditText)findViewById(R.id.editNum2);
                Intent intent = new Intent(getApplicationContext(), second.class);
                intent.putExtra("Num1", Integer.parseInt(editNum1.getText().toString()));
                intent.putExtra("Num2", Integer.parseInt(editNum2.getText().toString()));

                startActivityForResult(intent, 0);
            }
        });


    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK){
                int hap = data.getIntExtra("Hap", 0);
                Toast.makeText(getApplicationContext(), "합계 :" + hap, Toast.LENGTH_SHORT).show();
            }
        }

}