package com.example.maptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private static NaverMap naverMap;
    private ArrayList<Marker> markerList = new ArrayList<>();
    private Marker markerPath = new Marker();
    private ArrayList<Double> latitude = new ArrayList<>();
    private ArrayList<Double> longitude = new ArrayList<>();
    double latitudePath = 0;
    double longitudePath = 0;
    private ArrayList<InfoWindow> infoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //네이버 지도
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnDel = (Button) findViewById(R.id.btnDel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
                startActivity(intent);

                SharedData sharedData = SharedData.getInstance();
                Marker marker = new Marker();
                InfoWindow infoWindow = new InfoWindow();

                markerList.add(marker);
                infoList.add(infoWindow);
                latitude.add(latitudePath);
                longitude.add(longitudePath);

                setMark(marker, latitude.get(latitude.size()-1), longitude.get(longitude.size()-1), R.drawable.baseline_place_24, 0);
                for(int i=0; i<markerList.size(); i++){
                    int finalI = i;

                    markerList.get(i).setOnClickListener(new Overlay.OnClickListener() {
                        @Override
                        public boolean onClick(@NonNull Overlay overlay) {
                            Toast.makeText(getApplicationContext(), finalI +"번 마커 클릭되었습니다", Toast.LENGTH_SHORT).show();

                            Intent intent = getIntent();
                            String name = intent.getStringExtra("EditName");
                            String addr = intent.getStringExtra("EditAddr");
                            String content = intent.getStringExtra("EditContent");

                            ViewGroup rootView = (ViewGroup) findViewById(R.id.map_view);
                            pointAdapter adapter = new pointAdapter(MainActivity.this, rootView, name, addr, content);

                            infoList.get(finalI).setAdapter(adapter);
                            infoList.get(finalI).setZIndex(100);
                            infoList.get(finalI).setAlpha(0.9f);

                            if(markerList.get(finalI).getInfoWindow() == null) {
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

    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        this.naverMap = naverMap;
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
                markerPath.setIcon(OverlayImage.fromResource(R.drawable.baseline_place_24));
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