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


import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth uAuth;
    private String id;
    private String level;
    //    private String userIp;
    private Handler h;
    private String heightD;
    private String weightD;
    private int height;
    private int weight;
    private double bmi;
    private int rest;
    private String duration;
    private int exNo;
    private int sets;
    private int reps;
    //    private String exercises;
    private String equ;
    private String SessionNo;
    private  String trainingDays;
    private String day1;
    private String day2;
    private String day3;
    private String day4;
    private String day5;
    CollectionReference ex = db.collection("WorkoutPlan");
    private String force1;
    private String force2;
    private String force3;
    private String force4;
    private String force5;

    private String m1;
    private String m2;
    private String m3;
    private String m4;
    private String m5;


    private String equipmentList;
    private double tPlace;
    private int tP;
    private boolean bench;
    private boolean benchT;
    private boolean dumbbell;
    private boolean barbell;
    private boolean stabilityBall;
    private boolean dipMachine;
    private boolean cableMachine;

    private boolean r;
    TextView loadLbl;
    private String message;



    //    private CollectionReference plan = db.collection("userProfile");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pa);
        getSupportActionBar().hide();
        r=false;
        logo= findViewById(R.id.imageView4);
        loadLbl=findViewById(R.id.loadLbl);
        h=new Handler();
        SessionNo="0";
//        level="";
        rest=0;
        exNo=0;
        sets=0;
        reps=10;
        bench=true;
        barbell=true;
        cableMachine=true;
        stabilityBall=true;
        dipMachine=true;
        dumbbell=true;


//
//        retreiveInfo();
//
//        if(r) {
//            rotateAnimation();
//        }
//        else {
//            retreiveInfo();
//        }

        db = FirebaseFirestore.getInstance();
        //to get user email
        uAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = uAuth.getCurrentUser();
        id = curUser.getEmail();
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        userIp=Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        Map<String,Object> user = new HashMap<>();

        DocumentReference documentReference = db.collection("userProfile").document(id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                level= value.getString("level");
                planSettings(level);
                heightD=value.getString("height");
                weightD= value.getString("weight");
//                equ= value.getString("equpmtList");
                tPlace= value.getDouble("trainingPlace");
                SessionNo=value.getString("TrainingdaysNum");
                tP=(int)tPlace;
                if(tP==0) {
                    equ = value.getString("equpmtList");
                }
                height=Integer.parseInt(heightD);
                weight=Integer.parseInt(weightD);
                bmi= (weight/(height*height))*10000;
                BMI(bmi,level);
                addPlan();
                r=true;




            }
        });

    }

    public void retreiveInfo() {

//
    }

    public void rotateAnimation() {

        Animation rotate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);

        logo.setAnimation(rotate);
        h.postDelayed(new Runnable() {
            @Override

            public void run() {
                Intent i = new Intent(LoadPa.this, LoadPaP2.class);
//                startActivity(i);


                if(SessionNo.equals("2")){
                    i.putExtra("SessionNo","2");
                    i.putExtra("level",level);
                    startActivity(i);
                    finish();
                }
                else if(SessionNo.equals("3")){
                    i.putExtra("SessionNo","3");
                    i.putExtra("level",level);
                    startActivity(i);
                    finish();
                }
                else if(SessionNo.equals("4")){
                    i.putExtra("SessionNo","4");
                    i.putExtra("level",level);
                    startActivity(i);
                    finish();
                }
                else if(SessionNo.equals("5")){
                    i.putExtra("SessionNo","5");
                    i.putExtra("level",level);
                    startActivity(i);
                    finish();
                }
            }
        }, 1500);
    }

    public void planSettings(String level){

        if(level.equalsIgnoreCase("Beginner")){
            rest=120;
            exNo=7;
            duration=30+"";
        }
        else if(level.equalsIgnoreCase("Intermediate")){
            rest=90;
            exNo=10;
            duration=45+"";
        }
        else{
            rest=60;
            exNo=12;
            duration=60+"";
        }

    }

    public void BMI(double bmi, String level){

        if(level.equalsIgnoreCase("Beginner")){
            if(bmi < 18.5){
                sets =2;

            }
            else if(bmi >=18.5 &&bmi<=24.9){
                sets=3;
            }
            else{
                sets=2;
            }
        }
        else if(level.equalsIgnoreCase("Intermediate")){
            if(bmi < 18.5){
                sets =3;

            }
            else if(bmi >=18.5 &&bmi<=bmi){
                sets=4;
            }
            else{
                sets=3;
            }
        }
        else{
            if(bmi < 18.5){
                sets =4;

            }
            else if(bmi >=18.5 &&bmi<=24.9){
                sets=5;
            }
            else{
                sets=4;
            }
        }
        if(tP==0)  {
            if((equ.equals(0)||equ.equals("0")))
                bench=false;
            barbell=false;
            cableMachine=false;
            stabilityBall=false;
            dipMachine=false;
            dumbbell=false;

        }
        else if (tP==0){

        }
        else {
        }
    }







    public void addPlan(){

        Map<String,Object> planAdd = new HashMap<>();

        planAdd.put("sets",sets);
        planAdd.put("rest",rest);
        planAdd.put("reps",reps);
        planAdd.put("exNO",exNo);
        planAdd.put("duration",duration);

//        ex.document(userIp).set(planAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
        ex.document(id).collection("WorkoutPlan").document(id).set(planAdd).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
//                    Toast.makeText(LoadPa.this,"successful",Toast.LENGTH_SHORT);
                    rotateAnimation();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoadPa.this,"Faild",Toast.LENGTH_SHORT);

                startActivity(new Intent(LoadPa.this, Home.class));
            }
        });

    }



}