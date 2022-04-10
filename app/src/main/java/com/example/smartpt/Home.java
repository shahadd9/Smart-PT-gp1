package com.example.smartpt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//import com.example.smartpt.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity {
    private TextView textWelcome;
    private Button startbtn;
    private ImageView imageInHome;
    private FirebaseFirestore db;
    private String userIp;
    private String todaytDate;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
//    private TextView textSmartPT;
public final static String shared="sharedPrefs";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        db = FirebaseFirestore.getInstance();
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        setContentView(R.layout.activity_home);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        todaytDate = dateFormat.format(calendar.getTime());

        textWelcome = findViewById(R.id.welcome);
        startbtn = findViewById(R.id.getStarted);
        imageInHome = findViewById(R.id.homeImage);
        SharedPreferences sharedPreferences = getSharedPreferences(shared,MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt("sessionDone",0);
        editor.putString("duration","0.0");
        editor.apply();

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Map<String,Object> user = new HashMap<>();
//                Map<String,Object> week = new HashMap<>();
//
//                user.put("exerciseIndex",0);
//                user.put("duration",0.0);
//                week.put("week",1);
//                week.put("isItOne","1"); // if it is 1 then dont generate next week
//                week.put("startDateWeek1",todaytDate);
//
//
//                db.collection("Progress").document(userIp).collection("index").document("weeks").set(week).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                    }
//                });

//                db.collection("Progress").document(userIp).collection("index").document("weeks").collection("week1").document("day1").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            //Toast.makeText(Goal.this,"successful",Toast.LENGTH_SHORT);
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //Toast.makeText(Goal.this,"Faild",Toast.LENGTH_SHORT);
//
//                    }
//                });
//
//                db.collection("Progress").document(userIp).collection("index").document("weeks").collection("week1").document("day2").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            //Toast.makeText(Goal.this,"successful",Toast.LENGTH_SHORT);
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //Toast.makeText(Goal.this,"Faild",Toast.LENGTH_SHORT);
//
//                    }
//                });
//
//                db.collection("Progress").document(userIp).collection("index").document("weeks").collection("week1").document("day3").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            //Toast.makeText(Goal.this,"successful",Toast.LENGTH_SHORT);
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //Toast.makeText(Goal.this,"Faild",Toast.LENGTH_SHORT);
//
//                    }
//                });
//
//                db.collection("Progress").document(userIp).collection("index").document("weeks").collection("week1").document("day4").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            //Toast.makeText(Goal.this,"successful",Toast.LENGTH_SHORT);
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //Toast.makeText(Goal.this,"Faild",Toast.LENGTH_SHORT);
//
//                    }
//                });
//
//                db.collection("Progress").document(userIp).collection("index").document("weeks").collection("week1").document("day5").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            //Toast.makeText(Goal.this,"successful",Toast.LENGTH_SHORT);
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //Toast.makeText(Goal.this,"Faild",Toast.LENGTH_SHORT);
//
//                    }
//                });

                Intent intent = new Intent(Home.this, Name.class);
                startActivity(intent);
            }


        });

    }
}