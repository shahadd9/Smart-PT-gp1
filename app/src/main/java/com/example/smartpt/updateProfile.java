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
import android.widget.Spinner;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class updateProfile extends AppCompatActivity implements
        DB_Dialog.DialogListener, AdapterView.OnItemSelectedListener {
    EditText eName, eHeight, eWeight;
    TextView eDB;
    Spinner eGender, eReminder, eDuration, eTrainingDays;
    ArrayAdapter<String> eGenderAdapter;
    ArrayAdapter<String> eDurationAdapter;
    ArrayAdapter<String> eReminderAdapter;
    ArrayAdapter<String> eDaysAdapter;
    Button updateProfile;
    private FirebaseFirestore db;
    private String userIp;
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
    private double tDuration;
    private String level;
    private String SessionNo;

    private static ArrayList<String> tDays;//    private String day2;
//    private String day3;
//    private String day4;
//    private String day5;
    private String dayss;

    private Map<String, Object> user = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        SessionNo=getIntent().getStringExtra("SessionNo");
        level =getIntent().getStringExtra("level");
        tDaysN= new ArrayList<>();
//        dayss="2";

//        if(SessionNo.equals("2")){
//
//            day1=getIntent().getStringExtra("day1");
//
//
//
//            day2=getIntent().getStringExtra("day2");
//
//
//
//
//        }
//        else if(SessionNo.equals("3")){
//
//            day1=getIntent().getStringExtra("day1");
//
//
//
//            day2=getIntent().getStringExtra("day2");
//
//
//
//            day3=getIntent().getStringExtra("day3");
//
//
//
//
//        }
//        else if(SessionNo.equals("4")){
//            day1=getIntent().getStringExtra("day1");
//
//
//            day2=getIntent().getStringExtra("day2");
//
//
//
//            day3=getIntent().getStringExtra("day3");
//
//
//
//            day4=getIntent().getStringExtra("day4");
//
//
//        }
//        else if(SessionNo.equals("5")){
//            day1=getIntent().getStringExtra("day1");
//
//
//
//            day2=getIntent().getStringExtra("day2");
//
//
//
//            day3=getIntent().getStringExtra("day3");
//
//
//
//            day4=getIntent().getStringExtra("day4");
//
//
//
//            day5=getIntent().getStringExtra("day5");
//
//
//
//        }

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //(navigation bar)
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent i = new Intent(updateProfile.this, PlanView.class);

                        if(SessionNo.equals("2")){
                            i.putExtra("SessionNo",SessionNo);

                            i.putExtra("level",level);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            finish();
                            return true;
                        }
                        else if(SessionNo.equals("3")){
                            i.putExtra("SessionNo",SessionNo);
                            i.putExtra("level",level);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                        else if(SessionNo.equals("4")){
                            i.putExtra("SessionNo",SessionNo);
                            i.putExtra("level",level);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                        else if(SessionNo.equals("5")) {
                            i.putExtra("SessionNo", SessionNo);
                            i.putExtra("level", level);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            finish();
                        }

                    case R.id.profile:
//                        startActivity(new Intent(getApplicationContext(), updateProfile.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                      case R.id.progress:
//                          startActivity(new Intent(getApplicationContext(),progress.class));
//                          overridePendingTransition(0,0);
//                          return true;
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


        initializeDropdownData();

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        //get data from database
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("userProfile").document(userIp);
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
                w=value.getString("weight");
                eWeight.setText(w);
//                dayss=value.getString("TrainingdaysNum");

                // update reminder and duration spinners
                eReminder.setSelection(eReminderAdapter.getPosition(value.getString("TrainingTime")));
                eDuration.setSelection(eDurationAdapter.getPosition(value.getString("TrainingDuration")));
                eTrainingDays.setSelection(eDaysAdapter.getPosition(value.getString("TrainingdaysNum")));
            }
        });
            /////////////////////////////////////////////////////////
//        tDaysN=TrainingDays.gettDays();



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
                        if (task.isSuccessful()) {

//                            Toast.makeText(com.example.smartpt.updateProfile.this, "profile has been updated",
//                                    Toast.LENGTH_LONG).show();
                            Intent i = new Intent(updateProfile.this, LoadPa.class);
//                            i.putExtra("message","Updating Your Plan..");
//                            i.putExtra("SessionNo",dayss);
//                            i.putExtra("level",level);
                            startActivity(i);
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
                double height = Double.parseDouble(h);
                if (height > 249 || height < 99) {
                    eHeight.setError("your height is out of range!");
                } else {
                    user.put("height", h);
                }

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
                    user.put("weight", w);
                }

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
        user.put("TrainingTime", reminder);
    }

    private void updateDuration(String duration) {
        user.put("TrainingDuration", duration);
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

        user.put("TrainingdaysNum", days);
        user.put("trainingDays", tDaysString);

    }



}

