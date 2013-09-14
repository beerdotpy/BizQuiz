package com.boilingstocks.BizQuiz;

public class UserDetails {
	
	static String user_name;
	static String user_email;
    static String user_age;	
    static String user_contact;
    static String user_city;
    

    static void setname(String user){
  	  user_name=user;
    }
    
    static void setemail(String emailid){
    	  user_email=emailid;
      }
    
    static void setage(String age){
    	  user_age=age;
      }
    
    static void setcontact(String contact){
    	  user_contact=contact;
      }

    static void setcity(String city){
    	  user_city=city;
      }

    static String getname(){
  	  return user_name;
    }

    static String getemail(){
    	  return user_email;
      }

    static String getage(){
    	  return user_age;
      }

    static String getcontact(){
    	  return user_contact;
      }

    static String getcity(){
    	  return user_city;
      }
}

