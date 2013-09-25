package com.boilingstocks.BizQuiz;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {
	
	Button submit_pro;
	String user_name;
	String user_email;
	String user_contact;
	String user_city;
	String user_age;
	String user_pass;
	
	String new_name;
	String new_email;
	String new_contact;
	String new_age;
	String new_city;
	String new_pass;
	
	EditText ed1;
	EditText ed2;
	EditText ed3;
	EditText ed4;
	EditText ed5;
	EditText ed6;
	
	SharedPreferences shared;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_profile);
	
	
	shared=this.getSharedPreferences("First_run", MODE_PRIVATE);
    user_name=shared.getString("Username", "");
    
    user_email=shared.getString("Email", "");
    user_contact=shared.getString("Contact", "");
    user_age=shared.getString("Age", "");
    user_city=shared.getString("City", "");
	user_pass=shared.getString("Password", "");
	
	ed1=(EditText) findViewById(R.id.et_name);
	ed1.setHint(user_name);
	ed6=(EditText) findViewById(R.id.ed_pass);
	ed6.setHint(user_pass);
	ed2=(EditText) findViewById(R.id.et_email);
	ed2.setHint(user_email);
	ed3=(EditText) findViewById(R.id.et_contact);
	ed3.setHint(user_contact);
	ed4=(EditText) findViewById(R.id.et_city);
	ed4.setHint(user_city);
	ed5=(EditText) findViewById(R.id.et_age);
	ed5.setHint(user_age);

	submit_pro=(Button) findViewById(R.id.profile_submit);
	submit_pro.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			new_name=ed1.getText().toString();
			new_email=ed2.getText().toString();
			new_contact=ed3.getText().toString();
			new_age=ed5.getText().toString();
			new_city=ed4.getText().toString();					
			new_pass=ed6.getText().toString();
			
			new ProfileUpdate(Profile.this).execute();
		}
	});
}
	
	
	class ProfileUpdate extends AsyncTask<String, Void, Boolean> {

		Context context;
		ProgressDialog pDialog;
		JSONObject jsonObject = new JSONObject();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    JSONParser jsonParser = new JSONParser();
	    String url="http://practice.site11.com/UpdateProfile.php";
	    int status;
	    
	       
	   public ProfileUpdate(Context ctx) {
	    	this.context = ctx;
	        pDialog= new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute(){
			pDialog.setTitle("Please Wait...");
			pDialog.setMessage("Updating Profile..");
			pDialog.setIndeterminate(true);
			pDialog.show();
			
			}


		
		@Override
		protected Boolean doInBackground(String... str) {
				
			if(new_name.matches("")){
				new_name=user_name;
			}
			if(new_email.matches("")){
				new_email=user_email;
			}
			if(new_contact.matches("")){
				new_contact=user_contact;
			}
			if(new_age.matches("")){
				new_age=user_age;
			}
			if(new_city.matches("")){
				new_city=user_city;
			}
			if(new_pass.matches("")){
				new_pass=user_pass;
			}
			
			params.add(new BasicNameValuePair("username",new_name));
			params.add(new BasicNameValuePair("useremail",new_email));
			params.add(new BasicNameValuePair("usercontact",new_contact));
			params.add(new BasicNameValuePair("userage",new_age));
			params.add(new BasicNameValuePair("usercity",new_city));
			params.add(new BasicNameValuePair("old_name",user_name));
			params.add(new BasicNameValuePair("userpass",new_pass));
			
			
			jsonObject = jsonParser.makeHttpRequest(url, "GET", params);
			
			try {
				 status = jsonObject.getInt("status");
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			
			return true;
			
		}
		 
		protected void onPostExecute(Boolean b){
			
			pDialog.dismiss();
		
			
			shared = context.getSharedPreferences("First_run", MODE_PRIVATE);
		    SharedPreferences.Editor editor = shared.edit();
		    editor.putString("Username",new_name);
		    editor.putString("Password",new_pass);
		    editor.putString("Contact",new_contact);
		    editor.putString("Email",new_email);
		    editor.putString("Age",new_age);
		    editor.putString("City",new_city);
		    editor.commit();	
			
			if(status==1){
				
				Toast.makeText(Profile.this, "Profile Succefully updated", Toast.LENGTH_LONG).show();
				Intent intent=new Intent(Profile.this,Categories.class);
				startActivity(intent);
				
			}else{
				Intent intent=new Intent(Profile.this,Profile.class);
				startActivity(intent);
			}
		   
		}
		
	}
	
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                                                        INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
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
