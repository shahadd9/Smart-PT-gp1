package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class updateProfile extends AppCompatActivity implements
        DB_Dialog.DialogListener, AdapterView.OnItemSelectedListener {
    EditText eName, eHeight, eWeight;
    TextView eDB;
    ImageView logout;
    Spinner eGender, eReminder, eDuration, eTrainingDays;
    ArrayAdapter<String> eGenderAdapter;
    ArrayAdapter<String> eDurationAdapter;
    ArrayAdapter<String> eReminderAdapter;
    ArrayAdapter<String> eDaysAdapter;
    Button updateProfile;
    private FirebaseFirestore db;
//    private String userIp;
    private ArrayList<String> tDaysN;
    private String name;
    private String date;
    private String h;
    private String w;
    private int g;
    private int dur;
    private double gender;
    private ArrayList<String> goal;
    private String goalStrin = "";
    private String tDaysString = "";
    private ArrayList<String> a;
    private String tTime;
    private double tDuration,height,weight;
    private String level;
    private String SessionNo;
    private Boolean flag;
    private int sets;
    private String durationA;
    private String remind;
    private String numDays;

    private int week;
    private String currDay;

    private static ArrayList<String> tDays;//    private String day2;
//    private String day3;
//    private String day4;
//    private String day5;
    private String dayss;

    private Map<String, Object> user = new HashMap<>();
    private FirebaseAuth uAuth;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        SessionNo=getIntent().getStringExtra("SessionNo");
        level =getIntent().getStringExtra("level");
        currDay=getIntent().getStringExtra("currDay");
        week=getIntent().getIntExtra("week",0);
        tDaysN= new ArrayList<>();
        durationA="";
        remind="";
        numDays="";
        flag=false;

        uAuth= FirebaseAuth.getInstance();
        FirebaseUser curUser=uAuth.getCurrentUser();
        id=curUser.getEmail();



        //(navigation bar)
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.home:
                        Intent i = new Intent(updateProfile.this, PlanView.class);
//                        if(SessionNo.equals("2")){
                            i.putExtra("SessionNo", SessionNo);
                            i.putExtra("level", level);

                            startActivity(i);
                            overridePendingTransition(0, 0);
                            finish();
                            return true;


                    case R.id.progress:
                        i = new Intent(updateProfile.this, UserProgress.class);
                        i.putExtra("SessionNo", SessionNo);
                        i.putExtra("level", level);
                        i.putExtra("week",week);
                        i.putExtra("currDay",currDay);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });


        eName = (EditText) findViewById(R.id.editName);
        eGender = (Spinner) findViewById(R.id.editGender);
        eDB = (TextView) findViewById(R.id.editBD);
        eHeight = (EditText) findViewById(R.id.editHeight);
        eWeight = (EditText) findViewById(R.id.editWeight);
//        eFocusArea = (TextView) findViewById(R.id.editFocusArea);
        eReminder = (Spinner) findViewById(R.id.editReminder2);
        eDuration = (Spinner) findViewById(R.id.editDuration);
        eTrainingDays = (Spinner) findViewById(R.id.editTrainingDays);
        updateProfile = (Button) findViewById(R.id.updateProfileB);
        logout=(ImageView)findViewById(R.id.logout);


        initializeDropdownData();

//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        //get data from database
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("userProfile").document(id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                eTrainingDays.setText(value.getString("trainingDays"));
//                eFocusArea.setText(value.getString("focusArea"));

                name=value.getString("name");
                eName.setText(name);
                gender = value.getDouble("gender");
                g = (int) gender;

                // update gender Spinner
                if (g == 0)
                    eGender.setSelection(0);
                else
                    eGender.setSelection(1);
                date = value.getString("Birthdate");
                eDB.setText(date);
                h=value.getString("height");
                eHeight.setText(h);
                height= Double.parseDouble(h);
                w=value.getString("weight");
                weight=Double.parseDouble(w);
                eWeight.setText(w);

                remind = value.getString("TrainingTime");
                durationA= value.getString("TrainingDuration");
                numDays=value.getString("TrainingdaysNum");

                // update reminder and duration spinners
                eReminder.setSelection(eReminderAdapter.getPosition(value.getString("TrainingTime")));
                eDuration.setSelection(eDurationAdapter.getPosition(value.getString("TrainingDuration")));
                eTrainingDays.setSelection(eDaysAdapter.getPosition(value.getString("TrainingdaysNum")));
            }
        });



        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("userProfile").document(id).update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override

                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

