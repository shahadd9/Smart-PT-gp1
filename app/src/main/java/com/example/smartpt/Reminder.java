package com.example.smartpt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;


public class Reminder extends BroadcastReceiver{

    Random r= new Random();

    @Override
    public void onReceive(Context context, Intent intent){

        String[] ReminderText={"Keep up the good work!!","Let's do this.","Remember that goal? Let's go get it!", "You're on your way to your goal!"};
        int randnum=r.nextInt(ReminderText.length);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"notify")
                .setSmallIcon(R.drawable.logo1)
                .setContentTitle("Reminder")
                .setContentText(ReminderText[randnum])
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());
    }




}