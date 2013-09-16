package com.boilingstocks.BizQuiz;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.BizQuiz.R;

public class ArchiveMonthsActivity extends Activity {

	ArrayList<Month> monthList = new ArrayList<Month>();
	MonthAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_months);
		/***
		 * TODO 
		 * PARSE YOUR JSON RESPONSE AND CREATE THE LIST.
		 * 
		 */
		adapter = new MonthAdapter(ArchiveMonthsActivity.this, monthList);
		ListView monthLV = (ListView) findViewById(R.id.monthListView);
		monthLV.setAdapter(adapter);
		monthLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent archPageIntent = new Intent(ArchiveMonthsActivity.this,ArchiveActivity.class);
				archPageIntent.putExtra("archiveId", monthList.get(arg2)._Id);
				startActivity(archPageIntent);
			}
		});
	}
	
}
