package com.example.maptest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DialogActivity extends AppCompatActivity {
    public static Context mContext;
    public String editname;
    public String editaddr;
    public String editcontent;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        setTitle("주소지 등록");
        this.mContext = mContext;

        Intent intent = new Intent(getApplicationContext(), pointAdapter.class);
        Button btnReg = (Button) findViewById(R.id.btnRegister);
        Button btnCan = (Button) findViewById(R.id.btnCancel);
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editAddr = (EditText) findViewById(R.id.editAddr);
        EditText editContent = (EditText) findViewById(R.id.editContent);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedData sharedData = SharedData.getInstance();

                sharedData.setName(editName.getText().toString());
                sharedData.setAddr(editAddr.getText().toString());
                sharedData.setContent(editContent.getText().toString());

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("EditName", editname);
                intent.putExtra("EditAddr", editaddr);
                intent.putExtra("EditContent", editcontent);

                finish();
            }
        });
    }
}
