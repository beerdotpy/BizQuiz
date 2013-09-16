package com.boilingstocks.BizQuiz;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.BizQuiz.R;
import com.BizQuiz.R.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.CalendarContract.Attendees;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuesFetch extends Activity {
  
	int qid; 
	int catid;
	Button previous;
	Button submit;
	Button next;
	EditText etanswer;
	TextView tvquestion;
	TextView timer;
	AlertDialog.Builder builder;
	AlertDialog.Builder builder1;
	Handler mHandler; 
	Runnable mUpdateUITimerTask;
	Context context;
	TextView ans;
	Button sell;
	Button buy;
	String category;         
	TextView score;
	TextView ques_stats;     // how much questions answered out of total question
	int threshvalue;         //currently set to 50% 
	int max_ques;            //maximum question in each category
	boolean check=true;      //to check if activity is running first time 
	SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_qfetch);
       
       context=this;
       mHandler = new Handler();
       previous=(Button) findViewById(R.id.previous);
       submit=(Button) findViewById(R.id.submit);
       next=(Button) findViewById(R.id.category2);
       tvquestion=(TextView) findViewById(R.id.question);
       etanswer=(EditText) findViewById(R.id.answer);
       timer=(TextView) findViewById(R.id.timer);
       ans=(TextView) findViewById(R.id.ans);
       sell=(Button) findViewById(R.id.sell);
       buy=(Button) findViewById(R.id.buy);
       score=(TextView) findViewById(R.id.score);
       ques_stats=(TextView) findViewById(R.id.stats);
       
       QuizDetails.setshuffle(1);
       QuizDetails.setqid(1);
       QuizDetails.set_score(0);
   	   catid=QuizDetails.getcategory();
   	   
	   Log.d("id + questionNo.",Integer.toString(catid)+"+"+Integer.toString(QuizDetails.getqid()));

	   
	   builder1 = new AlertDialog.Builder(context);
		builder1.setMessage("Info about the Quiz"+
				" For each correct answer +1 point is awarded.Quiz has a time limit of 3min "+
				"Press Back to Quit or Ok to Play")
				.setCancelable(true)
				.setOnCancelListener(new OnCancelListener() {
					
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						
						Intent returnhome=new Intent(QuesFetch.this,Categories.class);
						startActivity(returnhome);
						
					}
				})
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub	
						mCountDown.start();
					}
				})
				.show();   
	  
	   
	   timer.setText(2+":"+59);
	   
	if(catid==1)
	{
		category="Category1";
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
		
	}else if(catid==2)
	{
		category="Category2";
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
		
	}else if(catid==3){
		
		category="Category3";
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
		
	}else if(catid==4){
		
		category="Category4";
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
		
	}else if(catid==5){
		
		category="Category5";
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
		
	}else if(catid==6){
		
		category="Category6";
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
		
	}else if(catid==7){
		
		category="Category7";
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
		
	}else if(catid==8){
		
		category="Category8";
		new Questionfetch(QuesFetch.this).execute(category);
		
		etanswer.setVisibility(View.INVISIBLE);
		ans.setVisibility(View.INVISIBLE);
		submit.setVisibility(View.INVISIBLE);
		
	}

	
		previous.setEnabled(false);
	
  previous.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		
		qid=QuizDetails.getqid();
	  	QuizDetails.setqid(qid-1);
	  		 
	  	new Questionfetch(QuesFetch.this).execute(category);
		
	}
});
  
  submit.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		
		String ansbyuser=etanswer.getText().toString();
		
	    validateanswer(ansbyuser);
	    
	    
	}
});
 
  next.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		
			qid=QuizDetails.getqid();
		  	QuizDetails.setqid(qid+1);
		  	previous.setEnabled(true);
		  	
		  	
		  	new Questionfetch(QuesFetch.this).execute(category);
			
		}
	});
  
  sell.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			
			if(QuizDetails.getans().equalsIgnoreCase("Sell"))
			{
				QuizDetails.setqid(QuizDetails.getqid()+1);
	  	        Toast.makeText(QuesFetch.this, "Correct Answer", Toast.LENGTH_LONG).show();
	  	        QuizDetails.set_score(QuizDetails.getscore()+1);
	  	        
	  	        
	  	      new Questionfetch(QuesFetch.this).execute(category);
			}else{
				
				 QuizDetails.setqid(QuizDetails.getqid()+1);
	  	         Toast.makeText(QuesFetch.this, "Incorrect Answer.Correct Answer is "+QuizDetails.getans(), Toast.LENGTH_LONG).show();
	  	        	  	        
	  	        new Questionfetch(QuesFetch.this).execute(category);
			
			}		
		}
	});
  
  buy.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			
			if(QuizDetails.getans().equalsIgnoreCase("Buy"))
			{
				QuizDetails.setqid(QuizDetails.getqid()+1);
	  	        Toast.makeText(QuesFetch.this, "Correct Answer", Toast.LENGTH_LONG).show();
	  	        QuizDetails.set_score(QuizDetails.getscore()+1);
	  	        
	  	        
	  	      new Questionfetch(QuesFetch.this).execute(category);
			}else{
				
				 QuizDetails.setqid(QuizDetails.getqid()+1);
	  	         Toast.makeText(QuesFetch.this, "Incorrect Answer.Correct Answer is "+QuizDetails.getans(), Toast.LENGTH_LONG).show();
	  	        	  	        
	  	        new Questionfetch(QuesFetch.this).execute(category);
			
			}		
		}
	});

  	}
	
	
