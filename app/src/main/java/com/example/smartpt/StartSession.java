package com.example.smartpt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class StartSession extends AppCompatActivity {
    Timer timer;
    private TimerTask timerTask;
    private Double time;
    private FirebaseFirestore db;
    private int  FBindex ;
    private int week;
    private Double FBindexD,weekD;
    private int rest,count;
    private String userIp;
    private String restText , SessionNo, level, currDay,nextEx,weekSt;
    int countDown;
    private TextView counter,counterMessage,txt1,txt2,timertxt;
    private MediaPlayer startAudio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_session);
        timer=new Timer();
        String audioUrl = "https://od.lk/s/NzVfMzI5OTExNzNf/start.mp3";
        startAudio = new MediaPlayer();

//        startAudio.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//
//        try {
//            startAudio.setDataSource(audioUrl);
//            // below line is use to prepare
//            // and start our media player.
//            startAudio.prepare();
//            startAudio.start();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        weekD=-1;
        week= getIntent().getIntExtra("week",0);
        counter = findViewById(R.id.timer);
        counterMessage=findViewById(R.id.counterMessage);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        timertxt=(TextView)findViewById(R.id.timertxt);

        time = getIntent().getDoubleExtra("duration",-1);
        rest = getIntent().getIntExtra("rest",0);
        restText=getIntent().getStringExtra("restText");
        SessionNo=getIntent().getStringExtra("SessionNo");
        level =getIntent().getStringExtra("level");
        currDay=getIntent().getStringExtra("currDay");
        count=getIntent().getIntExtra("counter",0);
        nextEx=getIntent().getStringExtra("nextEx");

        db = FirebaseFirestore.getInstance();

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

//        whatWeek();

        DocumentReference d = db.collection("Progress").document(userIp).collection("index").document("weeks").collection("week"+week).document("day"+currDay);
        d.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


                FBindexD= value.getDouble("exerciseIndex");
                FBindex=(int)Math.round(FBindexD);
//                FBindex=5;


            }
        });

//        startTimer();

        if(rest ==0){
            txt1.setVisibility(View.INVISIBLE);
            txt2.setVisibility(View.INVISIBLE);
            countDown=5000;
            restText="Starts in:";
            startAudio.setAudioStreamType(AudioManager.STREAM_MUSIC);


            try {
                startAudio.setDataSource(audioUrl);
                // below line is use to prepare
                // and start our media player.
                startAudio.prepare();
                startAudio.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(rest ==-1){
            restText="";
            countDown=5000;
            txt1.setVisibility(View.VISIBLE);
            txt2.setVisibility(View.VISIBLE);
            txt2.setText(nextEx);
            time=time+5;
        }
        else {
            txt1.setVisibility(View.VISIBLE);
            txt2.setVisibility(View.VISIBLE);
            txt2.setText(nextEx);
            countDown=rest*1000;
            time=time+rest;

            //////////////////////////
        }

        counterMessage.setText(restText); //////////////////////////////////////////////

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
        intent.putExtra("counter",FBindex);
        intent.putExtra("week",week);
        intent.putExtra("duration",time);
        startActivity(intent);

    }

//    private void whatWeek() {
//
//       DocumentReference documentReference = db.collection("Progress").document(userIp).collection("index").document("weeks");
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//
//
//                weekD= value.getDouble("week");
//                week=(int)weekD;
//
//            }
//        });
//    }
//
//    private void startTimer(){
//
//        timerTask=new TimerTask() {
//            @Override
//            public void run() {
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        time++;
//                        timertxt.setText(getTimertxt());
//                    }
//                });
//
//
//
//            }
//        };
//        timer.scheduleAtFixedRate(timerTask,0,1000);
//    }
//
//    private String getTimertxt() {
//
//        int rounded = (int) Math.round(time);
//        int second=((rounded % 86400)%3600)%60;
//        int min=((rounded % 86400)%3600)/60;
//        int hours=((rounded % 86400)/3600);
//
//        return String.format("%02d",hours)+" : "+String.format("%02d",min)+" : "+String.format("%02d",second);
//    }
}