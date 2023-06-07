package com.example.traveljotter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DialogEdit extends AppCompatActivity {

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "placeregister";

    private EditText mEditTextName;
    private EditText mEditTextAddr;
    private EditText mEditTextReview;
    private TextView mTextResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_edit);

        mEditTextName = (EditText)findViewById(R.id.edtName);
        mEditTextAddr = (EditText)findViewById(R.id.edtAddress);
        mEditTextReview = (EditText)findViewById(R.id.edtReview);
        mTextResult = (TextView)findViewById(R.id.testText);

        Button btnPAdd = (Button)findViewById(R.id.btnPlaceAdd);
        Button btnBack = (Button)findViewById(R.id.btnBack);

        btnPAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = getIntent();
                String Lat = intent2.getStringExtra("lat");
                String Lon = intent2.getStringExtra("long");

                String Name = mEditTextName.getText().toString();
                String Addr = mEditTextAddr.getText().toString();
                String Review = mEditTextReview.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/placeregister.php",Name,Addr,Review,Lat,Lon);

                Intent intent = new Intent(getApplicationContext(), Chk_Activity.class);
                intent.putExtra("name", Name);
                intent.putExtra("address", Addr);
                intent.putExtra("review", Review);

                mEditTextName.setText("");
                mEditTextAddr.setText("");
                mEditTextReview.setText("");

//                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(DialogEdit.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String pName = (String)params[1];
            String pAddr = (String)params[2];
            String pReview = (String)params[3];
            String pLat = (String)params[4];
            String pLon = (String)params[5];

            String serverURL = (String)params[0];
            String postParameters = "name=" + pName + "&address=" + pAddr + "&review=" + pReview + "&latitude=" + pLat + "&longtitude=" + pLon;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();

            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }
        }
    }

}