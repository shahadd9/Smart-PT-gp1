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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.google.firebase.firestore.FirebaseFirestore;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

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




public class UserProgress extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    double set , rep, Res;
    private String durationDis;
    int s, r, re,d;

    private FirebaseFirestore db;
    public final static String shared="sharedPrefs";
    private String SessionNo;
    private String numDays,level;
    private int finalDuartion;
    String days;
    private int  FBindex ;
    private Double FBindexD;
    private String durationA,currDay;
//    int finishedDuration=20;
    private Double time;

    private int durationInt;

    TextView pcM1, pcM2, pcM3, pcM4,finishedSession, sessionText, DurationText,test;
    PieChart pieChart, WeekpieChart;
    TextView pcDay1, pcDay2, pcDay3, pcDay4, pcDay5;
    public static int tday, fSession;
    //    String durationString=tDuration;
//    int duration=Integer.parseInt(durationString);
//    String durationInString=(duration+" Min");
//    String level="Beg";//NEED TO REMOVE
    String[] exercises = { "Suresh Dasari", "Trishika Dasari", "Rohini Alavala", "Praveen Kumar", "Madhav Sai" };

    private int week;

    //line chart
    LineChartView lineChartView;
    String[] axisData = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};//Training days
    int[] yAxisDataSets = {2, 2, 2, 3, 2, 2, 3};
    int[] yAxisDataReps = {20, 20, 15, 30, 20, 20, 15};
    int[] yAxisDataWeights = {2, 2, 2, 2, 5,5, 5};





    //popup window
    private Context mContext;
    private Activity mActivity;

    private RelativeLayout mRelativeLayout;
    private Button mButton;

    private PopupWindow mPopupWindow;




    //Calandar
    EditText edittext;
    Button button;
    Calendar calendar;
    int year,month,day;
    private TextView thedate;
    private Button btngocalendar;


//    private int finalDuartion;

    private String userIp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_progress);

        currDay=getIntent().getStringExtra("currDay");
        week=getIntent().getIntExtra("week",0);
        SessionNo=getIntent().getStringExtra("SessionNo");
        level =getIntent().getStringExtra("level");
        pcM1 = findViewById(R.id.pcM1);
        pcM2 = findViewById(R.id.pcM2);
        test=findViewById(R.id.Teest);

//        pcM3 = findViewById(R.id.pcM3);
//        pcM4 = findViewById(R.id.pcM4);
        pieChart = findViewById(R.id.piechart);
        DurationText=findViewById(R.id.DurationText);
        //Pie chart "This Week progress"
        WeekpieChart = findViewById(R.id.Weekpiechart);
        pcDay1= findViewById(R.id.pcDay1);
        pcDay2=findViewById(R.id.pcDay2);
        finishedSession=findViewById(R.id.FinishDay);
        sessionText=findViewById(R.id.sessionText);
        Map<String, Object> user = new HashMap<>();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        read();
        String w= week+"";
        String cr=currDay+"";

        readWC(w,cr);
        //get data from database
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference =  db.collection("userProfile").document(userIp);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    numDays=value.getString("TrainingdaysNum");

                } else {
//                    Toast.makeText(PlanView.this, "Document not exist", Toast.LENGTH_SHORT).show();
                }

            }
        });








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
//                        }
//                        else if(SessionNo.equals("3")){
//                            i.putExtra("SessionNo",SessionNo);
//                            i.putExtra("level",level);
//                            startActivity(i);
//                            overridePendingTransition(0, 0);
//                            finish();
//                        }
//                        else if(SessionNo.equals("4")){
//                            i.putExtra("SessionNo",SessionNo);
//                            i.putExtra("level",level);
//                            startActivity(i);
//                            overridePendingTransition(0, 0);
//                            finish();
//                        }
//                        else if(SessionNo.equals("5")) {
//                            i.putExtra("SessionNo", SessionNo);
//                            i.putExtra("level", level);
//                            startActivity(i);
//                            overridePendingTransition(0, 0);
//                            finish();
//                        }
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
//                        } else if (SessionNo.equals("3")) {
//                            i.putExtra("SessionNo", SessionNo);
//                            i.putExtra("level", level);
//                            startActivity(i);
//                            overridePendingTransition(0, 0);
//                            finish();
//                        } else if (SessionNo.equals("4")) {
//                            i.putExtra("SessionNo", SessionNo);
//                            i.putExtra("level", level);
//                            startActivity(i);
//                            overridePendingTransition(0, 0);
//                            finish();
//                        } else if (SessionNo.equals("5")) {
//                            i.putExtra("SessionNo", SessionNo);
//                            i.putExtra("level", level);
//                            startActivity(i);
//                            overridePendingTransition(0, 0);
//                            finish();
//                        }

                    case R.id.progress:


                }
                return false;
            }
        });

