package com.BizQuiz;

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
       
    
       cat1=(Button) findViewById(R.id.previous);
       cat1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		  	Intent intent=new Intent(Categories.this,QuesFetch.class);
		  	QuizDetails.setcategory(1);
		  	QuizDetails.setqid(1);
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
   			//startActivity(new Intent(Home.this, Settings.class));
   			return true;
   		case R.id.menu_about:
   			startActivity(new Intent(this, AboutUs.class));
   			return true;
   		case R.id.menu_register:
   		     startActivity(new Intent(this,Register.class));
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



