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
import android.widget.TextView;
import android.widget.Toast;

import com.BizQuiz.R;

public class ArchiveActivity extends Activity{
	ArrayList<ArchQues> archQuestions = new ArrayList<ArchQues>();
	ArchQuesAdapter adapter;
	ListView monthLV;
	TextView archive_ans;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archives);
		archive_ans=(TextView) findViewById(R.id.archive_answer);
		
		int id=getIntent().getIntExtra("archiveId",-1);
		Log.d("archive",Integer.toString(id));
		
		
		new ArchiveList(ArchiveActivity.this).execute(Integer.toString(id));
		
		monthLV = (ListView) findViewById(R.id.archiveListView);
		
		
		monthLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//Toast.makeText(ArchiveActivity.this, archQuestions.get(arg2).get_Answer(), Toast.LENGTH_SHORT).show();
			archive_ans.setText(archQuestions.get(arg2).get_Answer());
			}
		});
	}
	
	
	
	class ArchiveList extends AsyncTask<String, Void, Boolean> {

		Context context;
		ProgressDialog pDialog;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    JSONParser jsonParser = new JSONParser();
	    JSONArray jArray=new JSONArray();
	    String url="http://practice.site11.com/archive.php";
	    
	    JSONObject json=new JSONObject();
	    
	    String[] array_question=new String[11];
	    String[] array_answer=new String[11];
	    
	       
	   public ArchiveList(Context ctx) {
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
			
			
			params.add(new BasicNameValuePair("MonthID",str[0]));
			
			json = jsonParser.makeHttpRequest(url, "GET", params);
					
			try {
				jArray= json.getJSONArray("data");
				System.out.println("*****JARRAY*****"+jArray.length());
				
				for(int i=0;i<jArray.length();i++){
				
				JSONObject json_data = jArray.getJSONObject(i);
                
				array_question[i]=json_data.getString("question");
				array_answer[i]=json_data.getString("answer");
				
				
				Log.i("log_tag","ques"+json_data.getString("question")+
				  ", answer"+json_data.getString("answer") );
                  
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
				ArchQues ques_list=new ArchQues(); 
				ques_list.set_Question(array_question[i]);
				ques_list.set_Answer(array_answer[i]);
				archQuestions.add(ques_list);

			}
			
			adapter = new ArchQuesAdapter(ArchiveActivity.this, archQuestions);
			
			
			monthLV.setAdapter(adapter);
				
		}
		
	}
	
	@Override
    public void onBackPressed() {
       Log.d("CDA", "onBackPressed Called");
       Intent back = new Intent(this,ArchiveMonthsActivity.class);
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
