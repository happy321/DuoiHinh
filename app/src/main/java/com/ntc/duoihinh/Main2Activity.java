package com.ntc.duoihinh;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Main2Activity extends AppCompatActivity implements RewardedVideoAdListener {
    private static final String TAG ="rdom";
    private ImageView imThoat, imGoiY;
    private TextView tv_Lever;
    private TextView tv_ruby;
    private int ruby = 0;
    private LinearLayout layout_2;
    private Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_10,btn_11,btn_12,btn_13,btn_14,btn_15,btn_16;
    private Button btnD_1,btnD_2,btnD_3,btnD_4,btnD_5,btnD_6,btnD_7,btnD_8,btnD_9,btnD_10,btnD_11,btnD_12,btnD_13,btnD_14,btnD_15,btnD_16;
    private final String[] Key = {
            "HOIDONG","AOMUA","OTO","DANONG","XAKEP","TOHOAI","CANTHIEP","CATTUONG",
            "DANHLUA","TICHPHAN","QUYHANG","SONGHANH","THOTHE","THATTINH","TRANHTHU", "TOTIEN","MASAT","HONGTAM",
            "BAIBAC","BIBACH","CHIASE","CHINCHAN","CUSUHAO","DETIEN","DOITRANGTHAYDEN",
            "FIFA","GIAODIEN","HOANGHAU","HOANHTRANG","HOCLECH","HOIHOP","HOVIETTRUNG",
            "KHAITHAC","LABAN","LANHDAO","MIEUTA","NHATBAN","ONGBAU","QUYCHUAN",
            "TAMLANGNUACAN","TETHAP","THONGTHOANG","THUONGHIEU","TOICAO","TRUONGSONTAY","TUNGTANG"
    };
    public static final int[] QUESTIONS = {
            R.drawable.hoidong, R.drawable.aomua, R.drawable.oto,
            R.drawable.danong, R.drawable.xakep,
            R.drawable.tohoai, R.drawable.canthiep, R.drawable.cattuong, R.drawable.danhlua,
            R.drawable.tichphan, R.drawable.quyhang, R.drawable.songsong, R.drawable.thothe,
            R.drawable.thattinh, R.drawable.tranhthu, R.drawable.totien, R.drawable.masat,
            R.drawable.hongtam,R.drawable.baibac,R.drawable.bibach,R.drawable.chiase,R.drawable.chinchan,
            R.drawable.cusuhao,R.drawable.detien,R.drawable.doitrangthayden,R.drawable.fifa,R.drawable.giaodien,
            R.drawable.hoanghau,R.drawable.hoanhtrang,R.drawable.hoclech,R.drawable.hoihop,R.drawable.hovt,
            R.drawable.khaithac,R.drawable.laban,R.drawable.lanhdao,R.drawable.mieuta,R.drawable.nhatban,R.drawable.ongbau,R.drawable.quychuan
            ,R.drawable.tamlang,R.drawable.tethap,R.drawable.thongthoang,R.drawable.thuonghieu,R.drawable.toicao,R.drawable.truongsontay,R.drawable.tungtang

    };
    private Random random = new Random();
    private int rd;

    private String ketQua ="";
    private int lev = 0;
    SharedPreferences sharedPreferences;
    public static String NameSharedPrefrence="namesharedreferen";
    public static final String lever = "1";
    public static final String diem="0";
    private ArrayList<Button> arrKey=new ArrayList<Button>();
    private  ArrayList<Button> arrQue=new ArrayList<Button>();
    //private ArrayList<Integer> arrRD = new ArrayList<>();

    final int[] vt = {0};
    int click = 0;
    int click1=0;

   private ArrayList<Integer> arrVitri=new ArrayList<>();
   private SQLiteDatabase db;
    RewardedVideoAd mAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActionBar b = getSupportActionBar();
        b.hide();
        inintText();
        innitSQL();
        khoitaoketqua();
        kttsl();

        innitvideo();
        creatChosse();
        creatImage();
        createButton();
        createButtonPick();
    }


    private void innitSQL() {
        db=openOrCreateDatabase("demketqua.db",MODE_PRIVATE,null);
    String sql="CREATE TABLE IF NOT EXISTS SoLuong(id integer primary key autoincrement,number integer)";

   //String sql="DROP TABLE SoLuong";

        db.execSQL(sql);

    }

    private void khoitaoketqua() {
        tv_Lever = findViewById(R.id.tv_Lever);
        tv_ruby = findViewById(R.id.tv_ruby);
        sharedPreferences=getSharedPreferences(NameSharedPrefrence,MODE_PRIVATE);
        if(sharedPreferences.contains(lever)){
            tv_Lever.setText(sharedPreferences.getString(lever,"1"));
        }
        if(sharedPreferences.contains(diem)){
            tv_ruby.setText(sharedPreferences.getString(diem,"0"));
        }
        System.out.println("biến radom: "+sharedPreferences.getInt("Radom",0));
    }
    private  void kttsl(){


        Cursor cursor=db.rawQuery("SELECT* FROM SoLuong",null);
        int x=QUESTIONS.length;
        int t=cursor.getCount();

//        System.out.println(" biến count:"+t);
//        System.out.println(" biến x:"+x);
        if(t>=x){
            Intent i=new Intent(Main2Activity.this,Main4Activity.class);
            startActivity(i);
           Toast.makeText(this,"full câu hỏi",Toast.LENGTH_LONG).show();
            //cursor.close();
            //db.close();

        }

        else {
            rd = random1();
        }

        System.out.println("bien radom: "+rd);
    }
    private int random1() {

        int rdNumber=sharedPreferences.getInt("Radom",0);

          if(!kiemtra(rdNumber)){
             boolean ok=true;
            while (ok){
                rdNumber = random.nextInt(QUESTIONS.length);
                if(kiemtra(rdNumber)){
                    ok=false;
                }
            }

        }
        return rdNumber;
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Main2Activity.this,MainActivity.class);
        startActivity(i);
        super.onBackPressed();

    }

    // ham kiem tra xem so co nam trong mản chua
    private boolean kiemtra(int rdNumber) {

        String sql="SELECT* FROM SoLuong WHERE number='"+rdNumber+"'";
        Cursor cursor=db.rawQuery(sql,null);
       // System.out.println("xuat hien count: "+cursor.getCount());
        if(cursor.getCount()!=0){
            return false;
        }

        return true;
    }
    private boolean check(ArrayList<Integer> arrRD,int rdNumber) {
        for (int i = 0; i < arrRD.size(); i++) {
            if (rdNumber == arrRD.get(i)) {
                return false;
            }
        }
        return true;
    }

    private void inintText() {
        imThoat = findViewById(R.id.imthoat);
        imGoiY = findViewById(R.id.imGoiY);
         btnD_1=findViewById(R.id.btnD_1);
         btnD_2=findViewById(R.id.btnD_2);
         btnD_3=findViewById(R.id.btnD_3);
         btnD_4=findViewById(R.id.btnD_4);
         btnD_5=findViewById(R.id.btnD_5);
         btnD_6=findViewById(R.id.btnD_6);
         btnD_7=findViewById(R.id.btnD_7);
         btnD_8=findViewById(R.id.btnD_8);
         btnD_9=findViewById(R.id.btnD_9);
         btnD_10=findViewById(R.id.btnD_10);
         btnD_11=findViewById(R.id.btnD_11);
         btnD_12=findViewById(R.id.btnD_12);
         btnD_13=findViewById(R.id.btnD_13);
         btnD_14=findViewById(R.id.btnD_14);
         btnD_15=findViewById(R.id.btnD_15);
         btnD_16=findViewById(R.id.btnD_16);
        arrQue.add(btnD_1);
        arrQue.add(btnD_2);
        arrQue.add(btnD_3);
        arrQue.add(btnD_4);
        arrQue.add(btnD_5);
        arrQue.add(btnD_6);
        arrQue.add(btnD_7);
        arrQue.add(btnD_8);
        arrQue.add(btnD_9);
        arrQue.add(btnD_10);
        arrQue.add(btnD_11);
        arrQue.add(btnD_12);
        arrQue.add(btnD_13);
        arrQue.add(btnD_14);
        arrQue.add(btnD_15);
        arrQue.add(btnD_16);


         btn_1=findViewById(R.id.btn_1);
         btn_2=findViewById(R.id.btn_2);
         btn_3=findViewById(R.id.btn_3);
         btn_4=findViewById(R.id.btn_4);
         btn_5=findViewById(R.id.btn_5);
         btn_6=findViewById(R.id.btn_6);
         btn_7=findViewById(R.id.btn_7);
         btn_8=findViewById(R.id.btn_8);
         btn_9=findViewById(R.id.btn_9);
         btn_10=findViewById(R.id.btn_10);
         btn_11=findViewById(R.id.btn_11);
         btn_12=findViewById(R.id.btn_12);
         btn_13=findViewById(R.id.btn_13);
         btn_14=findViewById(R.id.btn_14);
         btn_15=findViewById(R.id.btn_15);
         btn_16=findViewById(R.id.btn_16);

          arrKey.add(btn_1);
          arrKey.add(btn_2);
          arrKey.add(btn_3);
          arrKey.add(btn_4);
          arrKey.add(btn_5);
          arrKey.add(btn_6);
          arrKey.add(btn_7);
          arrKey.add(btn_8);
          arrKey.add(btn_9);
          arrKey.add(btn_10);
          arrKey.add(btn_11);
          arrKey.add(btn_12);
          arrKey.add(btn_13);
          arrKey.add(btn_14);
          arrKey.add(btn_15);
          arrKey.add(btn_16);


    }
    private void innitvideo() {
        MobileAds.initialize(this, getString(R.string.admod_app_id));
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
    }


    private void creatChosse() {

        imThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                builder.setTitle("Bạn có muốn quay trở lại menu !!!");
                builder.setIcon(R.drawable.nhaymat);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
        imGoiY.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int ru = Integer.parseInt(tv_ruby.getText().toString());
                ru = ru - 5;
                if (ru > -1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    builder.setTitle("bạn có muốn gợi ý một ký tự?");
                    builder.setIcon(R.drawable.nhaymat);
                    final int finalRu = ru;
                    builder.setPositiveButton("Có < trừ 5 ruby>", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            arrKey.get(vt[0]).setText(Key[rd].charAt(vt[0]) + "");
                            arrKey.get(vt[0]).setEnabled(false);
                            String f = Key[rd].charAt(vt[0]) + "";
                            for (int i = 0; i < 16; i++) {
                                if (f.compareTo(arrQue.get(i).getText().toString()) == 0) {
                                    arrQue.get(i).setVisibility(View.INVISIBLE);
                                    break;
                                }

                            }
                            int le = Integer.parseInt(tv_Lever.getText().toString());
                            luu(finalRu, le,rd);
                            tv_ruby.setText(finalRu + "");
                            String s = String.valueOf(Key[rd].charAt(vt[0]));
                            vt[0]++;
                            click1++;
                            ketQua += s;
                            if (click1 == Key[rd].length()) {
                                findKey();

                            }
                        }


                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    builder.setTitle("rất tiếc bạn không đủ ruby để mở ký tự?");
                    builder.setIcon(R.drawable.nhaymat);
                    builder.setPositiveButton("kiếm ruyby", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAd.isLoaded()) {
                                mAd.show();
                            } else {
                                Log.e("Error", "ads not load");
                            }
                        }
                    });
                    builder.setNegativeButton("thoát", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }



        });
    }

    private void creatImage() {
        layout_2 = (LinearLayout) findViewById(R.id.layout_2);
        ImageView iv[] = new ImageView[QUESTIONS.length];
        iv[rd] = new ImageView(this);
        iv[rd].setImageResource(QUESTIONS[rd]);
        layout_2.addView(iv[rd]);
    }

