package in.bizquiz.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Feedback extends Activity {
	
	EditText name;
	EditText email;
	EditText feedback;
	Button submit;
	Spinner sp;
	TextView fb_link;
	TextView site_link;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_feedback);
 
   name=(EditText) findViewById(R.id.ed_username);
   email=(EditText) findViewById(R.id.ed_email);
   feedback=(EditText) findViewById(R.id.ed_about);
   submit=(Button) findViewById(R.id.fd_submit);
   sp=(Spinner) findViewById(R.id.spinner1);
   fb_link=(TextView) findViewById(R.id.facebook_link);
   site_link=(TextView) findViewById(R.id.site_link);
   
   
    String temp="www.bizquiz.in";
	SpannableString spanString = new SpannableString(temp);
	spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
	site_link.setText(spanString);
	
	String temp1="On Facebook: www.facebook.com/www.bizquiz.in";
	SpannableString spanString1 = new SpannableString(temp1);
	spanString1.setSpan(new UnderlineSpan(), 13, spanString1.length(), 0);
	fb_link.setText(spanString1);
		
   
   site_link.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
   
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bizquiz.in"));
		startActivity(browserIntent);
      
		
	}
});
   
   fb_link.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
	   
			Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/www.bizquiz.in"));
			startActivity(browserIntent1);
		
			
		}
	});
   
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
	    String url="http://bizquiz.in/BizQuiz/Feedback.php";
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
			Toast.makeText(context, "Thank You!  ", Toast.LENGTH_LONG).show();
		   Intent intent=new Intent(Feedback.this,Categories.class);
		   startActivity(intent);
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
	
	@Override
    public void onBackPressed() {
       Log.d("CDA", "onBackPressed Called");
       Intent back = new Intent(this,Categories.class);
//       setIntent.addCategory(Intent.CATEGORY_HOME);
//       setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(back);
    }
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                                                        INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
