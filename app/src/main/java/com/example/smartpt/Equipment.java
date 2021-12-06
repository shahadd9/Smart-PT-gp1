package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Equipment extends AppCompatActivity {
    private Button equBtn;
    private CheckBox dmbl;
    private CheckBox barbell;
    private CheckBox ball;
    private CheckBox bench;
    private CheckBox dip;
    private CheckBox cable;
    //    private  CheckBox btlR;
//    private CheckBox band;
    private FirebaseFirestore db;
    private String userIp;
    //    private TextView t ;
    public static ArrayList<String> equpmtList;
    private int count;
    //    private int place;  //0 for home 1 for gym
//    private String level;
//    private ArrayList<String> goal;
//    private ArrayList<String> tDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        db = FirebaseFirestore.getInstance();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        equBtn= findViewById(R.id.equBtn);
        dmbl=findViewById(R.id.dumbble);
        barbell=findViewById(R.id.barbell);
        ball=findViewById(R.id.ball);
        bench=findViewById(R.id.Bench);
        dip=findViewById(R.id.dip);
        cable=findViewById(R.id.cable);
//        btlR=findViewById(R.id.battleR);
//        band=findViewById(R.id.band);
        equpmtList=new ArrayList<>();
//        level= getIntent().getStringExtra("level");
//        goal=getIntent().getStringArrayListExtra("goal");
//        tDays=getIntent().getStringArrayListExtra("tDays");
//        place=getIntent().getIntExtra("place",0);


        //t= findViewById(R.id.textView2);

        count=0;
        dmbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dmbl.isChecked()){
                    equpmtList.add((String) dmbl.getText());
                    count++;
                }
                else {
                    count--;
                    equpmtList.remove((String) dmbl.getText());
                }
//                activate();
            }
        });
        barbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(barbell.isChecked()){
                    equpmtList.add((String) barbell.getText());
                    count++;
                }
                else {
                    count--;
                    equpmtList.remove((String) barbell.getText());
                }
//                activate();
            }
        });

        ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ball.isChecked()){
                    equpmtList.add((String) ball.getText());
                    count++;
                }
                else {
                    count--;
                    equpmtList.remove((String) ball.getText());
                }
//                activate();
            }
        });

        bench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bench.isChecked()){
                    equpmtList.add((String) bench.getText());
                    count++;
                }
                else {
                    count--;
                    equpmtList.remove((String) bench.getText());
                }
//                activate();
            }
        });
        dip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dip.isChecked()){
                    equpmtList.add((String) dip.getText());
                    count++;
                }
                else {
                    count--;
                    equpmtList.remove((String) dip.getText());
                }
//                activate();
            }
        });

        cable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cable.isChecked()){
                    equpmtList.add((String) cable.getText());
                    count++;
                }
                else {
                    count--;
                    equpmtList.remove((String) cable.getText());
                }
//                activate();
            }
        });

//        btlR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(btlR.isChecked()){
//                    equpmtList.add((String) btlR.getText());
//                    count++;
//                }
//                else {
//                    count--;
//                    equpmtList.remove((String) btlR.getText());
//                }
////                activate();
//            }
//        });

//        band.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(band.isChecked()){
//                    equpmtList.add((String) band.getText());
//                    count++;
//                }
//                else {
//                    count--;
//                    equpmtList.remove((String) band.getText());
//                }
////                activate();
//            }
//        });

        equBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean empty=false;
                //StringBuilder stringBuilder= new StringBuilder();

                // for(String a: result){
                if(equpmtList.isEmpty()){
                    empty=true;
                }
                // else {

                //stringBuilder.append(a).append("\n");
                //}
                //   }
                if(empty){
                    //show.setText(stringBuilder.toString());
                    //show.setText("you must choose one at least");
                    //show.setEnabled(false);
                    Map<String,Object> user = new HashMap<>();
                    user.put("equpmtList","0");
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
                    goNext();
                }
                else {

                    Map<String,Object> user = new HashMap<>();
                    user.put("equpmtList",equpmtList.toString());
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
                    goNext();
                }
            }

        });
    }
    public void goNext(){
        Intent intent= new Intent(this, TrainingTime.class);
//        intent.putExtra("tDays",tDays);
//        intent.putExtra("goal",goal);
//        intent.putExtra("level",level);
//        intent.putExtra("place",place);
//        intent.putExtra("equpmtList",equpmtList);
        startActivity(intent);
    }
//    public void activate(){
//
//        if(count>0){
//
//            equBtn.setBackgroundColor(Color.parseColor("#48D0FE"));
//            //t.setText(equpmtList.toString());
//
//        }
//        else{
//            equBtn.setBackgroundColor(Color.parseColor("#D8D4D4"));
//            //t.setText(equpmtList.toString());
//
//        }
//
//    }
}

