package com.boilingstocks.BizQuiz;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.BizQuiz.R;

public class ArchiveMonthsActivity extends Activity {

	ArrayList<Month> monthList = new ArrayList<Month>();
	MonthAdapter adapter;
	ListView monthLV;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_months);
		/***
		 * TODO 
		 * PARSE YOUR JSON RESPONSE AND CREATE THE LIST.
		 * 
		 */
		
		monthLV = (ListView) findViewById(R.id.monthListView);
		new ArchiveMonthList(ArchiveMonthsActivity.this).execute("status");
		
		monthLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent archPageIntent = new Intent(ArchiveMonthsActivity.this,ArchiveActivity.class);
				archPageIntent.putExtra("archiveId", monthList.get(arg2)._Id);
				startActivity(archPageIntent);
			}
		});
	}

	
	
	class ArchiveMonthList extends AsyncTask<String, Void, Boolean> {

		Context context;
		ProgressDialog pDialog;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    JSONParser jsonParser = new JSONParser();
	    JSONArray jArray=new JSONArray();
	    String url="http://practice.site11.com/ArchiveMonths.php";
	    
	    JSONObject json=new JSONObject();
	    
	    int[] array_id;
	    int[] array_year;
	    String[] array_month;
	    
	       
	   public ArchiveMonthList(Context ctx) {
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
				jArray= json.getJSONArray("data");
				System.out.println("*****JARRAY*****"+jArray.length());
				
				array_id=new int[jArray.length()];
				array_year=new int[jArray.length()];
				array_month=new String[jArray.length()];
				
				for(int i=0;i<jArray.length();i++){
				
				JSONObject json_data = jArray.getJSONObject(i);
                
				array_id[i]=json_data.getInt("id");
				array_month[i]=json_data.getString("Month");
				array_year[i]=json_data.getInt("Year");
				
				Log.i("log_tag","id"+json_data.getInt("id")+
				  ", month"+json_data.getString("Month") );
                  
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
				Month month_list=new Month(); 
				month_list.set_Id(array_id[i]);
				month_list.set_Month(array_month[i]);
				month_list.set_Year(array_year[i]);
				monthList.add(month_list);

			}
			
			adapter = new MonthAdapter(ArchiveMonthsActivity.this, monthList);
			
			
			monthLV.setAdapter(adapter);
				
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
   		case R.id.menu_profile:
   			Intent intent2 = new Intent(this,Profile.class);
   			startActivity(intent2);
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
	
}
