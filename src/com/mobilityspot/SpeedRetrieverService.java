package com.mobilityspot;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class SpeedRetrieverService extends Service implements LocationListener{
	
	private LocationManager locationManager;
	private String provider;
	
	/**
	 * @see android.app.Service#onBind(Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
    	System.out.println("Speed Retriever service created");
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!isGpsEnabled){
        	System.out.println("GPS not enabled");
        	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        	startActivity(intent);
        }else{
        	System.out.println("GPS is enabled, proceeding..");
        	Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, this);
        }
        
	}

	@Override
	public void onLocationChanged(Location location) {
		
		float speed = location.getSpeed();
		System.out.println("current speed :" + speed);
		float speedPreference = getSpeedPreference();
		if(speedPreference == 0){
			Log.e("SPEEDRETRIEVERSERVICE", "Speed Preference not set..");
			Intent intent = new Intent(this,PreferencesActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			//TODO: not sure what we normally do here?  when the preference is set in the settings activity, do we reload the mainactivity or how is the main activity loaded from preference activity?
			
		}
		if(speed > getSpeedPreference()) {
			activateDisplayActivity(speed);
			startBroadcastingNewSpeed(speed);
		}
	}

	private void startBroadcastingNewSpeed(float speed) {
		Intent i = new Intent("NEW_SPEED_ARRIVED");
		i.putExtra("speed", speed);
		this.sendBroadcast(i);
		
	}
	
	private float getSpeedPreference() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
   	    String listPrefs = preferences.getString("listOfSpeeds", "0");
   	    
		return Float.parseFloat(listPrefs);
	}

	private void activateDisplayActivity(float speed) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("speed", speed);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
		startActivity(intent);
		//PendingIntent pIntent = PendingIntent.getActivity(this, 1111, intent, 0);
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		Toast.makeText(this, "Disabled provider " + provider +". You must enable it to get speed updates " ,
		        Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		 Toast.makeText(this, "Enabled new provider " + provider,
			        Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "Service onDestroy() ", Toast.LENGTH_LONG).show();
	}

	
	
}