//                            Toast.makeText(com.example.smartpt.updateProfile.this, "profile has been updated",
//                                    Toast.LENGTH_LONG).show();
                            if(flag) {
                                startActivity(new Intent(updateProfile.this, LoadPa2.class));
                            }
                            else {
                                Toast.makeText(com.example.smartpt.updateProfile.this, "profile has been updated",Toast.LENGTH_LONG).show();
                            }
                        }
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
                } else {
                    eName.setError("Only Alphabets characters are accepted! ");
                }
                return "";
            }
        }
        });
        eName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = eName.getText().toString();
                if (n.isEmpty()) {
                    eName.setError("name is required!");
                } else {
                    user.put("name", n);
                }

            }
        });


        eHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String h = eHeight.getText().toString();
                 height = Double.parseDouble(h);
                if (height > 249 || height < 99) {
                    eHeight.setError("your height is out of range!");
                } else {
                    flag=true;
                    user.put("height", h);
                }
                double bmi= (weight/(height*height))*10000;

                if(level.equalsIgnoreCase("Beginner")){
                    if(bmi < 18.5){
                        sets =2;

                    }
                    else if(bmi >=18.5 &&bmi<=24.9){
                        sets=3;
                    }
                    else{
                        sets=2;
                    }
                }
                else if(level.equalsIgnoreCase("Intermediate")){
                    if(bmi < 18.5){
                        sets =3;

                    }
                    else if(bmi >=18.5 &&bmi<=bmi){
                        sets=4;
                    }
                    else{
                        sets=3;
                    }
                }
                else {
                    if (bmi < 18.5) {
                        sets = 4;

                    } else if (bmi >= 18.5 && bmi <= 24.9) {
                        sets = 5;
                    } else {
                        sets = 4;
                    }
                }
                Map<String,Object> planAdd = new HashMap<>();

                CollectionReference ex = db.collection("userProfile");
                planAdd.put("sets",sets);

                db.collection("userProfile").document(id).collection("WorkoutPlan").document(id).update(planAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override

                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

////                            Toast.makeText(com.example.smartpt.updateProfile.this, "profile has been updated",
////                                    Toast.LENGTH_LONG).show();
//                            if(flag) {
//                                startActivity(new Intent(updateProfile.this, LoadPa2.class));
//                            }
//                            else {
//                                Toast.makeText(com.example.smartpt.updateProfile.this, "profile has been updated",Toast.LENGTH_LONG).show();
//                            }
                        }
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

        eWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String w = eWeight.getText().toString();
                double weight = Double.parseDouble(w);
                if (weight > 249 || weight < 29) {
                    eWeight.setError("your weight is out of range!");
                } else {
                    flag=true;
                    user.put("weight", w);
                }
                double bmi= (weight/(height*height))*10000;

                if(level.equalsIgnoreCase("Beginner")){
                    if(bmi < 18.5){
                        sets =2;

                    }
                    else if(bmi >=18.5 &&bmi<=24.9){
                        sets=3;
                    }
                    else{
                        sets=2;
                    }
                }
                else if(level.equalsIgnoreCase("Intermediate")){
                    if(bmi < 18.5){
                        sets =3;

                    }
                    else if(bmi >=18.5 &&bmi<=bmi){
                        sets=4;
                    }
                    else{
                        sets=3;
                    }
                }
                else {
                    if (bmi < 18.5) {
                        sets = 4;

                    } else if (bmi >= 18.5 && bmi <= 24.9) {
                        sets = 5;
                    } else {
                        sets = 4;
                    }
                }
                Map<String,Object> planAdd = new HashMap<>();

                CollectionReference ex = db.collection("userProfile");
                planAdd.put("sets",sets);

                db.collection("userProfile").document(id).collection("WorkoutPlan").document(id).update(planAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override

                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

////                            Toast.makeText(com.example.smartpt.updateProfile.this, "profile has been updated",
////                                    Toast.LENGTH_LONG).show();
//                            if(flag) {
//                                startActivity(new Intent(updateProfile.this, LoadPa2.class));
//                            }
//                            else {
//                                Toast.makeText(com.example.smartpt.updateProfile.this, "profile has been updated",Toast.LENGTH_LONG).show();
//                            }
                        }
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



        eDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDB_Dialog();
            }


        });

