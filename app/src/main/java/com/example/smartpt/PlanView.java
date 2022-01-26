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

    private ImageView img8;
    private ImageView img9;
    private ImageView img10;
    private ImageView img11;
    private ImageView img12;

    private TextView m1;
    private TextView m2;
    private TextView m3;
    private TextView m4;
    private TextView m5;
    private TextView m6;
    private TextView m7;
    private TextView m8;
    private TextView m9;
    private TextView m10;
    private TextView m11;
    private TextView m12;

    private TextView f1;
    private TextView f2;
    private TextView f3;
    private TextView f4;
    private TextView f5;
    private TextView f6;
    private TextView f7;
    private TextView f8;
    private TextView f9;
    private TextView f10;
    private TextView f11;
    private TextView f12;


    private TextView TT;
    private String name;
    private String level;
    static String namedays = new String();

    private static final String TAG = "PlanView";

    private static final String KEY_T = "trainingDays";

    boolean daySat;
    boolean daySun;
    boolean dayMon;
    boolean dayTue;
    boolean dayWed;
    boolean dayThu;
    boolean dayFri;

    private String day1;
    String day11[] = new String[200];
    private String day2;
    String day22[] = new String[200];
    private String day3;
    String day33[] = new String[200];
    private String day4;
    String day44[] = new String[200];
    private String day5;
    String day55[] = new String[200];


    private String force1;
    String force11[] = new String[200];
    private String force2;
    String force22[] = new String[200];
    private String force3;
    String force33[] = new String[200];
    private String force4;
    String force44[] = new String[200];
    private String force5;
    String force55[] = new String[200];

    private String muscle1;
    String muscle11[] = new String[200];
    private String muscle2;
    String muscle22[] = new String[200];
    private String muscle3;
    String muscle33[] = new String[200];
    private String muscle4;
    String muscle44[] = new String[200];
    private String muscle5;
    String muscle55[] = new String[200];

    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date date = new Date();
    String dayOfTheWeek = sdf.format(date);


    private String Wplan;
//    private String SessionNo;


    private ArrayList<String> days = TrainingDaysNum.gettDays();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_view);

        TextviewEx1 = findViewById(R.id.textViewex1);
        TextviewEx2 = findViewById(R.id.textViewex2);
        TextviewEx3 = findViewById(R.id.textViewex3);
        TextviewEx4 = findViewById(R.id.textViewex4);
        TextviewEx5 = findViewById(R.id.textViewex5);
        TextviewEx6 = findViewById(R.id.textViewex6);
        TextviewEx7 = findViewById(R.id.textViewex7);
        TextviewEx8 = findViewById(R.id.textViewex8);
        TextviewEx9 = findViewById(R.id.textViewex9);
        TextviewEx10 = findViewById(R.id.textViewex10);
        TextviewEx11 = findViewById(R.id.textViewex11);
        TextviewEx12 = findViewById(R.id.textViewex12);

        img8=findViewById(R.id.start8);
        img9=findViewById(R.id.start9);
        img10=findViewById(R.id.start10);
        img11=findViewById(R.id.start11);
        img12=findViewById(R.id.start12);


        f1 = findViewById(R.id.f1);
        f2 = findViewById(R.id.f2);
        f3 = findViewById(R.id.f3);
        f4 = findViewById(R.id.f4);
        f5 = findViewById(R.id.f5);
        f6 = findViewById(R.id.f6);
        f7 = findViewById(R.id.f7);
        f8 = findViewById(R.id.f8);
        f9 = findViewById(R.id.f9);
        f10 = findViewById(R.id.f10);
        f11 = findViewById(R.id.f11);
        f12 = findViewById(R.id.f12);


        m1 = findViewById(R.id.m1);
        m2 = findViewById(R.id.m2);
        m3 = findViewById(R.id.m3);
        m4 = findViewById(R.id.m4);
        m5 = findViewById(R.id.m5);
        m6 = findViewById(R.id.m6);
        m7 = findViewById(R.id.m7);
        m8 = findViewById(R.id.m8);
        m9 = findViewById(R.id.m9);
        m10 = findViewById(R.id.m10);
        m11 = findViewById(R.id.m11);
        m12 = findViewById(R.id.m12);


        //ondata();
        SessionNo = getIntent().getStringExtra("SessionNo");
        level = getIntent().getStringExtra("level");
        TT = findViewById(R.id.WeeklytextView);

