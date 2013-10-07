package com.boilingstocks.BizQuiz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Statistics extends Activity {
	
	
	JSONArray jArray=new JSONArray();
	ListView lv;
	private ArrayList<ListData> myList = new ArrayList<ListData>();
	StatisticsRowAdapter adapter;
	int[] score_array=new int[11];
    String[] user_array=new String[11];
    SharedPreferences sp;
	TextView month_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	
	super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_statistics);
    
    sp=this.getSharedPreferences("First_run",this.MODE_PRIVATE );
	final String user_name =sp.getString("Username", " ");
    
     lv=(ListView) findViewById(R.id.listView1);
     
     month_name=(TextView) findViewById(R.id.month_name);
		Calendar c = Calendar.getInstance();   // this takes current date
	       int month_of_year=c.get(Calendar.MONTH);
	       
	       
	     switch(month_of_year){
	     
	     case 0: month_name.setText("January");
	             break;
	     case 1: month_name.setText("February");
	             break;
	     case 2: month_name.setText("March");
	             break;
	     case 3: month_name.setText("April");
	             break;
	     case 4: month_name.setText("May");
	             break;
	     case 5: month_name.setText("June");
	             break;
	     case 6: month_name.setText("July");
	             break;
	     case 7: month_name.setText("August");
	             break;
	     case 8: month_name.setText("September");
	             break; 
	     case 9: month_name.setText("October");
	             break;
	     case 10: month_name.setText("November");
	             break;
	     case 11: month_name.setText("December");
	              break;
	     	     
	     default: month_name.setText("");
	     }

    new Stats(Statistics.this).execute("get top 10 scores");    

    lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			try{
			if(myList.get(arg2).getusername().compareToIgnoreCase(user_name)==0)
	{
		Intent individual_score=new Intent(Statistics.this,User_Category_Score.class);
		startActivity(individual_score);
	}
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}
	});
    
    
	}

	class Stats extends AsyncTask<String, Void, Boolean> {

		Context context;
		ProgressDialog pDialog;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    JSONParser jsonParser = new JSONParser();
	    String url="http://bizquiz.in/BizQuiz/Statistics.php";
	    
	    JSONObject json=new JSONObject();
	    
	    
	    
	       
	   public Stats(Context ctx) {
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
			
			
			
			
			json = jsonParser.makeHttpRequest(url, "GET", params);
					
			try {
				jArray= json.getJSONArray("data");
				System.out.println("*****JARRAY*****"+jArray.length());
				
				for(int i=0;i<jArray.length();i++){
				
				JSONObject json_data = jArray.getJSONObject(i);
                
				score_array[i]=json_data.getInt("Score");
				user_array[i]=json_data.getString("Username");
				
				Log.i("log_tag","score"+json_data.getInt("Score")+
				  ", user_name"+json_data.getString("Username") );
                  
				}			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			
			return true;
			
		}
		 
		protected void onPostExecute(Boolean b){
			
			
			pDialog.dismiss();
			
			for(int i=0;i<jArray.length();i++){
				ListData myListData = new ListData(); 
				myListData.setscore(score_array[i]);
				myListData.setusername(user_array[i]);
				myList.add(myListData);

			}
			
			adapter = new StatisticsRowAdapter(Statistics.this, myList);
			
			
			lv.setAdapter(adapter);
				
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
