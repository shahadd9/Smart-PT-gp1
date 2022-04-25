package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.text.SimpleDateFormat;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlanView extends AppCompatActivity {


    // data base
    private FirebaseFirestore db;
    private FirebaseFirestore db2;
//    private String userIp;
    private ProgressDialog pd;
    private FirebaseAuth uAuth;

    private int  FBindex;
    private Double FBindexD;
    private String SessionNo;
    private Button buttonSat;
    private Button buttonSun;
    private Button buttonMon;
    private Button buttonTue;
    private Button buttonWed;
    private Button buttonFri;
    private Button buttonThu;
    private Button buttonALeart;

    private String finished;


   private  int Curweek;

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

    public final static String shared="sharedPrefs";
    private int done;

//    private ImageView img8;
//    private ImageView img9;
//    private ImageView img10;
//    private ImageView img11;
//    private ImageView img12;

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
    private TextView userNamep;
    private TextView weekNo;

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
    private int week;
    private Double weekD;

    private TextView TT;
    private String traineeName;
    private String level;
    static String namedays = new String();

    private String isItOne;

    private static final String TAG = "PlanView";

    private static final String KEY_T = "trainingDays";

    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    private int weekPassedNum;

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



    Button butstart1;

    Button butAlrt1 ;
    Button butAlrt2 ;
    Button butAlrt3 ;
    Button butAlrt4 ;
    Button butAlrt5 ;
    Button butAlrt6 ;
    Button butAlrt7 ;
    Button butAlrt8 ;
    Button butAlrt9 ;
    Button butAlrt10;
    Button butAlrt11;
    Button butAlrt12;
    TableRow exRow1;
    TableRow exRow2;
    TableRow exRow3;
    TableRow exRow4;
    TableRow exRow5;
    TableRow exRow6;
    TableRow exRow7;
    TableRow exRow8;
    TableRow exRow9;
    TableRow exRow10;
    TableRow exRow11;
    TableRow exRow12;
    private String startDate;
    String startDateAr[] = new String[4];
    private String todaytDate;
    String todaytDateAr[] = new String[4];
    private String id;




    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date date = new Date();
    String dayOfTheWeek = sdf.format(date);
    private String currDay;
//    private int c;
    private  Intent inProg;


    private String Wplan;
    //    private String SessionNo;
    AlertDialog.Builder builder;


    private ArrayList<String> days = TrainingDaysNum.gettDays();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_view);

        //to get user email
        uAuth= FirebaseAuth.getInstance();
        FirebaseUser curUser=uAuth.getCurrentUser();
        id=curUser.getEmail();

        currDay="0";
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
        userNamep=findViewById(R.id.userNamep);
        weekNo=findViewById(R.id.weekNo);


//        img8=findViewById(R.id.start8);
//        img9=findViewById(R.id.start9);
//        img10=findViewById(R.id.start10);
//        img11=findViewById(R.id.start11);
//        img12=findViewById(R.id.start12);


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
         butAlrt1 =(Button) findViewById(R.id.alrt1);
         butstart1 =(Button) findViewById(R.id.start100);
         butAlrt2 =(Button) findViewById(R.id.alrt2);
         butAlrt3 =(Button) findViewById(R.id.alrt3);
         butAlrt4 =(Button) findViewById(R.id.alrt4);
         butAlrt5 =(Button) findViewById(R.id.alrt5);
         butAlrt6 =(Button) findViewById(R.id.alrt6);
         butAlrt7 =(Button) findViewById(R.id.alrt7);
         butAlrt8 =(Button) findViewById(R.id.alrt8);
         butAlrt9 =(Button) findViewById(R.id.alrt9);
         butAlrt10 =(Button) findViewById(R.id.alrt10);
         butAlrt11 =(Button) findViewById(R.id.alrt11);
         butAlrt12 =(Button) findViewById(R.id.alrt12);

        exRow1 =findViewById(R.id.exRow1);
        exRow2 =findViewById(R.id.exRow2);
        exRow3 =findViewById(R.id.exRow3);
        exRow4 =findViewById(R.id.exRow4);
        exRow5 =findViewById(R.id.exRow5);
        exRow6 =findViewById(R.id.exRow6);
        exRow7 =findViewById(R.id.exRow7);
        exRow8 =findViewById(R.id.exRow8);
        exRow9 =findViewById(R.id.exRow9);
        exRow10 =findViewById(R.id.exRow10);
        exRow11 =findViewById(R.id.exRow11);
        exRow12 =findViewById(R.id.exRow12);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        todaytDate = dateFormat.format(calendar.getTime());



        builder= new AlertDialog.Builder(this);

        //ondata();
        SessionNo = getIntent().getStringExtra("SessionNo");
        level = getIntent().getStringExtra("level");
//        c = getIntent().getIntExtra("counter",0);

        TT = findViewById(R.id.WeeklytextView);




        db = FirebaseFirestore.getInstance();
        db2=FirebaseFirestore.getInstance();

        callweek();

        uAuth= FirebaseAuth.getInstance();
        id=curUser.getEmail();
        DocumentReference documentReference =  db.collection("userProfile").document(id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    String test = value.getString(KEY_T);
                    traineeName=value.getString("name");
                    userNamep.setText("Welcome "+traineeName+" !");
                    SessionNo=value.getString("TrainingdaysNum");
                    namedays = test;
                    currentDay();

                } else {
                    Toast.makeText(PlanView.this, "Document not exist", Toast.LENGTH_SHORT).show();
                }

            }
        });
