package com.boilingstocks.BizQuiz;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ArchiveCategory extends Activity {
	
	ListView catLV;
	CategoryRowAdapter adapter;	
	ArrayList<ListData> cat_list = new ArrayList<ListData>();
	
	int M_id;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_archive);
		
		String[] category_name={getResources().getString(R.string.Category1),
                getResources().getString(R.string.Category2),
                getResources().getString(R.string.Category3),
                getResources().getString(R.string.Category4),
                getResources().getString(R.string.Category5),
                getResources().getString(R.string.Category6)};
		
		M_id=getIntent().getIntExtra("archiveId",-1);
		 Log.d("archiveId",Integer.toString(M_id));
		
		catLV = (ListView) findViewById(R.id.catListView);	
	
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
