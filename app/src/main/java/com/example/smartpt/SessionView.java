package com.example.smartpt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
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

public class SessionView extends AppCompatActivity {

    VideoView v;
    String url="https://im.ezgif.com/tmp/ezgif-1-08e77a9bb3.mp4";
    ProgressDialog pd;
    private FirebaseFirestore db;
    private String userIp;
    private double set , Res;
    private int s, re, i,sIndex;
    private TextView sets, instTxt;
    private Button nextbtn,skipbtn;
    private String inst;
    String instArray[] = new String[5];
    private int prog;
    private ProgressBar progress_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_view);
        prog=0;
        progress_bar= (ProgressBar)findViewById(R.id.progress_bar);
        updteProgressBar();
        sets=(TextView) findViewById(R.id.sets); //Done
        instTxt=(TextView) findViewById(R.id.instTxt);
        v= (VideoView)findViewById(R.id.video);
        nextbtn =(Button)findViewById(R.id.nextbtn);
        skipbtn =(Button)findViewById(R.id.skipbtn);


        i=0;
        sIndex=0;
//        pd=new ProgressDialog(SessionView.this);
//        pd.setMessage("loading");
//        pd.show();
        Uri uri = Uri.parse(url);
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
                if(i+1==s){
//                    sets.setText("done");
                    sets.setText(i+1+"/"+s);
                    prog+=(100/s);
                    updteProgressBar();
                    nextExercise(re);

                }
                else{
                    sets.setText(i+1+"/"+s);
                    i=i+1;
                    if(prog<=90){
                        prog+=(100/s);
                        updteProgressBar();
                    }
                }
            }
        });
        retreiveInstructions();

    }
    public void updteProgressBar(){

        progress_bar.setProgress(prog);

    }
    public void nextExercise(int re){
        Intent intent= new Intent(this, StartSession.class);
        intent.putExtra("rest",re);
        intent.putExtra("restText","Rest");
        startActivity(intent);

    }

   public void retreiveInstructions(){
       if (! Python.isStarted()) {
           Python.start(new AndroidPlatform(this));
       }
       Python py = Python.getInstance();
       // creating python object
       PyObject pyObj= py.getModule("myscript"); // call the python file
       PyObject instructions = pyObj.callAttr("retreiveInstructions","diamond push-up"); // call the  method in python
       inst = instructions.toString();//retrieve  output
       instArray=inst.split("_");
       inst="";
       if(!instArray[0].equals("0")){
           inst=inst+instArray[0]+"\n\n";
       }
       if(!instArray[1].equals("0")){
           inst=inst+instArray[1]+"\n\n";
       }
       if(!instArray[2].equals("0")){
           inst=inst+instArray[2]+"\n\n";
       }
       if(!instArray[3].equals("0")){
           inst=inst+instArray[3]+"\n\n";
       }

       instTxt.setText(inst);
       instTxt.setMovementMethod(new ScrollingMovementMethod());


   }
}