package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlanView extends AppCompatActivity {


    // data base
    private FirebaseFirestore db;
    private String userIp;

    private String SessionNo;
    private Button buttonSat;
    private Button buttonSun;
    private Button buttonMon;
    private Button buttonTue;
    private Button buttonWed;
    private Button buttonFri;
    private Button buttonThu;
    private Button buttonALeart;



    private TextView TextviewEx1;
    private TextView TextviewEx2;
    private TextView TextviewEx3;
    private TextView TextviewEx4;
    private TextView TextviewEx5;
    private TextView TextviewEx6;
    private TextView TextviewEx7;
    private TextView TextviewEx8;
    private TextView TextviewEx9;
    private TextView TextviewEx10;
    private TextView TextviewEx11;
    private TextView TextviewEx12;


        private TextView TT;
    private String name;
private String level;
    static String namedays=new String();

    private static final String TAG = "PlanView";

    private static final String KEY_T="trainingDays";

    boolean daySat;
    boolean daySun;
    boolean dayMon;
    boolean dayTue;
    boolean dayWed;
    boolean dayThu;
    boolean dayFri;

    private String day1;
    String day11[]= new String[200];
    private String day2;
    String day22[]=new String[200];
    private String day3;
    String day33[]=new String[200];
    private String day4;
    String day44[]=new String[200];
    private String day5;
    String day55[]=new String[200];




    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date date = new Date();
    String dayOfTheWeek = sdf.format(date);



    private String Wplan;
//    private String SessionNo;




    private ArrayList<String> days=TrainingDaysNum.gettDays();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_view);

        TextviewEx1=findViewById(R.id.textViewex1);
        TextviewEx2=findViewById(R.id.textViewex2);
        TextviewEx3=findViewById(R.id.textViewex3);
        TextviewEx4=findViewById(R.id.textViewex4);
        TextviewEx5=findViewById(R.id.textViewex5);
        TextviewEx6=findViewById(R.id.textViewex6);
        TextviewEx7=findViewById(R.id.textViewex7);
        TextviewEx8=findViewById(R.id.textViewex8);
        TextviewEx9=findViewById(R.id.textViewex9);
        TextviewEx10=findViewById(R.id.textViewex10);
        TextviewEx11=findViewById(R.id.textViewex11);
        TextviewEx12=findViewById(R.id.textViewex12);






 //ondata();
SessionNo=getIntent().getStringExtra("sessionNo");
level =getIntent().getStringExtra("level");
TT=findViewById(R.id.WeeklytextView);




        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        db = FirebaseFirestore.getInstance();


        db.collection("userProfile").document(userIp).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                           String test= documentSnapshot.getString(KEY_T);
                           namedays=test;
                            currentDay();

                        } else {
                            Toast.makeText(PlanView.this, "Document not exist", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PlanView.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());

                    }
                });




//        retreiveTDays();
        splitPlan();

        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),PlanView.class));
                        overridePendingTransition(0,0);
                        return true;
//                     (update profile activity)
                    case R.id.profile:
                        Intent i = new Intent(PlanView.this, updateProfile.class);

                        if(SessionNo.equals("2")){
                            i.putExtra("sessionNo",SessionNo);
                            i.putExtra("day1",day1);
                            i.putExtra("day2",day2);
                            i.putExtra("level",level);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            finish();
                            return true;
                        }
                        else if(SessionNo.equals("3")){
                            i.putExtra("sessionNo",SessionNo);
                            i.putExtra("day1",day1);
                            i.putExtra("day2",day2);
                            i.putExtra("day3",day3);
                            i.putExtra("level",level);
                            startActivity(i);
                            overridePendingTransition(0, 0);
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
                            overridePendingTransition(0, 0);
                            finish();
                        }
                        else if(SessionNo.equals("5")) {
                            i.putExtra("sessionNo", SessionNo);
                            i.putExtra("day1", day1);
                            i.putExtra("day2", day2);
                            i.putExtra("day3", day3);
                            i.putExtra("day4", day4);
                            i.putExtra("day5", day5);
                            i.putExtra("level", level);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            finish();
                        }
