package com.ntc.duoihinh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.io.IOException;

import static com.ntc.duoihinh.Main2Activity.NameSharedPrefrence;
import static com.ntc.duoihinh.Main2Activity.diem;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener {
    TextView tvDiem, hien;
    ImageView imThoat, imshare, imlike;
    Button btnPlay, btnRuby;
    SharedPreferences sharedPreferences;
    MediaPlayer nhacnen = new MediaPlayer();
    CheckBox ckamthanh;

    RewardedVideoAd mAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar b = getSupportActionBar();
        b.hide();
        addcontroll();
        addEvent();
        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder()
                        .build());
    }

    //    @Override
//    protected void onResume() {
//        super.onResume();
//
//       nhacnen.stop();
//    }


    private void addEvent() {
        imThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulythoat();
            }


        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });
        imshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent();
                send.setAction(Intent.ACTION_SEND);
                send.putExtra(Intent.EXTRA_TEXT, "http:/// đây là trang game");
                send.setType("text/plain");
                startActivity(send);
            }
        });

        ckamthanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nhacnen.stop();
                } else {
                    nhacnen.start();
//                    try {
//                        nhacnen.prepare();// chạy lại lúc ta dừng ở đoạn nào đó
//                        nhacnen.start();
//                        //kiemtra= String.valueOf(false);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                }
            }
        });
        btnRuby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAd.isLoaded()) {
                    mAd.show();
                } else {
                    Log.e("Error", "ads not load");
                }

            }
        });
    }

    private void addcontroll() {
        hien = findViewById(R.id.tvhien);
        imThoat = findViewById(R.id.imthoat);
        imlike = findViewById(R.id.imlike);
        imshare = findViewById(R.id.imshare);
        btnPlay = findViewById(R.id.btnPlay);
        btnRuby = findViewById(R.id.btnRuby);

        tvDiem = findViewById(R.id.tv_diem);
        ckamthanh = findViewById(R.id.ckamthanh);
        sharedPreferences = getSharedPreferences(NameSharedPrefrence, MODE_PRIVATE);
        if (sharedPreferences.contains(diem)) {
            tvDiem.setText(sharedPreferences.getString(diem, "0"));
        }

        MobileAds.initialize(this, getString(R.string.admod_app_id));
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        nhacnen = MediaPlayer.create(MainActivity.this, R.raw.nhacnen);
        nhacnen.start();

//        mAd = MobileAds.getRewardedVideoAdInstance(this);
//        mAd.setRewardedVideoAdListener(this);
//        mAd.loadAd("ca-app-pub-3940256099942544/5224354917",
//                new AdRequest.Builder()
//                        .addTestDevice("6955C3C9E8E9286A35777305BC75929C")
//                        .build());



    }




    private void xulythoat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //tiêu đề
        builder.setTitle("Bạn có muốn thoát game?");
        //builder.setMessage(" Bạn có muốn thoát game?");
        builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public static final String TAG = "ADS";
    @Override
    public void onRewarded(RewardItem reward) {
       Log.e(TAG, "onRewarded! currency: " + reward.getType() + "  amount: " + reward.getAmount());
        int t=Integer.parseInt(tvDiem.getText().toString());
        t=t+reward.getAmount()-5;
        Log.e(TAG, "điểm x: "+t);
        tvDiem.setText(t+"");
        String r=String.valueOf(t);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(diem,r);
        editor.commit();
        // Reward the user.
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.e(TAG, "onRewardedVideoAdLeftApplication");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
       Log.e(TAG,"onRewardedVideoAdClosed");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        Log.e(TAG,"onRewardedVideoAdFailedToLoad:"+errorCode);
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Log.e(TAG,"onRewardedVideoAdLoaded");
    }

    @Override
    public void onRewardedVideoAdOpened() {
       Log.e(TAG, "onRewardedVideoAdOpened");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.e(TAG, "onRewardedVideoStarted");
    }

    @Override
    public void onRewardedVideoCompleted() {
        Log.e(TAG, "onRewardedVideoCompleted");

    }

    @Override
    public void onResume() {
        mAd.resume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //nhacnen.release();
        //nhacnen.start();
        nhacnen.stop();
        mAd.pause(this);
    }

//    @Override
//    public void onDestroy() {
//        mAd.destroy(this);
//        super.onDestroy();
//    }
}