//        eFocusArea.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                openAreaDialog();
//            }
//        });

//        eTrainingDays.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                openDaysDialog();
//            }
//        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 logout();
            }
        });

    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(updateProfile.this,Login.class));
    }

    public void openDB_Dialog() {
        DB_Dialog eDB = new DB_Dialog();
        eDB.show(getSupportFragmentManager(), "DB");
    }

//    public void openAreaDialog() {
//        areaDialog eFocusArea = new areaDialog();
//        eFocusArea.show(getSupportFragmentManager(), "Area");
//    }



//    public void openDaysDialog() {
//        daysDialog eTrainingDays = new daysDialog();
//        eTrainingDays.show(getSupportFragmentManager(), "Training Days");
//    }



    public void applyDBText(String DB) {
        eDB.setText(DB);
        user.put("Birthdate", DB);
    }


//    public void applyAreaText(String area) {
//        eFocusArea.setText(area);
//        user.put("focusArea",area);
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
//    public void applyDaysText(String days) {
//        eTrainingDays.setText(days);
//        user.put("trainingDays", days);
//    }

    public void onClick(View view) {}



    private void initializeDropdownData(){
        List<String> genders = new ArrayList<>(Arrays.asList("Male", "Female"));

        eGenderAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, genders);
        eGender.setAdapter(eGenderAdapter);
        eGender.setOnItemSelectedListener(this);

        List<String> reminders = new ArrayList<>(Arrays.asList("Morning","Afternoon","Evening"));

        eReminderAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, reminders);
        eReminder.setAdapter(eReminderAdapter);
        eReminder.setOnItemSelectedListener(this);

        List<String> durations = new ArrayList<>(Arrays.asList("30","45","60"));

        eDurationAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, durations);
        eDuration.setAdapter(eDurationAdapter);
        eDuration.setOnItemSelectedListener(this);

        List<String> days = new ArrayList<>(Arrays.asList("2","3","4","5"));
        eDaysAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, days);
        eTrainingDays.setAdapter(eDaysAdapter);
        eTrainingDays.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        switch (parent.getId()) {
            case R.id.editGender:
                updateGender(item);
                break;

            case R.id.editReminder2:
                updateReminder(item);
                break;

            case R.id.editDuration:
                updateDuration(item);
                break;

            case R.id.editTrainingDays:
                updateDays(item);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    private void updateGender(String gender) {
        if (gender.equals("Male")) {
            user.put("gender", 0);
        } else {
            user.put("gender", 1);
        }
    }

    private void updateReminder(String reminder) {
        if(!remind.equals(reminder)){
        flag=true;
        }
        user.put("TrainingTime", reminder);


    }

    private void updateDuration(String duration) {
        if(!durationA.equals(duration)){
            flag=true;
        }        user.put("TrainingDuration", duration);
    }

    private void updateDays(String days) {

        if(days.equals("2")){
            tDaysN.clear();
            tDaysN.add("Sun");
            tDaysN.add("Tue");
        }
        else if(days.equals("3")){
            tDaysN.clear();
            tDaysN.add("Sun");
            tDaysN.add("Tue");
            tDaysN.add("Thu");
        }
        else if(days.equals("4")){
            tDaysN.clear();
            tDaysN.add("Sun");
            tDaysN.add("Tue");
            tDaysN.add("Thu");
            tDaysN.add("Sat");
        }
        else if(days.equals("5")){
            tDaysN.clear();
            tDaysN.add("Sun");
            tDaysN.add("Mon");
            tDaysN.add("Tue");
            tDaysN.add("Thu");
            tDaysN.add("Fri");

        }
        tDaysString="";
        for(int i = 0; i<tDaysN.size();i++){

            tDaysString=tDaysString+" "+ tDaysN.get(i);
        }
        if(!numDays.equals(days)){
            flag=true;
        }
        user.put("TrainingdaysNum", days);
        user.put("trainingDays", tDaysString);

    }



}

