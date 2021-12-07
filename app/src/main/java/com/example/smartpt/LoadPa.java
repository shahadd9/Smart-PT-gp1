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
//    private String exercises;
    private String equ;
    private String SessionNo;
    private  String trainingDays;
    private String day1;
    private String day2;
    private String day3;
    private String day4;
    private String day5;

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
        SessionNo="0";
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

                if(SessionNo.equals("2")){
                i.putExtra("sessionNo",SessionNo);
                i.putExtra("day1",day1);
                i.putExtra("day2",day2);
                i.putExtra("level",level);
                startActivity(i);
                finish();
                }
               else if(SessionNo.equals("3")){
                    i.putExtra("sessionNo",SessionNo);
                    i.putExtra("day1",day1);
                    i.putExtra("day2",day2);
                    i.putExtra("day3",day3);
                    i.putExtra("level",level);
                    startActivity(i);
                    finish();
                }
               else if(SessionNo.equals("4")){
                    i.putExtra("sessionNo",SessionNo);
                    i.putExtra("day1",day1);
                    i.putExtra("day2",day2);
                    i.putExtra("day3",day3);
                    i.putExtra("day4",day4);
                    i.putExtra("level",level);
                    startActivity(i);
                    finish();
                }
               else if(SessionNo.equals("5")){
                    i.putExtra("sessionNo",SessionNo);
                    i.putExtra("day1",day1);
                    i.putExtra("day2",day2);
                    i.putExtra("day3",day3);
                    i.putExtra("day4",day4);
                    i.putExtra("day5",day5);
                    i.putExtra("level",level);
                    startActivity(i);
                    finish();
                }
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
                SessionNo=value.getString("TrainingdaysNum");
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
            createWorkoutPlan();
        }
        else if (tP==0){
            equipment();
        }
        else {createWorkoutPlan();}
    }
    public void createWorkoutPlan(){

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        // creating python object
        PyObject pyObj= py.getModule("myscript"); // call the python file
        PyObject equi = pyObj.callAttr("exercises",bench,barbell,stabilityBall,dumbbell,dipMachine,cableMachine); // call the exercise method in python
//        exercises = equi.toString();//retrieve  output

//        //1
        if(SessionNo.equals("2")){
//            int i;
//            for (i = 0 ;i<2;i++){

                PyObject fullBody= pyObj.callAttr("fullbody",level);
                day1 = fullBody.toString();
                addExercises(1,day1);

            PyObject fullBody2= pyObj.callAttr("fullbody",level);
            day2 = fullBody2.toString();

            addExercises(2,day2);

            }//}
        else if (SessionNo.equals("3")){
            int i;
//            for (i = 0 ;i<3;i++){

            PyObject fullBody= pyObj.callAttr("fullbody",level);
            day1 = fullBody.toString();
            addExercises(1,day1);

            PyObject fullBody2= pyObj.callAttr("fullbody",level);
            day2 = fullBody2.toString();
            addExercises(2,day2);

            PyObject fullBody3= pyObj.callAttr("fullbody",level);
            day3 = fullBody3.toString();
            addExercises(3,day3);

//            }
        }
        else if (SessionNo.equals("4")){


            PyObject upperbody= pyObj.callAttr("upperbody",level);
            day1 = upperbody.toString();
            addExercises(1,day1);

            PyObject lowerbody= pyObj.callAttr("lowerbody",level);
            day2 = lowerbody.toString();
            addExercises(2,day2);

            PyObject upperbody2= pyObj.callAttr("upperbody",level);
            day3 = upperbody2.toString();
            addExercises(3,day2); 
            PyObject lowerbody2= pyObj.callAttr("lowerbody",level);
            day4 = lowerbody2.toString();
            addExercises(4,day4);

        }
        else if(SessionNo.equals("5")){

            PyObject fiveDays1= pyObj.callAttr("fiveDay",level,1);
            day1 = fiveDays1.toString();
            addExercises(1,day1);

            PyObject fiveDays2= pyObj.callAttr("fiveDay",level,2);
            day2 = fiveDays2.toString();
            addExercises(2,day2);

            PyObject fiveDays3= pyObj.callAttr("fiveDay",level,3);
            day3 = fiveDays3.toString();
            addExercises(3,day3);
            PyObject fiveDays4= pyObj.callAttr("fiveDay",level,4);
            day4 = fiveDays4.toString();
            addExercises(4,day4);
            PyObject fiveDays5= pyObj.callAttr("fiveDay",level,5);
            day5 = fiveDays5.toString();
            addExercises(5,day5);

        }


//        //2
//        //3



        addPlan();

    }




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
        createWorkoutPlan();


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

        String s="day"+i;
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