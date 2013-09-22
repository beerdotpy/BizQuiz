package com.boilingstocks.BizQuiz;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.BizQuiz.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Category7 extends Activity {
	
	JSONArray jArray=new JSONArray();
	TextView fact;
	Button cat7_prev;
	Button cat7_next;
	String[] array_fact;
	int index=0;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_cat7);
       
       fact=(TextView) findViewById(R.id.fact);
       cat7_prev=(Button) findViewById(R.id.cat7_prev);
       cat7_next=(Button) findViewById(R.id.cat7_next);
       
       new Facts(Category7.this).execute("status");
       
       
       if(index==0){
    	   cat7_prev.setEnabled(false);
       }
       
       cat7_prev.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 
			index=index-1;
			setFact();
			
		}
	});
       
       cat7_next.setOnClickListener(new OnClickListener() {
   		
   		@Override
   		public void onClick(View v) {
   			 
   			index=index+1;
   			setFact();
   			
   		}
   	});
	}
	
	
	class Facts extends AsyncTask<String, Void, Boolean> {

		Context context;
		ProgressDialog pDialog;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    JSONParser jsonParser = new JSONParser();
	    String url="http://practice.site11.com/Category7.php";
	    
	    JSONObject json=new JSONObject();       
	       
	   public Facts(Context ctx) {
	    	this.context = ctx;
	        pDialog= new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute(){
			pDialog.setTitle("Please Wait...");
			pDialog.setMessage("Loading..");
			pDialog.setIndeterminate(true);
			
			pDialog.show();
		}
		
		@Override
		protected Boolean doInBackground(String... str) {
			
			
			params.add(new BasicNameValuePair("check",str[0]));
			
			json = jsonParser.makeHttpRequest(url, "GET", params);
					
			try {
				jArray= json.getJSONArray("fact");
				System.out.println("*****JARRAY*****"+jArray.length());
				
				array_fact=new String[jArray.length()];
				
				for(int i=0;i<jArray.length();i++){
				
				JSONObject json_data = jArray.getJSONObject(i);
                
				array_fact[i]=json_data.getString("Fact");
								
				Log.i("log_tag","fact"+json_data.getString("Fact"));
                  
				}			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			
			return true;
			
		}
		 
		protected void onPostExecute(Boolean b){
			
			
			pDialog.dismiss();
			setFact();
			
	       						
		}
		
	}

void setFact(){
	
	if(index==0){
 	   cat7_prev.setEnabled(false);
    }else{
    	cat7_prev.setEnabled(true);
    }
	if(index==jArray.length()-1){
		cat7_next.setEnabled(false);
	}else{
		cat7_next.setEnabled(true);
	}
		
    	
	fact.setText(array_fact[index]);
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
			return true;
		case R.id.menu_about:
			startActivity(new Intent(this, AboutUs.class));
			return true;
		case R.id.menu_exit:
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			return true;	
		case R.id.menu_feedback:
			Intent intent1=new Intent(this,Feedback.class);
			startActivity(intent1);
			return true;
		case R.id.menu_statistics:
		     startActivity(new Intent(this,Statistics.class));
		     return true;
		case R.id.menu_profile:
			Intent intent2 = new Intent(this,Profile.class);
			startActivity(intent2);
			return true;
		case R.id.menu_archive:
			Intent intent3 = new Intent(this,ArchiveMonthsActivity.class);
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
//   setIntent.addCategory(Intent.CATEGORY_HOME);
//   setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   startActivity(back);
}

}
