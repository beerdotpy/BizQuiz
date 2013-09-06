package com.BizQuiz;

import java.sql.Date;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity {
	
	SharedPreferences sp; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_home);
		
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);                 
	     
		   Date date=new Date(0);  
	       //---get current date and time---
	       Calendar cal_alarm = Calendar.getInstance();       
	       Calendar cal_now=Calendar.getInstance();
	       //---sets the time for the alarm to trigger---      
	       cal_alarm.set(Calendar.HOUR, 6);
	       cal_alarm.set(Calendar.MINUTE, 00);
	       cal_alarm.set(Calendar.SECOND, 0);
	       if(cal_alarm.before(cal_now)){//if its in the past increment
	           cal_alarm.add(Calendar.DATE,1);
	       }
	       //---PendingIntent to launch activity when the alarm triggers---                    
	       Intent i = new Intent(this,DisplayNotification.class);

	       //---assign an ID of 1---
	       i.putExtra("NotifID", 1);                                

	       PendingIntent displayIntent = PendingIntent.getActivity(
	           this, 0, i, 0);               

	       //---sets the alarm to trigger---
	       alarmManager.set(AlarmManager.RTC_WAKEUP, 
	           cal_alarm.getTimeInMillis(), displayIntent);  

	       

       sp=this.getSharedPreferences("First_run", MODE_PRIVATE);
       Boolean check=sp.getBoolean("Firstrun", true);
       
       Log.d("firstrun",Boolean.toString(check));
       if(check){

    	   Intent register=new Intent(Home.this,Register.class);
			startActivity(register);
   
       }else{
 
			Intent categories=new Intent(Home.this,Categories.class);
			startActivity(categories);

       }
	
	}       
   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
   		// Inflate the menu; this adds items to the action bar if it is present.
   		getMenuInflater().inflate(R.menu.activity_home, menu);
   		return true;
   	}
        

      
	public boolean onOptionsItemSelected(MenuItem item) {

   		switch (item.getItemId()) {
   		case R.id.menu_settings:
   			//startActivity(new Intent(Home.this, Settings.class));
   			return true;
   		case R.id.menu_about:
   			startActivity(new Intent(this, AboutUs.class));
   			return true;
   	    
   		default:
   			return super.onOptionsItemSelected(item);
   		}
    
       }
	
}