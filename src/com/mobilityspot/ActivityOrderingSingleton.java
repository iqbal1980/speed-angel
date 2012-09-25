package com.mobilityspot;

import java.util.* ;
class ActivityOrderingSingleton{
	private static ActivityOrderingSingleton instance= new ActivityOrderingSingleton();
	public boolean isActivityOnTop = false;
	private List myList;
	private Boolean state;
	private ActivityOrderingSingleton(){
		myList= new ArrayList();
		state = true;
	}
	public static ActivityOrderingSingleton getInstance(){
		return instance;
	}
}