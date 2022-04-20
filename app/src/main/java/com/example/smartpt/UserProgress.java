package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
//Firebase
//import java.util.Calendar;
//import java.util.Date;
//popup window






//@RequiresApi(api = Build.VERSION_CODES.N)
public class UserProgress extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    java.util.Date date=new java.util.Date();

    private TextView pcM1, pcM2,finishedSession, sessionText, DurationText;
    PieChart pieChart, WeekpieChart, pieChart2;
    TextView pcDay1;
    private Spinner spin;
    public static int tday, fSession;
    String durationInString;
    String[] userExer = { "Diamond push-up", "Weighted push-up", "Knee push-up", "Barbell bench press", "Stability ball decline push-up" };
    int  finalDuration;
    private int rem;
    String[] COLORS = new String[] { "#00AB78", "#00ab90","#007865","#bffff5", "#80ffeb"  };
    Random r= new Random();



    //line chart

    LineChartView lineChartViewWeight, lineChartViewReps, lineChartViewSets;
    String[] axisData;
    //{"Sat","Sun", "Mon", "Tue", "Wed", "Thur", "Fri"};//Training days
    int[] yAxisDataSets ={2, 2, 2, 3, 2, 2, 3};
    int[] yAxisDataReps= {10,10,10,10,10,10,10};
    int[] yAxisDataWeights= {0,0,0,0,0,0,0};
    String[] Ex;
    androidx.cardview.widget.CardView CardViewWeight, CardViewSets, CardViewReps;



    //popup window
    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;
    private Button mButton, sButton, lButton;
    private PopupWindow mPopupWindow, sPopupWindow, lPopupWindow;


    // SHAHAD DECLARATIONS
    private int week;
    private String SessionNo, level,currDay,duration,day;
    private Double time; // time is in seconds
    public final static String shared="sharedPrefs";
    private FirebaseFirestore db;
    private FirebaseAuth uAuth;
    private String id;
    //    private String userIp;
    private int durationInt;
    private int done;
    private boolean exist;
    String dayAr[];



    //TEST
//    EditText testDuration;
    private Map<String, Object> user = new HashMap<>();
    double durationEdited;
    ArrayAdapter<String> eDurationAdapter;
    private Boolean flag;
    String selectedExr;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_progress);


        currDay=getIntent().getStringExtra("currDay");
        week=getIntent().getIntExtra("week",0);
        SessionNo=getIntent().getStringExtra("SessionNo");
        level =getIntent().getStringExtra("level");


        // Pie chart "Today" progress :Minutes
        pcM1 = findViewById(R.id.pcM1);
        pcM2 = findViewById(R.id.pcM2);
        pieChart = findViewById(R.id.piechart);
        DurationText=findViewById(R.id.DurationText);

        durationInString=(duration+" Min");
        exist= false;


        //Pie chart "This Week progress"
        WeekpieChart = findViewById(R.id.Weekpiechart);
        pcDay1= findViewById(R.id.pcDay1);



        finishedSession=findViewById(R.id.FinishDay);
        sessionText=findViewById(R.id.sessionText);

        //line chart
        lineChartViewWeight = findViewById(R.id.chartWeight);
        lineChartViewReps= findViewById(R.id.chartReps);
        lineChartViewSets= findViewById((R.id.chartSets));

        CardViewWeight=findViewById(R.id.CardWeight);
        CardViewReps=findViewById(R.id.CardReps);
        CardViewSets=findViewById(R.id.CardSets);


        flag=true;






        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.progress);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.home:
                        Intent i = new Intent(UserProgress.this, PlanView.class);
//                        if(SessionNo.equals("2")){
                        i.putExtra("SessionNo", SessionNo);
                        i.putExtra("level", level);
                        i.putExtra("week",week);
                        i.putExtra("currDay",currDay);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.profile:
                        i = new Intent(UserProgress.this, updateProfile.class);

