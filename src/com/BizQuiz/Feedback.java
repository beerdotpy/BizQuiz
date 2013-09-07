package com.BizQuiz;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.BizQuiz.Register.RegisterValidation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Feedback extends Activity {
	
	EditText name;
	EditText email;
	EditText feedback;
	Button submit;
	Spinner sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_feedback);
 
   name=(EditText) findViewById(R.id.ed_username);
   email=(EditText) findViewById(R.id.ed_email);
   feedback=(EditText) findViewById(R.id.ed_about);
   submit=(Button) findViewById(R.id.fd_submit);
   sp=(Spinner) findViewById(R.id.spinner1);
   
	submit.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		
			String username=name.getText().toString();
			String emailid=email.getText().toString();
			String content=feedback.getText().toString();
			String type_fd=sp.getSelectedItem().toString();
			
			Log.d("details",username+emailid+content+type_fd);
			new FeedbackForm(Feedback.this).execute(username,emailid,content,type_fd);
		}
	});
	
	}
	
	class FeedbackForm extends AsyncTask<String, Void, Boolean> {

		Context context;
		ProgressDialog pDialog;
		JSONObject jsonObject = new JSONObject();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    JSONParser jsonParser = new JSONParser();
	    String url="http://practice.site11.com/feedback.php";
	    int status;
		
	    
	    public FeedbackForm(Context ctx) {
			this.context=ctx;
			pDialog= new ProgressDialog(context);
		}
	    
	    @Override
		protected void onPreExecute(){
			pDialog.setTitle("Please Wait...");
			pDialog.setMessage("Sending an Email..");
			pDialog.setIndeterminate(true);
			pDialog.show();
		}


		@Override
		protected Boolean doInBackground(String... str) {
			
            String uname=str[0];
            String emailid=str[1];
            String content=str[2];
            String type_fd=str[3];
     			
            Log.d("details",uname+emailid+content+type_fd);
            
			params.add(new BasicNameValuePair("name",uname));
			params.add(new BasicNameValuePair("email",emailid));
			params.add(new BasicNameValuePair("content",content));
			params.add(new BasicNameValuePair("type",type_fd));
			
			
			jsonObject = jsonParser.makeHttpRequest(url, "GET", params);
			
			
			
			return true;
		}
	
		protected void onPostExecute(Boolean b){
			
			pDialog.dismiss();
			Toast.makeText(context, "ThankYou for your valuable feedback ", Toast.LENGTH_LONG).show();
		   Intent intent=new Intent(Feedback.this,Categories.class);
		   startActivity(intent);
		   }
	
	
	}
	
}
