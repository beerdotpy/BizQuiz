package in.bizquiz.app;

import java.util.Random;

import android.util.Log;

public class QuizDetails {

	static String question;
	static String answer;
    static int qid;	
    static int category;
    static int max_ques=-1;
    static int score;
    static int shuffle=0;
    static int final_score=0;
    static int[] arr={1,2,3,4,5};
    static int day_of_month;    
    
  static void setcategory(int cat){
	  category=cat;
  }
  
  static void setques(String ques){
	  question=ques;
  }

  static void setans(String ans){
	  answer=ans;
  }

  static void setqid(int id){
	  qid=id;
  }
  
  static void set_score(int scr){
	  score=scr;
  }
  
  static void set_maxques(int max){
		 max_ques=max;
	 }
  
  static void setfinal_score(int scre){
	  final_score=scre;
  }
  
  static void setshuffle(int shuff){
	  shuffle=shuff;
  }
  
static int getcategory(){
	  
	  return category;
  }

  static String getques()
  {
	  return question;
  }
 
  static String getans()
  {
	  return answer;
  }
  
  static int getqid(){
	  return qid;
  }
  
  static int getmax_ques(){
	  return max_ques;
  }
  
  static int getscore(){
	  return score;
  }
  
 static int getfinal_score(){
	  return final_score;
  }
  
 static int getshuffle(){
	 return shuffle;
 }
 
 static void setdayofmonth(int d){
	 day_of_month=d;
	  }
 static int getday(){
	 return day_of_month;
 }
 
 static void shuffle(){
	  
	  Random rnd = new Random();
	  for (int i = arr.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = arr[index];
	      arr[index] = arr[i];
	      arr[i] = a;
	    }

	  
	   Log.d("value",Integer.toString(arr[0]));
  }
  
  static int getvalue(){
	  try{ 
	  return arr[qid];
	  }catch(ArrayIndexOutOfBoundsException e){
		  	e.printStackTrace();
		  	return 0;
	  }
  }
  
  
}