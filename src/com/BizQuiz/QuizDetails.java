package com.BizQuiz;

public class QuizDetails {

	static String question;
	static String answer;
    static int qid;	
    static int category;
    static int score=0;
    static int shuffle=0;
    
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
  
  static void set_score(int scr){
	  score=scr;
  }

  static int getscore(){
	  return score;
  }
  static int getshuffle()
  {
	  return shuffle;
  }
  static void setshuffle(int shuff)
  {
	  shuffle=shuff;
  }
}