//                        if (SessionNo.equals("2")) {
                        i.putExtra("SessionNo", SessionNo);
                        i.putExtra("level", level);
                        i.putExtra("week",week);
                        i.putExtra("currDay",currDay);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;


                    case R.id.progress:

                }
                return false;
            }
        });


////////////////////////////////////////////////////////////////////


//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        //to get user email
        uAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = uAuth.getCurrentUser();
        id = curUser.getEmail();
//        read();
        db = FirebaseFirestore.getInstance();


        DocumentReference d = db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+week).document("day"+currDay);

        d.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists() && document != null) {

                    exist=true;
                    read();
                }else{
                    time=0.0;
                    todayChart();
//                    lineChart();
                    weekChart();
                }
            }
        });

        DocumentReference documentReference = db.collection("userProfile").document(id).collection("WorkoutPlan").document(id).collection(id).document("day"+(currDay));
        d.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                if (document.exists() && document != null) {

                    exist=true;
                    //calling linechart method
                    readExerciseName();
                }else{

                    // this is rest day
                }

            }
        });





        //line chart

        lineChartViewWeight = findViewById(R.id.chartWeight);
        lineChartViewReps= findViewById(R.id.chartReps);
        lineChartViewSets= findViewById((R.id.chartSets));


        CardViewWeight=findViewById(R.id.CardWeight);
        CardViewReps=findViewById(R.id.CardReps);
        CardViewSets=findViewById(R.id.CardSets);









        //EDIT




        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = UserProgress.this;

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.userProgress);
        mButton = (Button) findViewById(R.id.editDuration);
        //sButton=(Button) findViewById(R.id.editSession);
        lButton=(Button) findViewById(R.id.editSRW);







    }
    //
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(), "Selected User: "+dayAr[position] ,Toast.LENGTH_SHORT).show();
        selectedExr = dayAr[position];

        List axisValues = new ArrayList();

        if (tday==2){
            axisData= new String[]{"sun", "Tue"};





        }else if (tday==3){
            axisData= new String[] {"Sun", "Tue","Thur"};

            //yAxisDataSets = new int[] {as default =2, 2, =index(1), 3, =index(3), 3, =index(4)};


            //just log in (sun, Tue, Thur)

        }else if (tday==4){
            axisData=new String[]{"Sat","Sun", "Tue", "Thur"};

            //yAxisDataSets = new int[] { 2,2, =index(1), 3, =index(3), 4, =index(4)};


            //just log (sun, Tue, Thur, Sat)

        }else {
            axisData=new String[] {"Sun","Mon","Tue", "Thur", "Fri"};

            //yAxisDataSets = new int[] { as default , 2,2, 3, =index(3), 4, =index(4)};


            //just log in (sun, mon, Tue, Thur, Fri)



        }





        for (int k = 0; k < axisData.length; k++) {
            axisValues.add(k, new AxisValue(k).setLabel(axisData[k]));
        }

        LineChartData dataWeight = new LineChartData();

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#024265"));
        dataWeight.setAxisXBottom(axis);

//         Line chart

        Axis yAxis = new Axis();
        yAxis.setTextColor(Color.parseColor("#024265"));
        yAxis.setTextSize(16);
        dataWeight.setAxisYLeft(yAxis);

