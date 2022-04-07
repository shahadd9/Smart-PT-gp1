package com.example.smartpt;

import static com.example.smartpt.TrainingTime.tTime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TrainingDuration extends AppCompatActivity {
    public static String tDuration;
    private RadioButton mor;
    private RadioButton noon;
    private RadioButton ev;
    private Button time;
    private FirebaseFirestore db;
    private String userIp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_duration);

        db = FirebaseFirestore.getInstance();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        mor=findViewById(R.id.mor);
        noon=findViewById(R.id.noon);
        ev=findViewById(R.id.ev);
        time=findViewById(R.id.time);
        tDuration=30+"";

        createNotificationChannel();

        time.setOnClickListener(new View.OnClickListener() {
            @Override



            public void onClick(View v) {



                Map<String,Object> user = new HashMap<>();
                user.put("TrainingDuration",tDuration);
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

                startActivity(new Intent(TrainingDuration.this, LoadPa.class));

                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(TrainingDuration.this,"Reminder Set",duration).show();


                Intent intent =new Intent(TrainingDuration.this,Reminder.class);
                PendingIntent pendingIntent= PendingIntent.getBroadcast(TrainingDuration.this, 0, intent, 0);
                AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
             //   calendar.set(Calendar.HOUR_OF_DAY, 9);
             //   calendar.set(Calendar.MINUTE, 00);
               // calendar.set(Calendar.SECOND, 00);
               // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                TrainingTime time= new TrainingTime();
               String Rtime=tTime;


                if (Rtime=="mor"){
                calendar.set(Calendar.HOUR_OF_DAY, 7);
                calendar.set(Calendar.MINUTE, 00);
                calendar.set(Calendar.SECOND, 00);}

                if (Rtime=="noon"){
                    calendar.set(Calendar.HOUR_OF_DAY, 14);
                    calendar.set(Calendar.MINUTE, 00);
                    calendar.set(Calendar.SECOND, 00);}

                if (Rtime=="ev"){
                    calendar.set(Calendar.HOUR_OF_DAY, 22);
                    calendar.set(Calendar.MINUTE, 00);
                    calendar.set(Calendar.SECOND, 00);}
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


            }
        });






    }

    public void rntn(View v){
        if(mor.isChecked()){
            tDuration=30+"";
        }
        else if(noon.isChecked()){
            tDuration=45+"";
        }
        else { tDuration= 60+"";}

    }

    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name="ReminderChannel";
            String description = "Channel for Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel=new NotificationChannel("notify", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}


