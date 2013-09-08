package com.BizQuiz;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		parse = new BQParse(ScoreActivity.this);
		setUpParse();
		setContentView(R.layout.activity_scores);
		
		TextView tvScore = (TextView) findViewById(R.id.tv_score);
		score=getIntent().getIntExtra("score", -1);
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
	    
	
}