//        SessionNo="";

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        db = FirebaseFirestore.getInstance();


        db.collection("userProfile").document(userIp).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String test = documentSnapshot.getString(KEY_T);
                            namedays = test;
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
        call_E_F_M();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(), PlanView.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                     (update profile activity)
                    case R.id.profile:
                        Intent i = new Intent(PlanView.this, updateProfile.class);



                        if (SessionNo.equals("2")) {
                            i.putExtra("SessionNo", SessionNo);
                            i.putExtra("level", level);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            finish();
                            return true;
                        } else if (SessionNo.equals("3")) {
                            i.putExtra("SessionNo", SessionNo);
                            i.putExtra("level", level);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            finish();
                        } else if (SessionNo.equals("4")) {
                            i.putExtra("SessionNo", SessionNo);
                            i.putExtra("level", level);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            finish();
                        } else if (SessionNo.equals("5")) {
                            i.putExtra("SessionNo", SessionNo);
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
        ScrollView scrollView = (ScrollView) findViewById(R.id.ScrollFrame);

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
                if (intIndexdayMon == -1) {
                    dayMon = false;
                } else {
                    dayMon = true;
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


                buttonSat.setTextColor(Color.parseColor("#696969"));

                buttonSun.setTextColor(Color.parseColor("#696969"));

                buttonTue.setTextColor(Color.parseColor("#696969"));

                buttonWed.setTextColor(Color.parseColor("#696969"));

                buttonThu.setTextColor(Color.parseColor("#696969"));

                buttonFri.setTextColor(Color.parseColor("#696969"));


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
                    if (SessionNo.equals("5")) {
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
                if (intIndexdaySat == -1) {
                    daySat = false;
                } else {
                    daySat = true;
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
                buttonMon.setTextColor(Color.parseColor("#696969"));
                buttonSun.setTextColor(Color.parseColor("#696969"));
                buttonTue.setTextColor(Color.parseColor("#696969"));
                buttonWed.setTextColor(Color.parseColor("#696969"));
                buttonThu.setTextColor(Color.parseColor("#696969"));
                buttonFri.setTextColor(Color.parseColor("#696969"));


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
                    if (SessionNo.equals("4")) {
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
                if (intIndexdaySun == -1) {
                    daySun = false;
                } else {
                    daySun = true;
                }
//                String SessionNo = getIntent().getStringExtra("sessionNo");


                TextView mon = (TextView) findViewById(R.id.ExercisesView);
                buttonSat.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonSun.setBackgroundColor(Color.parseColor("#24c8fe"));
                buttonSun.setTextColor(getResources().getColor(R.color.white));

                buttonMon.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonTue.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonWed.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonThu.setBackgroundColor(Color.parseColor("#f1f3fa"));

                buttonFri.setBackgroundColor(Color.parseColor("#f1f3fa"));


                buttonMon.setTextColor(Color.parseColor("#696969"));
                buttonSat.setTextColor(Color.parseColor("#696969"));

                buttonTue.setTextColor(Color.parseColor("#696969"));

                buttonWed.setTextColor(Color.parseColor("#696969"));

                buttonThu.setTextColor(Color.parseColor("#696969"));

                buttonFri.setTextColor(Color.parseColor("#696969"));


                if (daySun) {
                    mon.setText("My Exercises for this day");
//                    if (SessionNo.equals("2")) {
//                        day1();
//
//                    }
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                    scrollView.setVisibility(View.VISIBLE);

                    if (SessionNo.equals("2")) {

                        day1();

                    }
                    if (SessionNo.equals("3")) {
                        day1();


                    }
                    if (SessionNo.equals("4")) {
                        day1();

                    }
                    if (SessionNo.equals("5")) {
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
                if (intIndexdayTue == -1) {
                    dayTue = false;
                } else {
                    dayTue = true;
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


                buttonMon.setTextColor(Color.parseColor("#696969"));

                buttonSat.setTextColor(Color.parseColor("#696969"));

                buttonSun.setTextColor(Color.parseColor("#696969"));

                buttonWed.setTextColor(Color.parseColor("#696969"));

                buttonThu.setTextColor(Color.parseColor("#696969"));

                buttonFri.setTextColor(Color.parseColor("#696969"));


                if (dayTue) {
                    mon.setText("My Exercises for this day");
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                    scrollView.setVisibility(View.VISIBLE);


                    if (SessionNo.equals("2")) {
                        day2();


                    }
                    if (SessionNo.equals("3")) {
                        day2();


                    }
                    if (SessionNo.equals("4")) {
                        day2();

                    }
                    if (SessionNo.equals("5")) {
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
                int intIndexdayWed = namedays.indexOf("Wed");
                if (intIndexdayWed == -1) {
                    dayWed = false;
                } else {
                    dayWed = true;
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


                buttonMon.setTextColor(Color.parseColor("#696969"));

                buttonSat.setTextColor(Color.parseColor("#696969"));

                buttonSun.setTextColor(Color.parseColor("#696969"));

                buttonTue.setTextColor(Color.parseColor("#696969"));

                buttonThu.setTextColor(Color.parseColor("#696969"));

                buttonFri.setTextColor(Color.parseColor("#696969"));


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
                int intIndexdayThu = namedays.indexOf("Thu");
                if (intIndexdayThu == -1) {
                    dayThu = false;
                } else {
                    dayThu = true;
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

                buttonMon.setTextColor(Color.parseColor("#696969"));

                buttonSat.setTextColor(Color.parseColor("#696969"));

                buttonSun.setTextColor(Color.parseColor("#696969"));

                buttonTue.setTextColor(Color.parseColor("#696969"));

                buttonWed.setTextColor(Color.parseColor("#696969"));

                buttonFri.setTextColor(Color.parseColor("#696969"));


                if (dayThu) {
                    mon.setText("My Exercises for this day");
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                    scrollView.setVisibility(View.VISIBLE);


                    //    if(SessionNo.equals("2")){



                    //  }
                    if (SessionNo.equals("3")) {



                    }
                    if (SessionNo.equals("4")) {
                        day3();

                    }
                    if (SessionNo.equals("5")) {
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
                if (intIndex == -1) {
                    dayFri = false;
                } else {
                    dayFri = true;
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

                buttonMon.setTextColor(Color.parseColor("#696969"));

                buttonSat.setTextColor(Color.parseColor("#696969"));

                buttonSun.setTextColor(Color.parseColor("#696969"));

                buttonTue.setTextColor(Color.parseColor("#696969"));

                buttonWed.setTextColor(Color.parseColor("#696969"));

                buttonThu.setTextColor(Color.parseColor("#696969"));


                if (dayFri) {
                    mon.setText("My Exercises for this day");
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                    scrollView.setVisibility(View.VISIBLE);
                    //   break;
                    if (SessionNo.equals("5")) {
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
                i.putExtra("name", TextviewEx1.getText());
                i.putExtra("force", f1.getText());
                i.putExtra("muscle", m1.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",0);
                startActivity(i);

            }
        });
        TextviewEx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name", TextviewEx2.getText());
                i.putExtra("force", f2.getText());
                i.putExtra("muscle", m2.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",1);
                startActivity(i);

            }
        });
        TextviewEx3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name", TextviewEx3.getText());
                i.putExtra("force", f3.getText());
                i.putExtra("muscle", m3.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",2);
                startActivity(i);

            }
        });
        TextviewEx4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name", TextviewEx4.getText());
                i.putExtra("force", f4.getText());
                i.putExtra("muscle", m4.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("index",3);
                i.putExtra("level",level);
                startActivity(i);

            }
        });
        TextviewEx5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name", TextviewEx5.getText());
                i.putExtra("force", f5.getText());
                i.putExtra("muscle", m5.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",4);
                startActivity(i);

            }
        });
        TextviewEx6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name", TextviewEx6.getText());
                i.putExtra("force", f6.getText());
                i.putExtra("muscle", m6.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",5);
                startActivity(i);

            }
        });
        TextviewEx7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name", TextviewEx7.getText());
                i.putExtra("force", f7.getText());
                i.putExtra("muscle", m7.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",6);
                startActivity(i);

            }
        });
        TextviewEx8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name", TextviewEx8.getText());
                i.putExtra("force", f8.getText());
                i.putExtra("muscle", m8.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",7);
                startActivity(i);

            }
        });
        TextviewEx9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name", TextviewEx9.getText());
                i.putExtra("force", f9.getText());
                i.putExtra("muscle", m9.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",8);
                startActivity(i);

            }
        });

        TextviewEx10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name", TextviewEx10.getText());
                i.putExtra("force", f10.getText());
                i.putExtra("muscle", m10.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",9);
                startActivity(i);

            }
        });

        TextviewEx11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name", TextviewEx11.getText());
                i.putExtra("force", f11.getText());
                i.putExtra("muscle", m11.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",10);
                startActivity(i);

            }
        });

        TextviewEx12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);

                i.putExtra("name", TextviewEx12.getText());
                i.putExtra("force", f12.getText());
                i.putExtra("muscle", m12.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",11);
                startActivity(i);

            }
        });

