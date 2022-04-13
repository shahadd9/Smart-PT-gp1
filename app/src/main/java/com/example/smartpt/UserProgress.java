package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.google.firebase.firestore.FirebaseFirestore;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    private TextView pcM1, pcM2, pcM3, pcM4,finishedSession, sessionText, DurationText;
    PieChart pieChart, WeekpieChart, pieChart2;
    TextView pcDay1, pcDay2,pcDay3,pcDay4,pcDay5;
    private Spinner spin;
    public static int tday, fSession;
    String durationInString;
//    String level="Beg";//NEED TO REMOVE
    String[] users = { "Diamond push-up", "Weighted push-up", "Knee push-up", "Barbell bench press", "Stability ball decline push-up" };
    int beginnerDuration, intermediateDuration, advanceDuartion, finalDuartion;
    private int rem;
    String[] COLORS = new String[] { "#00AB78", "#00ab90","#007865","#bffff5", "#80ffeb"  };
    Random r= new Random();



    //line chart

    LineChartView lineChartViewWeight, lineChartViewReps, lineChartViewSets;
    String[] axisData = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};//Training days
    int[] yAxisDataSets = {2, 2, 2, 3, 2, 2, 3};
    int[] yAxisDataReps = {20, 20, 15, 30, 20, 20, 15};
    int[] yAxisDataWeights = {2, 2, 2, 2, 5,5, 5};
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
    private Double time;
    public final static String shared="sharedPrefs";
    private FirebaseFirestore db;
    private FirebaseAuth uAuth;
    private String id;
//    private String userIp;
    private int durationInt;
    private int done;
    private boolean exist;
    String dayAr[];






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_progress);
        pieChart2 = findViewById(R.id.piechartdate1);

//        tday=5;
//        fSession=4;
//        duration=20;

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
        pcDay2=findViewById(R.id.pcDay2);
        //SHAHAD DECLARATION
        pcDay3=findViewById(R.id.pcDay3);
        pcDay4=findViewById(R.id.pcDay4);
        pcDay5=findViewById(R.id.pcDay5);


        finishedSession=findViewById(R.id.FinishDay);
        sessionText=findViewById(R.id.sessionText);

        //line chart
        lineChartViewWeight = findViewById(R.id.chartWeight);
        lineChartViewReps= findViewById(R.id.chartReps);
        lineChartViewSets= findViewById((R.id.chartSets));

        CardViewWeight=findViewById(R.id.CardWeight);
        CardViewReps=findViewById(R.id.CardReps);
        CardViewSets=findViewById(R.id.CardSets);


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




//
//
//        spin = (Spinner) findViewById(R.id.spinner1);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dayAr);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spin.setAdapter(adapter);
//        spin.setOnItemSelectedListener(this);


        //line chart

        lineChartViewWeight = findViewById(R.id.chartWeight);
        lineChartViewReps= findViewById(R.id.chartReps);
        lineChartViewSets= findViewById((R.id.chartSets));


        CardViewWeight=findViewById(R.id.CardWeight);
        CardViewReps=findViewById(R.id.CardReps);
        CardViewSets=findViewById(R.id.CardSets);


//        todayChart();
//        lineChart();
//        weekChart();






    }
    //
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(), "Selected User: "+dayAr[position] ,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }








    //First Chart
    public void todayChart(){
//        read();


        if(level.equalsIgnoreCase("Beginner")){
            //Duration 30 Min
            durationInt=30;
            finalDuartion= (int)((time/60)*100/30);


        }else if (level.equalsIgnoreCase("Intermediate")){
            durationInt=45;
            finalDuartion= (int)((time/60)*100/45);

        }else{
            durationInt=60;
            finalDuartion= (int)((time/60)*100/60);

        }

        SharedPreferences sharedPreferences = getSharedPreferences(shared,MODE_PRIVATE);
//        time= Double.parseDouble(sharedPreferences.getString("duration","0.0"));


        DurationText.setText(durationInt+" Min"+ (time/60)+" curr:" +currDay+" w:"+week);
        pcM1.setText(finalDuartion+"");
        double remi=100-finalDuartion;
        if (remi<0){
            remi=0;
        }
        pcM2.setText(remi+"");


        // Set the data and color to the pie chart "Today"
        pieChart.addPieSlice(
                new PieModel(
                        "",
                        Integer.parseInt(pcM1.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "",
                        Float.parseFloat(pcM2.getText().toString()),
                        Color.parseColor("#D3C6B4")));

        pieChart.startAnimation();

    }


    public void lineChart (){


        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dayAr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        List axisValues = new ArrayList();


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

    public void repsChart(List axisValues, Axis yAxis){

        List yAxisValuesReps = new ArrayList();
        Line RepsLine = new Line(yAxisValuesReps).setColor(Color.parseColor("#FFA726"));
        for (int R = 0; R < yAxisDataReps.length; R++) {
            yAxisValuesReps.add(new PointValue(R, yAxisDataReps[R]));
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

    public void setsChart(List axisValues, Axis yAxis){

        List yAxisValuesSets = new ArrayList();
        Line SetsLine = new Line(yAxisValuesSets).setColor(Color.parseColor("#29B6F6"));

        for (int S = 0; S < yAxisDataSets.length; S++) {
            yAxisValuesSets.add(new PointValue(S, yAxisDataSets[S]));
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

    public void weightChart(List axisValues, Axis yAxis){

        List yAxisValuesWeight = new ArrayList();
        Line Weightline = new Line(yAxisValuesWeight).setColor(Color.parseColor("#66BB6A"));

        for (int W = 0; W < yAxisDataWeights.length; W++) {
            yAxisValuesWeight.add(new PointValue(W, yAxisDataWeights[W]));
        }

        List lineWeight = new ArrayList();
        lineWeight.add(Weightline);


        LineChartData dataWeight = new LineChartData();
        dataWeight.setLines(lineWeight);


//        yAxis.setName("Weight");
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

        finishedSession.setText(done+" of "+SessionNo+" Sessions complated");

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
            String[] oneText= {"One day dowm! So far, so good.", "You're on your way to your goal!", "One day of progress! Looking good.", "You nailed your first day. Nice job!", "Great start! We like what we're seeing."};
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

                todayChart();
//                lineChart();
                weekChart();


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