//    private ArrayList Qestion(){
//        ArrayList<Integer> arrMang=new ArrayList<>();
//        for(int i=0;i<Key[rd].length();i++){
//            arrMang.add(i);
//        }
//        return  arrMang;
//    }
    private void createButton() {

         int a = 0;
        System.out.println("keylent: "+Key[rd].length());

        for(int i=0;i<Key[rd].length();i++){
            final int x=i;
            arrKey.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(arrKey.get(x).getText().toString().compareTo("")==0){
                        arrKey.get(x).setClickable(false);
                    }
                    else {
                        //arrKey.get(x).setClickable(true);
                        String s = arrKey.get(x).getText().toString();
                        arrKey.get(x).setText("");

                        int tam=0;
                        for(int j=0;j<arrVitri.size();j++) {
//                                 int t= (int) Qestion().get(j);
//                            System.out.println("biến t: "+t);
//                            System.out.println("biến x"+ x);
                            if ( j==x) {
                                tam = arrVitri.get(x);
                                ketQua = deletChar(ketQua,j);
                                arrVitri.remove(j);
                                //Qestion().remove(j);

                            }
                        }
                            arrQue.get(tam).setVisibility(View.VISIBLE);




                        vt[0] = x;
                        click1--;

                    }
//                    if(click1==0){
//                        reset();
//                    }
                    System.out.println("ket qua strig:"+ketQua);
                }
            });
            a++;

        }

        if(a >Key[rd].length()-1){
            for(int j = a; j<16; j++){
                arrKey.get(j).setVisibility(View.GONE);
            }
        }



    }
