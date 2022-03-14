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
    private TextView lbl1,back;
    private String name,generalMuscle,currDay,SessionNo,level,exercise1,eqName1,eqName2,exercise2, excName1,excName2,ex1String,ex2String,force1,muscle1,force2,muscle2,day,force,muscle,dday,fforce,mmuscle;
    private int index;
    TextView exName[] = new TextView[4];
    TextView exEqu[] = new TextView[4];
    Button btn[]=new Button[4];

    String muscleA[] =new String[4];
    String forceA[] =new String[4];
    String nameA[] =new String[4];



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
        back=findViewById(R.id.back);

        // get exercise name textView
        exName[0]=findViewById(R.id.exName1);
        exName[1]=findViewById(R.id.exName2);
        exName[2]=findViewById(R.id.exName3);
        exName[3]=findViewById(R.id.exName4);

        // get exercise equipment name textView
        exEqu[0]=findViewById(R.id.equName1);
        exEqu[1]=findViewById(R.id.equName2);
        exEqu[2]=findViewById(R.id.equName3);
        exEqu[3]=findViewById(R.id.equName4);

        // get Buttons
        btn[0]=findViewById(R.id.exbtn1);
        btn[1]=findViewById(R.id.exbtn2);
        btn[2]=findViewById(R.id.exbtn3);
        btn[3]=findViewById(R.id.exbtn4);


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


        btn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dday="";
                fforce="";
                mmuscle="";
                dayAr[index]=nameA[0];
                forceAr[index]=forceA[0];
                muscleAr[index]=muscleA[0];
                for (int i = 0; i < dayAr.length; i++) {
                    dday+=dayAr[i]+ "_";
                    fforce+=forceAr[i]+"_";
                    mmuscle+=muscleAr[i]+"_";
                }

                dday="['"+dday+"']";
                fforce="['"+fforce+"']";
                mmuscle="['"+mmuscle+"']";



                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                String userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                db = FirebaseFirestore.getInstance();
                CollectionReference ex = db.collection("userProfile");

                String s="day"+(currDay);
                Map<String,Object> planEx = new HashMap<>();
                planEx.put("plan",dday);
                planEx.put("force",fforce);
                planEx.put("muscle",mmuscle);




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

        btn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dday="";
                fforce="";
                mmuscle="";
                dayAr[index]=nameA[1];
                forceAr[index]=forceA[1];
                muscleAr[index]=muscleA[1];
                for (int i = 0; i < dayAr.length; i++) {
                    dday+=dayAr[i]+ "_";
                    fforce+=forceAr[i]+"_";
                    mmuscle+=muscleAr[i]+"_";
                }
                dday="['"+dday+"']";
                fforce="['"+fforce+"']";
                mmuscle="['"+mmuscle+"']";





                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                String userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                db = FirebaseFirestore.getInstance();
                CollectionReference ex = db.collection("userProfile");

                String s="day"+(currDay);
                Map<String,Object> planEx = new HashMap<>();
                planEx.put("plan",dday);
                planEx.put("force",fforce);
                planEx.put("muscle",mmuscle);




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
        btn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dday="";
                fforce="";
                mmuscle="";
                dayAr[index]=nameA[2];
                forceAr[index]=forceA[2];
                muscleAr[index]=muscleA[2];
                for (int i = 0; i < dayAr.length; i++) {
                    dday+=dayAr[i]+ "_";
                    fforce+=forceAr[i]+"_";
                    mmuscle+=muscleAr[i]+"_";
                }
                dday="['"+dday+"']";
                fforce="['"+fforce+"']";
                mmuscle="['"+mmuscle+"']";





                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                String userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                db = FirebaseFirestore.getInstance();
                CollectionReference ex = db.collection("userProfile");

                String s="day"+(currDay);
                Map<String,Object> planEx = new HashMap<>();
                planEx.put("plan",dday);
                planEx.put("force",fforce);
                planEx.put("muscle",mmuscle);




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

        btn[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dday="";
                fforce="";
                mmuscle="";
                dayAr[index]=nameA[3];
                forceAr[index]=forceA[3];
                muscleAr[index]=muscleA[3];
                for (int i = 0; i < dayAr.length; i++) {
                    dday+=dayAr[i]+ "_";
                    fforce+=forceAr[i]+"_";
                    mmuscle+=muscleAr[i]+"_";
                }
                dday="['"+dday+"']";
                fforce="['"+fforce+"']";
                mmuscle="['"+mmuscle+"']";



                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                String userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                db = FirebaseFirestore.getInstance();
                CollectionReference ex = db.collection("userProfile");

                String s="day"+(currDay);
                Map<String,Object> planEx = new HashMap<>();
                planEx.put("plan",dday);
                planEx.put("force",fforce);
                planEx.put("muscle",mmuscle);




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

//        PyObject alt1 = pyObj.callAttr("findAlternative1",name,0,generalMuscle,"repeated"); // call the alt1 method in python
//        exercise1 = alt1.toString();//retrieve  output
//        ex1= exercise1.split("_");
//        excName1=ex1[0];
//        force1=ex1[1];
//        muscle1=ex1[2];
//        exName1.setText(excName1);
//        PyObject eq1 = pyObj.callAttr("altEqpmnt",excName1); // call the altEqpmnt method in python to return the exercise needed equipment
//        eqName1 = eq1.toString();//retrieve  output
//        equ1.setText(eqName1);
//
//
//        ///
//        PyObject alt2 = pyObj.callAttr("findAlternative1",name,1,generalMuscle,excName1); // call the alt2 method in python
//        exercise2 = alt2.toString();//retrieve
//        ex2= exercise2.split("_");
//        excName2=ex2[0];
//        force2=ex2[1];
//        muscle2=ex2[2];
//        exName2.setText(excName2);
//        PyObject eq2 = pyObj.callAttr("altEqpmnt",excName2); // call the altEqpmnt method in python to return the exercise needed equipment
//        eqName2 = eq2.toString();//retrieve  output
//        equ2.setText(eqName2);

        // ________________________________##########################################______________________________________-

        for(int i =0; i<4;i++) {
            PyObject alt1 = pyObj.callAttr("findAlternative1", name,i); // call the alt1 method in python
            exercise1 = alt1.toString();//retrieve  output
            ex1 = exercise1.split("_");
            nameA[i] = ex1[0];
            forceA[i] = ex1[1];
            muscleA[i] = ex1[2];
            exName[i].setText(nameA[i]);
            PyObject eq1 = pyObj.callAttr("altEqpmnt", nameA[i]); // call the altEqpmnt method in python to return the exercise needed equipment
            eqName1 = eq1.toString();//retrieve  output
            exEqu[i].setText(eqName1);

        }

    }


}