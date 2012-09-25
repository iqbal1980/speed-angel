package com.mobilityspot;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SpeedTrackingReceiver extends BroadcastReceiver {
	/**
	 * @see android.content.BroadcastReceiver#onReceive(Context,Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent iServ = new Intent();
		iServ.setClass(context, SpeedTrackingService.class); 
		context.startService(iServ);///Calls another activity, by name, without passing data

		/*Intent iExp = new Intent(context, SplashScreenActivity.class); 
		context.startActivity(iExp);*/
		
		
	}
}
