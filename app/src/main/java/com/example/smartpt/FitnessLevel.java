package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FitnessLevel extends AppCompatActivity {
    private Button pickDaysBtn;
//    private ArrayList<String> goal;
public static String level;
    private SeekBar seekBar;
    private TextView seekBarValue;
    private TextView tLvl;
    private FirebaseFirestore db;
    private FirebaseAuth uAuth;
    private String id;
//    private String userIp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_level);
        pickDaysBtn=findViewById(R.id.pickDaysBtn);
         seekBar = (SeekBar)findViewById(R.id.lvlSeekBar);
          seekBarValue = (TextView)findViewById(R.id.seekBarValue);
         tLvl= findViewById(R.id.lvl);
//        goal=getIntent().getStringArrayListExtra("goal");
        level="Intermediate";

        db = FirebaseFirestore.getInstance();
        //to get user email
        uAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = uAuth.getCurrentUser();
        id = curUser.getEmail();
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue.setText(String.valueOf(progress)+"/100");
                int lvl=progress;
                if(lvl<=33){
                    tLvl.setText("Beginner");
                    level="Beginner";
                }
                else if(lvl>33 && lvl<= 66){
                    tLvl.setText("Intermediate");
                    level="Intermediate";
                }
                else{
                    tLvl.setText("Professional");
                    level="Professional";

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
        pickDaysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> user = new HashMap<>();
                user.put("level",level);
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
                goTrainingDays();
            }


        });
    }
    public void goTrainingDays(){
        Intent intent= new Intent(this, TrainingDaysNum.class);
        intent.putExtra("level",level);
//        intent.putExtra("goal",goal);
        startActivity(intent);
    }


}