package com.example.smartpt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;
import android.widget.Toast;
import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class LoadPa extends AppCompatActivity {
    private ImageView logo;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ;
    private String level;
    private String userIp;
    private Handler h;
    private String heightD;
    private String weightD;
    private int height;
    private int weight;
    private double BMI;
    private int rest;
    private int duration;
    private int exNo;
    private int sets;
    private int reps;
    private CollectionReference plan = db.collection("userProfile");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pa);
        getSupportActionBar().hide();
        logo= findViewById(R.id.imageView4);
        h=new Handler();

        rest=0;
        exNo=0;
        sets=0;
        reps=10;

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp=Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        retreiveInfo();
        rotateAnimation();

        h.postDelayed(new Runnable() {
            @Override

            public void run() {
                Intent i = new Intent(LoadPa.this, PlanView.class);

                startActivity(i);
                 finish();
            }
        }, 2500);
    }

    public void retreiveInfo() {
    Map<String,Object> user = new HashMap<>();

    DocumentReference documentReference = db.collection("userProfile").document(userIp);
    documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                level= value.getString("level");
                heightD=value.getString("height");
                weightD= value.getString("weight");
                height=Integer.parseInt(heightD);
                weight=Integer.parseInt(weightD);
                BMI= (weight/(height*height))*10000;
                Level(level);
            }
        });

    }

    public void rotateAnimation() {

        Animation rotate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);

                logo.setAnimation(rotate);
    }

    public void Level(String level){

        if(level.equalsIgnoreCase("beginner")){
            rest=120;
            exNo=7;
            duration=30;
        }
        else if(level.equalsIgnoreCase("intermediate")){
            rest=90;
            exNo=10;
            duration=45;
        }
        else{
            rest=60;
            exNo=12;
            duration=60;
        }

        BMI(BMI,level);
    }

    public void BMI(double BMI, String level){

        if(level.equalsIgnoreCase("beginner")){
            if(BMI < 18.5){
                sets =2;

            }
            else if(BMI >=18.5 &&BMI<=24.9){
                sets=3;
            }
            else{
                sets=2;
            }
        }
        else if(level.equalsIgnoreCase("intermediate")){
            if(BMI < 18.5){
                sets =3;

            }
            else if(BMI >=18.5 &&BMI<=24.9){
                sets=4;
            }
            else{
                sets=3;
            }
        }
        else{
            if(BMI < 18.5){
                sets =4;

            }
            else if(BMI >=18.5 &&BMI<=24.9){
                sets=5;
            }
            else{
                sets=4;
            }
        }
        addPlan();
    }
    public void addPlan(){
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp=Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        Map<String,Object> planAdd = new HashMap<>();
        planAdd.put("sets",sets);
        planAdd.put("rest",rest);
        planAdd.put("reps",reps);
        planAdd.put("exNO",exNo);
        plan.document(userIp).collection("WorkoutPlan").document(userIp).set(planAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
//                    Toast.makeText(LoadPa.this,"successful",Toast.LENGTH_SHORT);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(LoadPa.this,"Faild",Toast.LENGTH_SHORT);

            }
        });

    }
}