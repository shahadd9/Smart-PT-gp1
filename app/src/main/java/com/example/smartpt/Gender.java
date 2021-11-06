package com.example.smartpt;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.HashMap;
import java.util.Map;
//
//import com.example.smartpt.databinding.ActivityGenderBinding;

public class Gender extends AppCompatActivity {

//    private FloatingActionButton btnBackToName;
    private RadioButton female;
    private RadioButton male;
    private RadioGroup gender;
//    private Button btnFemale;
//    private Button btnMale;
    private TextView Errormsg;
    private Button btnToBirthdate;
    private int count=0;
    private String userGender = "" ;
    boolean isGenderChecked= false;
    private TextView qGender;
    private TextView errGender;
    public static int gen=0;
    private FirebaseFirestore db;
    private String userIp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
//        btnBackToName= findViewById(R.id.backToName);
        db = FirebaseFirestore.getInstance();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        female= findViewById(R.id.female);
        male= findViewById(R.id.male);
        btnToBirthdate= findViewById(R.id.toBirthdate);
        gender = findViewById(R.id.gender);
        qGender = findViewById((R.id.qGender));
        errGender = findViewById(R.id.errGender);

//        btnBackToName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent= new Intent(Gender.this, Name.class);
//                startActivity(intent);
//
//            }
//
//
//        });
        btnToBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isGenderChecked = checkGender();
                if(isGenderChecked) {
                    Log.d("myTag", "we are here");
                    if(female.isChecked()){
                        gen=1;
                    }
                    else{
                        gen=0;
                    }
                    Map<String,Object> user = new HashMap<>();
                    user.put("gender",gen);
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
                    Intent intent = new Intent(Gender.this, Birthdate.class);
                    startActivity(intent);
                }

            }


        });


//        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.gender);
        gender.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                btnToBirthdate.setBackgroundColor(Color.parseColor("#48D0FE"));
                userGender = (String) radioButton.getText();
                Log.d("myTag", userGender);

            }
        });


    }



    private boolean checkGender() {
        if (gender.getCheckedRadioButtonId() == -1) {
            btnToBirthdate.setBackgroundColor(Color.parseColor("#D8D4D4"));
            errGender.setError("Please select an option!");
            return false;
        }
        // after  validation return true.

        errGender.setError(null);
        return true;
    }


}