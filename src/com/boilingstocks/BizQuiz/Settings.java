package com.boilingstocks.BizQuiz;



import java.util.prefs.Preferences;

import com.BizQuiz.R;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class Settings extends PreferenceActivity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
		
	
		
		Preference pref=findPreference("profile");
		pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				Intent launch=new Intent(Settings.this,Profile.class);
				startActivity(launch);
				
				return true;
			}
		});

	
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
   			startActivity(new Intent(this, Settings.class));
   			finish();
   			return true;
   		case R.id.menu_about:
   			startActivity(new Intent(this, AboutUs.class));
   			finish();
   			return true;
   		case R.id.menu_exit:
   			Intent intent = new Intent(Intent.ACTION_MAIN);
   			intent.addCategory(Intent.CATEGORY_HOME);
   			finish();
   			startActivity(intent);
   			return true;	
   		case R.id.menu_feedback:
   			Intent intent1=new Intent(this,Feedback.class);
   			finish();
   			startActivity(intent1);
   			return true;
   		case R.id.menu_statistics:
 		     startActivity(new Intent(this,Statistics.class));
 		     finish();
 		     return true;
   		case R.id.menu_archive:
   			Intent intent3 = new Intent(this,ArchiveMonthsActivity.class);
   			finish();
   			startActivity(intent3);
   			return true;	
  		
 		     
   		default:
   			return super.onOptionsItemSelected(item);
   		}
    
       }

	@Override
    public void onBackPressed() {
       Log.d("CDA", "onBackPressed Called");
       Intent back = new Intent(this,Categories.class);
//       setIntent.addCategory(Intent.CATEGORY_HOME);
//       setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(back);
    }
}
