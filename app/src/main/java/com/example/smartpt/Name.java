package com.example.smartpt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//import com.example.smartpt.databinding.ActivityNameBinding;

public class Name extends AppCompatActivity {

    private EditText eName;
    private TextView qName;
    private Button btnToGender;
//    Context context = getApplicationContext();
    private int count = 0;
//    private FloatingActionButton btnBackToHome;
    public static String name = "";
    private boolean isNameEntered = false;
    private FirebaseFirestore db;
    private FirebaseAuth uAuth;
    private String id;
//    private String userIp;

    public final static String shared="sharedPrefs";
    private String todaytDate;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        db = FirebaseFirestore.getInstance();
        //to get user email
        uAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = uAuth.getCurrentUser();
        id = curUser.getEmail();
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        eName = findViewById(R.id.traineeName);
        qName = findViewById(R.id.qName);
        btnToGender = findViewById(R.id.toGender);

        db = FirebaseFirestore.getInstance();
//        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());


        eName.setFilters(new InputFilter[]{new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals("")) {
                    return source;
                }
                if (source.toString().matches("[a-zA-Z ]+")) {
                    btnToGender.setBackgroundColor(Color.parseColor("#48D0FE"));
                    return source;
                }
                else {
                    eName.setError("Only Alphabets characters are accepted! ");
                    btnToGender.setBackgroundColor(Color.parseColor("#D8D4D4"));
                }
                return "";
            }
        }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(shared,MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt("sessionDone",0);
        editor.putString("duration","0.0");
        editor.apply();

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        todaytDate = dateFormat.format(calendar.getTime());

        btnToGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNameEntered = checkName();
                activate();

                Log.d("myTag", "we are here");
                if(isNameEntered) {
                    name =  eName.getText().toString();
                    count++;
                    activate();
                    Log.d("myName", name);
                    Map<String,Object> user1 = new HashMap<>();
                    Map<String,Object> user = new HashMap<>();
                    Map<String,Object> week = new HashMap<>();
                    user1.put("name",name);
                    user.put("exerciseIndex",0);
                    user.put("duration",0.0);
                    week.put("week",1);
                    week.put("isItOne","1"); // if it is 1 then dont generate next week
                    week.put("startDateWeek1",todaytDate);


                    db.collection("Progress").document(id).collection("index").document("weeks").set(week).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                    db.collection("Progress").document(id).collection("index").document("weeks").collection("week1").document("day1").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                    db.collection("Progress").document(id).collection("index").document("weeks").collection("week1").document("day2").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                    db.collection("Progress").document(id).collection("index").document("weeks").collection("week1").document("day3").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                    db.collection("Progress").document(id).collection("index").document("weeks").collection("week1").document("day4").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                    db.collection("Progress").document(id).collection("index").document("weeks").collection("week1").document("day5").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                  ///////////////////////////////////////////////////////////////////////////////////////
                    db.collection("userProfile").document(id).set(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                    ///////////////////////////////////////////////////////////////////////////////////////////////////////

                    Intent intent = new Intent(Name.this, Gender.class);
                    startActivity(intent);
                }
                activate();

            }


        });
        activate();
    }
//    public void backToHome(View view){
//        Intent intent= new Intent(this, Home.class);
//        startActivity(intent);
//    }


    public void activate() {
//
//        if (count > 0) {
//
//            btnToGender.setBackgroundColor(Color.parseColor("#D8D4D4"));
//        } else {
//            btnToGender.setBackgroundColor(Color.parseColor("#48D0FE"));
//
//        }

    }

    private boolean checkName() {
        if (eName.length() == 0) {
            btnToGender.setBackgroundColor(Color.parseColor("#D8D4D4"));
            eName.setError("This field is required");
            return false;
        }
        // after  validation return true.
        return true;
    }


}
