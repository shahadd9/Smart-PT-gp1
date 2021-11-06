package com.example.smartpt;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.smartpt.databinding.ActivityGenderBinding;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
//        btnBackToName= findViewById(R.id.backToName);
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