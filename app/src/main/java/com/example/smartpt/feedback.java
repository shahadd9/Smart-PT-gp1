package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hsalf.smileyrating.SmileyRating;


import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class feedback extends AppCompatActivity {

    private SmileyRating feedback1;
    private SmileyRating feedback2;
    private TextView q1;
    private TextView q2;
    private Button submitFeedback;
    public int rating1=-1;
    public int rating2=-1;
    public String answer1;
    public String answer2;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private FirebaseFirestore db;
    private FirebaseAuth uAuth;
    private String id;
    private String currDay;
    private int week;
//  private String userIp;
  private String SessionNo,level;
  private Map<String, Object> user = new HashMap<>();
    private String date;
    private String q1List[]={"How did you find your session?","How was the session?","How do you rate the overall session?"};//answers: from Great to Terrible
    private String q2List[]={"How simple was it?","How hard was it?","How was the difficulty level during this session?"};//answers: from very easy to very hard


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

//######## Database #########
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//         userIp = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        //to get user email
        uAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = uAuth.getCurrentUser();
        id = curUser.getEmail();
         db = FirebaseFirestore.getInstance();

        currDay=getIntent().getStringExtra("currDay");
        week=getIntent().getIntExtra("week",0);
        SessionNo=getIntent().getStringExtra("SessionNo");
        level =getIntent().getStringExtra("level");


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());
        submitFeedback=(Button)findViewById(R.id.submitFeedback);
        feedback1 = (SmileyRating) findViewById(R.id.feedback1);
        feedback2 = (SmileyRating) findViewById(R.id.feedback2);
        q1=(TextView)findViewById(R.id.q1feedback1);
        q2=(TextView)findViewById(R.id.q2feedback2);

        Random rand=new Random();
        int num1=rand.nextInt(q1List.length);
        int num2=rand.nextInt(q2List.length);
        q1.setText(q1List[num1]);
        q2.setText(q2List[num2]);



        submitFeedback.setEnabled(false);

        feedback1.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                SmileyRating.Type smiley = feedback1.getSelectedSmiley();
//                if (SmileyRating.Type.GREAT == type) {
//                    Toast.makeText(feedback.this, "Great", Toast.LENGTH_SHORT).show();
////                    answer1="Great";
//                }
//
//                if (SmileyRating.Type.GOOD == type) {
//                    Toast.makeText(feedback.this, "Good", Toast.LENGTH_SHORT).show();
////                    answer1="Good";
//                }
//
//                if (SmileyRating.Type.OKAY == type) {
//                    Toast.makeText(feedback.this, "Okay", Toast.LENGTH_SHORT).show();
////                    answer1="Okay";
//                }
//
//                if (SmileyRating.Type.BAD == type) {
//                    Toast.makeText(feedback.this, "Bad", Toast.LENGTH_SHORT).show();
////                    answer1="Bad";
//                }
//
//                if (SmileyRating.Type.TERRIBLE == type) {
//                    Toast.makeText(feedback.this, "Terrible", Toast.LENGTH_SHORT).show();
////                    answer1="Terrible";
//                }
                answer1=type.toString();
                rating1 = type.getRating();
//######## Database #########
                user.put("answer1", answer1);
                user.put("date", date);


                submitFeedback.setEnabled(rating1 != -1 && rating2 != -1);
            }
        });

        feedback2.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                SmileyRating.Type smiley = feedback2.getSelectedSmiley();
                if (SmileyRating.Type.GREAT == type) {
//                    Toast.makeText(feedback.this, "very easy", Toast.LENGTH_SHORT).show();
                    answer2="VERY EASY";
                }

                if (SmileyRating.Type.GOOD == type) {
//                    Toast.makeText(feedback.this, "easy", Toast.LENGTH_SHORT).show();
                    answer2="EASY";
                }

                if (SmileyRating.Type.OKAY == type) {
//                    Toast.makeText(feedback.this, "normal", Toast.LENGTH_SHORT).show();
                    answer2="NORMAL";
                }

                if (SmileyRating.Type.BAD == type) {
//                    Toast.makeText(feedback.this, "hard", Toast.LENGTH_SHORT).show();
                    answer2="HARD";
                }

                if (SmileyRating.Type.TERRIBLE == type) {
//                    Toast.makeText(feedback.this, "very hard", Toast.LENGTH_SHORT).show();
                    answer2="VERY HARD";
                }
                rating2=type.getRating();
//######## Database #########
                user.put("answer2", answer2);
                submitFeedback.setEnabled(rating1 != -1 && rating2 != -1);

            }
        });

        feedback2.setTitle(SmileyRating.Type.GREAT, "Very easy");
        feedback2.setTitle(SmileyRating.Type.GOOD, "Easy");
        feedback2.setTitle(SmileyRating.Type.OKAY, "Normal");
        feedback2.setTitle(SmileyRating.Type.BAD, "Hard");
        feedback2.setTitle(SmileyRating.Type.TERRIBLE, "Ver hard");

        feedback1.setFaceBackgroundColor(SmileyRating.Type.GREAT, Color.rgb(139,213,130));
        feedback2.setFaceBackgroundColor(SmileyRating.Type.GREAT, Color.rgb(139,213,130));

        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Progress").document(id).collection("index").document("weeks").collection("week"+week).document("day"+currDay).collection("feedback").document("feedback"+currDay).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override

                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            if (rating1 != -1 && rating2 != -1) {
                                Intent intent= new Intent(feedback.this, PlanView.class);
                                intent.putExtra("SessionNo",SessionNo);
                                intent.putExtra("level",level);
                                intent.putExtra("currDay",currDay);
                                intent.putExtra("week",week);
                                startActivity(intent);
                            } else {
                                Toast.makeText(com.example.smartpt.feedback.this, "feedback submitted", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

    }
}