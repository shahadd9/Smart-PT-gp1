package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telecom.Call;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.HashMap;
import java.util.Map;


public class Alternative extends AppCompatActivity {

    private FirebaseFirestore db;
//lbl1,exName1,exName2, equ2, back,
//    private TextView test1,test2,test3,test4, back;
    private TextView lbl1,exName1,exName2, equ1,equ2, back;
    private Button exBtn1,exBtn2;
    private String name,generalMuscle,currDay,SessionNo,level,exercise1,eqName1,eqName2,exercise2, excName1,excName2,ex1String,ex2String,force1,muscle1,force2,muscle2,day,force,muscle,dday,fforce,mmuscle;
    private int index;
    ;
    String ex1[] = new String[4];
    String ex2[] = new String[4];
    String dayAr[]=new String[100];
    String forceAr[] =new String[100];
    String muscleAr[] =new String[100];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative);

        //Strings
        name="";
    SessionNo="";
    level="";
    exercise1="";
    eqName1="";
    exercise2="";
    excName1="";
    excName2="";
    ex1String="";
    ex2String="";
    force1="";
    muscle1="";
    force2="";
    muscle2="";
    day="";
    force="";
    muscle="";
    dday="";
    fforce="";
    mmuscle="";
        //declare xml elements

        lbl1=findViewById(R.id.lbl1);
        exName1=findViewById(R.id.exName1);
        exName2=findViewById(R.id.exName2);
        equ1=findViewById(R.id.Equ1);
        equ2=findViewById(R.id.Equ2);
        back=findViewById(R.id.back);
        exBtn1=findViewById(R.id.exbtn1);
        exBtn2=findViewById(R.id.exbtn2);
//        test1=findViewById(R.id.test1);
//        test2=findViewById(R.id.test2);
//        test3=findViewById(R.id.test3);
//        test4=findViewById(R.id.test4);

        //get values from intent

        index=getIntent().getIntExtra("index",-1);
        SessionNo = getIntent().getStringExtra("SessionNo");
        name=getIntent().getStringExtra("name");
        level=getIntent().getStringExtra("level");
        currDay=getIntent().getStringExtra("currDay");
        generalMuscle=getIntent().getStringExtra("generalMuscle");


        //////////////////////////////////////////////////

        lbl1.setText(name+" Exercise Alternatives:");
//        lbl1.setText(index+"_"+currDay);

        ////////////////////////////////////////////////////
        findAlternative(name);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("userProfile").document(userIp).collection("WorkoutPlan").document(userIp).collection(userIp).document("day"+(currDay));
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                day = value.getString("plan");
                day = day.substring(2, day.length() - 3);
                dayAr = day.split("_");


                force = value.getString("force");
                force = force.substring(2, force.length() - 3);
                forceAr = force.split("_");

                muscle = value.getString("muscle");
                muscle = muscle.substring(2, muscle.length() - 3);
                muscleAr = muscle.split("_");
            }
        });

        //////////////////////////////////////////////////


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Alternative.this, PlanView.class);
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                startActivity(i);
            }
        });


        exBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dday="";
                fforce="";
                mmuscle="";
//                lbl1.setText(force1);
                dayAr[index]=excName1;
                forceAr[index]=force1;
                muscleAr[index]=muscle1;
                for (int i = 0; i < dayAr.length; i++) {
                    dday+=dayAr[i]+ "_";
                    fforce+=forceAr[i]+"_";
                    mmuscle+=muscleAr[i]+"_";
                }
//                test1.setText(dday);
//                test2.setText(fforce);

                dday="['"+dday+"']";
                fforce="['"+fforce+"']";
                mmuscle="['"+mmuscle+"']";
//                test3.setText(mmuscle);
//                test1.setText(dday);
//                test2.setText(fforce);




                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                String userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                db = FirebaseFirestore.getInstance();
                CollectionReference ex = db.collection("userProfile");

                String s="day"+(currDay);
                Map<String,Object> planEx = new HashMap<>();
                planEx.put("plan",dday);
                planEx.put("force",fforce);
                planEx.put("muscle",mmuscle);