///////////////////////////////////////////////////////

        ///What Chart will appear set reps as default

        lineChartViewWeight.setVisibility(View.INVISIBLE);
        lineChartViewReps.setVisibility(View.VISIBLE);
        lineChartViewSets.setVisibility(View.INVISIBLE);
        yAxis.setName("Repetitions");


        //Calling Methods
        repsChart(axisValues, yAxis);
        setsChart(axisValues, yAxis);
        weightChart(axisValues, yAxis);
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
        lineChart();
    }

    //Edit Duration
    public void btn_showMessage(View view){
        final AlertDialog.Builder alert = new AlertDialog.Builder(UserProgress.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_pop_up_window_duration,null);
        EditText txt_inputText = (EditText)mView.findViewById(R.id.txt_input);
        Button btn_cancel = (Button)mView.findViewById(R.id.btn_cancel);
        Button btn_okay = (Button)mView.findViewById(R.id.btn_okay);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DurationText.setText(txt_inputText.getText().toString()+"/"+durationInt+" Min");
                alertDialog.dismiss();

                todayChart();


                double inputText=Double.parseDouble(txt_inputText.getText().toString());
                Log.d("I'm Here and my value:", String.valueOf(inputText));
                time= inputText * 60; // to convert minutes to seconds
                //SAVE IT TO DB (time)

                db = FirebaseFirestore.getInstance();
                db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+week).document("day"+currDay).update("duration", time);

                todayChart();










                read();

            }
        });
        alertDialog.show();
    }





    //Edit Reps , Weight, sets
    public void btn_showEditLine(View view){
        final AlertDialog.Builder alert = new AlertDialog.Builder(UserProgress.this);
        View lView = getLayoutInflater().inflate(R.layout.activity_pop_up_window_line_chart,null);
        EditText reps_inputText = (EditText)lView.findViewById(R.id.reps_input);
        EditText weight_inputText = (EditText)lView.findViewById(R.id.weight_input);
        EditText sets_inputText = (EditText)lView.findViewById(R.id.sets_input);

        Button btn_cancel_line = (Button)lView.findViewById(R.id.btn_cancel_line);
        Button btn_okay_line = (Button)lView.findViewById(R.id.btn_okay_line);

        TextView exrciseDetails =(TextView)lView.findViewById(R.id.LogexerciseDetail);

        exrciseDetails.setText("Log "+ selectedExr+" details:");




        alert.setView(lView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_okay_line.setOnClickListener(new View.OnClickListener() {
            double inputReps, inputWeight, inputSets;
            @Override
            public void onClick(View v) {
//                DurationText.setText(txt_inputText.getText().toString()+"/"+durationInt+" Min");
                alertDialog.dismiss();
                String repsCheck = reps_inputText.getText().toString();
                if(!repsCheck.matches("")) {
                    inputReps = Double.parseDouble(reps_inputText.getText().toString());

                    if (inputReps < 10 || inputReps > 50) {
                        reps_inputText.setError("your reps is out of range!");
                        flag = false;


                    } else {
                        //SAVE TO DB ;
                        flag = true;
                    }
                }

                String weightCheck = weight_inputText.getText().toString();
                if (!weightCheck.matches("")) {
                    inputWeight = Double.parseDouble(weight_inputText.getText().toString());
                    if (inputWeight < 0 || inputWeight > 101) {
                        reps_inputText.setError("your Weight is out of range!");
                        flag = false;


                    } else {
                        //SAVE TO DB ;
//                        ({"ExerciseType": "x"},
//                        {"ExerciseDetail": {"reps": 1, "weight": 3, "sets": 2});
                        flag = true;
                    }
                }

                String setsCheck = sets_inputText.getText().toString();
                if (!setsCheck.matches("")) {
                    inputSets = Double.parseDouble(sets_inputText.getText().toString());

                    if (inputSets < 1 || inputReps > 20) {
                        reps_inputText.setError("your sets is out of range!");
                        flag = false;


                    } else {
                        //SAVE TO DB ;
                        flag = true;
                    }
                }



                Log.d("I'm Here and my Reps:", String.valueOf(inputReps));
                Log.d("I'm Here and my weight:", String.valueOf(inputWeight));
                Log.d("I'm Here and my Sets:", String.valueOf(inputSets));
                //SAVE IT TO DB (time)


                db = FirebaseFirestore.getInstance();
                DocumentReference docRef= db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+week).document("day"+currDay);
//                //docRef.set("Reps",{10,10});

                Map<String, Object> progress = new HashMap<>();
                Map<String, Object> progressDetail = new HashMap<>();
//                progressDetail.put("ExerciseType", selectedExr);
                progressDetail.put("Reps", inputReps);
                progressDetail.put("Weight", inputWeight);
                progressDetail.put("Sets", inputSets);
                progress.put("Type", selectedExr);
                progress.put("ExerciseDetail", Arrays.asList(progressDetail));

                db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+week).document("day"+currDay).update("Exercise", FieldValue.arrayUnion(progress)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Saved Data:", "Success");


//                        userExer

                        progressDetail.get("Reps");






                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Saved Data:", "Error!!");
                    }
                });

                read();

            }
        });

        alertDialog.show();


    }












    //First Chart
    public void todayChart(){

//        read();


        if(level.equalsIgnoreCase("Beginner")){
            //Duration 30 Min
            durationInt=30;
            finalDuration= (int)((time/60)*100/30);


        }else if (level.equalsIgnoreCase("Intermediate")){
            durationInt=45;
            finalDuration= (int)((time/60)*100/45);

        }else{
            durationInt=60;
            finalDuration= (int)((time/60)*100/60);

        }

//        SharedPreferences sharedPreferences = getSharedPreferences(shared,MODE_PRIVATE);
//        time= Double.parseDouble(sharedPreferences.getString("duration","0.0"));
//        int intime= Integer.parseInt(sharedPreferences.getString("duration","0.0"));


//        DurationText.setText(durationInt+" Min"+ (time/60)+" curr:" +currDay+" w:"+week);
//        int timeinInt= Integer.parseInt(time);
        DurationText.setText(Math.round(time/60)+"/"+durationInt+" Min");
//        testDuration.setText(Double.toString(Math.round(time/60)));
//
//        testDuration.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                // If the event is a key-down event on the "enter" button
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    // Perform action on key press
//                   double durationEdited= Double.parseDouble(testDuration.getText().toString());
//                    Log.d("myTag", String.valueOf(durationEdited));
//                    //todayChart();
//                    return true;
//                }
//                return false;
//            }
//        });


        pcM1.setText(Math.round(finalDuration) + "");
        int remi = 100 - Math.round(finalDuration);
        if (remi < 0){
            remi = 0;
        }
        pcM2.setText(remi + "");


        if (remi == 100) {
            pieChart.addPieSlice(
                    new PieModel(
                            "",
                            Integer.parseInt(pcM2.getText().toString()),
                            Color.parseColor("#D3C6B4")));
            pieChart.startAnimation();
        } else {
            // Set the data and color to the pie chart "Today"
            pieChart.addPieSlice(
                    new PieModel(
                            "",
                            Integer.parseInt(pcM1.getText().toString()),
                            Color.parseColor("#66BB6A")));
            pieChart.addPieSlice(
                    new PieModel(
                            "",
                            Integer.parseInt(pcM2.getText().toString()),
                            Color.parseColor("#D3C6B4")));
            pieChart.startAnimation();
        }


    }


    public void lineChart (){


        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dayAr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
    }

    public void repsChart(List axisValues, Axis yAxis){




        List<Double> weekReps = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        CollectionReference coll = db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+week);
        coll.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                Log.d("day:", Integer.parseInt(currDay)+"");
                ArrayList<ArrayList<HashMap<String, Object>>> dayExcList = new ArrayList<>();
                for(int i = 0; i < Integer.parseInt(currDay); i++){
                    if(value.getDocuments().get(i).getData().get("Exercise") == null){
                        HashMap<String, Object> emptyMap = new HashMap<>();
                        emptyMap.put("empty", "null");
                        emptyMap.put("data", "null");
                        ArrayList<HashMap<String,Object>> emptyList = new ArrayList<>();
                        emptyList.add(emptyMap);
                        dayExcList.add(emptyList);
                    }else {
                        dayExcList.add((ArrayList) value.getDocuments().get(i).getData().get("Exercise"));
                    }
                }
                int counter = 0;
                Boolean addedReps = false;
                Log.d("List size:", dayExcList.size()+"");
                for(int i = 0; i < dayExcList.size(); i++) {
                    Log.d("ExDay:", i+"");
                    Log.d("ValueOfColl:", dayExcList.get(i)+"");
                    Map<String, Object> Exercises = dayExcList.get(i).get(0);
                    Boolean exerciseTypeMached = false;
                    Boolean checkEmpty = false;
                    for (Map.Entry<String, Object> entry : Exercises.entrySet()) {
                        if(entry.getKey().equals("empty")){
                            weekReps.add(0.0);
                            checkEmpty = true;
                        }else {
                            checkEmpty = false;
                            Log.d("Selected Exer111", selectedExr);
                            if (entry.getKey().equals("Type") && entry.getValue().toString().equals(selectedExr)) {
                                Log.d("Condition1: ", "Passed!");
                                Log.d("Selected Exer", selectedExr);

                                exerciseTypeMached = true;
                            }
                            if (entry.getKey().toString().equals("ExerciseDetail") && exerciseTypeMached) {
                                Log.d("Condition1: ", "Passed!");
                                Map<String, Object> detail = (HashMap) ((ArrayList) entry.getValue()).get(0);
                                for (Map.Entry<String, Object> inner : detail.entrySet()) {
                                    if (inner.getKey().equals("Reps")) {
                                        weekReps.add((Double) inner.getValue());
                                        addedReps = true;
                                    }
                                }
                            }
                        }
                    }
                    if(counter % 2 == 0 && !addedReps && !checkEmpty){
                        weekReps.add(10.0);
                    }else{
                        addedReps = false;
                    }
                    Log.d("Reps", weekReps.toString());
                    counter++;
                    Log.d("Counter:", counter+"");
                }
                Log.d("WeekReps:" , weekReps.toString());
                List yAxisValuesReps = new ArrayList();
                Line RepsLine = new Line(yAxisValuesReps).setColor(Color.parseColor("#FFA726"));
                for (int R = 0; R < weekReps.size(); R++) {
                    yAxisValuesReps.add(new PointValue(R, Float.parseFloat(weekReps.get(R).toString())));
                }
                List lineReps = new ArrayList();
                lineReps.add(RepsLine);


                LineChartData dataReps = new LineChartData();
                dataReps.setLines(lineReps);

                Axis axisReps = new Axis();
                axisReps.setValues(axisValues);
                axisReps.setTextSize(16);
                axisReps.setTextColor(Color.parseColor("#024265"));
                dataReps.setAxisXBottom(axisReps);


                yAxis.setTextColor(Color.parseColor("#024265"));
                yAxis.setTextSize(16);
                dataReps.setAxisYLeft(yAxis);

                lineChartViewReps.setLineChartData(dataReps);
                Viewport viewportReps = new Viewport(lineChartViewReps.getMaximumViewport());
                viewportReps.top = 50;
                lineChartViewReps.setMaximumViewport(viewportReps);
                lineChartViewReps.setCurrentViewport(viewportReps);

