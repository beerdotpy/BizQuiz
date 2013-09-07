package com.BizQuiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
		}
	});
	
	}
	
}
