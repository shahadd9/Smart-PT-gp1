package com.example.smartpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class StartSession extends AppCompatActivity {

    private int rest,count;
    private String restText , SessionNo, level, currDay,nextEx;
    int countDown;
    private TextView counter,counterMessage,txt1,txt2;
    private long duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_session);

        counter = findViewById(R.id.timer);
        counterMessage=findViewById(R.id.counterMessage);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);

        rest = getIntent().getIntExtra("rest",0);
        restText=getIntent().getStringExtra("restText");
        SessionNo=getIntent().getStringExtra("SessionNo");
        level =getIntent().getStringExtra("level");
        currDay=getIntent().getStringExtra("currDay");
        count=getIntent().getIntExtra("counter",0);
        nextEx=getIntent().getStringExtra("nextEx");

        if(rest ==0){
            txt1.setVisibility(View.INVISIBLE);
            txt2.setVisibility(View.INVISIBLE);
            countDown=5000;
            restText="Starts in:";

        }
        else if(rest ==-1){
            restText="";
            countDown=5000;
            txt1.setVisibility(View.VISIBLE);
            txt2.setVisibility(View.VISIBLE);
            txt2.setText(nextEx);
        }
        else {
            txt1.setVisibility(View.VISIBLE);
            txt2.setVisibility(View.VISIBLE);
            txt2.setText(nextEx);
            countDown=rest*1000;
        }

        counterMessage.setText(restText);

        new CountDownTimer(countDown + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String eDuration = String.format(Locale.ENGLISH,"%02d : %02d"
                , TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                ,TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                counter.setText(eDuration);
//                counter.setText(String.valueOf(millisUntilFinished/1000));

            }

            @Override
            public void onFinish() {
                counter.setText("00 : 00");
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
        intent.putExtra("SessionNo",SessionNo);
        intent.putExtra("level",level);
        intent.putExtra("currDay",currDay);
        intent.putExtra("counter",count);
        startActivity(intent);

    }
}