//        What chart Will appear
                CardViewReps.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yAxis.setName("Repetitions");
                        lineChartViewWeight.setVisibility(View.INVISIBLE);
                        lineChartViewReps.setVisibility(View.VISIBLE);
                        lineChartViewSets.setVisibility(View.INVISIBLE);

                    }
                }));

            }
        });




//
//        List yAxisValuesReps = new ArrayList();
//        Line RepsLine = new Line(yAxisValuesReps).setColor(Color.parseColor("#FFA726"));
//        for (int R = 0; R < yAxisDataReps.length; R++) {
//            yAxisValuesReps.add(new PointValue(R,yAxisDataReps[R]));
//        }











    }

    public void setsChart(List axisValues, Axis yAxis){

        List<Double> weekSets = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        CollectionReference coll = db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+week);
        coll.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                Log.d("day:", Integer.parseInt(currDay)+"");
                ArrayList<ArrayList<HashMap<String, Object>>> dayExcList = new ArrayList<>();
                for(int i = 0; i < Integer.parseInt(currDay); i++){
                    if(value.getDocuments().get(i).getData().get("Exercise") == null){
                        HashMap<String, Object> emptyMap = new HashMap<>();
                        emptyMap.put("empty", "null");
                        emptyMap.put("data", "null");
                        ArrayList<HashMap<String,Object>> emptyList = new ArrayList<>();
                        emptyList.add(emptyMap);
                        dayExcList.add(emptyList);
                    }else {
                        dayExcList.add((ArrayList) value.getDocuments().get(i).getData().get("Exercise"));
                    }
                }
                int counter = 0;
                Boolean addedSets = false;
                Log.d("List size:", dayExcList.size()+"");
                for(int i = 0; i < dayExcList.size(); i++) {
                    Log.d("ExDay:", i+"");
                    Log.d("ValueOfColl:", dayExcList.get(i)+"");
                    Map<String, Object> Exercises = dayExcList.get(i).get(0);
                    Boolean exerciseTypeMached = false;
                    Boolean checkEmpty = false;
                    for (Map.Entry<String, Object> entry : Exercises.entrySet()) {
                        if(entry.getKey().equals("empty")){
                            weekSets.add(0.0);
                            checkEmpty = true;
                        }else {
                            checkEmpty = false;
                            if (entry.getKey().equals("Type") && entry.getValue().toString().equals(selectedExr)) {
                                Log.d("Condition2: ", "Passed!");

                                exerciseTypeMached = true;
                            }
                            if (entry.getKey().toString().equals("ExerciseDetail") && exerciseTypeMached) {
                                Log.d("Condition2: ", "Passed!");
                                Map<String, Object> detail = (HashMap) ((ArrayList) entry.getValue()).get(0);
                                for (Map.Entry<String, Object> inner : detail.entrySet()) {
                                    if (inner.getKey().equals("Sets")) {
                                        weekSets.add((Double) inner.getValue());
                                        addedSets = true;
                                    }
                                }
                            }
                        }
                    }
                    if(counter % 2 == 0 && !addedSets && !checkEmpty){
                        weekSets.add(2.0);
                    }else{
                        addedSets = false;
                    }
                    Log.d("Sets", weekSets.toString());
                    counter++;
                }
                Log.d("WeekSets:" , weekSets.toString());
                List yAxisValuesSets = new ArrayList();
                Line SetsLine = new Line(yAxisValuesSets).setColor(Color.parseColor("#29B6F6"));

                for (int S = 0; S < weekSets.size(); S++) {
                    yAxisValuesSets.add(new PointValue(S, Float.parseFloat(weekSets.get(S).toString())));
                }


                List lineSets = new ArrayList();
                lineSets.add(SetsLine);

                LineChartData dataSets = new LineChartData();
                dataSets.setLines(lineSets);


                // Sets Line Chart

                Axis axisSets = new Axis();
                axisSets.setValues(axisValues);
                axisSets.setTextSize(16);
                axisSets.setTextColor(Color.parseColor("#024265"));
                dataSets.setAxisXBottom(axisSets);

                yAxis.setTextColor(Color.parseColor("#024265"));
                yAxis.setTextSize(16);
                dataSets.setAxisYLeft(yAxis);

                lineChartViewSets.setLineChartData(dataSets);
                Viewport viewportSets = new Viewport(lineChartViewSets.getMaximumViewport());
                viewportSets.top = 20;
                lineChartViewSets.setMaximumViewport(viewportSets);
                lineChartViewSets.setCurrentViewport(viewportSets);


