package com.mobilityspot;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

public class SpeedTrackingReceiver extends BroadcastReceiver {
	/**
	 * @see android.content.BroadcastReceiver#onReceive(Context,Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        
        if(!isGpsEnabled) {
        	System.out.println("GPS not enabled");
        	Intent intentGps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        	context.startActivity(intent);
        }
		
        else {
			Intent iServ = new Intent();
			iServ.setClass(context, SpeedTrackingService.class); 
			context.startService(iServ);///Calls another activity, by name, without passing data
			 
			
			try {
				Intent iExp = new Intent(context, SplashScreenActivity.class); 
				//Adding FLAG_ACTIVITY_NEW_TASK to get rid of this error messagE:
				//Exception: Calling startActivity() from outside of an Activity
				//context requires the FLAG_ACTIVITY_NEW_TASK flas. Is this really what you want?
				iExp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(iExp);
			} catch(Exception err) {
				
			}
        }
		
		
	}
}
