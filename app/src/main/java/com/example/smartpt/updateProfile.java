package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class updateProfile extends AppCompatActivity implements
        genderDialog.DialogListener, DB_Dialog.DialogListener,
        areaDialog.DialogListener, reminderDialog.DialogListener, daysDialog.DialogListener,durationDialog.DialogListener{
    EditText eName , eHeight, eWeight;
    TextView  eGender, eDB, eFocusArea, eReminder,eDuration, eTrainingDays;
    Button updateProfile;
    private FirebaseFirestore db;
    private String userIp;
    private ArrayList<String> tDays;
    private String name;
    private String date;
    private int h;
    private int w;
    private double gender;
    private ArrayList<String> goal;
    private String goalStrin="";
    private String tDaysString="";
    private ArrayList<String>a;
    private String tTime;
    private String tDuration;


    private Map<String,Object> user = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


        eName=(EditText) findViewById(R.id.editName);
        eGender=(TextView) findViewById(R.id.editGender);
        eDB=(TextView) findViewById(R.id.editBD);
        eHeight=(EditText) findViewById(R.id.editHeight);
        eWeight=(EditText) findViewById(R.id.editWeight);
        eFocusArea=(TextView) findViewById(R.id.editFocusArea);
        eReminder = (TextView) findViewById(R.id.editReminder2);
        eDuration = (TextView) findViewById(R.id.editDuration);
        eTrainingDays = (TextView) findViewById(R.id.editTrainingDays);
        updateProfile=(Button) findViewById(R.id.updateProfileB);



        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference=db.collection("userProfile").document(userIp);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                eTrainingDays.setText(value.getString("trainingDays"));
                eFocusArea.setText(value.getString("focusArea"));
                eName.setText(value.getString("name"));
//
                gender=value.getDouble("gender");
                h= (int)gender;

                if (h==0)
                    eGender.setText("Male");
                else
                    eGender.setText("Female");
//
                eDB.setText(value.getString("Birthdate"));
////
                eHeight.setText(value.getString("height")+"");
                eWeight.setText(value.getString("weight")+"");
////
                eReminder.setText(value.getString("TrainingTime")+"");
                eDuration.setText(value.getString("TrainingDuration")+"");
            }
        });
//            /////////////////////////////////////////////////////////
//        tDays=TrainingDays.gettDays();
//        for(int i = 0; i<tDays.size();i++){
//
//            tDaysString=tDaysString+" "+ tDays.get(i);
//        }
//        eTrainingDays.setText(tDaysString);
//        ///////////////////////////////////////////////////////////
//        goal=Goal.focusArea;
//    for(int i = 0; i<goal.size();i++){
//
//        goalStrin=goalStrin+" "+ goal.get(i);
//        }
//        eFocusArea.setText(goalStrin);
/////////////////////////////////////////////////////////////////////
//        name=Name.name;
//        eName.setText(name);
/////////////////////////////////////////////////////////////////////
//        gender=Gender.gen;
//        if (gender==0)
//        eGender.setText("Male");
//        else
//            eGender.setText("Female");
/////////////////////////////////////////////////////////////////////
//        date=Birthdate.date;
//        eDB.setText(date);
/////////////////////////////////////////////////////////////////////
//        h=HeightandWeight.h;
//        w=HeightandWeight.w;
//        eHeight.setText(""+h+"cm");
//        eWeight.setText(""+w+"kg");
/////////////////////////////////////////////////////////////////////
//        tTime=TrainingTime.tTime;
//        eReminder.setText(tTime);
//
//        tDuration=TrainingDuration.tDuration+"";
//        eDuration.setText(tDuration+" minutes");

//        eName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(eName.getText().toString().matches("[a-zA-Z ]+")){
//                    eName.setError("Only Alphabets characters are accepted! ");
//                } else if( eName.getText().toString().equals("")){
//                    eName.setError("This field is required!");
//                }else{ applyNameText(eName.toString());}
//            }
//        });



        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("userProfile").document(userIp).update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override

                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(com.example.smartpt.updateProfile.this, "profile has been updated",
                                    Toast.LENGTH_LONG).show();                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(com.example.smartpt.updateProfile.this, "faild",
//                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });




                eName.setFilters(new InputFilter[]{new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (source.equals("")) {
                            return source;
                        }
                        if (source.toString().matches("[a-zA-Z ]+")) {
                            return source;
                        }
                        else {
                            eName.setError("Only Alphabets characters are accepted! ");
                        }
                        return "";
                    }
                }
                });
                eName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String n=eName.getText().toString();
                        if(n.isEmpty()){
                            eName.setError("name is required!");
                        }else {
                            user.put("name", n);
                        }

                    }
                });


        eHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String h= eHeight.getText().toString();
                int height=Integer.parseInt(h);
                if(height>249|| height<99){
                    eHeight.setError("your height is out of range!");}
                else {
                    user.put("height",h.concat("cm"));
                }

            }
        });

        eWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String w= eWeight.getText().toString();
                int weight=Integer.parseInt(w);
                if(weight>249 || weight<29){
                    eWeight.setError("your weight is out of range!");}
                else {
                    user.put("weight",w.concat("kg"));
                }

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

        eDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDurationDialog();
            }


        });

        eTrainingDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDaysDialog();
            }


        });
    }

    public void openGenderDialog(){
        genderDialog eGender=new genderDialog();
        eGender.show(getSupportFragmentManager(),"Gender");
    }

    public void openDB_Dialog(){
        DB_Dialog eDB=new DB_Dialog();
        eDB.show(getSupportFragmentManager(),"DB");
    }

    public void openAreaDialog(){
        areaDialog eFocusArea=new areaDialog();
        eFocusArea.show(getSupportFragmentManager(),"Area");
    }

    public void openReminderDialog(){
        reminderDialog eReminder=new reminderDialog();
        eReminder.show(getSupportFragmentManager(),"Reminder");
    }

    public void openDurationDialog(){
        durationDialog eDuration=new durationDialog();
        eDuration.show(getSupportFragmentManager(),"Duration");
    }

    public void openDaysDialog(){
        daysDialog eTrainingDays=new daysDialog();
        eTrainingDays.show(getSupportFragmentManager(),"Training Days");
    }


    public void applyGenderText(String gender){
        eGender.setText(gender);
        if(gender.equals("Male")) {

            user.put("gender", 0);
        }
            else{

                user.put("gender",1);

            }




    }

    public void applyDBText(String DB){
        eDB.setText(DB);
        user.put("Birthdate",DB);


    }


    public void applyAreaText(String area){
        eFocusArea.setText(area);
        user.put("focusArea",area);


    }

    public void applyReminderText(String reminder){
        eReminder.setText(reminder);
        user.put("TrainingTime",reminder);

    }

    public void applyDurationText(String duration){
        eDuration.setText(duration);
        user.put("TrainingDuration",duration);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void applyDaysText(String days){
        eTrainingDays.setText(days);
        user.put("trainingDays",days);

    }

    public void onClick(View view) {


    }


}

