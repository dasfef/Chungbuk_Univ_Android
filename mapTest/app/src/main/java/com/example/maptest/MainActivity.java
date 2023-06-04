package com.example.maptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private static NaverMap naverMap;

    //마커 변수 선언 및 초기화
    private Marker marker1 = new Marker();
    private Marker marker2 = new Marker();
    private Marker marker3 = new Marker();
    private ArrayList<Marker> markerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //네이버 지도
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Button btnMark1 = (Button) findViewById(R.id.btnmark1);
        Button btnMark2 = (Button) findViewById(R.id.btnmark2);
        Button btnMark3 = (Button) findViewById(R.id.btnmark3);

        btnMark1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMark(marker1, 36.7288, 127.4339, R.drawable.baseline_place_24, 0);
                marker1.setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        Toast.makeText(getApplicationContext(), "마커 1이 클릭되었습니다", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        });

        btnMark2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMark(marker2, 36.6678, 127.4787, R.drawable.baseline_place_24, 0);
                marker2.setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        Toast.makeText(getApplicationContext(), "여긴 우리집", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        });

        btnMark3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMark(marker3, 36.63473,  127.48801, R.drawable.baseline_place_24, 0);
                marker3.setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        Toast.makeText(getApplicationContext(), "여긴 시청입니다", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        });




    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        this.naverMap = naverMap;

        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(36.72773, 127.43676),      // 위치 지정
                14                                              // 줌 레벨
        );
        naverMap.setCameraPosition(cameraPosition);
        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
//                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude));
//                naverMap.moveCamera(cameraUpdate);

                Marker marker = new Marker();
                setMark(marker, latitude, longitude, R.drawable.baseline_place_24, 0);
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
        markerList.add(marker);
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