//    private void reset() {
//        arrVitri.clear();
//        ketQua = "";
//        for (int t = 0; t < 16; t++) {
//            arrQue.get(t).setVisibility(View.VISIBLE);
//        }
//    }

    private String deletChar(String s, int x) {
//        String at= String.valueOf(ketQua.charAt(x));
//        String ket= (String) ketQua.replaceFirst(at,"");
//        return ket;
        return s.substring(0, x) + s.substring(x + 1);
    }
    private ArrayList Question() {
        ArrayList<String> arrS = new ArrayList<>();
        int temp = random.nextInt(25) + 65;
        for (int i = 0; i < Key[rd].length(); i++) {
            arrS.add(Key[rd].charAt(i) + "");
        }
        for (int i = 0; i < 16 - Key[rd].length(); i++) {

            arrS.add((char) temp + "");
        }


        return arrS;
    }
    private void createButtonPick() {
        ArrayList<Integer> arrSo = new ArrayList<>();

          final int dem1 =Key[rd].length()-click;
        for (int i = 0; i <16; i++) {
             final int x=i;
                  arrQue.get(i).setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(click1<Key[rd].length()){
                              click1++;
                                  if (click1<dem1){
                                      arrKey.get(vt[0]).setText(arrQue.get(x).getText());
                                      ketQua += arrQue.get(x).getText();
                                      arrQue.get(x).setVisibility(View.INVISIBLE);

                                   }
                                   else if(click1==dem1){

                                      arrKey.get(vt[0]).setText(arrQue.get(x).getText());
                                      ketQua += arrQue.get(x).getText();
                                      arrQue.get(x).setVisibility(View.INVISIBLE);
                                      findKey();
                                  }
                                   else{
                                      arrQue.get(x).setEnabled(false);
                                  }

                                   vt[0]++;
                                   arrVitri.add(x);
                                }
                           else{
                                       arrQue.get(x).setClickable(false);
                           }
                           //System.out.println("arrayvt size"+arrVitri.size());

                       }
                   });

               while (arrQue.get(i).getText() == "") {
                    int temp = random.nextInt(16);
                    //kiểm tra tồn tại trong mảng chưa
                  if (check(arrSo, temp)) {
                        arrQue.get(i).setText((CharSequence) randomQuestion().get(temp));
                        randomQuestion().remove(temp);
                        arrSo.add(temp);
                    }
                }

        }

    }

    private ArrayList randomQuestion() {
        ArrayList<String> arrS = new ArrayList<>();
        int temp = random.nextInt(25) + 65;
        for (int i = 0; i < Key[rd].length(); i++) {
            arrS.add(Key[rd].charAt(i) + "");
        }
        for (int i = 0; i < 16 - Key[rd].length(); i++) {

            arrS.add((char) temp + "");
        }


        return arrS;
    }

    public void findKey() {
            if (ketQua.equals(Key[rd])) {
                String sql="INSERT INTO SoLuong(number) VALUES('"+rd+"')";
                db.execSQL(sql);

                int a = Integer.parseInt(tv_Lever.getText().toString());
                int b = Integer.parseInt(tv_ruby.getText().toString());
                lev = a + 1;
                ruby = b + 5;
                ketQua ="";
                click1 = 0;
                luu(ruby, lev,rd);
                Intent in = new Intent(this, Main3Activity.class);
                String s = String.valueOf(rd);
                in.putExtra("a", s);
                startActivity(in);
                finish();

            } else {
                Toast.makeText(Main2Activity.this, "sai rồi !!!", Toast.LENGTH_LONG).show();
                return;

            }


    }

    private void luu(int ruby,int lev,int rd){
        String r=String.valueOf(ruby);
        String l=String.valueOf(lev);
        String y=String.valueOf(rd);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(lever,l);
        editor.putString(diem,r);
        editor.putInt("Radom",rd);
        editor.commit();

    }


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        mAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder()
                        .build());

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        int ru = Integer.parseInt(tv_ruby.getText().toString());
        arrKey.get(vt[0]).setText(Key[rd].charAt(vt[0]) + "");
        String f = Key[rd].charAt(vt[0]) + "";
        for (int i = 0; i < 16; i++) {
            if (f.compareTo(arrQue.get(i).getText().toString()) == 0) {
                arrQue.get(i).setVisibility(View.INVISIBLE);
                break;
            }

        }
        int le = Integer.parseInt(tv_Lever.getText().toString());
        luu(ru, le,rd);
        tv_ruby.setText(ru+ "");
        String s = String.valueOf(Key[rd].charAt(vt[0]));
        vt[0]++;
        click1++;
        ketQua += s;
        if (click1 == Key[rd].length()) {
            findKey();

        }

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}

