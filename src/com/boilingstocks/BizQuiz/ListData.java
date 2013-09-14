package com.boilingstocks.BizQuiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

public class ListData {
	
	private String username;
	private int score;
	SharedPreferences sp;
	Context ctx;
	
	public ListData(String t, int s){
		
		username = t;
		score = s;
	}
	
	public ListData(){
		
	}
	
	public String getusername() {
		return username;
	}
	public void setusername(String user) {
		this.username = user;
	}
	public int getscore() {
		return score;
	}
	public void setscore(int scre) {
		this.score = scre;
	}
    	
	
}