//        call_E_F_M();
    }


    public void ondata() {
        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
        String currid = uid.getUid();
        DocumentReference reference;
        reference = db.collection("userProfile").document(currid);
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {
                            String nameday = task.getResult().getString(KEY_T);
                            namedays = nameday;

                        } else {
                            Toast.makeText(PlanView.this, "Document not exist", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


    public void currentDay() {

        if (dayOfTheWeek.contains("Friday")) {
            buttonFri.performClick();
        } else if (dayOfTheWeek.contains("Monday")) {
            buttonMon.performClick();
        } else if (dayOfTheWeek.contains("Sunday")) {
            buttonSun.performClick();
        } else if (dayOfTheWeek.contains("Saturday")) {
            buttonSat.performClick();
        } else if (dayOfTheWeek.contains("Thursday")) {
            buttonThu.performClick();
        } else if (dayOfTheWeek.contains("Tuesday")) {
            buttonTue.performClick();
        } else if (dayOfTheWeek.contains("Wednesday")) {
            buttonWed.performClick();
        }
    }


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

    public void call_E_F_M() {

        if (SessionNo.equals("2")) {

            retreiveF_M1();
            retreiveF_M2();


        } else if (SessionNo.equals("3")) {

            retreiveF_M1();
            retreiveF_M2();
            retreiveF_M3();


        } else if (SessionNo.equals("4")) {
            retreiveF_M1();
            retreiveF_M2();
            retreiveF_M3();
            retreiveF_M4();

        } else if (SessionNo.equals("5")) {
            retreiveF_M1();
            retreiveF_M2();
            retreiveF_M3();
            retreiveF_M4();
            retreiveF_M5();


        }


    }

    public void day1() {

        if (level.equals("Beginner")) {

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

            img8.setVisibility(View.GONE);
            img9.setVisibility(View.GONE);
            img10.setVisibility(View.GONE);
            img11.setVisibility(View.GONE);
            img12.setVisibility(View.GONE);

            f1.setText(force11[0]);
            f2.setText(force11[1]);
            f3.setText(force11[2]);
            f4.setText(force11[3]);
            f5.setText(force11[4]);
            f6.setText(force11[5]);
            f7.setText(force11[6]);

            m1.setText(muscle11[0]);
            m2.setText(muscle11[1]);
            m3.setText(muscle11[2]);
            m4.setText(muscle11[3]);
            m5.setText(muscle11[4]);
            m6.setText(muscle11[5]);
            m7.setText(muscle11[6]);



        }
        if (level.equals("Intermediate")) {

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


            img11.setVisibility(View.GONE);
            img12.setVisibility(View.GONE);

            f1.setText(force11[0]);
            f2.setText(force11[1]);
            f3.setText(force11[2]);
            f4.setText(force11[3]);
            f5.setText(force11[4]);
            f6.setText(force11[5]);
            f7.setText(force11[6]);
            f8.setText(force11[7]);
            f9.setText(force11[8]);
            f10.setText(force11[9]);

            m1.setText(muscle11[0]);
            m2.setText(muscle11[1]);
            m3.setText(muscle11[2]);
            m4.setText(muscle11[3]);
            m5.setText(muscle11[4]);
            m6.setText(muscle11[5]);
            m7.setText(muscle11[6]);
            m8.setText(muscle11[7]);
            m9.setText(muscle11[8]);
            m10.setText(muscle11[9]);


        }
        if (level.equals("Professional")) {
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

            f1.setText(force11[0]);
            f2.setText(force11[1]);
            f3.setText(force11[2]);
            f4.setText(force11[3]);
            f5.setText(force11[4]);
            f6.setText(force11[5]);
            f7.setText(force11[6]);
            f8.setText(force11[7]);
            f9.setText(force11[8]);
            f10.setText(force11[9]);
            f11.setText(force11[10]);
            f12.setText(force11[11]);

            m1.setText(muscle11[0]);
            m2.setText(muscle11[1]);
            m3.setText(muscle11[2]);
            m4.setText(muscle11[3]);
            m5.setText(muscle11[4]);
            m6.setText(muscle11[5]);
            m7.setText(muscle11[6]);
            m8.setText(muscle11[7]);
            m9.setText(muscle11[8]);
            m10.setText(muscle11[9]);
            m11.setText(muscle11[10]);
            m12.setText(muscle11[11]);
        }

    }

    public void day2() {

        if (level.equals("Beginner")) {

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

            img8.setVisibility(View.GONE);
            img9.setVisibility(View.GONE);
            img10.setVisibility(View.GONE);
            img11.setVisibility(View.GONE);
            img12.setVisibility(View.GONE);

            f1.setText(force22[0]);
            f2.setText(force22[1]);
            f3.setText(force22[2]);
            f4.setText(force22[3]);
            f5.setText(force22[4]);
            f6.setText(force22[5]);
            f7.setText(force22[6]);


            m1.setText(muscle22[0]);
            m2.setText(muscle22[1]);
            m3.setText(muscle22[2]);
            m4.setText(muscle22[3]);
            m5.setText(muscle22[4]);
            m6.setText(muscle22[5]);
            m7.setText(muscle22[6]);



        }
        if (level.equals("Intermediate")) {

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


            img11.setVisibility(View.GONE);
            img12.setVisibility(View.GONE);

            f1.setText(force22[0]);
            f2.setText(force22[1]);
            f3.setText(force22[2]);
            f4.setText(force22[3]);
            f5.setText(force22[4]);
            f6.setText(force22[5]);
            f7.setText(force22[6]);
            f8.setText(force22[7]);
            f9.setText(force22[8]);
            f10.setText(force22[9]);


            m1.setText(muscle22[0]);
            m2.setText(muscle22[1]);
            m3.setText(muscle22[2]);
            m4.setText(muscle22[3]);
            m5.setText(muscle22[4]);
            m6.setText(muscle22[5]);
            m7.setText(muscle22[6]);
            m8.setText(muscle22[7]);
            m9.setText(muscle22[8]);
            m10.setText(muscle22[9]);



        }
        if (level.equals("Professional")) {
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

            f1.setText(force22[0]);
            f2.setText(force22[1]);
            f3.setText(force22[2]);
            f4.setText(force22[3]);
            f5.setText(force22[4]);
            f6.setText(force22[5]);
            f7.setText(force22[6]);
            f8.setText(force22[7]);
            f9.setText(force22[8]);
            f10.setText(force22[9]);
            f11.setText(force22[10]);
            f12.setText(force22[11]);

            m1.setText(muscle22[0]);
            m2.setText(muscle22[1]);
            m3.setText(muscle22[2]);
            m4.setText(muscle22[3]);
            m5.setText(muscle22[4]);
            m6.setText(muscle22[5]);
            m7.setText(muscle22[6]);
            m8.setText(muscle22[7]);
            m9.setText(muscle22[8]);
            m10.setText(muscle22[9]);
            m11.setText(muscle22[10]);
            m12.setText(muscle22[11]);
        }

    }

    public void day3() {

        if (level.equals("Beginner")) {

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

            img8.setVisibility(View.GONE);
            img9.setVisibility(View.GONE);
            img10.setVisibility(View.GONE);
            img11.setVisibility(View.GONE);
            img12.setVisibility(View.GONE);

            f1.setText(force33[0]);
            f2.setText(force33[1]);
            f3.setText(force33[2]);
            f4.setText(force33[3]);
            f5.setText(force33[4]);
            f6.setText(force33[5]);
            f7.setText(force33[6]);


            m1.setText(muscle33[0]);
            m2.setText(muscle33[1]);
            m3.setText(muscle33[2]);
            m4.setText(muscle33[3]);
            m5.setText(muscle33[4]);
            m6.setText(muscle33[5]);
            m7.setText(muscle33[6]);



        }
        if (level.equals("Intermediate")) {

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


            img11.setVisibility(View.GONE);
            img12.setVisibility(View.GONE);
            f1.setText(force33[0]);
            f2.setText(force33[1]);
            f3.setText(force33[2]);
            f4.setText(force33[3]);
            f5.setText(force33[4]);
            f6.setText(force33[5]);
            f7.setText(force33[6]);
            f8.setText(force33[7]);
            f9.setText(force33[8]);
            f10.setText(force33[9]);


            m1.setText(muscle33[0]);
            m2.setText(muscle33[1]);
            m3.setText(muscle33[2]);
            m4.setText(muscle33[3]);
            m5.setText(muscle33[4]);
            m6.setText(muscle33[5]);
            m7.setText(muscle33[6]);
            m8.setText(muscle33[7]);
            m9.setText(muscle33[8]);
            m10.setText(muscle33[9]);



        }
        if (level.equals("Professional")) {
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

            f1.setText(force33[0]);
            f2.setText(force33[1]);
            f3.setText(force33[2]);
            f4.setText(force33[3]);
            f5.setText(force33[4]);
            f6.setText(force33[5]);
            f7.setText(force33[6]);
            f8.setText(force33[7]);
            f9.setText(force33[8]);
            f10.setText(force33[9]);
            f11.setText(force33[10]);
            f12.setText(force33[11]);

            m1.setText(muscle33[0]);
            m2.setText(muscle33[1]);
            m3.setText(muscle33[2]);
            m4.setText(muscle33[3]);
            m5.setText(muscle33[4]);
            m6.setText(muscle33[5]);
            m7.setText(muscle33[6]);
            m8.setText(muscle33[7]);
            m9.setText(muscle33[8]);
            m10.setText(muscle33[9]);
            m11.setText(muscle33[10]);
            m12.setText(muscle33[11]);
        }
    }

    public void day4() {

        if (level.equals("Beginner")) {

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

            img8.setVisibility(View.GONE);
            img9.setVisibility(View.GONE);
            img10.setVisibility(View.GONE);
            img11.setVisibility(View.GONE);
            img12.setVisibility(View.GONE);

            f1.setText(force44[0]);
            f2.setText(force44[1]);
            f3.setText(force44[2]);
            f4.setText(force44[3]);
            f5.setText(force44[4]);
            f6.setText(force44[5]);
            f7.setText(force44[6]);


            m1.setText(muscle44[0]);
            m2.setText(muscle44[1]);
            m3.setText(muscle44[2]);
            m4.setText(muscle44[3]);
            m5.setText(muscle44[4]);
            m6.setText(muscle44[5]);
            m7.setText(muscle44[6]);



        }
        if (level.equals("Intermediate")) {

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



            img11.setVisibility(View.GONE);
            img12.setVisibility(View.GONE);
            f1.setText(force44[0]);
            f2.setText(force44[1]);
            f3.setText(force44[2]);
            f4.setText(force44[3]);
            f5.setText(force44[4]);
            f6.setText(force44[5]);
            f7.setText(force44[6]);
            f8.setText(force44[7]);
            f9.setText(force44[8]);
            f10.setText(force44[9]);


            m1.setText(muscle44[0]);
            m2.setText(muscle44[1]);
            m3.setText(muscle44[2]);
            m4.setText(muscle44[3]);
            m5.setText(muscle44[4]);
            m6.setText(muscle44[5]);
            m7.setText(muscle44[6]);
            m8.setText(muscle44[7]);
            m9.setText(muscle44[8]);
            m10.setText(muscle44[9]);



        }
        if (level.equals("Professional")) {
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

            f1.setText(force44[0]);
            f2.setText(force44[1]);
            f3.setText(force44[2]);
            f4.setText(force44[3]);
            f5.setText(force44[4]);
            f6.setText(force44[5]);
            f7.setText(force44[6]);
            f8.setText(force44[7]);
            f9.setText(force44[8]);
            f10.setText(force44[9]);
            f11.setText(force44[10]);
            f12.setText(force44[11]);

            m1.setText(muscle44[0]);
            m2.setText(muscle44[1]);
            m3.setText(muscle44[2]);
            m4.setText(muscle44[3]);
            m5.setText(muscle44[4]);
            m6.setText(muscle44[5]);
            m7.setText(muscle44[6]);
            m8.setText(muscle44[7]);
            m9.setText(muscle44[8]);
            m10.setText(muscle44[9]);
            m11.setText(muscle44[10]);
            m12.setText(muscle44[11]);
        }

    }

    public void day5() {

        if (level.equals("Beginner")) {

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

            img8.setVisibility(View.GONE);
            img9.setVisibility(View.GONE);
            img10.setVisibility(View.GONE);
            img11.setVisibility(View.GONE);
            img12.setVisibility(View.GONE);

            f1.setText(force55[0]);
            f2.setText(force55[1]);
            f3.setText(force55[2]);
            f4.setText(force55[3]);
            f5.setText(force55[4]);
            f6.setText(force55[5]);
            f7.setText(force55[6]);


            m1.setText(muscle55[0]);
            m2.setText(muscle55[1]);
            m3.setText(muscle55[2]);
            m4.setText(muscle55[3]);
            m5.setText(muscle55[4]);
            m6.setText(muscle55[5]);
            m7.setText(muscle55[6]);



        }
        if (level.equals("Intermediate")) {

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


            img11.setVisibility(View.GONE);
            img12.setVisibility(View.GONE);

            f1.setText(force55[0]);
            f2.setText(force55[1]);
            f3.setText(force55[2]);
            f4.setText(force55[3]);
            f5.setText(force55[4]);
            f6.setText(force55[5]);
            f7.setText(force55[6]);
            f8.setText(force55[7]);
            f9.setText(force55[8]);
            f10.setText(force55[9]);


            m1.setText(muscle55[0]);
            m2.setText(muscle55[1]);
            m3.setText(muscle55[2]);
            m4.setText(muscle55[3]);
            m5.setText(muscle55[4]);
            m6.setText(muscle55[5]);
            m7.setText(muscle55[6]);
            m8.setText(muscle55[7]);
            m9.setText(muscle55[8]);
            m10.setText(muscle55[9]);



        }
        if (level.equals("Professional")) {
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

            f1.setText(force55[0]);
            f2.setText(force55[1]);
            f3.setText(force55[2]);
            f4.setText(force55[3]);
            f5.setText(force55[4]);
            f6.setText(force55[5]);
            f7.setText(force55[6]);
            f8.setText(force55[7]);
            f9.setText(force55[8]);
            f10.setText(force55[9]);
            f11.setText(force55[10]);
            f12.setText(force55[11]);

            m1.setText(muscle55[0]);
            m2.setText(muscle55[1]);
            m3.setText(muscle55[2]);
            m4.setText(muscle55[3]);
            m5.setText(muscle55[4]);
            m6.setText(muscle55[5]);
            m7.setText(muscle55[6]);
            m8.setText(muscle55[7]);
            m9.setText(muscle55[8]);
            m10.setText(muscle55[9]);
            m11.setText(muscle55[10]);
            m12.setText(muscle55[11]);
        }

    }

//    public void splitPlan(){
//        String SessionNo=getIntent().getStringExtra("sessionNo");
//
//        if(SessionNo.equals("2")){
//
//                    day1=getIntent().getStringExtra("day1");
//
//                    day11=day1.split(" ");
//
//                    day2=getIntent().getStringExtra("day2");
//
//                    day22=day2.split(" ");
//
//
//        }
//        else if(SessionNo.equals("3")){
//
//            day1=getIntent().getStringExtra("day1");
//
//            day11=day1.split(" ");
//
//            day2=getIntent().getStringExtra("day2");
//
//            day22=day2.split(" ");
//
//            day3=getIntent().getStringExtra("day3");
//
//            day33=day3.split(" ");
//
//
//                }
//                else if(SessionNo.equals("4")){
//            day1=getIntent().getStringExtra("day1");
//
//            day11=day1.split(" ");
//
//            day2=getIntent().getStringExtra("day2");
//
//            day22=day2.split(" ");
//
//            day3=getIntent().getStringExtra("day3");
//
//            day33=day3.split(" ");
//
//            day4=getIntent().getStringExtra("day4");
//
//            day44=day4.split(" ");
//                }
//                else if(SessionNo.equals("5")){
//            day1=getIntent().getStringExtra("day1");
//
//            day11=day1.split(" ");
//
//            day2=getIntent().getStringExtra("day2");
//
//            day22=day2.split(" ");
//
//            day3=getIntent().getStringExtra("day3");
//
//            day33=day3.split(" ");
//
//            day4=getIntent().getStringExtra("day4");
//
//            day44=day4.split(" ");
//
//            day5=getIntent().getStringExtra("day5");
//
//            day55=day5.split(" ");
//
//                }
//
//        }

    public void retreiveF_M1() {

        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("userProfile").document(userIp).collection("WorkoutPlan").document(userIp).collection(userIp).document("day1");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                day1 = value.getString("plan");
                day1=day1.substring(2,day1.length()-3);
                day11 = day1.split("_");

                force1 = value.getString("force");
                force1=force1.substring(2,force1.length()-3);
                force11 = force1.split("_");


                muscle1 = value.getString("muscle");
                muscle1=muscle1.substring(2,muscle1.length()-3);
                muscle11 = muscle1.split("_");


            }
        });
    }


    public void retreiveF_M2() {

        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("userProfile").document(userIp).collection("WorkoutPlan").document(userIp).collection(userIp).document("day2");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                day2 = value.getString("plan");
                day2=day2.substring(2,day2.length()-3);
                day22 = day2.split("_");

                force2 = value.getString("force");
                force2=force2.substring(2,force2.length()-3);
                force22 = force2.split("_");

                muscle2 = value.getString("muscle");
                muscle2=muscle2.substring(2,muscle2.length()-3);
                muscle22 = muscle2.split("_");

            }
        });
    }

    public void retreiveF_M3() {

        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("userProfile").document(userIp).collection("WorkoutPlan").document(userIp).collection(userIp).document("day3");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                day3 = value.getString("plan");
                day3=day3.substring(2,day3.length()-3);
                day33 = day3.split("_");

                force3 = value.getString("force");
                force3=force3.substring(2,force3.length()-3);
                force33 = force3.split("_");

                muscle3 = value.getString("muscle");
                muscle3=muscle3.substring(2,muscle3.length()-3);
                muscle33 = muscle3.split("_");

            }
        });
    }

    public void retreiveF_M4() {

        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("userProfile").document(userIp).collection("WorkoutPlan").document(userIp).collection(userIp).document("day4");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                day4 = value.getString("plan");
                day4=day4.substring(2,day4.length()-3);
                day44 = day4.split("_");

                force4 = value.getString("force");
                force4=force4.substring(2,force4.length()-3);
                force44 = force4.split("_");

                muscle4 = value.getString("muscle");
                muscle4=muscle4.substring(2,muscle4.length()-3);
                muscle44 = muscle4.split("_");

            }
        });
    }

    public void retreiveF_M5() {

        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("userProfile").document(userIp).collection("WorkoutPlan").document(userIp).collection(userIp).document("day5");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                day5 = value.getString("plan");
                day5=day5.substring(2,day5.length()-3);
                day55 = day5.split("_");

                force5 = value.getString("force");
                force5=force5.substring(2,force5.length()-3);
                force55 = force5.split("_");

                muscle5 = value.getString("muscle");
                muscle5=muscle5.substring(2,muscle5.length()-3);
                muscle55 = muscle5.split("_");

            }
        });
    }

    public void retriveProfile() {
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("userProfile").document(userIp);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

//                SessionNo= value.getString("TrainingdaysNum");
//                level=value.getString("level");


            }
        });

    }
}

