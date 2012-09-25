package com.mobilityspot;

class ActivityOrderingSingleton{
	private static ActivityOrderingSingleton instance= new ActivityOrderingSingleton();
	public Boolean isActivityOnTop = false;
 
	public static ActivityOrderingSingleton getInstance(){
		return instance;
	}
}