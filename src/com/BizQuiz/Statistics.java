package com.BizQuiz;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import android.widget.TextView;

public class Statistics extends Activity {
	
	
	JSONArray jArray=new JSONArray();
	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	TextView tv5;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	
	super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_statistics);
    
     tv1=(TextView) findViewById(R.id.tv1);
     tv2=(TextView) findViewById(R.id.tv2);
     tv3=(TextView) findViewById(R.id.tv3);
     tv4=(TextView) findViewById(R.id.tv4);
     tv5=(TextView) findViewById(R.id.tv5);

    new Stats(Statistics.this).execute("check");    

	}

	class Stats extends AsyncTask<String, Void, Boolean> {

		Context context;
		ProgressDialog pDialog;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    JSONParser jsonParser = new JSONParser();
	    String url="http://practice.site11.com/statistics.php";
	    
	    JSONObject json=new JSONObject();
	    
	    int[] score_array=new int[50];
	    String[] user_array=new String[50];
	    
	       
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
			
			
			params.add(new BasicNameValuePair("check",str[0]));
			
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
		
           sort(score_array,user_array);			        
//         tv1.setText(Integer.toString(score_array[jArray.length()]));
//         tv2.setText(Integer.toString(score_array[jArray.length()-1]));
//         tv3.setText(Integer.toString(score_array[jArray.length()-3]));
//         tv4.setText(Integer.toString(score_array[jArray.length()-4]));
//         tv5.setText(Integer.toString(score_array[jArray.length()-5]));
//			        
		}
		
	}	

	void sort(int[] score,String[] user)
	{
	for(int i=0;i<jArray.length();i++){

        int temp1;
        String temp;
	          for (int j=0;j<jArray.length()-i;j++ )
	                {
	                  if (score[j]>score[j+1])
	                 {  
	                     temp1=score[j];
	                     temp=user[j];
	                     score[j]=score[j+1];
	                     user[j]=user[j+1];
	                     score[j+1]=temp1;
	                     user[j+1]=temp;
	                 
	                 }
	                }
	              } 
	            
	        for(int i=0; i<=jArray.length(); i++)
	         {
	             System.out.println(score[i] + " " + user[i]+" ");
	          }
	
}
	
	}