//        What Chart Will Appear
                CardViewSets.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yAxis.setName("Sets");
                        lineChartViewWeight.setVisibility(View.INVISIBLE);
                        lineChartViewReps.setVisibility(View.INVISIBLE);
                        lineChartViewSets.setVisibility(View.VISIBLE);

                    }
                }));


            }
        });











    }

    public void weightChart(List axisValues, Axis yAxis){


        List<Double> weekWeight = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        CollectionReference coll = db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+week);
        coll.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                Log.d("day:", Integer.parseInt(currDay)+"");
                ArrayList<ArrayList<HashMap<String, Object>>> dayExcList = new ArrayList<>();
                for(int i = 0; i < Integer.parseInt(currDay); i++){
                    if(value.getDocuments().get(i).getData().get("Exercise") == null){
                        HashMap<String, Object> emptyMap = new HashMap<>();
                        emptyMap.put("empty", "null");
                        emptyMap.put("data", "null");
                        ArrayList<HashMap<String,Object>> emptyList = new ArrayList<>();
                        emptyList.add(emptyMap);
                        dayExcList.add(emptyList);
                    }else {
                        dayExcList.add((ArrayList) value.getDocuments().get(i).getData().get("Exercise"));
                    }
                }
                int counter = 0;
                Boolean addedWeight = false;
                Log.d("List size:", dayExcList.size()+"");
                for(int i = 0; i < dayExcList.size(); i++) {
                    Log.d("ExDay:", i+"");
                    Log.d("ValueOfColl:", dayExcList.get(i)+"");
                    Map<String, Object> Exercises = dayExcList.get(i).get(0);
                    Boolean exerciseTypeMached = false;
                    Boolean checkEmpty = false;
                    for (Map.Entry<String, Object> entry : Exercises.entrySet()) {
                        if(entry.getKey().equals("empty")){
                            weekWeight.add(0.0);
                            checkEmpty = true;
                        }else {
                            checkEmpty = false;
                            if (entry.getKey().equals("Type") && entry.getValue().toString().equals(selectedExr)) {
                                Log.d("Condition3: ", "Passed!");

                                exerciseTypeMached = true;
                            }
                            if (entry.getKey().toString().equals("ExerciseDetail") && exerciseTypeMached) {
                                Log.d("Condition3: ", "Passed!");
                                Map<String, Object> detail = (HashMap) ((ArrayList) entry.getValue()).get(0);
                                for (Map.Entry<String, Object> inner : detail.entrySet()) {
                                    if (inner.getKey().equals("Weight")) {
                                        weekWeight.add((Double) inner.getValue());
                                        addedWeight = true;
                                    }
                                }
                            }
                        }
                    }
                    if(counter % 2 == 0 && !addedWeight && !checkEmpty){
                        weekWeight.add(0.0);
                    }else{
                        addedWeight = false;
                    }
                    Log.d("Weight", weekWeight.toString());
                    counter++;
                }
                Log.d("WeekWeight:" , weekWeight.toString());
                List yAxisValuesWeight = new ArrayList();
                Line Weightline = new Line(yAxisValuesWeight).setColor(Color.parseColor("#66BB6A"));

                for (int W = 0; W < weekWeight.size(); W++) {
                    yAxisValuesWeight.add(new PointValue(W, Float.parseFloat(weekWeight.get(W).toString())));
                }

                List lineWeight = new ArrayList();
                lineWeight.add(Weightline);


                LineChartData dataWeight = new LineChartData();
                dataWeight.setLines(lineWeight);


