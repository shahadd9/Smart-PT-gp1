package com.example.smartpt;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.smartpt.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity {
    private TextView textWelcome;
    private Button startbtn;
    private ImageView imageInHome;
//    private TextView textSmartPT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        textWelcome = findViewById(R.id.welcome);
        startbtn = findViewById(R.id.getStarted);
        imageInHome = findViewById(R.id.homeImage);
//        textSmartPT = findViewById(R.id.aboutSmartPT);

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Name.class);
                startActivity(intent);
            }


        });

    }
}