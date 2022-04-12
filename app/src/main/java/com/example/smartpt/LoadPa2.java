package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class LoadPa2 extends AppCompatActivity {

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

    TextView loadLbl;
    private String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pa2);
        getSupportActionBar().hide();
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




        retreiveInfo();
        rotateAnimation();



    }

    public void retreiveInfo() {
        db = FirebaseFirestore.getInstance();
        //to get user email
        uAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = uAuth.getCurrentUser();
        id = curUser.getEmail();
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
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




            }
        });
//
    }

    public void rotateAnimation() {

        Animation rotate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);

        logo.setAnimation(rotate);
        h.postDelayed(new Runnable() {
            @Override

            public void run() {
                Intent i = new Intent(LoadPa2.this, PlanView.class);
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
//            addPlan();
            createWorkoutPlan();
        }
        else if (tP==0){
//            addPlan();
            equipment();
        }
        else {
//            addPlan();
            createWorkoutPlan();}
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
            PyObject fullBodyN1= pyObj.callAttr("getfullbodyName");
            PyObject fullBodyF1= pyObj.callAttr("getfullbodyForce");
            PyObject fullBodyM1= pyObj.callAttr("getfullbodygeneralMuscle");


            day1 = fullBodyN1.toString();
            force1=fullBodyF1.toString();
            m1=fullBodyM1.toString();
            addExercises(1,day1,m1,force1);

            PyObject fullBody2= pyObj.callAttr("fullbody",level);
            PyObject fullBodyN2= pyObj.callAttr("getfullbodyName");
            PyObject fullBodyF2= pyObj.callAttr("getfullbodyForce");
            PyObject fullBodyM2= pyObj.callAttr("getfullbodygeneralMuscle");


            day2 = fullBodyN2.toString();
            force2=fullBodyF2.toString();
            m2=fullBodyM2.toString();
            addExercises(2,day2,m2,force2);

        }//}
        else if (SessionNo.equals("3")){
            int i;

            PyObject fullBody= pyObj.callAttr("fullbody",level);
            PyObject fullBodyN1= pyObj.callAttr("getfullbodyName");
            PyObject fullBodyF1= pyObj.callAttr("getfullbodyForce");
            PyObject fullBodyM1= pyObj.callAttr("getfullbodygeneralMuscle");


            day1 = fullBodyN1.toString();
            force1=fullBodyF1.toString();
            m1=fullBodyM1.toString();
            addExercises(1,day1,m1,force1);

            PyObject fullBody2= pyObj.callAttr("fullbody",level);
            PyObject fullBodyN2= pyObj.callAttr("getfullbodyName");
            PyObject fullBodyF2= pyObj.callAttr("getfullbodyForce");
            PyObject fullBodyM2= pyObj.callAttr("getfullbodygeneralMuscle");


            day2 = fullBodyN2.toString();
            force2=fullBodyF2.toString();
            m2=fullBodyM2.toString();
            addExercises(2,day2,m2,force2);

            PyObject fullBody3= pyObj.callAttr("fullbody",level);
            PyObject fullBodyN3= pyObj.callAttr("getfullbodyName");
            PyObject fullBodyF3= pyObj.callAttr("getfullbodyForce");
            PyObject fullBodyM3= pyObj.callAttr("getfullbodygeneralMuscle");


            day3 = fullBodyN3.toString();
            force3=fullBodyF3.toString();
            m3=fullBodyM3.toString();
            addExercises(3,day3,m3,force3);

//            }
        }
        else if (SessionNo.equals("4")){


            PyObject upperbody= pyObj.callAttr("upperbody",level);
            PyObject fullBodyN1= pyObj.callAttr("getupperName");
            PyObject fullBodyF1= pyObj.callAttr("getupperForce");
            PyObject fullBodyM1= pyObj.callAttr("getuppergeneralMuscle");

            day1 = fullBodyN1.toString();
            force1=fullBodyF1.toString();
            m1=fullBodyM1.toString();
            addExercises(1,day1,m1,force1);

            PyObject lowerbody= pyObj.callAttr("lowerbody",level);
            PyObject fullBodyN2= pyObj.callAttr("getlowerName");
            PyObject fullBodyF2= pyObj.callAttr("getlowerForce");
            PyObject fullBodyM2= pyObj.callAttr("getlowergeneralMuscle");


            day2 = fullBodyN2.toString();
            force2=fullBodyF2.toString();
            m2=fullBodyM2.toString();
            addExercises(2,day2,m2,force2);

            PyObject upperbody2= pyObj.callAttr("upperbody",level);
            PyObject fullBodyN3= pyObj.callAttr("getupperName");
            PyObject fullBodyF3= pyObj.callAttr("getupperForce");
            PyObject fullBodyM3= pyObj.callAttr("getuppergeneralMuscle");


            day3 = fullBodyN3.toString();
            force3=fullBodyF3.toString();
            m3=fullBodyM3.toString();
            addExercises(3,day3,m3,force3);


            PyObject lowerbody2= pyObj.callAttr("lowerbody",level);
            PyObject fullBodyN4= pyObj.callAttr("getlowerName");
            PyObject fullBodyF4= pyObj.callAttr("getlowerForce");
            PyObject fullBodyM4= pyObj.callAttr("getlowergeneralMuscle");


            day4 = fullBodyN4.toString();
            force4=fullBodyF4.toString();
            m4=fullBodyM4.toString();
            addExercises(4,day4,m4,force4);

        }
        else if(SessionNo.equals("5")){

            PyObject fiveDays1= pyObj.callAttr("fiveDay",level,1);
            PyObject fullBodyN1= pyObj.callAttr("getfivedayName");
            PyObject fullBodyF1= pyObj.callAttr("getfivedayForce");
            PyObject fullBodyM1= pyObj.callAttr("getfivedaymuscle");

            day1 = fullBodyN1.toString();
            force1=fullBodyF1.toString();
            m1=fullBodyM1.toString();
            addExercises(1,day1,m1,force1);

            PyObject fiveDays2= pyObj.callAttr("fiveDay",level,2);
            PyObject fullBodyN2= pyObj.callAttr("getfivedayName");
            PyObject fullBodyF2= pyObj.callAttr("getfivedayForce");
            PyObject fullBodyM2= pyObj.callAttr("getfivedaymuscle");


            day2 = fullBodyN2.toString();
            force2=fullBodyF2.toString();
            m2=fullBodyM2.toString();
            addExercises(2,day2,m2,force2);

            PyObject fiveDays3= pyObj.callAttr("fiveDay",level,3);
            PyObject fullBodyN3= pyObj.callAttr("getfivedayName");
            PyObject fullBodyF3= pyObj.callAttr("getfivedayForce");
            PyObject fullBodyM3= pyObj.callAttr("getfivedaymuscle");


            day3 = fullBodyN3.toString();
            force3=fullBodyF3.toString();
            m3=fullBodyM3.toString();
            addExercises(3,day3,m3,force3);


            PyObject fiveDays4= pyObj.callAttr("fiveDay",level,4);
            PyObject fullBodyN4= pyObj.callAttr("getfivedayName");
            PyObject fullBodyF4= pyObj.callAttr("getfivedayForce");
            PyObject fullBodyM4= pyObj.callAttr("getfivedaymuscle");

            day4 = fullBodyN4.toString();
            force4=fullBodyF4.toString();
            m4=fullBodyM4.toString();
            addExercises(4,day4,m4,force4);



            PyObject fiveDays5= pyObj.callAttr("fiveDay",level,5);
            PyObject fullBodyN5= pyObj.callAttr("getfivedayName");
            PyObject fullBodyF5= pyObj.callAttr("getfivedayForce");
            PyObject fullBodyM5= pyObj.callAttr("getfivedaymuscle");

            day5 = fullBodyN5.toString();
            force5=fullBodyF5.toString();
            m5=fullBodyM5.toString();
            addExercises(5,day5,m5,force5);

        }



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
        //to get user email
        uAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = uAuth.getCurrentUser();
        id = curUser.getEmail();

//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        userIp=Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        Map<String,Object> planAdd = new HashMap<>();
        CollectionReference ex = db.collection("userProfile");

        planAdd.put("sets",sets);
        planAdd.put("rest",rest);
        planAdd.put("reps",reps);
        planAdd.put("exNO",exNo);
        planAdd.put("duration",duration);

        ex.document(id).collection("WorkoutPlan").document(id).set(planAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoadPa2.this,"successful",Toast.LENGTH_SHORT);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoadPa2.this,"Faild",Toast.LENGTH_SHORT);

            }
        });

    }


    public void addExercises(int i, String exercises, String m, String f){
        CollectionReference ex = db.collection("userProfile");

        String s="day"+i;
        Map<String,Object> planEx = new HashMap<>();
        planEx.put("plan",exercises);
        planEx.put("force",f);
        planEx.put("muscle",m);
//        planEx.put("sessionNo",SessionNo);
//        planEx.put("level",level);



        ex.document(id).collection("WorkoutPlan").document(id).collection(id).document(s).set(planEx).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoadPa2.this,"successful",Toast.LENGTH_SHORT);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoadPa2.this,"Faild",Toast.LENGTH_SHORT);

            }
        });

    }

}
