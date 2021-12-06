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
    private String exercises;
    private String equ;
    private String num;
    private  String trainingDays;
    private String sunEx;
    private String monEx;
    private String tueEx;
    private String wedEx;
    private String thurEx;
    private String friEx;
    private String satEx;
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


    private CollectionReference plan = db.collection("userProfile");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pa);
        getSupportActionBar().hide();
        logo= findViewById(R.id.imageView4);
        h=new Handler();
num="0";
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

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp=Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        rotateAnimation();
        retreiveInfo();

        h.postDelayed(new Runnable() {
            @Override

            public void run() {
                Intent i = new Intent(LoadPa.this, PlanView.class);

                startActivity(i);
                 finish();
            }
        }, 15000);
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
                equ= value.getString("equpmtList");
                tPlace= value.getDouble("trainingPlace");
                num=value.getString("TrainingdaysNum");
                tP=(int)tPlace;
                if(tP==0) {
                    equipmentList = value.getString("equpmtList");
                }
                height=Integer.parseInt(heightD);
                weight=Integer.parseInt(weightD);
                BMI= (weight/(height*height))*10000;
                Level(level);
                //trainingDays=value.getString("trainingDays");
                //TrainingDays(trainingdaysNum,trainingDays);
               // equipmentList=value.getString("equpmtList");
                //equipment(equipmentList);
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
        if(tP==0 && (equipmentList.equals(0)||equipmentList=="0"))  {
            bench=false;
            barbell=false;
            cableMachine=false;
            stabilityBall=false;
            dipMachine=false;
            dumbbell=false;
            ex();
        }
        else if (tP==0){
            equipment();
        }
        else {ex();}
    }
    public void ex(){

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        // creating python object
        PyObject pyObj= py.getModule("myscript"); // call the python file
        PyObject equi = pyObj.callAttr("exercises",bench,barbell,stabilityBall,dumbbell,dipMachine,cableMachine); // call the exercise method in python
//        exercises = equi.toString();//retrieve  output

//        //1
        if(num.equals("2")){
            int i;
            for (i = 0 ;i<2;i++){

                PyObject fullBody= pyObj.callAttr("fullbody",level);
                exercises = fullBody.toString();
                addExercises(i,exercises);

            }}
        else if (num.equals("3")){
            int i;
            for (i = 0 ;i<3;i++){

                PyObject fullBody= pyObj.callAttr("fullbody",level);
                exercises = fullBody.toString();
                addExercises(i,exercises);

            }
        }
        else if (num.equals("4")){
            int i;
            for (i = 0 ;i<2;i++){

                PyObject upperbody= pyObj.callAttr("upperbody",level);
                exercises = upperbody.toString();
                addExercises(i,exercises);
                PyObject lowerbody= pyObj.callAttr("lowerbody",level);
                exercises = lowerbody.toString();
                addExercises(i+1,exercises);


            }




        }


//        //2
//        //3



        addPlan();

    }

    /*
   public void TrainingDays(Integer trainingdaysNum,String trainingDays){
       // String[] splitedDays = trainingDays.split("\\s+");
        if (trainingdaysNum==2){
            sunEx="full body";
            tueEx="full body";

        }
        else if(trainingdaysNum==3){
            sunEx="full body";
            tueEx="full body";
            thurEx="full body";

        }
        else if(trainingdaysNum==4){
            sunEx="upper";
            tueEx="lower";
            thurEx="upper";
            satEx="lower";

        }
        else {//Number of Training Days = 5
            sunEx="chest";
            monEx="back,leg";
            tueEx="shoulder";
            thurEx="core";
            friEx="arm";
        }


    }*/

    public void equipment(){



       if (equ.indexOf("Bench")==-1){
           bench=false;
       }else {
           bench=true;
       }

        if (equ.indexOf("Barbell")==-1){
            barbell=false;
        }else {
            barbell=true;
        }

        if (equ.indexOf("Dumbbell")==-1){
            dumbbell=false;
        }else {
            dumbbell=true;
        }

        if (equ.indexOf("Stability ball")==-1){
            stabilityBall=false;
        }else {
            stabilityBall=true;
        }

        if (equ.indexOf("Dip Machine")==-1){
            dipMachine=false;
        }else {
            dipMachine=true;
        }

        if (equ.indexOf("Cable Machine")==-1){
            cableMachine=false;
        }else {
            cableMachine=true;
        }
          ex();


    }


    public void addPlan(){
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp=Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        Map<String,Object> planAdd = new HashMap<>();
        planAdd.put("sets",sets);
        planAdd.put("rest",rest);
        planAdd.put("reps",reps);
        planAdd.put("exNO",exNo);
//        planAdd.put("test",exercises);

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


    public void addExercises(int i, String exercises){
        CollectionReference ex = db.collection("userProfile");

        String s="day"+(i+1);
        Map<String,Object> planEx = new HashMap<>();
        planEx.put("plan",exercises);

        ex.document(userIp).collection("WorkoutPlan").document(userIp).collection(userIp).document(s).set(planEx).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoadPa.this,"successful",Toast.LENGTH_SHORT);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoadPa.this,"Faild",Toast.LENGTH_SHORT);

            }
        });

    }

}