//        yAxis.setName("Weight");

                Axis axisWeight = new Axis();
                axisWeight.setValues(axisValues);
                axisWeight.setTextSize(16);
                axisWeight.setTextColor(Color.parseColor("#024265"));
                dataWeight.setAxisXBottom(axisWeight);

                yAxis.setTextColor(Color.parseColor("#024265"));
                yAxis.setTextSize(16);
                dataWeight.setAxisYLeft(yAxis);

                lineChartViewWeight.setLineChartData(dataWeight);
                Viewport viewportWeight = new Viewport(lineChartViewWeight.getMaximumViewport());
                viewportWeight.top = 100;
                lineChartViewWeight.setMaximumViewport(viewportWeight);
                lineChartViewWeight.setCurrentViewport(viewportWeight);


                ///What Chart will appear

                CardViewWeight.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yAxis.setName("Weight");

                        lineChartViewWeight.setVisibility(View.VISIBLE);
                        lineChartViewReps.setVisibility(View.INVISIBLE);
                        lineChartViewSets.setVisibility(View.INVISIBLE);

                    }
                }));



            }
        });







    }


    public void weekChart(){

        SharedPreferences sharedPreferences = getSharedPreferences(shared,MODE_PRIVATE);
        done= sharedPreferences.getInt("sessionDone",0);


//        done=2;
//        SessionNo="4";
        tday= Integer.parseInt(SessionNo);


        if (SessionNo.equals("2")){

            pcDay1.setText(Integer.toString(50));


        }else if (SessionNo.equals("3")){

            pcDay1.setText(Integer.toString(33));


//            pcDay1=pcDay2=pcDay3=33.33;

        }else if (SessionNo.equals("4")){

            pcDay1.setText(Integer.toString(25));


        }else {
            pcDay1.setText(Integer.toString(20));



        }




//        else if(finalDuration!=(time/60)){
//            done--;
//        }




        int t=0;
        while(t< done) {

//            String c= COLORS[t];

            WeekpieChart.addPieSlice(
                    new PieModel(
                            "",
                            Integer.parseInt(pcDay1.getText().toString()),
                            Color.parseColor(COLORS[t])));

            t++;

        }
        int i=0;
//        5-5=0
        rem=tday-done; //5-4 =1
        while(i< rem) {
            WeekpieChart.addPieSlice(
                    new PieModel(
                            "",
                            Integer.parseInt(pcDay1.getText().toString()),
                            Color.parseColor("#D3C6B4")));

            i++;

        }



        WeekpieChart.startAnimation();

        finishedSession.setText(done+" of "+SessionNo+" Sessions completed");

        if (done==0){ //0 of 4 days 0/2 0/3 0/4 0/5
            String[] zeroText= {"This could be your best week ever.", "New week, new chances. Let's do this!", "Alright, let's make this happen.", "Remember that goal? Let's go get it!"};
            int randnum=r.nextInt(zeroText.length);
            sessionText.setText(zeroText[randnum]);



        }else if (tday==done){ // 4 of 4 days 2/2 3/3 4/4 5/5
            String[] complText= {"Yippee! You've finished the session for this week. "};
            int randnum= r.nextInt(complText.length);
            sessionText.setText(complText[randnum]);

        } else if (tday-done==1){ // 3 of 4 days 1/2 4/5 2/3 3/4
            String[] oneDayLeftText={"Almost there! You're in it to win it.","You're almost to your goal! Let's do this." };
            int randnum=r.nextInt(oneDayLeftText.length);
            sessionText.setText(oneDayLeftText[randnum]);

        } else if (done==1){ // 1/3 1/4 1/5
            String[] oneText= {"One day down! So far, so good.", "You're on your way to your goal!", "One day of progress! Looking good.", "You nailed your first day. Nice job!", "Great start! We like what we're seeing."};
            int randnum=r.nextInt(oneText.length);
            sessionText.setText(oneText[randnum]);

        } else if (done==2 ){// 2/4 2/5
            String[] secondText={"Second day down! Keep on trucking.", "You're on your way to your goal!"};

        }else if (done==3){// 3/5
            sessionText.setText("Almost there! You're in it to win it.");
        }


    }
    public void read(){
        db = FirebaseFirestore.getInstance();
        DocumentReference doc = db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+week).document("day"+currDay);
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                time = value.getDouble("duration");
//                testDuration.setText(0);
                Log.d("This is the time:", ""+time);
                if (time == null){
                    time = 0.0;
                }





                todayChart();
//                lineChart();
                weekChart();


            }
        });
    }


    public void readExerciseDetails(){

        DocumentReference documentReference = db.collection("progress").document(id).collection("WorkoutPlan").document(id).collection(id).document("day"+(currDay));
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                day = value.getString("plan");
                day = day.substring(2, day.length() - 3);
                dayAr = day.split("_");

                lineChart();


            }
        });




    }


    public void readExerciseName(){
        DocumentReference documentReference = db.collection("userProfile").document(id).collection("WorkoutPlan").document(id).collection(id).document("day"+(currDay));
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                day = value.getString("plan");
                day = day.substring(2, day.length() - 3);
                dayAr = day.split("_");

                lineChart();


            }
        });

    }

}