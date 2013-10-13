package in.bizquiz.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
	AlertDialog.Builder builder_back;
	Handler mHandler; 
	Runnable mUpdateUITimerTask;
	Context context;
	Button sell;
	Button buy;
	String category;
	String dialog_start;
	TextView ques_stats;     // how much questions answered out of total question
	TextView name_of_quiz;
	int threshvalue;         //currently set to 50% if length is less than 10 else set to 75%
	int max_ques;            //maximum question in each category
	boolean check=true;      //to check if activity is running first time for displaying the dialog box
	SharedPreferences sp;
	
	int month_id;
	int day_of_month;
	
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
       name_of_quiz=(TextView) findViewById(R.id.heading);
       
       sell=(Button) findViewById(R.id.sell);
       buy=(Button) findViewById(R.id.buy);
       
       ques_stats=(TextView) findViewById(R.id.stats);
       
       QuizDetails.setshuffle(1);
       QuizDetails.setqid(1);
       QuizDetails.set_score(0);
   	   catid=QuizDetails.getcategory();
   	   
   	Calendar c = Calendar.getInstance();   // this takes current date
    day_of_month=c.get(Calendar.DAY_OF_MONTH);
    int month=c.get(Calendar.MONTH);
    Log.d("day_of_month",Integer.toString(day_of_month));
    
    switch(month){
	     
	     case 0: month_id=4;
	             break;
	     case 1: month_id=5;
	             break;
	     case 2: month_id=6;
	             break;
	     case 3: month_id=7;
	             break;
	     case 4: month_id=8;
	             break;
	     case 5: month_id=9;
	             break;
	     case 6: month_id=10;
	             break;
	     case 7: month_id=11;
	             break;
	     case 8: month_id=12;
	             break; 
	     case 9: month_id=1;
	             break;
	     case 10: month_id=2;
	             break;
	     case 11: month_id=3;
	              break;
	     	     
	     default: month_id=0;
	     }
   	   
	   Log.d("id + questionNo.",Integer.toString(catid)+"+"+Integer.toString(QuizDetails.getqid()));
	   timer.setText(2+":"+59);
	   
	if(catid==1)
	{
		category=getResources().getString(R.string.Category1);
		name_of_quiz.setText(category);
		dialog_start=getResources().getString(R.string.BizUpdate);
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
		
	}else if(catid==2)
	{
		category=getResources().getString(R.string.Category2);
		name_of_quiz.setText(category);
		dialog_start=getResources().getString(R.string.Brandology);
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
		
	}else if(catid==3){
		
		category=getResources().getString(R.string.Category3);
		name_of_quiz.setText(category);
		dialog_start=getResources().getString(R.string.WhatDFact);
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
		
	}else if(catid==4){
		
		category=getResources().getString(R.string.Category4);
		name_of_quiz.setText(category);
		dialog_start=getResources().getString(R.string.Bizness_inc);
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
		
	}else if(catid==5){
		
		category=getResources().getString(R.string.Category5);
		name_of_quiz.setText(category);
		dialog_start=getResources().getString(R.string.CryptiClues);
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
				
	}else if(catid==6){
		
		category=getResources().getString(R.string.Category6);
		name_of_quiz.setText(category);
		new Questionfetch(QuesFetch.this).execute(category);
		sell.setVisibility(View.INVISIBLE);
		buy.setVisibility(View.INVISIBLE);
		next.setVisibility(View.INVISIBLE);
		previous.setVisibility(View.INVISIBLE);
		timer.setVisibility(View.INVISIBLE);
		
	}else if(catid==8){
		
		category=getResources().getString(R.string.Category8);
		name_of_quiz.setText(category);
		dialog_start=getResources().getString(R.string.HotStocks);
		new Questionfetch(QuesFetch.this).execute(category);
		
		etanswer.setVisibility(View.INVISIBLE);
		submit.setVisibility(View.INVISIBLE);
		
	}

	if(catid!=6){
		
	builder1 = new AlertDialog.Builder(context);
	builder1.setMessage(dialog_start+"\n Time: 3minutes")
			.setCancelable(true)
			.setOnCancelListener(new OnCancelListener() {
				
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					
					Intent returnhome=new Intent(QuesFetch.this,Categories.class);
					startActivity(returnhome);
					
				}
			})
			.setPositiveButton("Start",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub	
					mCountDown.start();
				}
			})
			.show();   
	
	}
		previous.setVisibility(View.INVISIBLE);
		next.setVisibility(View.INVISIBLE);
	
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
	  	        Toast.makeText(QuesFetch.this, "Right! \nAnswer is: Sell ", Toast.LENGTH_LONG).show();
	  	        QuizDetails.set_score(QuizDetails.getscore()+1);
	  	        
	  	        
	  	      new Questionfetch(QuesFetch.this).execute(category);
			}else{
				
				 QuizDetails.setqid(QuizDetails.getqid()+1);
	  	         Toast.makeText(QuesFetch.this, "Wrong \nAnswer is: Sell", Toast.LENGTH_LONG).show();
	  	        	  	        
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
	  	        Toast.makeText(QuesFetch.this, "Right! \nAnswer is: Buy ", Toast.LENGTH_LONG).show();
	  	        QuizDetails.set_score(QuizDetails.getscore()+1);
	  	        
	  	        
	  	      new Questionfetch(QuesFetch.this).execute(category);
			}else{
				
				 QuizDetails.setqid(QuizDetails.getqid()+1);
	  	         Toast.makeText(QuesFetch.this, "Wrong! \nAnswer is: Buy "+QuizDetails.getans(), Toast.LENGTH_LONG).show();
	  	        	  	        
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
    String url="http://bizquiz.in/BizQuiz/QuesFetch.php";
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
		params.add(new BasicNameValuePair("month_id",Integer.toString(month_id)));
		params.add(new BasicNameValuePair("day_of_month",Integer.toString(day_of_month)));
		
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
		
      if(max_ques<QuizDetails.getqid() && max_ques!=0){
			
    	    
			Intent score=new Intent (QuesFetch.this,ScoreActivity.class);
			score.putExtra("cat_name", category);
			startActivity(score);
		
		}
      
      if(max_ques==0){
    	  
    	  Intent back=new Intent(QuesFetch.this,Categories.class);
    	  startActivity(back);
      }

		QuizDetails.setques(question);
		QuizDetails.setans(qanswer);
		QuizDetails.setshuffle(0);
		QuizDetails.set_maxques(max_ques);
		tvquestion.setText(QuizDetails.getques());
		
	    ques_stats.setText(Integer.toString(QuizDetails.getscore())+"/"+Integer.toString(QuizDetails.getqid()-1));
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
	   // threshvalue=length/2+1;
	    
	    //Log.d("ThreshValue",Integer.toString(threshvalue));
	    Boolean check=compareanswer(useranswer,length);
	      
	    if(check){
	  
	  	        QuizDetails.setqid(QuizDetails.getqid()+1);
//	  	        Toast.makeText(this, "Right ! \nAnswer is: "+QuizDetails.getans(), Toast.LENGTH_LONG).show();
	  	        Toast.makeText(this, "Right ! \nAnswer is: Chosen Answer Here", Toast.LENGTH_LONG).show();
	  	        QuizDetails.set_score(QuizDetails.getscore()+1);
	  	        pDialog.dismiss();
	  	        
	  	      new Questionfetch(QuesFetch.this).execute(category);
	 
	    }else{

		
		          QuizDetails.setqid(QuizDetails.getqid()+1);
		  	      Toast.makeText(this, "Wrong ! \nAnswer is: Chosen Answer Here", Toast.LENGTH_LONG).show();
		//          Toast.makeText(this, "Wrong. \nAnswer is: "+QuizDetails.getans(), Toast.LENGTH_LONG).show();
	  	        pDialog.dismiss();
	  	        
	  	        new Questionfetch(QuesFetch.this).execute(category);
	  	
        }

     }
	
    Boolean compareanswer(String ans,int length){
    	
    	if(length>10){
    		
    		threshvalue=(length*3)/4;
    	}else{
    		threshvalue=length/2;
    	}
    	
    	Log.d("threshvalue",Integer.toString(threshvalue)); 	  	
    	
    	int counter = 0;
    	
    	ans=ans.toLowerCase();
    	ans=ans.replaceAll("//s+","");
    	
    	String correctans=QuizDetails.getans().toLowerCase();
    	correctans=correctans.replaceAll("//s+", "");
    	
    	for(int i=0;i<correctans.length();i++){		
    try{		
    	  if(ans.charAt(i)==correctans.charAt(i)){
    		   counter++;		
    		}
    		}catch(StringIndexOutOfBoundsException e){
    		e.printStackTrace();
    		}
    	}
    	
    	
    	     if(threshvalue<=counter){
    	    	      return true;
    	     }else
    	     {
                      return false;  
             }
   
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
		finish();
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
    				getResources().getString(R.string.DialogBox_end))
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
           builder_back = new AlertDialog.Builder(context);
   		builder_back.setMessage(
   				getResources().getString(R.string.DialogBox_back))
   				.setCancelable(true)
   				.setOnCancelListener(new OnCancelListener() {
   					
   					public void onCancel(DialogInterface dialog) {
   						// TODO Auto-generated method stub
   						
//   						Intent playconti=new Intent(QuesFetch.this,ScoreActivity.class);
//   						calscore();
//   						startActivity(playconti);
   					}
   				})
   				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
   					
   					public void onClick(DialogInterface dialog, int which) {
   						// TODO Auto-generated method stub
   						
   						mCountDown.cancel();
   						Intent returnhome=new Intent(QuesFetch.this,ScoreActivity.class);
   						returnhome.putExtra("cat_name", category);
   			     		startActivity(returnhome);
   					}
   				})
   				.show();
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

