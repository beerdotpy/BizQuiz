package com.BizQuiz;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;


public class DisplayNotification extends Activity {	 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	

      //---get the notification ID for the notification; 
        // passed in by the MainActivity---
        int notifID = getIntent().getExtras().getInt("NotifID");
 
        
        NotificationManager nm = (NotificationManager)
            getSystemService(NOTIFICATION_SERVICE);
        
//        NotificationCompat.Builder builder=
//        		new NotificationCompat.Builder(this)
//        .setSmallIcon(R.drawable.ic_launcher)
//        .setContentTitle("Message")
//        .setContentText("Notification");
//        nm.notify(notifID, builder.build());

        finish();
	}
         
}
