package com.ntc.duoihinh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Main3Activity extends AppCompatActivity {

    private final String[] Key={
            "HỘI ĐỒNG","ÁO MƯA","Ô TÔ","ĐÀN ÔNG","XÀ KÉP","TÔ HOÀI","CAN THIỆP","CÁT TƯỜNG",
            "DÁNH LỪA","TÍCH PHÂN","QUY HÀNG","SONG HÀNH","THỎ THẺ","THẤT TÌNH","TRANH THỦ",
            "TỔ TIÊN","MA SÁT","HỒNNG TÂM","BÀI BẠC","BÍ BÁCH","CHIA SẺ","CHÍN CHẮN","CỦ SU HÀO","DÊ TIỆN","ĐỔI TRẮNG THAY ĐEN",
            "FIFA","GIAO DIỆN","HOÀNG HẬU","HOÀNH TRÁNG","HỌC LỆCH","HỒI HỘP","HỒ VIỆT TRUNG",
            "KHAI THÁC","LA BÀN","LÃNH ĐẠO","MIÊU TẢ","NHẬT BẢN","ÔNG BẦU","QUY CHUẨN",
            "TÁM LẠNG NỬA CÂN","TÊ THẤP","THÔNG THOÁNG","THƯƠNG HIÊU","TỐI CAO","TRƯỜNG SƠN TÂY","TUNG TĂNG"
    };
    TextView tvdapan;
    Button btnTiep,btnThoat;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ActionBar b = getSupportActionBar();
        b.hide();
        addEvent();
        addControl();
        MobileAds.initialize(this, " ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void addControl() {
        loadData();

        btnTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Main3Activity.this,Main2Activity.class);
                startActivity(in); finish();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Main3Activity.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        });
    }

    private void loadData() {

        Intent i=getIntent();
        String s=i.getStringExtra("a");
        //System.out.println(" s là : "+s);
        int t=Integer.parseInt(s);
        tvdapan.setText(Key[t]);
    }

    private void addEvent() {
        tvdapan=findViewById(R.id.tvdapan);
        btnThoat=findViewById(R.id.btnThoat);
        btnTiep=findViewById(R.id.btnTiep);
    }




}
