package com.example.traveljotter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;

public class pointAdapter extends InfoWindow.DefaultViewAdapter{
    private final Context mContext;
    private final ViewGroup mParent;
    private final String name;
    private final String addr;
    private final String content;

    public pointAdapter(@NonNull Context context, ViewGroup parent, String name, String addr, String review){
        super(context);

        mContext = context;
        mParent = parent;
        this.name = name;
        this.addr = addr;
        this.content = review;
    }

    @NonNull
    @Override
    public View getContentView(@NonNull InfoWindow infoWindow){
//        Intent intent = getIntent();
        View view = (View) LayoutInflater.from(mContext).inflate(R.layout.activity_itempoint, mParent, false);

        TextView txtTitle = (TextView) view.findViewById(R.id.txttitle);
        ImageView imagePoint = (ImageView) view.findViewById(R.id.imagepoint);
        TextView txtAddr = (TextView) view.findViewById(R.id.txtaddr);
        TextView txtContent = (TextView) view.findViewById(R.id.txtcontent);

        txtTitle.setText(name);
        txtAddr.setText(addr);
        txtContent.setText(content);

        /*txtTitle.setText("제주특별자치도청");
        imagePoint.setImageResource(R.drawable.touslesjours);
        txtAddr.setText("제주 제주시 문연로 6\n(지번) 연동 312-1");
        txtContent.setText("064-710-2114");*/

        return view;
    }
}
