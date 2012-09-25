package com.mobilityspot;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class SpeedTrackingService extends Service implements LocationListener {
	private LocationManager locationManager;
	private String provider;
 
   @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

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
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Speed Tracking Service Destroyed ", Toast.LENGTH_LONG).show();
    }

	@Override
	public void onLocationChanged(Location location) {

		try {
		double speedDbl = location.getSpeed();
    
         String speedStr = Double.toString(speedDbl);

 
         ActivityOrderingSingleton sgl = ActivityOrderingSingleton.getInstance();
         System.out.println(">>>>>>>>>>>>>> Speed ===== *****"+speedStr +"SINGLETON ON ===" +sgl.isActivityOnTop);
		 
         if(speedDbl > -1) { 
			 if(sgl.isActivityOnTop == false) {
		         Intent iExp = new Intent(this, ScreenBlockingActivity.class);
		         iExp.putExtra("speed1", speedStr);
				 iExp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 startActivity(iExp);
			 }
			 
	         Intent intent = new Intent("speedExceeded");
	         intent.putExtra("speed", speedStr);
	         LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
 
		 }
		} catch(Exception err) {
			System.out.println(">>>>>>>>>>>>>> ERROR :( ===== *****"+err.getMessage());
		}
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Please enable your GPS ", Toast.LENGTH_LONG).show();
    	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    	startActivity(intent);
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}


