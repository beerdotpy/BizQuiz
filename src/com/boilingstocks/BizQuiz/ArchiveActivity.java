package com.boilingstocks.BizQuiz;


import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.BizQuiz.R;

public class ArchiveActivity extends Activity{
	ArrayList<ArchQues> archQuestions = new ArrayList<ArchQues>();
	ArchQuesAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archives);
		
		/***
		 * TODO 
		 * PARSE YOUR JSON RESPONSE AND CREATE THE LIST.
		 * 
		 */
		adapter = new ArchQuesAdapter(ArchiveActivity.this, archQuestions);
		ListView monthLV = (ListView) findViewById(R.id.archiveListView);
		monthLV.setAdapter(adapter);
		monthLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(ArchiveActivity.this, archQuestions.get(arg2).get_Answer(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
}
