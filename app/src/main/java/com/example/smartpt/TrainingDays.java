package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrainingDays extends AppCompatActivity {
    private Button sun,mon,tue,wed,thur,fri,sat,next;
//    private TextView tLbl,lbl;
//    private int count;
    private int sunCount;
    private int monCount;
    private int tueCount;
    private int wedCount;
    private int thurCount;
    private int friCount;
    private int satCount;
    private static ArrayList<String> tDays;
//    private TextView days;
    private TextView tip;
    private String txt;
    private FirebaseFirestore db;
    private String userIp;
//    private boolean isChoose;
    private String level;
    private ArrayList<String> goal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_days);
//        tLbl=findViewById(R.id.trainingDayslbl);
//        lbl=findViewById(R.id.days);
        sun= findViewById(R.id.sunBtn);
        mon=findViewById(R.id.monBtn);
        tue=findViewById(R.id.tueBtn);
        wed=findViewById(R.id.wedBtn);
        thur=findViewById(R.id.thruBtn);
        fri=findViewById(R.id.friBtn);
        sat=findViewById(R.id.satBtn);
//        days= findViewById(R.id.days);
        next= findViewById(R.id.next);
        sunCount=0;
        db = FirebaseFirestore.getInstance();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

//        isChoose=false;
        tDays= new ArrayList<>();
//        count=0;
        level= getIntent().getStringExtra("level");
        goal=getIntent().getStringArrayListExtra("goal");


        tip= findViewById(R.id.biggenerTip);
        if(level.equals("Beginner")){
            tip.setText("tip: It is recommended for beginners to train 2-3 times a week");
        }
        else {
            tip.setText("");

        }




        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tDays.size()<5 || sunCount%2==1) {

                    sunCount++;
                    color(sunCount,sun);
                    activate();


                }}
        });


        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tDays.size()<5|| monCount%2==1) {

                    monCount++;
                    color(monCount, mon);
                    activate();
                }}
        });


        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tDays.size()<5|| tueCount%2==1) {
                    tueCount++;
                    color(tueCount, tue);
                    activate();

                }}
        });


        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tDays.size()<5|| wedCount%2==1) {

                    wedCount++;
                    color(wedCount,wed);
                    activate();

                }}
        });



        thur.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(tDays.size()<5|| thurCount%2==1) {

                    thurCount++;
                    color(thurCount, thur);
                    activate();


                }}
        });


        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tDays.size()<5|| friCount%2==1) {
                    friCount++;
                    color(friCount, fri);
                    activate();


                }}
        });



        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tDays.size()<5|| satCount%2==1) {

                    satCount++;
                    color(satCount, sat);
                    activate();

                }}
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tDays.size()>=2){

                    Map<String,Object> user = new HashMap<>();
                    user.put("trainingDays",tDays.toString());
                    db.collection("userProfile").document(userIp).update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                //Toast.makeText(Goal.this,"successful",Toast.LENGTH_SHORT);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(Goal.this,"Faild",Toast.LENGTH_SHORT);

                        }
                    });
                    goEqupment();
                }
            }
        });
    }
    public void color(int c, Button btn){
        txt= (String) btn.getText();
        if(c%2==1){
            btn.setBackgroundColor(Color.parseColor("#ED0B8A"));
            tDays.add(txt);
            //days.setText(tDays.toString()+ tDays.size());

        }
        else {
            btn.setBackgroundColor(Color.parseColor("#E3C6D0"));
            tDays.remove(txt);
            //days.setText(tDays.toString()+ tDays.size());

        }
    }
    public void goEqupment(){
        Intent intent= new Intent(this, TrainingPlace.class);
        intent.putExtra("tDays",tDays);
        intent.putExtra("goal",goal);
        intent.putExtra("level",level);
        startActivity(intent);
    }

    public void activate(){
        if(tDays.size()>=2){
            next.setBackgroundColor(Color.parseColor("#48D0FE"));
        }
        else {
            next.setBackgroundColor(Color.parseColor("#D8D4D4"));


        }

    }

    public static ArrayList<String> gettDays(){
        return tDays;
    }

}