package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Exercise extends AppCompatActivity {

    String name, targetMuscle,generalMuscle, execute, tip,equip, mecha, forc, str;
    double set , rep, Res;
    private String durationDis;
    int s, r, re,d;
    TextView alt, exName, target,general, sets, reps, rest, equipment,duration, execution, tips,start, mechanism, force;
    private FirebaseFirestore db;
    private String userIp;
    private double tPlace;
    private int tP;
    private String equ;
    private String level;
    private String SessionNo;
    private String currDay;
    private Button okBtn;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionNo = getIntent().getStringExtra("SessionNo");
        level = getIntent().getStringExtra("level");
        name=getIntent().getStringExtra("name");
        generalMuscle=getIntent().getStringExtra("muscle");
        forc=getIntent().getStringExtra("force");
        index=getIntent().getIntExtra("index",-1);
        currDay=getIntent().getStringExtra("currDay");
        alt=(TextView)findViewById(R.id.alternative);




//        okBtn= (Button) findViewById(R.id.okBtn);

//
        setContentView(R.layout.activity_exercise);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_nav);
//        bottomNavigationView.setSelectedItemId(R.id.home);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.home:
//                        Intent i = new Intent(Exercise.this, PlanView.class);
//
//                        if(SessionNo.equals("2")){
//                            i.putExtra("sessionNo",SessionNo);
//
//                            i.putExtra("level",level);
//                            startActivity(i);
//                            overridePendingTransition(0, 0);
//                            finish();
//                            return true;
//                        }
//                        else if(SessionNo.equals("3")){
//                            i.putExtra("sessionNo",SessionNo);
//                            i.putExtra("level",level);
//                            startActivity(i);
//                            overridePendingTransition(0, 0);
//                            finish();
//                        }
//                        else if(SessionNo.equals("4")){
//                            i.putExtra("sessionNo",SessionNo);
//                            i.putExtra("level",level);
//                            startActivity(i);
//                            overridePendingTransition(0, 0);
//                            finish();
//                        }
//                        else if(SessionNo.equals("5")) {
//                            i.putExtra("sessionNo", SessionNo);
//                            i.putExtra("level", level);
//                            startActivity(i);
//                            overridePendingTransition(0, 0);
//                            finish();
//                        }
//
//                    case R.id.profile:
//                        Intent intent = new Intent(Exercise.this, updateProfile.class);
//
//                        if (SessionNo.equals("2")) {
//                            intent.putExtra("sessionNo", SessionNo);
//
//                            intent.putExtra("level", level);
//                            startActivity(intent);
//                            overridePendingTransition(0, 0);
//                            finish();
//                            return true;
//                        } else if (SessionNo.equals("3")) {
//                            intent.putExtra("sessionNo", SessionNo);
//                            intent.putExtra("level", level);
//                            startActivity(intent);
//                            overridePendingTransition(0, 0);
//                            finish();
//                        } else if (SessionNo.equals("4")) {
//                            intent.putExtra("sessionNo", SessionNo);
//                            intent.putExtra("level", level);
//                            startActivity(intent);
//                            overridePendingTransition(0, 0);
//                            finish();
//                        } else if (SessionNo.equals("5")) {
//                            intent.putExtra("sessionNo", SessionNo);
//                            intent.putExtra("level", level);
//                            startActivity(intent);
//                            overridePendingTransition(0, 0);
//                            finish();
//                        }
////
////                    case R.id.progress:
////                        startActivity(new Intent(getApplicationContext(),progress.class));
////                        overridePendingTransition(0,0);
////                        return true;
//                }
//                return false;
//            }
//        });

        exName=(TextView) findViewById(R.id.exName);
        sets=(TextView) findViewById(R.id.sets); //Done
        reps=(TextView) findViewById(R.id.reps); //Done
        rest=(TextView) findViewById(R.id.rest); //Done
        duration=(TextView) findViewById(R.id.duration);
//        equipment=(TextView) findViewById(R.id.equipment);
//        target=(TextView) findViewById(R.id.target);
        general=(TextView) findViewById(R.id.mchanism);
//        mechanism=(TextView) findViewById(R.id.mchanism);
        force=(TextView) findViewById(R.id.force);
//        execution=(TextView) findViewById(R.id.execution);
//        tips=(TextView) findViewById(R.id.tips);
//        start=(TextView) findViewById(R.id.startPos);


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        //get data from database
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("userProfile").document(userIp).collection("WorkoutPlan").document(userIp);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


                exName.setText(name);



//                targetMuscle=value.getString("target");
//                target.setText(targetMuscle);

                general.setText(generalMuscle);
//
                force.setText(forc);
//
//                mecha=value.getString("mechanism");
//                mechanism.setText(mecha);

                set=value.getDouble("sets");
                s=(int)set;
                sets.setText(""+s+"");

                rep=value.getDouble("reps");
                r=(int)rep;
                reps.setText(r+"");

                Res=value.getDouble("rest");
                re=(int)Res;
                rest.setText(re+" sec");

                durationDis=value.getString("duration");
                duration.setText(durationDis+" min");

//                tPlace= value.getDouble("trainingPlace");
//                tP=(int)tPlace;
//                if(tP==0) {
//                    equ = value.getString("equpmtList");
//                    if(equip=="0"){
//                        equipment.setText("No Equipment needed for this exercise");
//                    }else {
//                        equipment.setText(equip);
//                    }
//                }

//
//                execute=value.getString("execution");
//                execution.setText(execute);
//
//                tip=value.getString("tips");
//                tips.setText(tip);
//
//                str=value.getString("starting position");
//                start.setText(str);



            }
        });

//        okBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(Exercise.this, PlanView.class);
//                startActivity(i);
////                        if(SessionNo.equals("2")){
////                            i.putExtra("SessionNo",SessionNo);
////
////                            i.putExtra("level",level);
////                            startActivity(i);
////                            finish();
////                        }
////                        else if(SessionNo.equals("3")){
////                            i.putExtra("SessionNo",SessionNo);
////                            i.putExtra("level",level);
////                            startActivity(i);
////                            finish();
////                        }
////                        else if(SessionNo.equals("4")){
////                            i.putExtra("SessionNo",SessionNo);
////                            i.putExtra("level",level);
////                            startActivity(i);
////                            finish();
////                        }
////                        else if(SessionNo.equals("5")) {
////                            i.putExtra("SessionNo", SessionNo);
////                            i.putExtra("level", level);
////                            startActivity(i);
////                            finish();
////                        }
//
//            }
//        });
//
    }

    public void goToAlt(View view) {
                Intent i = new Intent(Exercise.this, Alternative.class);
                i.putExtra("name",name);
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("index",index);
                i.putExtra("level",level);
                i.putExtra("generalMuscle",generalMuscle);
                i.putExtra("currDay",currDay);
                startActivity(i);
    }
}