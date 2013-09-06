package com.BizQuiz;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.BizQuiz.QuesFetch.Questionfetch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

	EditText etname;
	EditText etcontact;
	EditText etcity;
	EditText etage;
	EditText etemail;
	String name;
	String contact;
	String email;
	String city;
 	String age;	
    Button register; 
 	SharedPreferences sp;
 	

 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_register);
	
   etname=(EditText) findViewById(R.id.edname);
   etcontact=(EditText) findViewById(R.id.edcontact);
   etcity=(EditText) findViewById(R.id.edcity);
   etage=(EditText) findViewById(R.id.edage);
   etemail=(EditText) findViewById(R.id.edeamil);
   register=(Button) findViewById(R.id.reg);
   
   
	register.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			name=etname.getText().toString();
			contact=etcontact.getText().toString();
			email=etemail.getText().toString();
			city=etcity.getText().toString();
		    age=etage.getText().toString();
			
		    new RegisterValidation(Register.this).execute(name,contact,email,city,age);
		}
	});
	
	
	}
	
	class RegisterValidation extends AsyncTask<String, Void, Boolean> {

		Context context;
		ProgressDialog pDialog;
		JSONObject jsonObject = new JSONObject();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    JSONParser jsonParser = new JSONParser();
	    String url="http://practice.site11.com/login.php";
	    int status;
		String uname;
	    
	    public RegisterValidation(Context ctx) {
			this.context=ctx;
			pDialog= new ProgressDialog(context);
		}
	    
	    @Override
		protected void onPreExecute(){
			pDialog.setTitle("Please Wait...");
			pDialog.setMessage("Registering...");
			pDialog.setIndeterminate(true);
			pDialog.show();
		}


		@Override
		protected Boolean doInBackground(String... str) {
			
            uname=str[0];
            String uphone=str[1];
            String uemail=str[2];
            String ucity=str[3];
            String uage=str[4];
			
			Log.d("name",uname);
			Log.d("phone",uphone);
			Log.d("email",uemail);
			Log.d("city",ucity);
			Log.d("age",uage);
			
			params.add(new BasicNameValuePair("name",uname));
			params.add(new BasicNameValuePair("contact",uphone));
			params.add(new BasicNameValuePair("email",uemail));
			params.add(new BasicNameValuePair("age",uage));
			params.add(new BasicNameValuePair("city",ucity));
			
			
			jsonObject = jsonParser.makeHttpRequest(url, "GET", params);
			
			
			try {
				
				status = jsonObject.getInt("status");
				
                Log.d("status",Integer.toString(status));
                                				
			    } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
			
			return true;
		}
	
		protected void onPostExecute(Boolean b){
			
			pDialog.dismiss();
			if(status==1)
            {
			  Toast.makeText(context, "Successfully Registered", Toast.LENGTH_LONG).show();
              
			  sp = context.getSharedPreferences("First_run", MODE_PRIVATE);
			    SharedPreferences.Editor editor = sp.edit();
			    editor.putBoolean("Firstrun", false);
			    editor.putString("Username",uname);
			    editor.commit();
			    
			  Intent intent=new Intent(context,Categories.class);
  			    context.startActivity(intent);
  			    
  			
  			    
  		   }
  		   else if(status==0){
  			   Toast.makeText(context, "Registeration Failed.Username alreday exist.Please try different username",
  					   Toast.LENGTH_LONG).show();
  			   context.startActivity(new Intent(context,Register.class));
            }else{
            	Toast.makeText(context, "Registeration Failed.Please try again",
   					   Toast.LENGTH_LONG).show();
   			   context.startActivity(new Intent(context,Home.class));
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
}