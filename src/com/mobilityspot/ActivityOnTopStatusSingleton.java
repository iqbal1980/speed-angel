package com.mobilityspot;

class ActivityOnTopStatusSingleton{
	private static ActivityOnTopStatusSingleton instance= new ActivityOnTopStatusSingleton();
	public Boolean isActivityOnTop = false;
 
	public static ActivityOnTopStatusSingleton getInstance(){
		return instance;
	}
}