package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class updateProfile extends AppCompatActivity implements nameDialog.DialogListener,
        genderDialog.DialogListener, DB_Dialog.DialogListener, heightDialog.DialogListener,
        areaDialog.DialogListener, weightDialog.DialogListener,
        reminderDialog.DialogListener, daysDialog.DialogListener{
    TextView eName, eGender, eDB, eHeight, eWeight, eFocusArea, eReminder, eTrainingDays;
    Button updateProfile;
    private FirebaseFirestore db;
    private int place=0;  //0 for home 1 for gym
    private String level;
    private ArrayList<String> goal;
    private ArrayList<String> tDays;
    private ArrayList<String> equpmtList;
    private String userIp;
    private String goalString;
    private String tDaysString;
    private Map<String,Object> user = new HashMap<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        level="";
        equpmtList= new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //(navigation bar)
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),PlanView.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),updateProfile.class));
                        overridePendingTransition(0,0);
                        return true;
//                      case R.id.progress:
//                          startActivity(new Intent(getApplicationContext(),progress.class));
//                          overridePendingTransition(0,0);
//                          return true;
                }
                return false;
            }
        });


        eName=(TextView) findViewById(R.id.editName);
        eGender=(TextView) findViewById(R.id.editGender);
        eDB=(TextView) findViewById(R.id.editBD);
        eHeight=(TextView) findViewById(R.id.editHeight);
        eWeight=(TextView) findViewById(R.id.editWeight);
        eFocusArea=(TextView) findViewById(R.id.editFocusArea);
        eReminder = (TextView) findViewById(R.id.editReminder);
        eTrainingDays = (TextView) findViewById(R.id.editTrainingDays);
        updateProfile=(Button) findViewById(R.id.updateProfileB);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        db = FirebaseFirestore.getInstance();
        goalString="";
        tDaysString="";


        level= getIntent().getStringExtra("level");
        goal=getIntent().getStringArrayListExtra("goal");
        tDays=getIntent().getStringArrayListExtra("tDays");
        place=getIntent().getIntExtra("place",0);
        equpmtList= getIntent().getStringArrayListExtra("equpmtList");

        //eName.setText();
        //eGender.setText();
        //eDB.setText();
        //eHeight.setText();
        //eWeight.setText();
        for(int i = 0; i<goal.size();i++){

            goalString=goalString+" "+ goal.get(i);
        }
        for(int i = 0; i<tDays.size();i++){

            tDaysString=tDaysString+" "+ tDays.get(i);
        }
        eFocusArea.setText(goalString);
        //eReminder.setText();
        eTrainingDays.setText(tDaysString);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("userProfile").document(userIp).update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override

                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(Goal.this,"successful",Toast.LENGTH_SHORT);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(Goal.this,"Faild",Toast.LENGTH_SHORT);
                    }
                });
            }
        });
        eName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNameDialog();
            }


        });

        eGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGenderDialog();
            }


        });

        eDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDB_Dialog();
            }


        });

        eHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openHeightDialog();
            }


        });

        eWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openWeightDialog();
            }


        });

        eFocusArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAreaDialog();
            }


        });

        eReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openReminderDialog();
            }


        });

        eTrainingDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDaysDialog();
            }


        });
    }

    public void openNameDialog(){
        nameDialog eName=new nameDialog();
        eName.show(getSupportFragmentManager(),"Name");
    }

    public void openGenderDialog(){
        genderDialog eGender=new genderDialog();
        eGender.show(getSupportFragmentManager(),"Gender");
    }

    public void openDB_Dialog(){
        DB_Dialog eDB=new DB_Dialog();
        eDB.show(getSupportFragmentManager(),"DB");
    }

    public void openHeightDialog(){
        heightDialog eHeight=new heightDialog();
        eHeight.show(getSupportFragmentManager(),"Height");
    }

    public void openWeightDialog(){
        weightDialog eWeight=new weightDialog();
        eWeight.show(getSupportFragmentManager(),"Weight");
    }

    public void openAreaDialog(){
        areaDialog eFocusArea=new areaDialog();
        eFocusArea.show(getSupportFragmentManager(),"Area");
    }

    public void openReminderDialog(){
        reminderDialog eReminder=new reminderDialog();
        eReminder.show(getSupportFragmentManager(),"Reminder");
    }

    public void openDaysDialog(){
        daysDialog eTrainingDays=new daysDialog();
        eTrainingDays.show(getSupportFragmentManager(),"Training Days");
    }

    public void applyNameText(String name){
        eName.setText(name);

//        user.put("focusArea",goal.toString());

    }

    public void applyGenderText(String gender){
        eGender.setText(gender);

    }

    public void applyDBText(String DB){
        eDB.setText(DB);

    }

    public void applyHeightText(String height){
        eHeight.setText(height);

    }


    public void applyWeightText(String weight){
        eWeight.setText(weight);

    }

    public void applyAreaText(String area){
        eFocusArea.setText(area);
        user.put("focusArea",area);


    }

    public void applyReminderText(String reminder){
        eReminder.setText(reminder);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void applyDaysText(String days){
        eTrainingDays.setText(days);
        user.put("trainingDays",days);


    }

    public void onClick(View view) {


    }


}