//        db.collection("userProfile").document(userIp).get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            String test = documentSnapshot.getString(KEY_T);
//                            namedays = test;
//                            currentDay();
//
//                        } else {
//                            Toast.makeText(PlanView.this, "Document not exist", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(PlanView.this, "Error!", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, e.toString());
//
//                    }
//                });


        call_E_F_M();

        inProg = new Intent(PlanView.this, UserProgress.class);


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


        ImageView restimg=(ImageView) findViewById(R.id.restimg);



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
                buttonSat.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonSun.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonMon.setBackgroundColor(Color.parseColor("#6A8DE6"));

                buttonMon.setTextColor(getResources().getColor(R.color.white));

                buttonTue.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonWed.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonThu.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonFri.setBackgroundColor(Color.parseColor("#70d4f4"));


                buttonSat.setTextColor(getResources().getColor(R.color.white));

                buttonSun.setTextColor(getResources().getColor(R.color.white));

                buttonTue.setTextColor(getResources().getColor(R.color.white));

                buttonWed.setTextColor(getResources().getColor(R.color.white));

                buttonThu.setTextColor(getResources().getColor(R.color.white));

                buttonFri.setTextColor(getResources().getColor(R.color.white));


                // for (int i=0;i<days.size();i++){
                //   String day =days.get(i);
                if (dayMon) {
                    mon.setText("My Exercises for this day");
                    restimg.setVisibility(View.INVISIBLE);
                    butstart1.setVisibility(View.VISIBLE);

                    scrollView.setVisibility(View.VISIBLE);

//

                    if (SessionNo.equals("5")) {
                        currDay="2";
                        inProg.putExtra("currDay",currDay);
                        getExIndex(currDay,week);
                        day2();
                    }


                    ///     break;

                } else {

                    mon.setText("Rest Day\n");
                    restimg.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    butstart1.setVisibility(View.INVISIBLE);


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
                buttonSat.setBackgroundColor(Color.parseColor("#6A8DE6"));
                buttonSat.setTextColor(getResources().getColor(R.color.white));
                buttonSun.setBackgroundColor(Color.parseColor("#70d4f4"));
                buttonMon.setBackgroundColor(Color.parseColor("#70d4f4"));
                buttonTue.setBackgroundColor(Color.parseColor("#70d4f4"));
                buttonWed.setBackgroundColor(Color.parseColor("#70d4f4"));
                buttonThu.setBackgroundColor(Color.parseColor("#70d4f4"));
                buttonFri.setBackgroundColor(Color.parseColor("#70d4f4"));
                buttonMon.setTextColor(getResources().getColor(R.color.white));
                buttonSun.setTextColor(getResources().getColor(R.color.white));
                buttonTue.setTextColor(getResources().getColor(R.color.white));
                buttonWed.setTextColor(getResources().getColor(R.color.white));
                buttonThu.setTextColor(getResources().getColor(R.color.white));
                buttonFri.setTextColor(getResources().getColor(R.color.white));


                if (daySat) {
                    mon.setText("My Exercises for this day");
                    restimg.setVisibility(View.INVISIBLE);

                    butstart1.setVisibility(View.VISIBLE);

                    scrollView.setVisibility(View.VISIBLE);


                    if (SessionNo.equals("4")) {
                        currDay="4";
                        inProg.putExtra("currDay",currDay);
                        getExIndex(currDay,week);
                        day4();

                    }



                } else {

                    mon.setText("Rest Day\n");
                    restimg.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    butstart1.setVisibility(View.INVISIBLE);
                    //  mon.setCompoundDrawablesWithIntrinsicBounds(
                    //       0, 0, 0,  R.drawable.relax);


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
                buttonSat.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonSun.setBackgroundColor(Color.parseColor("#6A8DE6"));
                buttonSun.setTextColor(getResources().getColor(R.color.white));

                buttonMon.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonTue.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonWed.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonThu.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonFri.setBackgroundColor(Color.parseColor("#70d4f4"));


                buttonMon.setTextColor(getResources().getColor(R.color.white));
                buttonSat.setTextColor(getResources().getColor(R.color.white));

                buttonTue.setTextColor(getResources().getColor(R.color.white));

                buttonWed.setTextColor(getResources().getColor(R.color.white));

                buttonThu.setTextColor(getResources().getColor(R.color.white));

                buttonFri.setTextColor(getResources().getColor(R.color.white));


                if (daySun) {
                    mon.setText("My Exercises for this day");
                    restimg.setVisibility(View.INVISIBLE);
//                    if (SessionNo.equals("2")) {
//                        day1();
//
//                    }
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                    butstart1.setVisibility(View.VISIBLE);

                    scrollView.setVisibility(View.VISIBLE);

                    if (SessionNo.equals("2")) {
                        currDay="1";
                        inProg.putExtra("currDay",currDay);

                        getExIndex(currDay,week);
                        day1();

                    }
                    if (SessionNo.equals("3")) {
                        currDay="1";                        inProg.putExtra("currDay",currDay);

                        getExIndex(currDay,week);
                        day1();


                    }
                    if (SessionNo.equals("4")) {
                        getExIndex(currDay,week);                        inProg.putExtra("currDay",currDay);

                        currDay="1";
                        day1();

                    }
                    if (SessionNo.equals("5")) {
                        currDay="1";                        inProg.putExtra("currDay",currDay);

                        getExIndex(currDay,week);
                        day1();
                    }
                    //     break;

                } else {

                    mon.setText("Rest Day\n");
                    restimg.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    butstart1.setVisibility(View.INVISIBLE);

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
                buttonSat.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonSun.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonMon.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonTue.setBackgroundColor(Color.parseColor("#6A8DE6"));
                buttonTue.setTextColor(getResources().getColor(R.color.white));
                //A2AAF9

                buttonWed.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonThu.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonFri.setBackgroundColor(Color.parseColor("#70d4f4"));


                buttonMon.setTextColor(getResources().getColor(R.color.white));

                buttonSat.setTextColor(getResources().getColor(R.color.white));

                buttonSun.setTextColor(getResources().getColor(R.color.white));

                buttonWed.setTextColor(getResources().getColor(R.color.white));

                buttonThu.setTextColor(getResources().getColor(R.color.white));

                buttonFri.setTextColor(getResources().getColor(R.color.white));


                if (dayTue) {
                    mon.setText("My Exercises for this day");
                    restimg.setVisibility(View.INVISIBLE);
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                    scrollView.setVisibility(View.VISIBLE);
                    butstart1.setVisibility(View.VISIBLE);


                    if (SessionNo.equals("2")) {
                        currDay="2";                        inProg.putExtra("currDay",currDay);

                        getExIndex(currDay,week);
                        day2();


                    }
                    if (SessionNo.equals("3")) {
                        currDay="2";                        inProg.putExtra("currDay",currDay);

                        getExIndex(currDay,week);
                        day2();


                    }
                    if (SessionNo.equals("4")) {
                        currDay="2";                        inProg.putExtra("currDay",currDay);

                        getExIndex(currDay,week);
                        day2();

                    }
                    if (SessionNo.equals("5")) {
                        currDay="3";                        inProg.putExtra("currDay",currDay);

                        getExIndex(currDay,week);
                        day3();
                    }
                    //        break;


                } else {

                    mon.setText("Rest Day\n");
                    restimg.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    butstart1.setVisibility(View.INVISIBLE);

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
                buttonSat.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonSun.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonMon.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonTue.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonWed.setBackgroundColor(Color.parseColor("#6A8DE6"));

                buttonWed.setTextColor(getResources().getColor(R.color.white));
                //24c8feOR A2AAF9

                buttonThu.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonFri.setBackgroundColor(Color.parseColor("#70d4f4"));


                buttonMon.setTextColor(getResources().getColor(R.color.white));

                buttonSat.setTextColor(getResources().getColor(R.color.white));

                buttonSun.setTextColor(getResources().getColor(R.color.white));

                buttonTue.setTextColor(getResources().getColor(R.color.white));

                buttonThu.setTextColor(getResources().getColor(R.color.white));

                buttonFri.setTextColor(getResources().getColor(R.color.white));


                if (dayWed) {
                    mon.setText("My Exercises for this day");
                    restimg.setVisibility(View.INVISIBLE);
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);

                    scrollView.setVisibility(View.VISIBLE);//
                    butstart1.setVisibility(View.VISIBLE);


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

                    mon.setText("Rest Day\n");
                    restimg.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    butstart1.setVisibility(View.INVISIBLE);

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
                buttonSat.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonSun.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonMon.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonTue.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonWed.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonThu.setBackgroundColor(Color.parseColor("#6A8DE6"));

                buttonThu.setTextColor(getResources().getColor(R.color.white));

                buttonFri.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonMon.setTextColor(getResources().getColor(R.color.white));

                buttonSat.setTextColor(getResources().getColor(R.color.white));

                buttonSun.setTextColor(getResources().getColor(R.color.white));

                buttonTue.setTextColor(getResources().getColor(R.color.white));

                buttonWed.setTextColor(getResources().getColor(R.color.white));

                buttonFri.setTextColor(getResources().getColor(R.color.white));


                if (dayThu) {
                    mon.setText("My Exercises for this day");
                    restimg.setVisibility(View.INVISIBLE);
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                    scrollView.setVisibility(View.VISIBLE);
                    butstart1.setVisibility(View.VISIBLE);


                    //    if(SessionNo.equals("2")){



                    //  }
                    if (SessionNo.equals("3")) {
                        currDay="3";
                        inProg.putExtra("currDay",currDay);
                        getExIndex(currDay,week);
                        day3();


                    }
                    if (SessionNo.equals("4")) {
                        currDay="3";
                        inProg.putExtra("currDay",currDay);
                        getExIndex(currDay,week);
                        day3();

                    }
                    if (SessionNo.equals("5")) {
                        currDay="4";
                        getExIndex(currDay,week);
                        inProg.putExtra("currDay",currDay);
                        day4();
                    }
                    //       break;


                } else {

                    mon.setText("Rest Day\n");
                    restimg.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    butstart1.setVisibility(View.INVISIBLE);

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
                buttonSat.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonSun.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonMon.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonTue.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonWed.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonThu.setBackgroundColor(Color.parseColor("#70d4f4"));

                buttonFri.setBackgroundColor(Color.parseColor("#6A8DE6"));

                buttonFri.setTextColor(getResources().getColor(R.color.white));

                buttonMon.setTextColor(getResources().getColor(R.color.white));

                buttonSat.setTextColor(getResources().getColor(R.color.white));

                buttonSun.setTextColor(getResources().getColor(R.color.white));

                buttonTue.setTextColor(getResources().getColor(R.color.white));

                buttonWed.setTextColor(getResources().getColor(R.color.white));

                buttonThu.setTextColor(getResources().getColor(R.color.white));


                if (dayFri) {
                    mon.setText("My Exercises for this day");
                    restimg.setVisibility(View.INVISIBLE);
//                        loadEx1(v);
//                        loadEx2(v);
//                        loadEx3(v);
                    scrollView.setVisibility(View.VISIBLE);
                    butstart1.setVisibility(View.VISIBLE);

                    //   break;
                    if (SessionNo.equals("5")) {
                        currDay="5";
                        inProg.putExtra("currDay",currDay);

                        getExIndex(currDay,week);
                        day5();
                    }


                } else {

                    mon.setText("Rest Day\n");
                    restimg.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    butstart1.setVisibility(View.INVISIBLE);

                }

                // }// end for


            }
        });

/////////////////////////////////////////////////////////////////////////////////////////////////////////

        butAlrt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);
                i.putExtra("name", TextviewEx1.getText());
                i.putExtra("force", f1.getText());
                i.putExtra("muscle", m1.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",0);
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","methodAlt");

                startActivity(i);
            }
        });

        butAlrt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);
                i.putExtra("name", TextviewEx2.getText());
                i.putExtra("force", f2.getText());
                i.putExtra("muscle", m2.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",1);
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","methodAlt");

                startActivity(i);
            }
        });
        butAlrt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);
                i.putExtra("name", TextviewEx3.getText());
                i.putExtra("force", f3.getText());
                i.putExtra("muscle", m3.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",2);
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","methodAlt");

                startActivity(i);
            }
        });

        butAlrt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);
                i.putExtra("name", TextviewEx4.getText());
                i.putExtra("force", f4.getText());
                i.putExtra("muscle", m4.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",3);
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","methodAlt");

                startActivity(i);
            }
        });


        butAlrt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);
                i.putExtra("name", TextviewEx5.getText());
                i.putExtra("force", f5.getText());
                i.putExtra("muscle", m5.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",4);
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","methodAlt");

                startActivity(i);
            }
        });

        butAlrt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);
                i.putExtra("name", TextviewEx6.getText());
                i.putExtra("force", f6.getText());
                i.putExtra("muscle", m6.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",5);
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","methodAlt");

                startActivity(i);
            }
        });

        butAlrt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);
                i.putExtra("name", TextviewEx7.getText());
                i.putExtra("force", f7.getText());
                i.putExtra("muscle", m7.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",6);
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","methodAlt");

                startActivity(i);
            }
        });

        butAlrt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);
                i.putExtra("name", TextviewEx8.getText());
                i.putExtra("force", f8.getText());
                i.putExtra("muscle", m8.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",7);
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","methodAlt");

                startActivity(i);
            }
        });

        butAlrt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);
                i.putExtra("name", TextviewEx9.getText());
                i.putExtra("force", f9.getText());
                i.putExtra("muscle", m9.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",8);
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","methodAlt");

                startActivity(i);
            }
        });

        butAlrt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);
                i.putExtra("name", TextviewEx10.getText());
                i.putExtra("force", f10.getText());
                i.putExtra("muscle", m10.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",9);
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","methodAlt");

                startActivity(i);
            }
        });

        butAlrt11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);
                i.putExtra("name", TextviewEx11.getText());
                i.putExtra("force", f11.getText());
                i.putExtra("muscle", m11.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",10);
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","methodAlt");

                startActivity(i);
            }
        });

        butAlrt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PlanView.this, Exercise.class);
                i.putExtra("name", TextviewEx12.getText());
                i.putExtra("force", f12.getText());
                i.putExtra("muscle", m12.getText());
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                i.putExtra("index",11);
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","methodAlt");

                startActivity(i);
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
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","no");
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
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","no");

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
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","no");
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
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","no");
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
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","no");
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
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","no");

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
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","no");

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
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","no");

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
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","no");

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
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","no");

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
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","no");

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
                i.putExtra("currDay",currDay);
                i.putExtra("butAlt","no");

                startActivity(i);

            }
        });

