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
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
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
    String url="https://storage.cloudconvert.com/tasks/c5152c17-64b1-4cfd-914b-738cf226f065/7df78f_807968e6d7044c33a6c370297d715845~mv2.mp4?AWSAccessKeyId=cloudconvert-production&Expires=1645205345&Signature=fOBcA%2BXRPXtp2vdzyucyzg88zVs%3D&response-content-disposition=inline%3B%20filename%3D%227df78f_807968e6d7044c33a6c370297d715845~mv2.mp4%22&response-content-type=video%2Fmp4";
    ProgressDialog pd;
    private FirebaseFirestore db;
    private String userIp;
    private double set , Res;
    private int s, re, i,sIndex;
    private TextView sets, instTxt;
    private Button nextbtn;
    private String inst;
    String instArray[] = new String[5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_view);
        sets=(TextView) findViewById(R.id.sets); //Done
        instTxt=(TextView) findViewById(R.id.instTxt);
        v= (VideoView)findViewById(R.id.video);
        nextbtn =(Button)findViewById(R.id.nextbtn);
        i=1;
        sIndex=0;

//        pd=new ProgressDialog(SessionView.this);
//        pd.setMessage("loading");
//        pd.show();
        Uri uri = Uri.parse(url);
        v.setVideoURI(uri);
        MediaController mediaController= new MediaController(this);
        v.setMediaController(mediaController);
        mediaController.setAnchorView(v);
//        v.start();
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
                sets.setText("1/"+s+"");


                Res=value.getDouble("rest");
                re=(int)Res;



            }
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==s){
                    sets.setText("done");
                    nextExercise();

                }
                else{
                    sets.setText(i+1+"/"+s);
                    i=i+1;
                }
            }
        });
        retreiveInstructions();

    }
    public void nextExercise(){
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
       if((!instArray[0].equals(0))||instArray[0]!="0"){
           inst=inst+instArray[0]+"\n\n";
       }
       if((!instArray[1].equals(0))||instArray[1]!="0"){
           inst=inst+instArray[1]+"\n\n";
       }
       if((!instArray[2].equals(0))||instArray[2]!="0"){
           inst=inst+instArray[2]+"\n\n";
       }
       if((!instArray[3].equals(0))||instArray[3]!="0"){
           inst=inst+instArray[3]+"\n\n";
       }

       instTxt.setText(inst);


   }
}