package com.mobilityspot;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;

public class BasicReceiver extends BroadcastReceiver {
	/**
	 * @see android.content.BroadcastReceiver#onReceive(Context,Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent iServ = new Intent();
		System.out.println("**********Brodcast started**********************");
		iServ.setClass(context, BasicService.class); 
		context.startService(iServ);
	}
}