//        call_E_F_M();
//        getExIndex(currDay,week);

        butstart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pd= new ProgressDialog(PlanView.this);
                Intent i = new Intent(PlanView.this, StartSession.class);
                i.putExtra("name", TextviewEx1.getText());
                i.putExtra("force", f1.getText());
                i.putExtra("muscle", m1.getText());
                i.putExtra("level",level);
                i.putExtra("currDay",currDay);
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("week",week);

                if(FBindex<99){
//                    TextviewEx1.setText(FBindex+"_"+currDay);
//                    pd.show();
                    startActivity(i);
                    finish();
//                    pd.dismiss();
                }
                else{
                    builder.setTitle("").setMessage("You have finished this session").setCancelable(true)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            }).show();

                }


            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////
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
//                        if (SessionNo.equals("2")) {
                        i.putExtra("SessionNo", SessionNo);
                        i.putExtra("level", level);
                        i.putExtra("week",week);
                        i.putExtra("currDay",currDay);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;


                    case R.id.progress:
                        inProg.putExtra("SessionNo", SessionNo);
                        inProg.putExtra("level", level);
                        inProg.putExtra("week",week);
                        inProg.putExtra("currDay",currDay);
                        startActivity(inProg);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;


                }
                return false;
            }
        });

    }

    private void callweek() {

        DocumentReference documentReference = db2.collection("Progress").document(id).collection("index").document("weeks");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    if (value.exists()) {
//                        exists
                        weekD= value.getDouble("week");
                        week=(int)Math.round(weekD);
                        isItOne=value.getString("isItOne");
                        startDate=value.getString("startDateWeek"+week);
                    } else {
                        //doesn't exist
                    }
                }

            }
        });

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

        getExIndex(currDay,week);


        if (dayOfTheWeek.contains("Friday")) {
            buttonFri.performClick();
            weekNo.setText("Week"+week);

        } else if (dayOfTheWeek.contains("Monday")) {
            buttonMon.performClick();
            weekNo.setText("Week"+week);


        } else if (dayOfTheWeek.contains("Sunday")) {
            buttonSun.performClick();
            GeneratenextWeek();
            weekNo.setText("Week"+week);

        } else if (dayOfTheWeek.contains("Saturday")) {
            buttonSat.performClick();
            updateFlag();
            weekNo.setText("Week"+week);

        } else if (dayOfTheWeek.contains("Thursday")) {
            buttonThu.performClick();
            weekNo.setText("Week"+week);

        } else if (dayOfTheWeek.contains("Tuesday")) {
            buttonTue.performClick();
            weekNo.setText("Week"+week);

        } else if (dayOfTheWeek.contains("Wednesday")) {
            buttonWed.performClick();
            weekNo.setText("Week"+week);

        }
    }

    private void GeneratenextWeek() {

        Curweek=0;

        String update="0";
        try{
            Date date1;
            Date date2;

            date1=dateFormat.parse(startDate);
            date2=dateFormat.parse(todaytDate);

            long difference = Math.abs(date2.getTime() - date1.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            weekPassedNum= (int)differenceDates/7;
            if (weekPassedNum==0){
                weekPassedNum=1;
            }
            Curweek= week+weekPassedNum-1;

            if(differenceDates>=7){
                updateFlag();
                isItOne=update;
            }
        } catch (Exception exception) {
            Toast.makeText(this, "Unable to find difference", Toast.LENGTH_SHORT).show();
        }
        Map<String,Object> weeks = new HashMap<>();

        if(isItOne=="0"|| isItOne.equals("0")) {
            try{
                Date date1;
                Date date2;

                date1=dateFormat.parse(startDate);
                date2=dateFormat.parse( currDay);

                long difference = Math.abs(date2.getTime() - date1.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);

                weekPassedNum= (int)differenceDates/7;
                if (weekPassedNum==0){
                    weekPassedNum=1;
                }
                Curweek= week+weekPassedNum-1;

            } catch (Exception exception) {
                Toast.makeText(this, "Unable to find difference", Toast.LENGTH_SHORT).show();
            }
            weeks.put("week", ++Curweek);
            weeks.put("isItOne", "1");
            weeks.put("startDateWeek"+Curweek,todaytDate);
            generateNextWeek(Curweek);



        }
        else {
            weeks.put("week", week);
            weeks.put("isItOne", "1");
//            weeks.put("startDateWeek"+week,todaytDate);
        }
        db.collection("Progress").document(id).collection("index").document("weeks").update(weeks).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }

    private void generateNextWeek(int weekD) {

        SharedPreferences sharedPreferences = getSharedPreferences(shared,MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt("sessionDone",0);
        editor.apply();

        Map<String,Object> user = new HashMap<>();
        Map<String,Object> week = new HashMap<>();

        user.put("exerciseIndex",0);
        user.put("duration",0);


        db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+weekD).document("day1").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+weekD).document("day2").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+weekD).document("day3").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+weekD).document("day4").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+weekD).document("day5").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    }

    private void updateFlag() {



        Map<String,Object> weeks = new HashMap<>();

        weeks.put("isItOne", "0");
        db.collection("Progress").document(id).collection("index").document("weeks").update(weeks).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }


    //////////////////////////////////////////////////////////////////////////////////////////
    public void retreivePlan(int i) {
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection("userProfile").document(id).collection("WorkoutPlan").document(id).collection(id).document("day" + i);
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
//            TextviewEx8.setVisibility(View.GONE);
//           TextviewEx9.setVisibility(View.GONE);
//             TextviewEx10.setVisibility(View.GONE);
//             TextviewEx11.setVisibility(View.GONE);
//             TextviewEx12.setVisibility(View.GONE);

/*            butAlrt8.setVisibility(View.GONE);
            butAlrt9.setVisibility(View.GONE);
            butAlrt10.setVisibility(View.GONE);
/*         butAlrt11.setVisibility(View.GONE);
            butAlrt12.setVisibility(View.GONE);*/

            exRow8.setVisibility(View.GONE);
            exRow9.setVisibility(View.GONE);
            exRow10.setVisibility(View.GONE);
            exRow11.setVisibility(View.GONE);
            exRow12.setVisibility(View.GONE);

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
        
//             TextviewEx11.setVisibility(View.GONE);
//             TextviewEx12.setVisibility(View.GONE);



/*         butAlrt11.setVisibility(View.GONE);
            butAlrt12.setVisibility(View.GONE);*/


            exRow11.setVisibility(View.GONE);
            exRow12.setVisibility(View.GONE);

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
//            TextviewEx8.setVisibility(View.GONE);
//           TextviewEx9.setVisibility(View.GONE);
//             TextviewEx10.setVisibility(View.GONE);
//             TextviewEx11.setVisibility(View.GONE);
//             TextviewEx12.setVisibility(View.GONE);
/*            butAlrt8.setVisibility(View.GONE);
            butAlrt9.setVisibility(View.GONE);
            butAlrt10.setVisibility(View.GONE);
/*         butAlrt11.setVisibility(View.GONE);
            butAlrt12.setVisibility(View.GONE);*/

            exRow8.setVisibility(View.GONE);
            exRow9.setVisibility(View.GONE);
            exRow10.setVisibility(View.GONE);
            exRow11.setVisibility(View.GONE);
            exRow12.setVisibility(View.GONE);

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
        
//             TextviewEx11.setVisibility(View.GONE);
//             TextviewEx12.setVisibility(View.GONE);



/*         butAlrt11.setVisibility(View.GONE);
            butAlrt12.setVisibility(View.GONE);*/


            exRow11.setVisibility(View.GONE);
            exRow12.setVisibility(View.GONE);

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
//            TextviewEx8.setVisibility(View.GONE);
//           TextviewEx9.setVisibility(View.GONE);
//             TextviewEx10.setVisibility(View.GONE);
//             TextviewEx11.setVisibility(View.GONE);
//             TextviewEx12.setVisibility(View.GONE);
/*            butAlrt8.setVisibility(View.GONE);
            butAlrt9.setVisibility(View.GONE);
            butAlrt10.setVisibility(View.GONE);
/*         butAlrt11.setVisibility(View.GONE);
            butAlrt12.setVisibility(View.GONE);*/

            exRow8.setVisibility(View.GONE);
            exRow9.setVisibility(View.GONE);
            exRow10.setVisibility(View.GONE);
            exRow11.setVisibility(View.GONE);
            exRow12.setVisibility(View.GONE);

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
        
//             TextviewEx11.setVisibility(View.GONE);
//             TextviewEx12.setVisibility(View.GONE);



/*         butAlrt11.setVisibility(View.GONE);
            butAlrt12.setVisibility(View.GONE);*/


            exRow11.setVisibility(View.GONE);
            exRow12.setVisibility(View.GONE);

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
//            TextviewEx8.setVisibility(View.GONE);
//           TextviewEx9.setVisibility(View.GONE);
//             TextviewEx10.setVisibility(View.GONE);
//             TextviewEx11.setVisibility(View.GONE);
//             TextviewEx12.setVisibility(View.GONE);
/*            butAlrt8.setVisibility(View.GONE);
            butAlrt9.setVisibility(View.GONE);
            butAlrt10.setVisibility(View.GONE);
/*         butAlrt11.setVisibility(View.GONE);
            butAlrt12.setVisibility(View.GONE);*/

            exRow8.setVisibility(View.GONE);
            exRow9.setVisibility(View.GONE);
            exRow10.setVisibility(View.GONE);
            exRow11.setVisibility(View.GONE);
            exRow12.setVisibility(View.GONE);

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
        
//             TextviewEx11.setVisibility(View.GONE);
//             TextviewEx12.setVisibility(View.GONE);



/*         butAlrt11.setVisibility(View.GONE);
            butAlrt12.setVisibility(View.GONE);*/


            exRow11.setVisibility(View.GONE);
            exRow12.setVisibility(View.GONE);

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
//            TextviewEx8.setVisibility(View.GONE);
//           TextviewEx9.setVisibility(View.GONE);
//             TextviewEx10.setVisibility(View.GONE);
//             TextviewEx11.setVisibility(View.GONE);
//             TextviewEx12.setVisibility(View.GONE);
/*            butAlrt8.setVisibility(View.GONE);
            butAlrt9.setVisibility(View.GONE);
            butAlrt10.setVisibility(View.GONE);
/*         butAlrt11.setVisibility(View.GONE);
            butAlrt12.setVisibility(View.GONE);*/

            exRow8.setVisibility(View.GONE);
            exRow9.setVisibility(View.GONE);
            exRow10.setVisibility(View.GONE);
            exRow11.setVisibility(View.GONE);
            exRow12.setVisibility(View.GONE);

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
        
//             TextviewEx11.setVisibility(View.GONE);
//             TextviewEx12.setVisibility(View.GONE);


/*         butAlrt11.setVisibility(View.GONE);
            butAlrt12.setVisibility(View.GONE);*/


            exRow11.setVisibility(View.GONE);
            exRow12.setVisibility(View.GONE);

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
        DocumentReference documentReference = db.collection("userProfile").document(id).collection("WorkoutPlan").document(id).collection(id).document("day1");
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
        DocumentReference documentReference = db.collection("userProfile").document(id).collection("WorkoutPlan").document(id).collection(id).document("day2");
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
        DocumentReference documentReference = db.collection("userProfile").document(id).collection("WorkoutPlan").document(id).collection(id).document("day3");
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
        DocumentReference documentReference = db.collection("userProfile").document(id).collection("WorkoutPlan").document(id).collection(id).document("day4");
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
        DocumentReference documentReference = db.collection("userProfile").document(id).collection("WorkoutPlan").document(id).collection(id).document("day5");
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

    public void getExIndex(String curr,int wee) {
        db = FirebaseFirestore.getInstance();
        DocumentReference d = db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+week).document("day"+currDay);
        d.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                FBindexD= value.getDouble("exerciseIndex");
                if(FBindexD !=null)
                FBindex=(int)Math.round(FBindexD);
                int zero=FBindex;

                FBindex=FBindex+1;

                if(zero==0){

                    exRow1.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow2.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow3.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow4.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow5.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow6.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow7.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow8.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow9.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow10.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow11.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow12.setBackgroundColor(Color.parseColor("#ffffff"));
                    TextviewEx1.setTextSize(18);
                    TextviewEx2.setTextSize(18);
                    TextviewEx3.setTextSize(18);
                    TextviewEx4.setTextSize(18);
                    TextviewEx5.setTextSize(18);
                    TextviewEx6.setTextSize(18);
                    TextviewEx7.setTextSize(18);
                    TextviewEx8.setTextSize(18);
                    TextviewEx9.setTextSize(18);
                    TextviewEx10.setTextSize(18);
                    TextviewEx11.setTextSize(18);
                    TextviewEx12.setTextSize(18);



                }

              else   if(FBindex==1){
                    exRow1.setBackgroundColor(Color.parseColor("#ECF9FD"));
                    TextviewEx1.setTextSize(21);
                    exRow2.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow3.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow4.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow5.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow6.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow7.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow8.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow9.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow10.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow11.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow12.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextviewEx2.setTextSize(18);
                    TextviewEx3.setTextSize(18);
                    TextviewEx4.setTextSize(18);
                    TextviewEx5.setTextSize(18);
                    TextviewEx6.setTextSize(18);
                    TextviewEx7.setTextSize(18);
                    TextviewEx8.setTextSize(18);
                    TextviewEx9.setTextSize(18);
                    TextviewEx10.setTextSize(18);
                    TextviewEx11.setTextSize(18);
                    TextviewEx12.setTextSize(18);
                }
                else if(FBindex==2){

                    exRow2.setBackgroundColor(Color.parseColor("#ECF9FD"));
                    TextviewEx2.setTextSize(21);
                    exRow1.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow3.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow4.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow5.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow6.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow7.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow8.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow9.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow10.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow11.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow12.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextviewEx1.setTextSize(18);
                    TextviewEx3.setTextSize(18);
                    TextviewEx4.setTextSize(18);
                    TextviewEx5.setTextSize(18);
                    TextviewEx6.setTextSize(18);
                    TextviewEx7.setTextSize(18);
                    TextviewEx8.setTextSize(18);
                    TextviewEx9.setTextSize(18);
                    TextviewEx10.setTextSize(18);
                    TextviewEx11.setTextSize(18);
                    TextviewEx12.setTextSize(18);
                }
                else if(FBindex==3){

                    exRow3.setBackgroundColor(Color.parseColor("#ECF9FD"));
                    TextviewEx3.setTextSize(21);
                    exRow1.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow2.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow4.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow5.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow6.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow7.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow8.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow9.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow10.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow11.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow12.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextviewEx1.setTextSize(18);
                    TextviewEx2.setTextSize(18);
                    TextviewEx4.setTextSize(18);
                    TextviewEx5.setTextSize(18);
                    TextviewEx6.setTextSize(18);
                    TextviewEx7.setTextSize(18);
                    TextviewEx8.setTextSize(18);
                    TextviewEx9.setTextSize(18);
                    TextviewEx10.setTextSize(18);
                    TextviewEx11.setTextSize(18);
                    TextviewEx12.setTextSize(18);
                }
                else if(FBindex==4){

                    exRow4.setBackgroundColor(Color.parseColor("#ECF9FD"));
                    TextviewEx4.setTextSize(21);
                    exRow1.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow2.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow3.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow5.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow6.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow7.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow8.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow9.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow10.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow11.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow12.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextviewEx1.setTextSize(18);
                    TextviewEx2.setTextSize(18);
                    TextviewEx3.setTextSize(18);
                    TextviewEx5.setTextSize(18);
                    TextviewEx6.setTextSize(18);
                    TextviewEx7.setTextSize(18);
                    TextviewEx8.setTextSize(18);
                    TextviewEx9.setTextSize(18);
                    TextviewEx10.setTextSize(18);
                    TextviewEx11.setTextSize(18);
                    TextviewEx12.setTextSize(18);
                }
                else if(FBindex==5){

                    exRow5.setBackgroundColor(Color.parseColor("#ECF9FD"));
                    TextviewEx5.setTextSize(21);
                    exRow1.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow2.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow3.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow4.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow6.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow7.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow8.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow9.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow10.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow11.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow12.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextviewEx1.setTextSize(18);
                    TextviewEx2.setTextSize(18);
                    TextviewEx3.setTextSize(18);
                    TextviewEx4.setTextSize(18);
                    TextviewEx6.setTextSize(18);
                    TextviewEx7.setTextSize(18);
                    TextviewEx8.setTextSize(18);
                    TextviewEx9.setTextSize(18);
                    TextviewEx10.setTextSize(18);
                    TextviewEx11.setTextSize(18);
                    TextviewEx12.setTextSize(18);
                }
                else if(FBindex==6){

                    exRow6.setBackgroundColor(Color.parseColor("#ECF9FD"));
                    TextviewEx6.setTextSize(21);
                    exRow1.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow2.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow3.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow4.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow5.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow7.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow8.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow9.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow10.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow11.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow12.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextviewEx1.setTextSize(18);
                    TextviewEx2.setTextSize(18);
                    TextviewEx3.setTextSize(18);
                    TextviewEx4.setTextSize(18);
                    TextviewEx5.setTextSize(18);
                    TextviewEx7.setTextSize(18);
                    TextviewEx8.setTextSize(18);
                    TextviewEx9.setTextSize(18);
                    TextviewEx10.setTextSize(18);
                    TextviewEx11.setTextSize(18);
                    TextviewEx12.setTextSize(18);
                }
                else if(FBindex==7){

                    exRow7.setBackgroundColor(Color.parseColor("#ECF9FD"));
                    TextviewEx7.setTextSize(21);
                    exRow1.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow2.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow3.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow4.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow5.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow6.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow8.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow9.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow10.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow11.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow12.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextviewEx1.setTextSize(18);
                    TextviewEx2.setTextSize(18);
                    TextviewEx3.setTextSize(18);
                    TextviewEx4.setTextSize(18);
                    TextviewEx5.setTextSize(18);
                    TextviewEx6.setTextSize(18);
                    TextviewEx8.setTextSize(18);
                    TextviewEx9.setTextSize(18);
                    TextviewEx10.setTextSize(18);
                    TextviewEx11.setTextSize(18);
                    TextviewEx12.setTextSize(18);
                }
                else if(FBindex==8){

                    exRow8.setBackgroundColor(Color.parseColor("#ECF9FD"));
                    TextviewEx8.setTextSize(21);
                    exRow1.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow2.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow3.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow4.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow5.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow6.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow7.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow9.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow10.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow11.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow12.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextviewEx1.setTextSize(18);
                    TextviewEx2.setTextSize(18);
                    TextviewEx3.setTextSize(18);
                    TextviewEx4.setTextSize(18);
                    TextviewEx5.setTextSize(18);
                    TextviewEx6.setTextSize(18);
                    TextviewEx7.setTextSize(18);
                    TextviewEx9.setTextSize(18);
                    TextviewEx10.setTextSize(18);
                    TextviewEx11.setTextSize(18);
                    TextviewEx12.setTextSize(18);
                }
                else if(FBindex==9){

                    exRow9.setBackgroundColor(Color.parseColor("#ECF9FD"));
                    TextviewEx9.setTextSize(21);
                    exRow1.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow2.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow3.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow4.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow5.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow6.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow7.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow8.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow10.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow11.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow12.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextviewEx1.setTextSize(18);
                    TextviewEx2.setTextSize(18);
                    TextviewEx3.setTextSize(18);
                    TextviewEx4.setTextSize(18);
                    TextviewEx5.setTextSize(18);
                    TextviewEx6.setTextSize(18);
                    TextviewEx7.setTextSize(18);
                    TextviewEx8.setTextSize(18);
                    TextviewEx10.setTextSize(18);
                    TextviewEx11.setTextSize(18);
                    TextviewEx12.setTextSize(18);
                }
                else if(FBindex==10){

                    exRow10.setBackgroundColor(Color.parseColor("#ECF9FD"));
                    TextviewEx10.setTextSize(21);
                    exRow1.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow2.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow3.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow4.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow5.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow6.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow7.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow8.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow9.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow11.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow12.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextviewEx1.setTextSize(18);
                    TextviewEx2.setTextSize(18);
                    TextviewEx3.setTextSize(18);
                    TextviewEx4.setTextSize(18);
                    TextviewEx5.setTextSize(18);
                    TextviewEx6.setTextSize(18);
                    TextviewEx7.setTextSize(18);
                    TextviewEx8.setTextSize(18);
                    TextviewEx9.setTextSize(18);
                    TextviewEx11.setTextSize(18);
                    TextviewEx12.setTextSize(18);
                }
                else if(FBindex==11){

                    exRow11.setBackgroundColor(Color.parseColor("#ECF9FD"));
                    TextviewEx11.setTextSize(21);
                    exRow1.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow2.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow3.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow4.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow5.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow6.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow7.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow8.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow9.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow10.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow12.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextviewEx1.setTextSize(18);
                    TextviewEx2.setTextSize(18);
                    TextviewEx3.setTextSize(18);
                    TextviewEx4.setTextSize(18);
                    TextviewEx5.setTextSize(18);
                    TextviewEx6.setTextSize(18);
                    TextviewEx7.setTextSize(18);
                    TextviewEx8.setTextSize(18);
                    TextviewEx9.setTextSize(18);
                    TextviewEx10.setTextSize(18);
                    TextviewEx12.setTextSize(18);
                }
                else if(FBindex==12){

                    exRow12.setBackgroundColor(Color.parseColor("#ECF9FD"));
                    TextviewEx12.setTextSize(21);
                    exRow1.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow2.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow3.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow4.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow5.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow6.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow7.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow8.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow9.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow10.setBackgroundColor(Color.parseColor("#ffffff"));
                    exRow11.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextviewEx1.setTextSize(18);
                    TextviewEx2.setTextSize(18);
                    TextviewEx3.setTextSize(18);
                    TextviewEx4.setTextSize(18);
                    TextviewEx5.setTextSize(18);
                    TextviewEx6.setTextSize(18);
                    TextviewEx7.setTextSize(18);
                    TextviewEx8.setTextSize(18);
                    TextviewEx9.setTextSize(18);
                    TextviewEx10.setTextSize(18);
                    TextviewEx11.setTextSize(18);

                }


            }
        });

    }
}