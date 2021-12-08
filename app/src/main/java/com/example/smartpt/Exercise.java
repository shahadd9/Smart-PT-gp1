package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.MenuItem;
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
    TextView exName, target,general, sets, reps, rest, equipment,duration, execution, tips,start, mechanism, force;
    private FirebaseFirestore db;
    private String userIp;
    private double tPlace;
    private int tP;
    private String equ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_nav);
//        bottomNavigationView.setSelectedItemId(R.id.home);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(),PlanView.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.profile:
//                        startActivity(new Intent(getApplicationContext(),updateProfile.class));
//                        overridePendingTransition(0,0);
//                        return true;
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
        general=(TextView) findViewById(R.id.general);
        mechanism=(TextView) findViewById(R.id.mchanism);
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


                name=getIntent().getStringExtra("name");
                exName.setText(name);



//                targetMuscle=value.getString("target");
//                target.setText(targetMuscle);

                generalMuscle=getIntent().getStringExtra("muscle");
                general.setText(generalMuscle);
//
                forc=getIntent().getStringExtra("force");
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
                rest.setText(re+"");

                durationDis=value.getString("duration");
                duration.setText(durationDis);

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


    }
}