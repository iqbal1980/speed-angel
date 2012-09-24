package com.mobilityspot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SpeedBroadcastReceiver extends BroadcastReceiver {
	/**
	 * @see android.content.BroadcastReceiver#onReceive(Context,Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("*********** starting receiver **********");
		Intent mainIntent = new Intent();
		mainIntent.setClass(context, SpeedRetrieverService.class);
		context.startService(mainIntent); 
		
		
	}
}