//        planEx.put("sessionNo",SessionNo);
//        planEx.put("level",level);



                ex.document(userIp).collection("WorkoutPlan").document(userIp).collection(userIp).document(s).set(planEx).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(Alternative.this, PlanView.class);
                            i.putExtra("SessionNo",SessionNo);
                            i.putExtra("level",level);

                            startActivity(i);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(LoadPa2.this,"Faild",Toast.LENGTH_SHORT);

                    }
                });
//
//
//
            }
        });

        exBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dday="";
                fforce="";
                mmuscle="";
//                lbl1.setText(muscle2);
                dayAr[index]=excName2;
                forceAr[index]=force2;
                muscleAr[index]=muscle2;
                for (int i = 0; i < dayAr.length; i++) {
                    dday+=dayAr[i]+ "_";
                    fforce+=forceAr[i]+"_";
                    mmuscle+=muscleAr[i]+"_";
                }
//                test1.setText(dday);
//                test2.setText(fforce);

                dday="['"+dday+"']";
                fforce="['"+fforce+"']";
                mmuscle="['"+mmuscle+"']";
//                test3.setText(mmuscle);
//                test1.setText(dday);
//                test2.setText(fforce);




                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                String userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                db = FirebaseFirestore.getInstance();
                CollectionReference ex = db.collection("userProfile");

                String s="day"+(currDay);
                Map<String,Object> planEx = new HashMap<>();
                planEx.put("plan",dday);
                planEx.put("force",fforce);
                planEx.put("muscle",mmuscle);
//        planEx.put("sessionNo",SessionNo);
//        planEx.put("level",level);



                ex.document(userIp).collection("WorkoutPlan").document(userIp).collection(userIp).document(s).set(planEx).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(Alternative.this, PlanView.class);
                            i.putExtra("SessionNo",SessionNo);
                            i.putExtra("level",level);

                            startActivity(i);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(LoadPa2.this,"Faild",Toast.LENGTH_SHORT);

                    }
                });
//
//
//
            }
        });

    }

    private void findAlternative(String name) {
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        // creating python object
        PyObject pyObj= py.getModule("myscript"); // call the python file

        PyObject alt1 = pyObj.callAttr("findAlternative1",name,0,generalMuscle); // call the alt1 method in python
        exercise1 = alt1.toString();//retrieve  output
//        ex1String=exercise1.substring(1,exercise1.length()-3);
        ex1= exercise1.split("_");
//        excName1=ex1[0].substring(1,ex1[0].length());
//        force1=ex1[1].substring(3,ex1[1].length());
//        muscle1=ex1[2].substring(3,ex1[2].length());
        excName1=ex1[0];
        force1=ex1[1];
        muscle1=ex1[2];
        exName1.setText(excName1);
        PyObject eq1 = pyObj.callAttr("altEqpmnt",excName1); // call the altEqpmnt method in python to return the exercise needed equipment
        eqName1 = eq1.toString();//retrieve  output
        equ1.setText(eqName1);


        ///
        PyObject alt2 = pyObj.callAttr("findAlternative1",name,1,generalMuscle); // call the alt2 method in python
        exercise2 = alt2.toString();//retrieve
//        ex2String=exercise2.substring(1,exercise2.length()-3);
        ex2= exercise2.split("_");
        excName2=ex2[0];
        force2=ex2[1];
        muscle2=ex2[2];
        exName2.setText(excName2);
        PyObject eq2 = pyObj.callAttr("altEqpmnt",excName2); // call the altEqpmnt method in python to return the exercise needed equipment
        eqName2 = eq2.toString();//retrieve  output
        equ2.setText(eqName2);


//        ex2String=exercise2.substring(2,exercise2.length()-3);
//        ex2= ex2String.split("_");

    }


}