class Questionfetch extends AsyncTask<String, Void, Boolean> {

	Context context;
	ProgressDialog pDialog;
	JSONObject jsonObject = new JSONObject();
	List<NameValuePair> params = new ArrayList<NameValuePair>();
    JSONParser jsonParser = new JSONParser();
    String url="http://practice.site11.com/questionfetch.php";
    String nameofcat;
    String question;
    String qanswer;
    int quesid;
    
       
   public Questionfetch(Context ctx) {
    	this.context = ctx;
        pDialog= new ProgressDialog(context);
	}

	@Override
	protected void onPreExecute(){
		if(!check)
		{
			pDialog.setTitle("Please Wait...");
		
		pDialog.setMessage("Loading Question");
		pDialog.setIndeterminate(true);
		etanswer.setText("");
		pDialog.show();
		
		}
		
		check=false;
		
		
	}
	
	@Override
	protected Boolean doInBackground(String... str) {
		nameofcat=str[0];
		
		Log.d("Category + QuestionNo",nameofcat+" "+Integer.toString(QuizDetails.getqid()));
		
		params.add(new BasicNameValuePair("selectedcat",nameofcat));
		params.add(new BasicNameValuePair("QuestionNO",Integer.toString(QuizDetails.getqid())));
		params.add(new BasicNameValuePair("shuffle",Integer.toString(QuizDetails.getshuffle())));
		
		jsonObject = jsonParser.makeHttpRequest(url, "GET", params);
		
		try {
			 quesid = jsonObject.getInt("id");
			 question=jsonObject.getString("question");
			 qanswer=jsonObject.getString("answer");
			 max_ques=jsonObject.getInt("max_question");
			 
			Log.d("qid+shuffle",Integer.toString(qid));
			Log.d("question+answer",question+" + "+qanswer);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		return true;
		
	}
	 
	protected void onPostExecute(Boolean b){
		
      if(max_ques<QuizDetails.getqid()){
			
    	    calscore();
			Intent score=new Intent (QuesFetch.this,ScoreActivity.class);
			startActivity(score);
		
		}

		QuizDetails.setques(question);
		QuizDetails.setans(qanswer);
		QuizDetails.setshuffle(0);
		QuizDetails.set_maxques(max_ques);
		tvquestion.setText(QuizDetails.getques());
		score.setText("Score:"+Integer.toString(QuizDetails.getscore()));
	    ques_stats.setText(Integer.toString(QuizDetails.getscore())+"/"+Integer.toString(QuizDetails.getqid()));
		pDialog.dismiss();
	   
	}

}

