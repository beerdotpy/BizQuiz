package com.boilingstocks.BizQuiz;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MonthAdapter extends BaseAdapter{

	Context context;
	ArrayList<Month> monthList = new ArrayList<Month>();
	LayoutInflater inflater;
	public MonthAdapter(Context context, ArrayList<Month> monthList){
		this.context = context;
		this.monthList = monthList;
		inflater = LayoutInflater.from(this.context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return monthList.size();
	}

	@Override
	public Month getItem(int position) {
		// TODO Auto-generated method stub
		return monthList.get(position);
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
		
		if(convertView==null)
		{
			convertView = inflater.inflate(R.layout.month_row_layout, null);
			myViewHolder = new MyViewHolder();
			convertView.setTag(myViewHolder);
		}else{
			myViewHolder = (MyViewHolder) convertView.getTag();
		}
		
		myViewHolder._monthTextView = (TextView) convertView.findViewById(R.id.tv_monthText);
		myViewHolder._monthTextView.setText(monthList.get(position).get_Month()+","+monthList.get(position).get_Year());
		
		return convertView;
	}

	public static class MyViewHolder{
		
		TextView _monthTextView;
	}
}
