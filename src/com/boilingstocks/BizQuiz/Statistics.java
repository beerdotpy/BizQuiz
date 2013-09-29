package com.boilingstocks.BizQuiz;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Statistics extends Activity {
	
	
	JSONArray jArray=new JSONArray();
	ListView lv;
	private ArrayList<ListData> myList = new ArrayList<ListData>();
	StatisticsRowAdapter adapter;
	int[] score_array=new int[11];
    String[] user_array=new String[11];
    SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	
	super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_statistics);
    
    sp=this.getSharedPreferences("First_run",this.MODE_PRIVATE );
	final String user_name =sp.getString("Username", " ");
    
     lv=(ListView) findViewById(R.id.listView1);

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
	    String url="http://practice.site11.com/statistics.php";
	    
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
				
				for(int i=0;i<=10;i++){
				
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
			
			for(int i=0;i<=10;i++){
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
//       setIntent.addCategory(Intent.CATEGORY_HOME);
//       setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(back);
    }
	
	}
