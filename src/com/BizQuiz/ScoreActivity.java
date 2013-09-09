package com.BizQuiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class ScoreActivity extends Activity {

	ProgressDialog pDialog;

	int score ;
	BQParse parse;
	String username;
    SharedPreferences sp;   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		parse = new BQParse(ScoreActivity.this);
		setUpParse();
		setContentView(R.layout.activity_scores);
		
		TextView tvScore = (TextView) findViewById(R.id.tv_score);
		score=getIntent().getIntExtra("score", 0);
		sp=this.getSharedPreferences("First_run", MODE_PRIVATE);
	    username=sp.getString("Username", "hi");
	    Log.d("usernme",username);
	    
	    new Updatescore(ScoreActivity.this).execute(username);
		tvScore.setText("Score : "+score);
		
		Button shareButton = (Button) findViewById(R.id.bt_share);
		shareButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shareScoreOnFacebook(score);
			}
		});
		
		
	}

	public void setUpParse(){

		parse.initBQParse();
		parse.initializeParseFacebookUtils();

	}
	
	public void shareScoreOnFacebook(final int score){
		
	    pDialog = ProgressDialog.show(
	            ScoreActivity.this, "", "Logging in...", true);
	    List<String> permissions = Arrays.asList("basic_info", "user_about_me",
	            "user_relationships", "user_birthday", "user_location");
	    
	    ParseFacebookUtils.logIn(permissions,  ScoreActivity.this, new LogInCallback() {
	        @Override
	        public void done(ParseUser user, ParseException err) {
	            pDialog.dismiss();
	            if (user == null) {
	                Log.d("NewsInShorts-ParseAPI",
	                        "Uh oh. The user cancelled the Facebook login.");
	            } else if (user.isNew()) {
	                Log.d("NewsInShorts-ParseAPI",
	                        "User signed up and logged in through Facebook!");
	        	    List<String> permissions = Arrays.asList("publish_actions");
	        	//	Session.NewPermissionsRequest publishAction = new Session.NewPermissionsRequest( ScoreActivity.this, permissions);
	        	//	ParseFacebookUtils.getSession().requestNewPublishPermissions(publishAction);
	        		ParseFacebookUtils.saveLatestSessionData(user);
	        	    makeMyPost(score);

	        		//showMeNews();
	            } else {
	                Log.d("NewsInShorts-ParseAPI",
	                        "User logged in through Facebook!");
	        	    makeMyPost(score);

	                //showMeNews();
	            }
	        }
	    });

		
		
		
	}
	
	public void makeMyPost(final int score){
	    Session session = Session.getActiveSession();
	    if (session != null){


	        Bundle postParams = new Bundle();
	        postParams.putString("name", "***URGENT***");
	        postParams.putString("caption", "This is an automated status update by BizQuiz for Android");
	        postParams.putString("description", "score is "+score);
//	        postParams.putString("link", "http://blog.boilingstocks.com");
	        postParams.putString("picture", "http://blog.boilingstocks.com/wp-content/uploads/2013/06/keep-calm-and-carry-on-programming-150x150.png");

	        Request.Callback callback= new Request.Callback() {
	            public void onCompleted(Response response) {
	                JSONObject graphResponse = response
	                                           .getGraphObject()
	                                           .getInnerJSONObject();
	                String postId = null;
	                try {
	                    postId = graphResponse.getString("id");
	                } catch (JSONException e) {
	                    Log.i("BizQuiz FB",
	                        "JSON error "+ e.getMessage());
	                }
	                FacebookRequestError error = response.getError();
	                if (error != null) {
	                    Toast.makeText(ScoreActivity.this,
	                         error.getErrorMessage(),
	                         Toast.LENGTH_SHORT).show();
	                    } else {
	                        Toast.makeText(ScoreActivity.this, 
	                             "Post Successful",
	                             Toast.LENGTH_LONG).show();
	                }
	            }
	        };

	        Request request = new Request(session, "me/feed", postParams, 
	                              HttpMethod.POST, callback);

	        RequestAsyncTask task = new RequestAsyncTask(request);
	        task.execute();
	    }

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}
	    
	
	class Updatescore extends AsyncTask<String, Void, Boolean> {

		Context context;
		ProgressDialog pDialog;
		JSONObject jsonObject = new JSONObject();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    JSONParser jsonParser = new JSONParser();
	    String url="http://practice.site11.com/updatescore.php";
	    int status;
		
	    
	    public Updatescore(Context ctx) {
			this.context=ctx;
			pDialog= new ProgressDialog(context);
		}
	    
	    @Override
		protected void onPreExecute(){
			pDialog.setTitle("Please Wait...");
			pDialog.setMessage("Updating Score..");
			pDialog.setIndeterminate(true);
			pDialog.show();
		}


		@Override
		protected Boolean doInBackground(String... str) {
			
            Log.d("username",str[0]);
			
			params.add(new BasicNameValuePair("username",str[0]));
			params.add(new BasicNameValuePair("score",Integer.toString(score)));
			
			
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
			  Toast.makeText(context, "Score Updated", Toast.LENGTH_LONG).show();
  		   }
  		   else {
  			   Toast.makeText(context, "Please try again",
  					   Toast.LENGTH_LONG).show();
  			   	   
		}
		}
	}
	
	@Override
    public void onBackPressed() {
       Log.d("CDA", "onBackPressed Called");
       Intent back = new Intent(ScoreActivity.this,Categories.class);
//       setIntent.addCategory(Intent.CATEGORY_HOME);
//       setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(back);
    }
}
