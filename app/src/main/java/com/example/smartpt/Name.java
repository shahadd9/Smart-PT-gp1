package com.example.smartpt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.text.Spanned;
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

//import com.example.smartpt.databinding.ActivityNameBinding;

public class Name extends AppCompatActivity {

    private EditText eName;
    private TextView qName;
    private Button btnToGender;
//    Context context = getApplicationContext();
    private int count = 0;
//    private FloatingActionButton btnBackToHome;
    private String name = "";
    boolean isNameEntered = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        eName = findViewById(R.id.traineeName);
        qName = findViewById(R.id.qName);
        btnToGender = findViewById(R.id.toGender);
//        btnBackToHome=findViewById(R.id.backToHome);


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

//        btnBackToHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(Name.this, Home.class);
//                startActivity(intent);
//
//            }
//
//
//        });
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
