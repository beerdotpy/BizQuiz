package com.boilingstocks.BizQuiz;

import com.BizQuiz.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class Categories extends Activity {

	Button cat1;
	Button cat2;
	Button cat3;
	Button cat4;
	Button cat5;
	Button cat6;
	Button cat7;
	Button cat8;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_categories);
              
       cat1=(Button) findViewById(R.id.category1);
       cat2=(Button) findViewById(R.id.category2);
       cat3=(Button) findViewById(R.id.category3);
       cat4=(Button) findViewById(R.id.category4);
       cat5=(Button) findViewById(R.id.category5);
       cat6=(Button) findViewById(R.id.category6);
       cat7=(Button) findViewById(R.id.category7);
       cat8=(Button) findViewById(R.id.category8);
       
       
       cat1.setOnClickListener(new OnClickListener() {
	   	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		  	Intent intent=new Intent(Categories.this,QuesFetch.class);
		  	QuizDetails.setcategory(1);
		  	startActivity(intent);
		}
	});
	
       cat2.setOnClickListener(new OnClickListener() {
   	   	
   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   		  	Intent intent=new Intent(Categories.this,QuesFetch.class);
   		  	QuizDetails.setcategory(2);
   		  	startActivity(intent);
   		}
   	});
       
       cat3.setOnClickListener(new OnClickListener() {
   	   	
   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   		  	Intent intent=new Intent(Categories.this,QuesFetch.class);
   		  	QuizDetails.setcategory(3);
   		  	startActivity(intent);
   		}
   	});
       
       cat4.setOnClickListener(new OnClickListener() {
   	   	
   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   		  	Intent intent=new Intent(Categories.this,QuesFetch.class);
   		  	QuizDetails.setcategory(4);
   		  	startActivity(intent);
   		}
   	});
       
       cat5.setOnClickListener(new OnClickListener() {
   	   	
   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   		  	Intent intent=new Intent(Categories.this,QuesFetch.class);
   		  	QuizDetails.setcategory(5);
   		  	startActivity(intent);
   		}
   	});
       
       cat6.setOnClickListener(new OnClickListener() {
   	   	
   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   		  	Intent intent=new Intent(Categories.this,QuesFetch.class);
   		  	QuizDetails.setcategory(6);
   		  	startActivity(intent);
   		}
   	});
       
       cat7.setOnClickListener(new OnClickListener() {
   	   	
   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   		  	Intent intent=new Intent(Categories.this,QuesFetch.class);
   		  	QuizDetails.setcategory(7);
   		  	startActivity(intent);
   		}
   	});
            
       cat8.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(Categories.this,QuesFetch.class);
		  	QuizDetails.setcategory(8);
		  	startActivity(intent);
			
		}
	});
       
       
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
   			startActivity(new Intent(Categories.this, Settings.class));
   			return true;
   		case R.id.menu_about:
   			startActivity(new Intent(this, AboutUs.class));
   			return true;
   		case R.id.menu_archive:
   			Intent intent3 = new Intent(this,ArchiveMonthsActivity.class);
   			startActivity(intent3);
   			return true;
   		case R.id.menu_exit:
   			Intent intent = new Intent(Intent.ACTION_MAIN);
   			intent.addCategory(Intent.CATEGORY_HOME);
   			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   			startActivity(intent);
   		case R.id.menu_feedback:
   			Intent intent1=new Intent(this,Feedback.class);
   			startActivity(intent1);
   			return true;
   			
   		case R.id.menu_statistics:
  		     startActivity(new Intent(this,Statistics.class));
  		     return true;
   		case R.id.menu_profile:
   			Intent intent2 = new Intent(Categories.this,Profile.class);
   			startActivity(intent2);
   			return true;
  		     
   		default:
   			return super.onOptionsItemSelected(item);
   		}
    
       }

	@Override
    public void onBackPressed() {
       Log.d("CDA", "onBackPressed Called");
       Intent back = new Intent(Categories.this,Categories.class);
//       setIntent.addCategory(Intent.CATEGORY_HOME);
//       setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(back);
    }
    
}