//        int durationInInt = Integer.parseInt(durationDis);
//        int finalDuartion= (int) (finishedDuration*100/durationInInt);




//        pcM1.setText(durationDis+"");//NEED MODIFY

//        pcM1.setText(30+"");//NEED MODIFY
//NEED MODIFY
//        pcM2.setText(30+"");

        SharedPreferences sharedPreferences = getSharedPreferences(shared,MODE_PRIVATE);
        time= Double.parseDouble(sharedPreferences.getString("duration","0"));
        test.setText(time+"");
        pcM1.setText(durationInt+"");//NEED MODIFY
        pcM2.setText(time/60+"");
        pieChart.addPieSlice(
                new PieModel(
                        "",
                        Float.parseFloat(pcM1.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "",
                        Float.parseFloat(pcM2.getText().toString()),
                        Color.parseColor("#D3C6B4")));

        pieChart.startAnimation();



        ////////////

        //set the Pie chart (WEEK )


        WeekpieChart = findViewById(R.id.Weekpiechart);
        pcDay1= findViewById(R.id.pcDay1);
        pcDay2=findViewById(R.id.pcDay2);


        if(SessionNo.equals("2")){
            pcDay1.setText(Integer.toString(50));
            pcDay2.setText(Integer.toString(50));
        }
        else if(SessionNo.equals("3")){
            pcDay1.setText(Integer.toString(33));
            pcDay2.setText(Integer.toString(33));
        }
        else if(SessionNo.equals("4")){
            pcDay1.setText(Integer.toString(25));
            pcDay2.setText(Integer.toString(25));

        }
        else {

            pcDay1.setText(Integer.toString(20));
            pcDay2.setText(Integer.toString(20));


        }


        ///WEEEEEEKKK PIE CHART


        String[] COLORS = new String[] { "#00AB78", "#00ab90","#007865","#bffff5", "#80ffeb"  };
        Random r= new Random();

        int tday= Integer.parseInt(SessionNo);

        int rem=tday-fSession; //5-4 =1


        int t=0;
        while(t< fSession) {

            String c= COLORS[t];

            WeekpieChart.addPieSlice(
                    new PieModel(
                            "",
                            Integer.parseInt(pcDay1.getText().toString()),
                            Color.parseColor(c)));

            t++;

        }
        int i=0;
//        5-5=0
        while(i< rem) {
            WeekpieChart.addPieSlice(
                    new PieModel(
                            "",
                            Integer.parseInt(pcDay1.getText().toString()),
                            Color.parseColor("#D3C6B4")));

            i++;

        }


        WeekpieChart.startAnimation();

        finishedSession.setText(fSession+" of "+tday+" Sessions complated");

        if (fSession==0){ //0 of 4 days 0/2 0/3 0/4 0/5
            String[] zeroText= {"This could be your best week ever.", "New week, new chances. Let's do this!", "Alright, let's make this happen.", "Remember that goal? Let's go get it!"};
            int randnum=r.nextInt(zeroText.length);
            sessionText.setText(zeroText[randnum]);



        }else if (tday==fSession){ // 4 of 4 days 2/2 3/3 4/4 5/5
            String[] complText= {"Yippee! You've finished the session for this week. "};
            int randnum= r.nextInt(complText.length);
            sessionText.setText(complText[randnum]);

        } else if (tday-fSession==1){ // 3 of 4 days 1/2 4/5 2/3 3/4
            String[] oneDayLeftText={"Almost there! You're in it to win it.","You're almost to your goal! Let's do this." };
            int randnum=r.nextInt(oneDayLeftText.length);
            sessionText.setText(oneDayLeftText[randnum]);

        } else if (fSession==1){ // 1/3 1/4 1/5
            String[] oneText= {"One day dowm! So far, so good.", "You're on your way to your goal!", "One day of progress! Looking good.", "You nailed your first day. Nice job!", "Great start! We like what we're seeing."};
            int randnum=r.nextInt(oneText.length);
            sessionText.setText(oneText[randnum]);

        } else if (fSession==2 ){// 2/4 2/5
            String[] secondText={"Second day down! Keep on trucking.", "You're on your way to your goal!"};

        }else if (fSession==3){// 3/5
            sessionText.setText("Almost there! You're in it to win it.");
        }




        ////////


        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exercises);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);



        //line chart

        lineChartView = findViewById(R.id.chart);

        List yAxisValuesReps = new ArrayList();
        List yAxisValuesSets = new ArrayList();
        List yAxisValuesWeight = new ArrayList();

        List axisValues = new ArrayList();

