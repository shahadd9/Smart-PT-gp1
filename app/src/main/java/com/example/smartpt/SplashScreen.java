package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SplashScreen extends AppCompatActivity {
//    private String userIp;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private FirebaseAuth uAuth;
    private String id;
    private String SessionNo;
    private String level;
    private FirebaseFirestore db;
    private int  FBindex;
    private double FBindexD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SessionNo = "";
        level = "";
        db = FirebaseFirestore.getInstance();


//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        //to get user email
        uAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = uAuth.getCurrentUser();
         id = curUser.getEmail();

        if (id==null) {
            Intent i = new Intent(SplashScreen.this, Home.class);
            startActivity(i);
        }else{
        /////////////////////////////////////////////////////////
        DocumentReference documentReference = rootRef.collection("userProfile").document(id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                SessionNo = value.getString("TrainingdaysNum");
                level = value.getString("level");
                if (SessionNo == null || level == null) {
                    goTO(SessionNo, level);

                }
                if (SessionNo != "" && level != "") {

                    goTO(SessionNo, level);
                }

            }
        });
    }// end else
        //////////////////////////////////////////////////////////


    }

    //*************** for log in *******************
    // to check if user logged in or not
//    @Override
//    public void onStart(){
//        super.onStart();
//        FirebaseUser currUser=uAuth.getCurrentUser();
//        if(currUser==null){
//            startActivity(new Intent(SplashScreen.this,Home.class));
//        }else{
//            startActivity(new Intent(SplashScreen.this,PlanView.class));
//
//        }
//    }




    private void goTO(String session, String lvl){
        DocumentReference docIdRef = rootRef.collection("userProfile").document(id);

        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists() && document != null) {
                        if(session != null && lvl !=null) {

//                        ret();
                            Intent i = new Intent(SplashScreen.this, PlanView.class);
                            i.putExtra("SessionNo", session);
                            i.putExtra("level", lvl);
//                        i.putExtra("counter",FBindex);
                            startActivity(i);
                        }else {
                            Intent i = new Intent(SplashScreen.this, Home.class);
                            startActivity(i);
                        }
                    } else {
                        Intent i = new Intent(SplashScreen.this, Home.class);
                        startActivity(i);
                    }
                } else {
                    Intent i = new Intent(SplashScreen.this, Gender.class);
                    startActivity(i);
                }
            }
        });

    }

    public void ret(){

        DocumentReference documentReference = db.collection("Progress").document(id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


//                        set=value.getDouble("sets");
//                        s=(int)set;
//                        sets.setText(""+s+"");

//                        rep=value.getDouble("reps");
//                        r=(int)rep;
//                        reps.setText(r+"");

                FBindexD= value.getDouble("exerciseIndex");
                FBindex=(int)FBindexD;


            }
        });

    }
}