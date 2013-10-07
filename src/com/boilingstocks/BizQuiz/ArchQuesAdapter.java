package com.boilingstocks.BizQuiz;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArchQuesAdapter extends BaseAdapter {

	Context context;
	ArrayList<ArchQues> archQuestions = new ArrayList<ArchQues>();
	LayoutInflater inflater;
	public ArchQuesAdapter(Context context, ArrayList<ArchQues> archQuestions){
		this.context = context;
		this.archQuestions = archQuestions;
		inflater = LayoutInflater.from(this.context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return archQuestions.size();
	}

	@Override
	public ArchQues getItem(int position) {
		// TODO Auto-generated method stub
		return archQuestions.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	
		MyViewHolder myViewHolder;
		
		if(convertView == null){
			
			convertView = inflater.inflate(R.layout.ques_row_layout, null);
			myViewHolder = new MyViewHolder();
			convertView.setTag(myViewHolder);
		}else{
			
			myViewHolder = (MyViewHolder) convertView.getTag();
		}
		
		myViewHolder.myArchQuesTextView = (TextView) convertView.findViewById(R.id.tv_quesText);
		myViewHolder.myArchQuesTextView.setText(archQuestions.get(position).get_Question());
		
		return convertView;
	}
	
	public static class MyViewHolder{
		
		TextView myArchQuesTextView;
	}

}
