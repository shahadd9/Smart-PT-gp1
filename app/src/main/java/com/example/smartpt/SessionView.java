package com.example.smartpt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SessionView extends AppCompatActivity {

    VideoView v;
    String url="https://i.imgur.com/HOfLu88.mp4";
    ProgressDialog pd;
    private FirebaseFirestore db;
    private String userIp;
    private double set , Res;
    private int s, re, i,sIndex,counter;
    private TextView sets, instTxt,exerciseName,counterTxt,rest;
    private ImageView exist;
    private Button nextbtn,skipbtn,pausebtn;
    private String inst, SessionNo, level,currDay,day,nextExercise, exName, videoLink;
    String instArray[] = new String[5];
    String dayAr[];

    private int prog;
    private ProgressBar progress_bar;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_view);
        prog=0;
        dayAr=new String[50];
        exName="";
        counter=getIntent().getIntExtra("counter",0);
        progress_bar= (ProgressBar)findViewById(R.id.progress_bar);
        updteProgressBar();
        sets=(TextView) findViewById(R.id.sets); //Done
        exerciseName=(TextView) findViewById(R.id.exerciseName);
        counterTxt=(TextView) findViewById(R.id.counter);
        instTxt=(TextView) findViewById(R.id.instTxt);
        rest=(TextView) findViewById(R.id.rest);
        v= (VideoView)findViewById(R.id.video);
        nextbtn =(Button)findViewById(R.id.nextbtn);
        skipbtn =(Button)findViewById(R.id.skipbtn);
        pausebtn =(Button)findViewById(R.id.pausebtn);
        exist =(ImageView) findViewById(R.id.exist);
        builder= new AlertDialog.Builder(this);
        SessionNo=getIntent().getStringExtra("SessionNo");
        level =getIntent().getStringExtra("level");
        currDay=getIntent().getStringExtra("currDay");
        retrieveExerciseName();



        i=0;
        sIndex=0;
//        pd=new ProgressDialog(SessionView.this);
//        pd.setMessage("loading");
//        pd.show();
//        Uri uri = Uri.parse(retreiveVideo(exName));
//        v.setVideoURI(uri);
//        MediaController mediaController= new MediaController(this);
//        v.setMediaController(mediaController);
//        mediaController.setAnchorView(v);
//        v.start();
//        v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
////                pd.dismiss();
//            }
//        });
//        v.setVideoURI(uri);
//        v.setMediaController(new MediaController(this));
//
//        v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.setLooping(true);
//            }
//        });


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        //get data from database
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("userProfile").document(userIp).collection("WorkoutPlan").document(userIp);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {



                set=value.getDouble("sets");
                s=(int)set;
                sets.setText("0/"+s+"");


                Res=value.getDouble("rest");
                re=(int)Res;



            }
        });
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prog=0;
                updteProgressBar();
            nextExercise(-1);
            }
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nextbtn.getText().equals("Finish")){
                    endSession();
                }
                else if(i+1==s){
//                    sets.setText("done");
                    sets.setText(i+1+"/"+s);
                    prog+=(100/s);
                    prog=0;
                    updteProgressBar();
                    nextExercise(re);

                }
                else{
                    sets.setText(i+1+"/"+s);
                    i=i+1;
                    if(prog<=90){
                        prog+=(100/s);
                        updteProgressBar();
                        new CountDownTimer(10000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                counterTxt.setVisibility(View.VISIBLE);
                                rest.setVisibility(View.VISIBLE);
                                nextbtn.setVisibility(View.INVISIBLE);
                                skipbtn.setVisibility(View.INVISIBLE);
                                pausebtn.setVisibility(View.INVISIBLE);
                                counterTxt.setText(String.valueOf(millisUntilFinished/1000));

                            }

                            @Override
                            public void onFinish() {
                                counterTxt.setVisibility(View.INVISIBLE);
                                rest.setVisibility(View.INVISIBLE);
                                nextbtn.setVisibility(View.VISIBLE);
                                skipbtn.setVisibility(View.VISIBLE);
                                pausebtn.setVisibility(View.VISIBLE);



                            }
                        }.start();
                    }
                }
            }
        });

        exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setTitle("").setMessage("Are you sure you want to end the session?").setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        endSession();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                }).show();
            }
        });

