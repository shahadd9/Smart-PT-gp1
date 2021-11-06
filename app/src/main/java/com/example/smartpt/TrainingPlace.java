package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrainingPlace extends AppCompatActivity {
    private Button home;
    private Button gym;
    public static int place;  //0 for home 1 for gym
//    private String level;
//    private ArrayList<String> goal;
//    private ArrayList<String> tDays;
    private FirebaseFirestore db;
    private String userIp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_place);
        place=0;
        home = findViewById(R.id.homeBtn);
        gym= findViewById(R.id.gymBtn);
//        level= getIntent().getStringExtra("level");
//        goal=getIntent().getStringArrayListExtra("goal");
//        tDays=getIntent().getStringArrayListExtra("tDays");

        db = FirebaseFirestore.getInstance();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                place=0;
                goEqupment2();
                Map<String,Object> user = new HashMap<>();
                user.put("trainingPlace",place);
                db.collection("userProfile").document(userIp).update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        });

        gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                place=1;
                Map<String,Object> user = new HashMap<>();
                user.put("trainingPlace",place);
                db.collection("userProfile").document(userIp).update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                goEqupment2();
            }
        });
    }

    public void goEqupment2(){
        Intent intent= new Intent(this, Equipment.class);
//        intent.putExtra("tDays",tDays);
//        intent.putExtra("goal",goal);
//        intent.putExtra("level",level);
//        intent.putExtra("place",place);
        startActivity(intent);
    }
}