    void validateanswer(String useranswer){
	
      	ProgressDialog pDialog;
     	pDialog= new ProgressDialog(this);
     	pDialog.setTitle("Please Wait...");
	    pDialog.setMessage("Validating Answer");
	    pDialog.setIndeterminate(true);
	    pDialog.show();
	
	    int length=QuizDetails.getans().length();
	    threshvalue=length/2+1;
	    
	    Log.d("ThreshValue",Integer.toString(threshvalue));
	    Boolean check=compareanswer(threshvalue,useranswer);
	      
	    if(check){
	  
	  	        QuizDetails.setqid(QuizDetails.getqid()+1);
	  	        Toast.makeText(this, "Correct Answer", Toast.LENGTH_LONG).show();
	  	        QuizDetails.set_score(QuizDetails.getscore()+1);
	  	        pDialog.dismiss();
	  	        
	  	      new Questionfetch(QuesFetch.this).execute(category);
	 
	    }else{

		
		          QuizDetails.setqid(QuizDetails.getqid()+1);
	  	          Toast.makeText(this, "Incorrect Answer.Correct Answer is "+QuizDetails.getans(), Toast.LENGTH_LONG).show();
	  	        pDialog.dismiss();
	  	        
	  	        new Questionfetch(QuesFetch.this).execute(category);
	  	
        }

     }
	
    Boolean compareanswer(int threshval,String ans){
    	int counter = 0;
    	ans=ans.toLowerCase();
    	String correctans=QuizDetails.getans().toLowerCase();
    	
    	for(int i=0;i<correctans.length();i++){		
    try{		
    	  if(ans.charAt(i)==correctans.charAt(i)){
    		   counter++;		
    		}
    		}catch(StringIndexOutOfBoundsException e){
    		e.printStackTrace();
    		}
    	}
    	
    	
    	     if(threshval<=counter){
    	    	      return true;
    	     }else
    	     {
                      return false;  
             }
   
    }
    
    void calscore(){
    	
      sp=QuesFetch.this.getSharedPreferences("First_run", MODE_PRIVATE);
  	  SharedPreferences.Editor editor = sp.edit();
  	  editor.putInt(category,QuizDetails.getscore());
  	  editor.commit();
  	    int score1 =sp.getInt("Category1", 0);
	    int score2 =sp.getInt("Category2", 0);
	    int score3 =sp.getInt("Category3", 0);
	    int score4 =sp.getInt("Category4", 0);
	    int score5 =sp.getInt("Category5", 0);
	    int score6 =sp.getInt("Category6", 0);
	    int score7 =sp.getInt("Category7", 0);
	    int score8 =sp.getInt("Category8", 0);
	    QuizDetails.setfinal_score(score1+score2+score3+score4+score5+score6+score7+score8);
	    
    }
    
    @Override
	protected void onResume(){
	
		super.onResume();	
		mUpdateUITimerTask = new Runnable() {
			int min=2,sec=59;
			public void run() {
		    	
		        
		        timer.setText(Integer.toString(min)+":"+Integer.toString(sec));
		        sec--;
		        if(sec==0)
		        {
		        	min--;
		        	sec=59;
		        }
		    }
		};
	}
	
	@Override
	protected void onPause(){
		mHandler.removeCallbacks(mUpdateUITimerTask);
		mCountDown.cancel();
		super.onPause();
		
		
	}
    
    protected CountDownTimer mCountDown = new CountDownTimer(178000, 1000)
    {

        @Override
        public void onTick(long millisUntilFinished)
        {
        	
        	Log.d("Timer","time for 3mins");
        	mHandler.post(mUpdateUITimerTask);
        	            
        	        }

        @Override
        public void onFinish()
        {   
        	Log.d("Timer","3mins finish");
        	builder = new AlertDialog.Builder(context);
    		builder.setMessage(
    				"Sorry your time is over.Press Ok to play again and Back to Quit")
    				.setCancelable(true)
    				.setOnCancelListener(new OnCancelListener() {
    					
    					public void onCancel(DialogInterface dialog) {
    						// TODO Auto-generated method stub
    						mCountDown.cancel();
    						Intent returnhome=new Intent(QuesFetch.this,Categories.class);
    						startActivity(returnhome);
    						
    					}
    				})
    				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
    					
    					public void onClick(DialogInterface dialog, int which) {
    						// TODO Auto-generated method stub
    						Intent playconti=new Intent(QuesFetch.this,QuesFetch.class);
    						startActivity(playconti);
    						
    					}
    				})
    				.show();
            }
        };
    
        @Override
        public void onBackPressed() {
           Log.d("CDA", "onBackPressed Called");
           Intent back = new Intent(QuesFetch.this,ScoreActivity.class);
           calscore();
           startActivity(back);
        }
        
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                                                            INPUT_METHOD_SERVICE);
            if(imm != null)
             {
            	imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
             }
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
       		case R.id.menu_profile:
       			Intent intent2 = new Intent(this,Profile.class);
       			startActivity(intent2);
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