//        retreiveInstructions(exName);
//        exerciseName.setText(exName);

    }
    public void updteProgressBar(){

        progress_bar.setProgress(prog);

    }
    public void nextExercise(int re){
        Intent intent= new Intent(this, StartSession.class);
        intent.putExtra("rest",re);
        intent.putExtra("restText","Rest");
        intent.putExtra("SessionNo",SessionNo);
        intent.putExtra("level",level);
        intent.putExtra("counter",++counter);
        intent.putExtra("currDay",currDay);
        intent.putExtra("nextEx",nextExercise);
        startActivity(intent);

    }
    public void endSession(){
        Intent intent= new Intent(this, PlanView.class);
        intent.putExtra("SessionNo",SessionNo);
        intent.putExtra("level",level);
        startActivity(intent);

    }

   public void retreiveInstructions(String exName){
       if (! Python.isStarted()) {
           Python.start(new AndroidPlatform(this));
       }
       Python py = Python.getInstance();
       // creating python object
       PyObject pyObj= py.getModule("myscript"); // call the python file
       PyObject instructions = pyObj.callAttr("retreiveInstructions",exName); // call the  method in python
       PyObject video = pyObj.callAttr("retreiveVideo",exName); // call the  method in python

       videoLink = video.toString();

       inst = instructions.toString();//retrieve  output
       instArray=inst.split("_");
       inst="";
       if(!instArray[0].equals("0")){
           inst=inst+"1. "+instArray[0]+"\n\n";
       }
       if(!instArray[1].equals("0")){
           inst=inst+"2. "+instArray[1]+"\n\n";
       }
       if(!instArray[2].equals("0")){
           inst=inst+"3. "+instArray[2]+"\n\n";
       }
       if(!instArray[3].equals("0")){
           inst=inst+"4. "+instArray[3]+"\n\n";
       }

           instTxt.setText(inst);

       instTxt.setMovementMethod(new ScrollingMovementMethod());

       Uri uri = Uri.parse(videoLink);
       v.setVideoURI(uri);
       MediaController mediaController= new MediaController(this);
       v.setMediaController(mediaController);
       mediaController.setAnchorView(v);
       v.start();
       v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
           @Override
           public void onPrepared(MediaPlayer mp) {
//                pd.dismiss();
           }
       });
       v.setVideoURI(uri);
       v.setMediaController(new MediaController(this));

       v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
           @Override
           public void onPrepared(MediaPlayer mp) {
               mp.setLooping(true);
           }
       });

   }

//    public String  retreiveVideo(String exName){
//        if (! Python.isStarted()) {
//            Python.start(new AndroidPlatform(this));
//        }
//        Python py = Python.getInstance();
//        // creating python object
//        PyObject pyObj= py.getModule("myscript"); // call the python file
//        PyObject video = pyObj.callAttr("retreiveVideo",exName); // call the  method in python
//        videoLink = video.toString();
//        return videoLink;
//
//    }

    public void retrieveExerciseName() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("userProfile").document(userIp).collection("WorkoutPlan").document(userIp).collection(userIp).document("day"+(currDay));
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                day = value.getString("plan");
                day = day.substring(2, day.length() - 3);
                dayAr = day.split("_");
//                exName= dayAr[counter];

                exerciseName.setText(dayAr[counter]);
                retreiveInstructions(dayAr[counter]);

                if(counter==dayAr.length-1 || counter==dayAr.length+1  || counter==dayAr.length){
                    nextExercise="finish";
                    nextbtn.setText("Finish");
                    skipbtn.setVisibility(View.INVISIBLE);
                }
                else {
                    nextExercise = dayAr[counter + 1];
                }

            }
        });
    }

}