//
//                    case R.id.progress:
//                        startActivity(new Intent(getApplicationContext(),progress.class));
//                        overridePendingTransition(0,0);
//                        return true;
                }
                return false;
            }
        });

       // LinearLayout exFrame = (LinearLayout) findViewById(R.id.ExFrame);
        buttonALeart = (Button) findViewById(R.id.alertButton);
        FrameLayout alertFrame = (FrameLayout) findViewById(R.id.alertFrame);
        ScrollView scrollView =(ScrollView)findViewById(R.id.ScrollFrame);
        
//        TextviewEx1 = findViewById(R.id.textViewex1);
//        TextviewEx2 = findViewById(R.id.textViewex2);
//        TextviewEx3 = findViewById(R.id.textViewex3);



        buttonALeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView) findViewById(R.id.alertText);
                text.setVisibility(View.GONE);
                alertFrame.setVisibility(View.GONE);
                v.setVisibility(View.GONE);




            }
        });
























        buttonMon = (Button) findViewById(R.id.buttonMon);
        buttonSat = (Button) findViewById(R.id.buttonSat);
        buttonSun = (Button) findViewById(R.id.buttonSun);
        buttonTue = (Button) findViewById(R.id.buttonTue);
        buttonWed = (Button) findViewById(R.id.buttonWed);
        buttonThu = (Button) findViewById(R.id.buttonThu);
        buttonFri = (Button) findViewById(R.id.buttonFri);







                buttonMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int intIndexdayMon = namedays.indexOf("Mon");
                if(intIndexdayMon == - 1) {
                    dayMon=false;
                }else {
                    dayMon=true;
                }

                TextView mon = (TextView) findViewById(R.id.ExercisesView);
                buttonSat.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonSun.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonMon.setBackgroundColor(Color.parseColor("#24c8fe"));

                buttonMon.setTextColor(getResources().getColor(R.color.white));

                buttonTue.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonWed.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonThu.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonFri.setBackgroundColor(Color.parseColor("#f1f3fa"));


                buttonSat.setTextColor( Color.parseColor("#696969"));

                buttonSun.setTextColor( Color.parseColor("#696969"));

                buttonTue.setTextColor( Color.parseColor("#696969"));

                buttonWed.setTextColor( Color.parseColor("#696969"));

                buttonThu.setTextColor( Color.parseColor("#696969"));

                buttonFri.setTextColor( Color.parseColor("#696969"));



               // for (int i=0;i<days.size();i++){
                 //   String day =days.get(i);
                    if (dayMon) {
                        mon.setText("My Exercises for this day");
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                        scrollView.setVisibility(View.VISIBLE);

//                        if(SessionNo.equals("2")){
//
//
//                        }
//                        if(SessionNo.equals("3")){
//
//
//                        }
//                        if(SessionNo.equals("4")){
//                          day2();

//                        }
                          if(SessionNo.equals("5")) {
                            day2();
                        }

                            ///     break;

                    } else {

                        mon.setText("There is no exercises for this day");
                        scrollView.setVisibility(View.INVISIBLE);
                    }

                //}// end for

            }
        });


        buttonSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intIndexdaySat = namedays.indexOf("Sat");
                if(intIndexdaySat == - 1) {
                    daySat=false;
                }else {
                    daySat=true;
                }
                TextView mon = (TextView) findViewById(R.id.ExercisesView);
                buttonSat.setBackgroundColor(Color.parseColor("#24c8fe"));
                buttonSat.setTextColor(getResources().getColor(R.color.white));
                buttonSun.setBackgroundColor(Color.parseColor("#f1f3fa"));
                buttonMon.setBackgroundColor(Color.parseColor("#f1f3fa"));
                buttonTue.setBackgroundColor(Color.parseColor("#f1f3fa"));
                buttonWed.setBackgroundColor(Color.parseColor("#f1f3fa"));
                buttonThu.setBackgroundColor(Color.parseColor("#f1f3fa"));
                buttonFri.setBackgroundColor(Color.parseColor("#f1f3fa"));
                buttonMon.setTextColor( Color.parseColor("#696969"));
                buttonSun.setTextColor( Color.parseColor("#696969"));
                buttonTue.setTextColor( Color.parseColor("#696969"));
                buttonWed.setTextColor( Color.parseColor("#696969"));
                buttonThu.setTextColor( Color.parseColor("#696969"));
                buttonFri.setTextColor( Color.parseColor("#696969"));



                    if (daySat) {
                        mon.setText("My Exercises for this day");
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                        scrollView.setVisibility(View.VISIBLE);

//                        if(SessionNo.equals("2")){
//
//
//                        }
//                        if(SessionNo.equals("3")){
//
//
//                        }
                        if(SessionNo.equals("4")){
                            day4();

                        }
//                        if(SessionNo.equals("5")) {
//                            day2();
//                        }
                        ///      break;

                    } else {

                        mon.setText("There is no exercises for this day");
                        scrollView.setVisibility(View.INVISIBLE);
                    }

                //}/// end for


            }
        });

        buttonSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intIndexdaySun = namedays.indexOf("Sun");
                if(intIndexdaySun == - 1) {
                    daySun=false;
                }else {
                    daySun=true;
                }
                String SessionNo=getIntent().getStringExtra("sessionNo");


                TextView mon = (TextView) findViewById(R.id.ExercisesView);
                buttonSat.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonSun.setBackgroundColor(Color.parseColor("#24c8fe"));
                buttonSun.setTextColor(getResources().getColor(R.color.white));

                buttonMon.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonTue.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonWed.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonThu.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonFri.setBackgroundColor(Color.parseColor("#f1f3fa"));


                buttonMon.setTextColor( Color.parseColor("#696969"));
                buttonSat.setTextColor( Color.parseColor("#696969"));

                buttonTue.setTextColor( Color.parseColor("#696969"));

                buttonWed.setTextColor( Color.parseColor("#696969"));

                buttonThu.setTextColor( Color.parseColor("#696969"));

                buttonFri.setTextColor( Color.parseColor("#696969"));




                    if (daySun) {
                        mon.setText("My Exercises for this day");

//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                        scrollView.setVisibility(View.VISIBLE);

                        if(SessionNo.equals("2")){

                            day1();

                        }
                        if(SessionNo.equals("3")){
                            day1();


                        }
                        if(SessionNo.equals("4")){
                            day1();

                        }
                        if(SessionNo.equals("5")) {
                            day1();
                        }
                        //     break;

                    } else {

                        mon.setText("There is no exercises for this day");
                        scrollView.setVisibility(View.INVISIBLE);
                    }

                //}// end for


            }
        });
        buttonTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intIndexdayTue = namedays.indexOf("Tue");
                if(intIndexdayTue == - 1) {
                    dayTue=false;
                }else {
                    dayTue=true;
                }

                TextView mon = (TextView) findViewById(R.id.ExercisesView);
                buttonSat.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonSun.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonMon.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonTue.setBackgroundColor(Color.parseColor("#24c8fe"));
                buttonTue.setTextColor(getResources().getColor(R.color.white));

                buttonWed.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonThu.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonFri.setBackgroundColor(Color.parseColor("#f1f3fa"));


                buttonMon.setTextColor( Color.parseColor("#696969"));

                buttonSat.setTextColor( Color.parseColor("#696969"));

                buttonSun.setTextColor( Color.parseColor("#696969"));

                buttonWed.setTextColor( Color.parseColor("#696969"));

                buttonThu.setTextColor( Color.parseColor("#696969"));

                buttonFri.setTextColor( Color.parseColor("#696969"));


                    if (dayTue) {
                        mon.setText("My Exercises for this day");
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                        scrollView.setVisibility(View.VISIBLE);

                        if(SessionNo.equals("2")){
                            day2();




                        }
                        if(SessionNo.equals("3")){
                            day2();


                        }
                        if(SessionNo.equals("4")){
                            day2();

                        }
                      if(SessionNo.equals("5")) {
                          day3();
                    }
                        //        break;

                    } else {

                        mon.setText("There is no exercises for this day");
                        scrollView.setVisibility(View.INVISIBLE);
                    }

              //  }// end for


            }
        });


        buttonWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intIndexdayWed= namedays.indexOf("Wed");
                if(intIndexdayWed == - 1) {
                    dayWed=false;
                }else {
                    dayWed=true;
                }

                TextView mon = (TextView) findViewById(R.id.ExercisesView);
                buttonSat.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonSun.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonMon.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonTue.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonWed.setBackgroundColor(Color.parseColor("#24c8fe"));

                buttonWed.setTextColor(getResources().getColor(R.color.white));

                buttonThu.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonFri.setBackgroundColor(Color.parseColor("#f1f3fa"));



                buttonMon.setTextColor( Color.parseColor("#696969"));

                buttonSat.setTextColor( Color.parseColor("#696969"));

                buttonSun.setTextColor( Color.parseColor("#696969"));

                buttonTue.setTextColor( Color.parseColor("#696969"));

                buttonThu.setTextColor( Color.parseColor("#696969"));

                buttonFri.setTextColor( Color.parseColor("#696969"));


                    if (dayWed) {
                        mon.setText("My Exercises for this day");
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                        scrollView.setVisibility(View.VISIBLE);//

                     /*   if(SessionNo.equals("2")){
                            day2();


                        }
                        if(SessionNo.equals("3")){


                        }
                        if(SessionNo.equals("4")){
                            day3();

                        }
                        if(SessionNo.equals("5")) {
                            day4();
                        }
                        //       break;*/

                    } else {

                        mon.setText("There is no exercises for this day");
                        scrollView.setVisibility(View.INVISIBLE);
                    }

            //    }// end for


            }
        });

        buttonThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intIndexdayThu= namedays.indexOf("Thu");
                if(intIndexdayThu == - 1) {
                    dayThu=false;
                }else {
                    dayThu=true;
                }
                TextView mon = (TextView) findViewById(R.id.ExercisesView);
                buttonSat.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonSun.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonMon.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonTue.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonWed.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonThu.setBackgroundColor(Color.parseColor("#24c8fe"));

                buttonThu.setTextColor(getResources().getColor(R.color.white));

                buttonFri.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonMon.setTextColor( Color.parseColor("#696969"));

                buttonSat.setTextColor( Color.parseColor("#696969"));

                buttonSun.setTextColor( Color.parseColor("#696969"));

                buttonTue.setTextColor( Color.parseColor("#696969"));

                buttonWed.setTextColor( Color.parseColor("#696969"));

                buttonFri.setTextColor( Color.parseColor("#696969"));



                    if (dayThu) {
                        mon.setText("My Exercises for this day");
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                        scrollView.setVisibility(View.VISIBLE);


                    //    if(SessionNo.equals("2")){


                      //  }
                        if(SessionNo.equals("3")){

                            day3();

                        }
                        if(SessionNo.equals("4")){
                            day3();

                        }
                        if(SessionNo.equals("5")) {
                            day4();
                        }
                 //       break;

                    } else {

                        mon.setText("There is no exercises for this day");
                        scrollView.setVisibility(View.INVISIBLE);
                    }

              //  }// end for


            }
        });

        buttonFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intIndex = namedays.indexOf("Fri");
                if(intIndex == - 1) {
                    dayFri=false;
                }else {
                    dayFri=true;
                }
                TextView mon = (TextView) findViewById(R.id.ExercisesView);
                buttonSat.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonSun.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonMon.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonTue.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonWed.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonThu.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonFri.setBackgroundColor(Color.parseColor("#24c8fe"));

                buttonFri.setTextColor(getResources().getColor(R.color.white));

                buttonMon.setTextColor( Color.parseColor("#696969"));

                buttonSat.setTextColor( Color.parseColor("#696969"));

                buttonSun.setTextColor( Color.parseColor("#696969"));

                buttonTue.setTextColor( Color.parseColor("#696969"));

                buttonWed.setTextColor( Color.parseColor("#696969"));

                buttonThu.setTextColor( Color.parseColor("#696969"));


                    if (dayFri) {
                        mon.setText("My Exercises for this day");
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                        scrollView.setVisibility(View.VISIBLE);
                     //   break;
                     if(SessionNo.equals("5")) {
                            day5();
                        }

                    } else {

                        mon.setText("There is no exercises for this day");
                        scrollView.setVisibility(View.INVISIBLE);
                    }

               // }// end for


            }
        });

        TextviewEx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name",TextviewEx1.getText());
                    startActivity(i);

            }
        });
        TextviewEx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name",TextviewEx2.getText());
                startActivity(i);

            }
        });
        TextviewEx3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name",TextviewEx3.getText());
                startActivity(i);

            }
        });
        TextviewEx4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name",TextviewEx4.getText());
                startActivity(i);

            }
        });
        TextviewEx5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name",TextviewEx5.getText());
                startActivity(i);

            }
        });
        TextviewEx6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name",TextviewEx6.getText());
                startActivity(i);

            }
        });
        TextviewEx7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name",TextviewEx7.getText());
                startActivity(i);

            }
        });
        TextviewEx8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name",TextviewEx8.getText());
                startActivity(i);

            }
        });
        TextviewEx9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name",TextviewEx9.getText());
                startActivity(i);

            }
        });
    }





    public void ondata(){
        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
String currid=uid.getUid();
DocumentReference reference;
reference=db.collection("userProfile").document(currid);
reference.get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){
                    String nameday=task.getResult().getString(KEY_T);
                    namedays=nameday;

                }else {
                    Toast.makeText(PlanView.this,"Document not exist",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



public void currentDay(){

    if(dayOfTheWeek.contains("Friday")){ buttonFri.performClick();}
    else
    if(dayOfTheWeek.contains("Monday")){ buttonMon.performClick();}
    else
    if(dayOfTheWeek.contains("Sunday")){ buttonSun.performClick();}
    else
    if(dayOfTheWeek.contains("Saturday")){ buttonSat.performClick();}
    else
    if(dayOfTheWeek.contains("Thursday")){ buttonThu.performClick();}
    else
    if(dayOfTheWeek.contains("Tuesday")){ buttonTue.performClick();}
    else
    if(dayOfTheWeek.contains("Wednesday")){ buttonWed.performClick();}}



    //////////////////////////////////////////////////////////////////////////////////////////
    public void retreivePlan(int i) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection("userProfile").document(userIp).collection("WorkoutPlan").document(userIp).collection(userIp).document("day" + i);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Wplan = value.getString("plan");
//                TT.setText(Wplan);


            }
        });
    }
