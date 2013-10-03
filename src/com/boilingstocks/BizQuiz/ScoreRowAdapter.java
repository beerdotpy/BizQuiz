package com.boilingstocks.BizQuiz;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScoreRowAdapter extends BaseAdapter {
	
	private final Context context;
	private ArrayList<ListData> list = new ArrayList<ListData>();
	private LayoutInflater inflater;
	
	public ScoreRowAdapter(Context context, ArrayList<ListData> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int arg0) {
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
		
		mViewHolder.cat_name = (TextView) convertView.findViewById(R.id.user_name);
		mViewHolder.cat_score = (TextView) convertView.findViewById(R.id.scre);
		
		
		mViewHolder.cat_name.setText(list.get(position).getcatname());
		
		mViewHolder.cat_score.setText(Integer.toString(list.get(position).getscore()));
		
			
		return convertView;
	}

public static class MyViewHolder{
		
		TextView cat_name,cat_score;
		
	}
}
