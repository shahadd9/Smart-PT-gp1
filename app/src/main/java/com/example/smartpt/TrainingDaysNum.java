package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrainingDaysNum extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth uAuth;
    private String id;
//    private String userIp;
    private RadioButton r1;
    private RadioButton r2;
    private RadioButton r3;
    private RadioButton r4;
    private String tDaysNo;
    private Button time;
    private String tDaysString;

    private static ArrayList<String> tDays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_days_num);

        db = FirebaseFirestore.getInstance();
        //to get user email
        uAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = uAuth.getCurrentUser();
        id = curUser.getEmail();
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        tDays= new ArrayList<>();
        r1= findViewById(R.id.r1);
        r2= findViewById(R.id.r2);
        r3= findViewById(R.id.r3);
        r4= findViewById(R.id.r4);
        time=findViewById(R.id.time);
       //as default
        tDays.add("Sun");
        tDays.add("Tue");
        tDaysNo=2+"";

        tDaysString="";

        time.setOnClickListener(new View.OnClickListener() {
            @Override



            public void onClick(View v) {
                tDays=TrainingDaysNum.gettDays();
                for(int i = 0; i<tDays.size();i++){

                    tDaysString=tDaysString+" "+ tDays.get(i);
                }
                Map<String,Object> user = new HashMap<>();
                user.put("TrainingdaysNum",tDaysNo);
                user.put("trainingDays",tDaysString);
                db.collection("userProfile").document(id).update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                startActivity(new Intent(TrainingDaysNum.this, TrainingPlace.class));
            }
        });
    }

    public void rntn(View v){
        if(r1.isChecked()){
            tDaysNo=2+"";
            tDays.clear();
            tDays.add("Sun");
            tDays.add("Tue");
        }
        else if(r2.isChecked()){
            tDaysNo=3+"";
            tDays.clear();
            tDays.add("Sun");
            tDays.add("Tue");
            tDays.add("Thu");
        }
        else if(r3.isChecked()){
            tDaysNo=4+"";
            tDays.clear();
            tDays.add("Sun");
            tDays.add("Tue");
            tDays.add("Thu");
            tDays.add("Sat");

        }
        else {
            tDaysNo=5+"";
            tDays.clear();
            tDays.add("Sun");
            tDays.add("Mon");
            tDays.add("Tue");
            tDays.add("Thu");
            tDays.add("Fri");

        }

    }
    public static  ArrayList<String> gettDays(){
        return tDays;
    }

}
