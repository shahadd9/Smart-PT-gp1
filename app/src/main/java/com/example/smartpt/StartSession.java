package com.example.smartpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

public class StartSession extends AppCompatActivity {

    private int rest;
    private String restText;
    int countDown;
    private TextView counter,counterMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_session);

        counter = findViewById(R.id.timer);
        counterMessage=findViewById(R.id.counterMessage);

        rest = getIntent().getIntExtra("rest",0);
        restText=getIntent().getStringExtra("restText");

        if(rest ==0){

            countDown=5000;
            restText="Starts in:";
        }else {

            countDown=rest*1000;
        }

        counterMessage.setText(restText);

        new CountDownTimer(countDown + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counter.setText(String.valueOf(millisUntilFinished/1000));

            }

            @Override
            public void onFinish() {
                counter.setText("0");
                startSession();
/*
                intent.start
                Toast.makeText(getApplicationContext(),"")
*/

            }
        }.start();



    }

    public void startSession(){
        Intent intent= new Intent(this, SessionView.class);
        startActivity(intent);

    }
}