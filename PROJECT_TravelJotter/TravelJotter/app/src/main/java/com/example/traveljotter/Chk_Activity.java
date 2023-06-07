package com.example.traveljotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Chk_Activity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private static NaverMap naverMap;
    private ArrayList<Marker> markerList = new ArrayList<>();
    private Marker markerPath = new Marker();
    private ArrayList<Double> latitude = new ArrayList<>();
    private ArrayList<Double> longitude = new ArrayList<>();
    double latitudePath = 0;
    double longitudePath = 0;
    private ArrayList<InfoWindow> infoList = new ArrayList<>();
    int count = 0;

    // 데이터 받기용 선언
    String url = "http://10.0.2.2/getplace.php";
    String mJsonString;
    public GettingPHP gPHP;
//    HashMap<String, ArrayList<String>> hash = new HashMap<>();
    ArrayList<HashMap<String, String>> hash = new ArrayList<>();

    Button btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // 데이터 받기용
        gPHP = new GettingPHP();
        gPHP.execute(url);

        // 네이버 지도
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDel = (Button) findViewById(R.id.btnDel);
        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        Button btnNothing = (Button) findViewById(R.id.btnNothing);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DialogEdit.class);
                String lat = String.format("%.5f", latitudePath);
                String lon = String.format("%.5f", longitudePath);
                intent.putExtra("lat", lat);
                intent.putExtra("long", lon);
                startActivity(intent);

//                SharedData sharedData = SharedData.getInstance();
                Marker marker = new Marker();
                InfoWindow infoWindow = new InfoWindow();

                markerList.add(marker);
                infoList.add(infoWindow);
                latitude.add(latitudePath);
                longitude.add(longitudePath);

                setMark(marker, latitude.get(latitude.size() - 1), longitude.get(longitude.size() - 1), R.drawable.baseline_location_on_24, 0);
            }
        });

        btnNothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < markerList.size(); i++) {
                    int finalI = i;

                    markerList.get(i).setOnClickListener(new Overlay.OnClickListener() {
                        @Override
                        public boolean onClick(@NonNull Overlay overlay) {
                            Toast.makeText(getApplicationContext(), finalI + "번 마커 클릭되었습니다", Toast.LENGTH_SHORT).show();

                            Intent intent = getIntent();
                            String name = intent.getStringExtra("name");
                            String addr = intent.getStringExtra("address");
                            String review = intent.getStringExtra("review");

                            infoList.get(finalI).setAdapter(new InfoWindow.DefaultTextAdapter(getApplicationContext()) {
                                @NonNull
                                @Override
                                public CharSequence getText(@NonNull InfoWindow infoWindow) {
                                    return hash.get(finalI).get("name");
                                }
                            });

                            /*ViewGroup rootView = (ViewGroup) findViewById(R.id.map_view);
                            pointAdapter adapter = new pointAdapter(Chk_Activity.this, rootView, name, addr, review);

                            infoList.get(finalI).setAdapter(adapter);
                            infoList.get(finalI).setZIndex(100);
                            infoList.get(finalI).setAlpha(0.9f);*/

                            if (markerList.get(finalI).getInfoWindow() == null) {
                                infoList.get(finalI).open(markerList.get(finalI));
                            } else {
                                infoList.get(finalI).close();
                            }


                            btnDel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getApplicationContext(), finalI + "번 마커 제거되었습니다", Toast.LENGTH_SHORT).show();
                                    delMark(markerList.get(finalI), infoList.get(finalI));
                                }
                            });
                            return false;
                        }
                    });
                }
            }
        });

    }


    private void delMark(Marker marker, InfoWindow info){
        marker.setMap(null);
        markerList.remove(marker);
        infoList.remove(info);
    }

    class GettingPHP extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String phpUrl = params[0];
            try {
                URL url = new URL(phpUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                conn.setUseCaches(false);

                int responseStatusCode = conn.getResponseCode();
                Log.d("webnautes", "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK){
                    inputStream = conn.getInputStream();
                }
                else {
                    inputStream = conn.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();
            } catch ( Exception e ) {
                Log.d("webnautes", "InsertData: Error ", e);
                return null;
            }

        }

        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            if (str == null){

                Toast.makeText(getApplicationContext(), "str = null", Toast.LENGTH_SHORT).show();
            }
            else {

                mJsonString = str;
                try {
                    // PHP에서 받아온 JSON 데이터를 JSON오브젝트로 변환
                    JSONObject jObject = new JSONObject(mJsonString);
                    // results라는 key는 JSON배열로 되어있다.
                    JSONArray results = jObject.getJSONArray("webnautes");

                    for ( int i = 0; i < results.length(); ++i ) {
                        JSONObject item = results.getJSONObject(i);

//                        ArrayList<String> nameList = new ArrayList<>();
                        String name = item.getString("name");
//                        nameList.add(name);

//                        ArrayList<String> addrList = new ArrayList<>();
                        String address = item.getString("address");
//                        addrList.add(address);

//                        ArrayList<String> revList = new ArrayList<>();
                        String review = item.getString("review");
//                        revList.add(review);

//                        ArrayList<String> latList = new ArrayList<>();
                        String latitude = item.getString("latitude");
//                        latList.add(latitude);

//                        ArrayList<String> lonList = new ArrayList<>();
                        String longtitude = item.getString("longtitude");
//                        lonList.add(longtitude);

                        HashMap <String, String> map = new HashMap<>();
                        map.put("name", name);
                        map.put("address", address);
                        map.put("review", review);
                        map.put("latitude", latitude);
                        map.put("longtitude", longtitude);

                        hash.add(map);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        this.naverMap = naverMap;

        naverMap.addOnLoadListener(new NaverMap.OnLoadListener() {
            @Override
            public void onLoad() {
                for (int i=0; i<hash.size(); i++){
                    Marker marker = new Marker();
                    InfoWindow infoWindow = new InfoWindow();
                    markerList.add(marker);
                    infoList.add(infoWindow);
                    setMark(marker, Double.parseDouble(hash.get(i).get("latitude")), Double.parseDouble(hash.get(i).get("longtitude")), R.drawable.baseline_location_on_24, 0);
                }
            }
        });

        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(36.72773, 127.43676),      // 위치 지정
                14                                                    // 줌 레벨
        );
        naverMap.setCameraPosition(cameraPosition);
        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {

                latitudePath = latLng.latitude;
                longitudePath = latLng.longitude;

                // 카메라 움직이기
                // CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude));
                // naverMap.moveCamera(cameraUpdate);

                markerPath.setPosition(new LatLng(latitudePath,longitudePath));
                markerPath.setIcon(OverlayImage.fromResource(R.drawable.baseline_location_on_24));
                markerPath.setMap(naverMap);

            }
        });

    }

    private void setMark(Marker marker, double lat, double lng, int resourceID, int zIndex)
    {
        //원근감 표시
        marker.setIconPerspectiveEnabled(true);
        //아이콘 지정
        marker.setIcon(OverlayImage.fromResource(resourceID));
        //마커의 투명도
        marker.setAlpha(0.8f);
        //마커 위치
        marker.setPosition(new LatLng(lat, lng));
        //마커 우선순위
        marker.setZIndex(zIndex);
        //마커 표시
        marker.setMap(naverMap);

    }

    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}