/////////////////////////////////////////////////////////////////////////////////////////

//    public void retreiveTDays() {
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
//
//        db = FirebaseFirestore.getInstance();
//        DocumentReference documentReference =  db.collection("userProfile").document(userIp);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                name= value.getString("name");
//                SessionNo=value.getString("TrainingdaysNum");
//
//            }
//        });
//
//
//        }

    public void day1(){

        if(level.equals("Beginner")){

            TextviewEx1.setText(day11[0]);
            TextviewEx2.setText(day11[1]);
            TextviewEx3.setText(day11[2]);
            TextviewEx4.setText(day11[3]);
            TextviewEx5.setText(day11[4]);
            TextviewEx6.setText(day11[5]);
            TextviewEx7.setText(day11[6]);
            TextviewEx8.setVisibility(View.GONE);
            TextviewEx9.setVisibility(View.GONE);
            TextviewEx10.setVisibility(View.GONE);
            TextviewEx11.setVisibility(View.GONE);
            TextviewEx12.setVisibility(View.GONE);

        }
        if(level.equals("Intermediate")){

            TextviewEx1.setText(day11[0]);
            TextviewEx2.setText(day11[1]);
            TextviewEx3.setText(day11[2]);
            TextviewEx4.setText(day11[3]);
            TextviewEx5.setText(day11[4]);
            TextviewEx6.setText(day11[5]);
            TextviewEx7.setText(day11[6]);
            TextviewEx8.setText(day11[7]);
            TextviewEx9.setText(day11[8]);
            TextviewEx10.setText(day11[9]);
            TextviewEx11.setVisibility(View.GONE);
            TextviewEx12.setVisibility(View.GONE);

        }
        if(level.equals("Professional")){
            TextviewEx1.setText(day11[0]);
            TextviewEx2.setText(day11[1]);
            TextviewEx3.setText(day11[2]);
            TextviewEx4.setText(day11[3]);
            TextviewEx5.setText(day11[4]);
            TextviewEx6.setText(day11[5]);
            TextviewEx7.setText(day11[6]);
            TextviewEx8.setText(day11[7]);
            TextviewEx9.setText(day11[8]);
            TextviewEx10.setText(day11[9]);
            TextviewEx11.setText(day11[10]);
            TextviewEx12.setText(day11[11]);
        }

    }

    public void day2(){

        if(level.equals("Beginner")){

            TextviewEx1.setText(day22[0]);
            TextviewEx2.setText(day22[1]);
            TextviewEx3.setText(day22[2]);
            TextviewEx4.setText(day22[3]);
            TextviewEx5.setText(day22[4]);
            TextviewEx6.setText(day22[5]);
            TextviewEx7.setText(day22[6]);
            TextviewEx8.setVisibility(View.GONE);
            TextviewEx9.setVisibility(View.GONE);
            TextviewEx10.setVisibility(View.GONE);
            TextviewEx11.setVisibility(View.GONE);
            TextviewEx12.setVisibility(View.GONE);
            
        }
        if(level.equals("Intermediate")){

            TextviewEx1.setText(day22[0]);
            TextviewEx2.setText(day22[1]);
            TextviewEx3.setText(day22[2]);
            TextviewEx4.setText(day22[3]);
            TextviewEx5.setText(day22[4]);
            TextviewEx6.setText(day22[5]);
            TextviewEx7.setText(day22[6]);
            TextviewEx8.setText(day22[7]);
            TextviewEx9.setText(day22[8]);
            TextviewEx10.setText(day22[9]);
            TextviewEx11.setVisibility(View.GONE);
            TextviewEx12.setVisibility(View.GONE);
            

        }
        if(level.equals("Professional")){
            TextviewEx1.setText(day22[0]);
            TextviewEx2.setText(day22[1]);
            TextviewEx3.setText(day22[2]);
            TextviewEx4.setText(day22[3]);
            TextviewEx5.setText(day22[4]);
            TextviewEx6.setText(day22[5]);
            TextviewEx7.setText(day22[6]);
            TextviewEx8.setText(day22[7]);
            TextviewEx9.setText(day22[8]);
            TextviewEx10.setText(day22[9]);
            TextviewEx11.setText(day22[10]);
            TextviewEx12.setText(day22[11]);
        }

    }

    public void day3(){

        if(level.equals("Beginner")){

            TextviewEx1.setText(day33[0]);
            TextviewEx2.setText(day33[1]);
            TextviewEx3.setText(day33[2]);
            TextviewEx4.setText(day33[3]);
            TextviewEx5.setText(day33[4]);
            TextviewEx6.setText(day33[5]);
            TextviewEx7.setText(day33[6]);
            TextviewEx8.setVisibility(View.GONE);
            TextviewEx9.setVisibility(View.GONE);
            TextviewEx10.setVisibility(View.GONE);
            TextviewEx11.setVisibility(View.GONE);
            TextviewEx12.setVisibility(View.GONE);
            
        }
        if(level.equals("Intermediate")){

            TextviewEx1.setText(day33[0]);
            TextviewEx2.setText(day33[1]);
            TextviewEx3.setText(day33[2]);
            TextviewEx4.setText(day33[3]);
            TextviewEx5.setText(day33[4]);
            TextviewEx6.setText(day33[5]);
            TextviewEx7.setText(day33[6]);
            TextviewEx8.setText(day33[7]);
            TextviewEx9.setText(day33[8]);
            TextviewEx10.setText(day33[9]);
            TextviewEx11.setVisibility(View.GONE);
            TextviewEx12.setVisibility(View.GONE);
            

        }
        if(level.equals("Professional")){
            TextviewEx1.setText(day33[0]);
            TextviewEx2.setText(day33[1]);
            TextviewEx3.setText(day33[2]);
            TextviewEx4.setText(day33[3]);
            TextviewEx5.setText(day33[4]);
            TextviewEx6.setText(day33[5]);
            TextviewEx7.setText(day33[6]);
            TextviewEx8.setText(day33[7]);
            TextviewEx9.setText(day33[8]);
            TextviewEx10.setText(day33[9]);
            TextviewEx11.setText(day33[10]);
            TextviewEx12.setText(day33[11]);
        }

    }
    public void day4(){

        if(level.equals("Beginner")){

            TextviewEx1.setText(day44[0]);
            TextviewEx2.setText(day44[1]);
            TextviewEx3.setText(day44[2]);
            TextviewEx4.setText(day44[3]);
            TextviewEx5.setText(day44[4]);
            TextviewEx6.setText(day44[5]);
            TextviewEx7.setText(day44[6]);
            TextviewEx8.setVisibility(View.GONE);
            TextviewEx9.setVisibility(View.GONE);
            TextviewEx10.setVisibility(View.GONE);
            TextviewEx11.setVisibility(View.GONE);
            TextviewEx12.setVisibility(View.GONE);
            
        }
        if(level.equals("Intermediate")){

            TextviewEx1.setText(day44[0]);
            TextviewEx2.setText(day44[1]);
            TextviewEx3.setText(day44[2]);
            TextviewEx4.setText(day44[3]);
            TextviewEx5.setText(day44[4]);
            TextviewEx6.setText(day44[5]);
            TextviewEx7.setText(day44[6]);
            TextviewEx8.setText(day44[7]);
            TextviewEx9.setText(day44[8]);
            TextviewEx10.setText(day44[9]);
            TextviewEx11.setVisibility(View.GONE);
            TextviewEx12.setVisibility(View.GONE);

        }
        if(level.equals("Professional")){
            TextviewEx1.setText(day44[0]);
            TextviewEx2.setText(day44[1]);
            TextviewEx3.setText(day44[2]);
            TextviewEx4.setText(day44[3]);
            TextviewEx5.setText(day44[4]);
            TextviewEx6.setText(day44[5]);
            TextviewEx7.setText(day44[6]);
            TextviewEx8.setText(day44[7]);
            TextviewEx9.setText(day44[8]);
            TextviewEx10.setText(day44[9]);
            TextviewEx11.setText(day44[10]);
            TextviewEx12.setText(day44[11]);
        }

    }

    public void day5(){

        if(level.equals("Beginner")){

            TextviewEx1.setText(day55[0]);
            TextviewEx2.setText(day55[1]);
            TextviewEx3.setText(day55[2]);
            TextviewEx4.setText(day55[3]);
            TextviewEx5.setText(day55[4]);
            TextviewEx6.setText(day55[5]);
            TextviewEx7.setText(day55[6]);
            TextviewEx8.setVisibility(View.GONE);
            TextviewEx9.setVisibility(View.GONE);
            TextviewEx10.setVisibility(View.GONE);
            TextviewEx11.setVisibility(View.GONE);
            TextviewEx12.setVisibility(View.GONE);
            
        }
        if(level.equals("Intermediate")){

            TextviewEx1.setText(day55[0]);
            TextviewEx2.setText(day55[1]);
            TextviewEx3.setText(day55[2]);
            TextviewEx4.setText(day55[3]);
            TextviewEx5.setText(day55[4]);
            TextviewEx6.setText(day55[5]);
            TextviewEx7.setText(day55[6]);
            TextviewEx8.setText(day55[7]);
            TextviewEx9.setText(day55[8]);
            TextviewEx10.setText(day55[9]);
            TextviewEx11.setVisibility(View.GONE);
            TextviewEx12.setVisibility(View.GONE);           


        }
        if(level.equals("Professional")){
            TextviewEx1.setText(day55[0]);
            TextviewEx2.setText(day55[1]);
            TextviewEx3.setText(day55[2]);
            TextviewEx4.setText(day55[3]);
            TextviewEx5.setText(day55[4]);
            TextviewEx6.setText(day55[5]);
            TextviewEx7.setText(day55[6]);
            TextviewEx8.setText(day55[7]);
            TextviewEx9.setText(day55[8]);
            TextviewEx10.setText(day55[9]);
            TextviewEx11.setText(day55[10]);
            TextviewEx12.setText(day55[11]);
        }

    }

        public void splitPlan(){
        String SessionNo=getIntent().getStringExtra("sessionNo");

        if(SessionNo.equals("2")){

                    day1=getIntent().getStringExtra("day1");

                    day11=day1.split(" ");

                    day2=getIntent().getStringExtra("day2");

                    day22=day2.split(" ");


        }
        else if(SessionNo.equals("3")){

            day1=getIntent().getStringExtra("day1");

            day11=day1.split(" ");

            day2=getIntent().getStringExtra("day2");

            day22=day2.split(" ");

            day3=getIntent().getStringExtra("day3");

            day33=day3.split(" ");


                }
                else if(SessionNo.equals("4")){
            day1=getIntent().getStringExtra("day1");

            day11=day1.split(" ");

            day2=getIntent().getStringExtra("day2");

            day22=day2.split(" ");

            day3=getIntent().getStringExtra("day3");

            day33=day3.split(" ");

            day4=getIntent().getStringExtra("day4");

            day44=day4.split(" ");
                }
                else if(SessionNo.equals("5")){
            day1=getIntent().getStringExtra("day1");

            day11=day1.split(" ");

            day2=getIntent().getStringExtra("day2");

            day22=day2.split(" ");

            day3=getIntent().getStringExtra("day3");

            day33=day3.split(" ");

            day4=getIntent().getStringExtra("day4");

            day44=day4.split(" ");

            day5=getIntent().getStringExtra("day5");

            day55=day5.split(" ");

                }

        }

//    public void btnC(android.view.View i){
//
//
//    }
    }

