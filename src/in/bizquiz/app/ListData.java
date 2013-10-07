package in.bizquiz.app;

import android.content.Context;
import android.content.SharedPreferences;

public class ListData {
	
	private String username;
	private int score;
	private String cat_name;
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
    public void setcatname(String name){
    	cat_name=name;
    }
	public String getcatname(){	
		return cat_name;
	}	
	
	
}
