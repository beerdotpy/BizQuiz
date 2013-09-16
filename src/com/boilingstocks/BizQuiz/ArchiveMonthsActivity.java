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
	    
	    int[] array_id=new int[11];
	    int[] array_year=new int[11];
	    String[] array_month=new String[11];
	    
	       
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
				
				for(int i=0;i<=10;i++){
				
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
			
			for(int i=0;i<=10;i++){
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
	
	
}
