package com.boilingstocks.BizQuiz;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

import android.content.Context;
import android.util.Log;

public class BQParse {

	private static final String CLIENT_KEY = "6kGFLhmwfTlNEMeGYzFPHBXKSb5ZuAXHeRP0Hb0n";
	private static final String APP_KEY = "AiFTL8PEOK7AOkuupFDgwGTblNKoktVetsZznpsS";
	private static final String FACEBOOK_API_KEY = "154125568128582";
	
	private Context context;
	
	public BQParse(Context c){
		
		context = c;
		
	}
	
	public void initBQParse(){
		
		Parse.initialize(context, APP_KEY, CLIENT_KEY);
		Log.i("BQParse","Parse Initialized");

	}
	
	
	public void initializeParseFacebookUtils(){
		ParseFacebookUtils.initialize(FACEBOOK_API_KEY);
		Log.i("BQParse","Parse Facebook Initialized");
	}
}
