package com.boilingstocks.BizQuiz;

import java.util.ArrayList;

import com.BizQuiz.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

public class StatisticsRowAdapter extends BaseAdapter {
	
	private final Context context;
	private ArrayList<ListData> myList = new ArrayList<ListData>();
	private LayoutInflater inflater;
	SharedPreferences sp;
	int score[]=new int[8];
	
	String getuser(){
	sp=context.getSharedPreferences("First_run",context.MODE_PRIVATE );
	String user_name =sp.getString("Username", " ");
	
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
	
	return user_name;
	}
	
	public StatisticsRowAdapter(Context context, ArrayList<ListData> myList) {
		this.context = context;
		this.myList = myList;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
	  return	myList.size();
	}

	@Override
	public ListData getItem(int position) {
		// TODO Auto-generated method stub
		return myList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
           MyViewHolder mViewHolder;
		
		if(convertView == null){
			
			convertView = inflater.inflate(R.layout.statistics_row,null);
			mViewHolder = new MyViewHolder();
			convertView.setTag(mViewHolder);
		}else{
			
			mViewHolder = (MyViewHolder) convertView.getTag();
		}
		
		mViewHolder.u_name = (TextView) convertView.findViewById(R.id.user_name);
		mViewHolder.scre = (TextView) convertView.findViewById(R.id.scre);
		mViewHolder.individual_score=(TextView) convertView.findViewById(R.id.individual_score);
		mViewHolder.individual_score.setVisibility(View.INVISIBLE);
		
		mViewHolder.u_name.setText(myList.get(position).getusername());
		try{
			if(getuser().compareToIgnoreCase(myList.get(position).getusername())==0){
		
			Log.d("check","check");
			mViewHolder.individual_score.setVisibility(View.VISIBLE);
			 
			
			mViewHolder.individual_score.setText(Integer.toString(score[0])+","+Integer.toString(score[1])+","+Integer.toString(score[2])
					                              +","+Integer.toString(score[3])+","+Integer.toString(score[4])+","+Integer.toString(score[5])
					                              +","+Integer.toString(score[6])+","+Integer.toString(score[7]));	
			
		}
		
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		mViewHolder.scre.setText(Integer.toString(myList.get(position).getscore()));
		
		mViewHolder.u_name.setWidth(80);
		
		return convertView;
	}
	
public static class MyViewHolder{
		
		TextView u_name,scre,individual_score;
		
	}



}
