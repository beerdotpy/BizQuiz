package com.boilingstocks.BizQuiz;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.BizQuiz.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ArchiveCategory extends Activity {
	
	ListView catLV;
	CategoryRowAdapter adapter;	
	ArrayList<ListData> cat_list = new ArrayList<ListData>();
	String[] category_name={"Category1","Category2","Category3","Category4","Category5","Category6"};
	int M_id;	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_months);
		
		 M_id=getIntent().getIntExtra("archiveId",-1);
		
		catLV = (ListView) findViewById(R.id.monthListView);	
	
		for(int i=0;i<6;i++){
			ListData list=new ListData();
			list.setcatname(category_name[i]);
			cat_list.add(list);

		}
		
		adapter = new CategoryRowAdapter(ArchiveCategory.this,cat_list);
		
		
		catLV.setAdapter(adapter);
		
		catLV.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent archPageIntent = new Intent(ArchiveCategory.this,ArchiveActivity.class);
			archPageIntent.putExtra("catname", cat_list.get(arg2).getcatname());
			archPageIntent.putExtra("archiveId", M_id);
			startActivity(archPageIntent);
		}
	});
	}
	
	@Override
    public void onBackPressed() {
       Log.d("CDA", "onBackPressed Called");
       Intent back = new Intent(this,ArchiveMonthsActivity.class);
//       setIntent.addCategory(Intent.CATEGORY_HOME);
//       setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(back);
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
   		case R.id.menu_archive:
   			Intent intent3 = new Intent(this,ArchiveMonthsActivity.class);
   			startActivity(intent3);
   			return true;	
  		
 		     
   		default:
   			return super.onOptionsItemSelected(item);
   		}
    
       }
	
	
}
