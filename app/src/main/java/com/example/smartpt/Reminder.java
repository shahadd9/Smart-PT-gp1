package com.example.smartpt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class Reminder extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"notify")
                .setSmallIcon(R.drawable.logo1)
                .setContentTitle("Reminder")
                .setContentText("It's not about the results but the progress!\r\nKeep up the good work!!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());
    }




}