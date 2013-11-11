package in.bizquiz.app;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;



public class DisplayNotification extends Activity {	 
	

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	

       
      //---get the notification ID for the notification; 
        // passed in by the MainActivity---
        int notifID = getIntent().getExtras().getInt("NotifID");
        
        Intent intent = new Intent(this,Home.class);
        PendingIntent displayIntent = PendingIntent.getActivity(
 	           this, 0, intent, 0);        
 
       
        	
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);;
        
        NotificationCompat.Builder builder=
        		new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.bqicon)
        .setContentIntent(displayIntent)
        .setContentTitle("BizQuiz")
        .setContentText("Q Of The Day Updated!")
        .setAutoCancel(true);
        nm.notify(1, builder.build());
        
        finish();
	}
         
}