//
        Line RepsLine = new Line(yAxisValuesReps).setColor(Color.parseColor("#FFA726"));
        Line SetsLine = new Line(yAxisValuesSets).setColor(Color.parseColor("#29B6F6"));
        Line Weightline = new Line(yAxisValuesWeight).setColor(Color.parseColor("#66BB6A"));


        for (int k = 0; k < axisData.length; k++) {
            axisValues.add(k, new AxisValue(k).setLabel(axisData[k]));
        }

        for (int W = 0; W < yAxisDataWeights.length; W++) {
            yAxisValuesWeight.add(new PointValue(W, yAxisDataWeights[W]));
        }

        for (int S = 0; S < yAxisDataSets.length; S++) {
            yAxisValuesSets.add(new PointValue(S, yAxisDataSets[S]));
        }
        for (int R = 0; R < yAxisDataReps.length; R++) {
            yAxisValuesReps.add(new PointValue(R, yAxisDataReps[R]));
        }


        List lines = new ArrayList();
        lines.add(Weightline);
        lines.add(RepsLine);
        lines.add(SetsLine);


        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#024265"));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        yAxis.setName("The number of...");
        yAxis.setTextColor(Color.parseColor("#024265"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 60;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);




        ///pop up window

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = UserProgress.this;

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.userProgress);
        mButton = (Button) findViewById(R.id.editDuration);

        // Set a click listener for the text view
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Initialize a new instance of LayoutInflater service
//                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//
//                // Inflate the custom layout/view
////                View customView = inflater.inflate(R.layout.activity_pop_up_window_duration,null);
//
//                /*
//                    public PopupWindow (View contentView, int width, int height)
//                        Create a new non focusable popup window which can display the contentView.
//                        The dimension of the window must be passed to this constructor.
//
//                        The popup does not provide any background. This should be handled by
//                        the content view.
//
//                    Parameters
//                        contentView : the popup's content
//                        width : the popup's width
//                        height : the popup's height
//                */
//                // Initialize a new instance of popup window
//                mPopupWindow = new PopupWindow(
//                        customView,
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                );
//
//                // Set an elevation value for popup window
//                // Call requires API level 21
//                if(Build.VERSION.SDK_INT>=21){
//                    mPopupWindow.setElevation(5.0f);
//                }
//
//                // Get a reference for the custom view close button
////                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
//
//                // Set a click listener for the popup window close button
//                closeButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // Dismiss the popup window
//                        mPopupWindow.dismiss();
//                    }
//                });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
                // Finally, show the popup window at the center location of root relative layout
//                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
//            }
//        });

















    }

    private void readWC(String w, String cr) {
        DocumentReference d = db.collection("Progress").document(userIp).collection("index").document("weeks").collection("week"+w).document("day"+cr);
        d.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


                FBindexD= value.getDouble("exerciseIndex");
                FBindex=(int)Math.round(FBindexD);
//                time= value.getDouble("duration");

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(), "Selected User: "+exercises[position] ,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }

    public void read(){
        db = FirebaseFirestore.getInstance();
        DocumentReference doc = db.collection("userProfile").document(userIp).collection("WorkoutPlan").document(userIp);
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


                set=value.getDouble("sets");
                s=(int)set;
//                sets.setText(""+s+"");

                rep=value.getDouble("reps");
                r=(int)rep;

                durationDis=value.getString("duration");
                durationInt = Integer.parseInt(durationDis);
                DurationText.setText(durationDis+" Min");
//                test.setText("rep"+rep+"set"+set+durationDis+currDay+week+time);

//                finalDuartion= (int) (30*100/43);
//                finalDuartion=100;
//                pcM1.setText(finalDuartion+"");//NEED MODIFY
//                pcM1.setText(2+"");//NEED MODIFY
//                double remi=30-finalDuartion;//NEED MODIFY
////                pcM2.setText(remi+"");
////                pcM2.setText(1+"");
//                pcM1.setText(100+"");
//                pcM2.setText(60+"");
            }
        });
    }
}