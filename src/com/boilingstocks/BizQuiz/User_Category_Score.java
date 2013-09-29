package com.boilingstocks.BizQuiz;

import java.util.ArrayList;

import com.BizQuiz.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

public class User_Category_Score extends Activity {
	
	ListView lv_score;
	ArrayList<ListData> my_score_list = new ArrayList<ListData>();
	ScoreRowAdapter adapter;
	int score[]=new int[8];
	
	SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	
	super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_score);
    
    String[] name={getResources().getString(R.string.Category1),
            getResources().getString(R.string.Category2),
            getResources().getString(R.string.Category3),
            getResources().getString(R.string.Category4),
            getResources().getString(R.string.Category5),
            getResources().getString(R.string.Category6),
            getResources().getString(R.string.Category7),
            getResources().getString(R.string.Category8)};
    
    sp=this.getSharedPreferences("First_run",this.MODE_PRIVATE );
    score[0] =sp.getInt("Category1", 0);
    score[1] =sp.getInt("Category2", 0);
    score[2] =sp.getInt("Category3", 0);
    score[3] =sp.getInt("Category4", 0);
    score[4] =sp.getInt("Category5", 0);
    score[5] =sp.getInt("Category6", 0);
    score[6] =sp.getInt("Category7", 0);
    score[7] =sp.getInt("Category8", 0);
    
    for(int i=0;i<8;i++){
    	if(score[i]==-1){
    		    		     
    		score[i]=0;
    	}
    }
    
	lv_score=(ListView) findViewById(R.id.lv_score);
	
	for(int i=0;i<8;i++){
		ListData list=new ListData();
		list.setcatname(name[i]);
		list.setscore(score[i]);
		my_score_list.add(list);

	}
	
	adapter = new ScoreRowAdapter(User_Category_Score.this,my_score_list);
	
	
	lv_score.setAdapter(adapter);
		
	
	}

}
