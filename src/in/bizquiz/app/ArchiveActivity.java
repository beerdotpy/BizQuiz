package in.bizquiz.app;


import java.util.ArrayList;
import java.util.Calendar;
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

public class ArchiveActivity extends Activity{
	ArrayList<ArchQues> archQuestions = new ArrayList<ArchQues>();
	ArchQuesAdapter adapter;
	ListView monthLV;
	TextView archive_ans;
	String cat_name;
	int day_of_month;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archives);
		archive_ans=(TextView) findViewById(R.id.archive_answer);
		
		int id=getIntent().getIntExtra("archiveId",-1);
		Log.d("archive",Integer.toString(id));
		
		cat_name=getIntent().getStringExtra("catname");
		Log.d("category_name",cat_name);
		
		Calendar c = Calendar.getInstance();   // this takes current date
	    day_of_month=c.get(Calendar.DAY_OF_MONTH);
		
		monthLV = (ListView) findViewById(R.id.archiveListView);
		new ArchiveList(ArchiveActivity.this).execute(Integer.toString(id),cat_name);
		
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
	    int length;
	    String url="http://bizquiz.in/BizQuiz/Archive.php";
	    
	    JSONObject json=new JSONObject();
	    
	    String[] array_id;	      
	    String[] array_question;
	    String[] temp_question;
	    String[] array_answer;
	    String[] temp_answer;
	    
	       
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
			params.add(new BasicNameValuePair("day_of_month",Integer.toString(day_of_month)));
			params.add(new BasicNameValuePair("Category_name",cat_name));
			 
			json = jsonParser.makeHttpRequest(url, "GET", params);
					
			try {
				jArray= json.getJSONArray("data");
				length=json.getInt("length");
				Log.d("length",Integer.toString(length));				
				
				array_id=new String[jArray.length()];
				array_question=new String[jArray.length()];
				array_answer=new String[jArray.length()];
				temp_question=new String[jArray.length()];
				temp_answer=new String[jArray.length()];
				
				System.out.println("*****JARRAY*****"+jArray.length());
				
				for(int i=0;i<jArray.length();i++){
				
				JSONObject json_data = jArray.getJSONObject(i);
                
				array_id[i]=json_data.getString("id");
				array_question[i]=json_data.getString("question");
				array_answer[i]=json_data.getString("keyword");
				
				
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
			
			for(int i=0,j=0;i<jArray.length();i++){
				
				if(cat_name.compareToIgnoreCase(array_id[i])==0)
				{	
					
					temp_question[j]=array_question[i];
					temp_answer[j]=array_answer[i];
					j++;
				}
			}
					
			for(int k=0;k<length;k++){		
				ArchQues ques_list=new ArchQues();
				ques_list.set_Question(temp_question[k]);
				ques_list.set_Answer(temp_answer[k]);
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
	
}
