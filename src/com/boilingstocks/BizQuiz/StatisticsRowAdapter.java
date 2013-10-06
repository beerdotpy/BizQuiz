package com.boilingstocks.BizQuiz;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StatisticsRowAdapter extends BaseAdapter {
	
	private final Context context;
	private ArrayList<ListData> myList = new ArrayList<ListData>();
	private LayoutInflater inflater;
	SharedPreferences sp;
	int score[]=new int[8];

	public StatisticsRowAdapter(Context context, ArrayList<ListData> myList) {
		this.context = context;
		this.myList = myList;
		inflater = LayoutInflater.from(this.context);
	}

	String return_username(){
		
		sp=context.getSharedPreferences("First_run",Context.MODE_PRIVATE );
		final String user_name =sp.getString("Username", " ");
		
		return user_name;	
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
		
		Log.d("username",return_username());
		Log.d("list_username",myList.get(position).getusername());
		
		if(myList.get(position).getusername().compareToIgnoreCase(return_username())==0){
			
//		String temp=return_username();
//		SpannableString spanString = new SpannableString(temp);
//		spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
//		mViewHolder.u_name.setText(spanString);
//		
//		String temp_score=Integer.toString(myList.get(position).getscore());
//		SpannableString spanString_score = new SpannableString(temp_score);
//		spanString_score.setSpan(new UnderlineSpan(), 0, spanString_score.length(), 0);
//		mViewHolder.scre.setText(spanString_score);
			
			convertView.setBackgroundColor(Color.parseColor("#FFDBB8"));
			mViewHolder.u_name.setText(myList.get(position).getusername());		
			mViewHolder.scre.setText(Integer.toString(myList.get(position).getscore()));
		}else{
		
			convertView.setBackgroundColor(Color.TRANSPARENT);	
		mViewHolder.u_name.setText(myList.get(position).getusername());		
		mViewHolder.scre.setText(Integer.toString(myList.get(position).getscore()));
		}
		return convertView;
	}
	
public static class MyViewHolder{
		
		TextView u_name,scre;
		
	}



}
