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
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class SpeedTrackingService extends Service implements LocationListener {
	private LocationManager locationManager;
	private String provider;
	private double speedThreshold;
	private boolean showBlockActivity = true;
 
	public TelephonyManager tm;

 

	 private PhoneStateListener mPhoneListener = new PhoneStateListener() {
	  public void onCallStateChanged(int state, String incomingNumber) {
	   try {
	    switch (state) {
	    case TelephonyManager.CALL_STATE_RINGING:
	     //Toast.makeText(SpeedTrackingService.this, "CALL_STATE_RINGING", Toast.LENGTH_SHORT).show();
	     showBlockActivity = false;
	     break;
	    case TelephonyManager.CALL_STATE_OFFHOOK:
	     //Toast.makeText(SpeedTrackingService.this, "CALL_STATE_OFFHOOK", Toast.LENGTH_SHORT).show();
	     showBlockActivity = true;	
	     break;
	    case TelephonyManager.CALL_STATE_IDLE:
	    showBlockActivity = true;	
	     //Toast.makeText(SpeedTrackingService.this, "CALL_STATE_IDLE", Toast.LENGTH_SHORT).show();
	     break;
	    default:
	     showBlockActivity = true;		
	     Toast.makeText(SpeedTrackingService.this, "default", Toast.LENGTH_SHORT).show();
	    }
	   } catch (Exception e) {
	   }
	  }
	 };
	
   @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
  	  tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
  	  tm.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    	
    	SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String speedUnit = SP.getString("listOfSpeedUnits", "ms");
		
		Toast.makeText(this, "Speed unit  **** >>>>>>>" + speedUnit, Toast.LENGTH_LONG).show();
		double speedThresholdTmp = Double.valueOf(SP.getString("listOfSpeeds", "2"));
		Toast.makeText(this, "Threshold in double ==== >>>>>>>" + speedThresholdTmp, Toast.LENGTH_LONG).show();
		
		if(speedUnit.equals("mph")) {
			speedThreshold = SpeedUnitsConversion.mphToMeterPerSecond(speedThresholdTmp) ;
		}
		if(speedUnit.equals("kph")) {
			speedThreshold = SpeedUnitsConversion.kphToMeterPerSecond(speedThresholdTmp) ;
		}
		if(speedUnit.equals("ms")) {
			speedThreshold =  speedThresholdTmp;
		}
		
		//speedThreshold = (double) (-1);
		Toast.makeText(this, "Speed threshold  = " + speedThreshold, Toast.LENGTH_LONG).show();
		
    	System.out.println("Speed Retriever service created");
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!isGpsEnabled) {
        	System.out.println("GPS not enabled");
        	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        	startActivity(intent);
        	stopSelf();
        } else {
        	System.out.println("GPS is enabled, proceeding..");
        	Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, this);
        }
    }

    @Override
    public void onDestroy() {
    	
		/*Intent iExp2 = new Intent(this, ScreenBlockingActivity.class);
		iExp2.putExtra("finish", "destoryMe");
		iExp2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(iExp2);*/
    	Toast.makeText(this, "Speed Tracking Service Destroyed ", Toast.LENGTH_LONG).show();
    	Toast.makeText(this, "Thank you for using SpeedAngel ", Toast.LENGTH_LONG).show();
    	android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
        
    }

	@Override
	public void onLocationChanged(Location location) {

		try {
		double speedDbl = location.getLatitude();//location.getSpeed();
    
         String speedStr = Double.toString(speedDbl);

 
         ActivityOnTopStatusSingleton sgl = ActivityOnTopStatusSingleton.getInstance();
         System.out.println(">>>>>>>>>>>>>> Speed ===== *****"+speedStr +"SINGLETON ON ===" +sgl.isActivityOnTop);
         
         if(speedDbl > speedThreshold) { 
			 if(sgl.isActivityOnTop == false &&  showBlockActivity == true) {
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
		stopSelf();
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
	
	
	@Override
	public int onStartCommand (Intent intent, int flags, int startId) {
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		 return START_STICKY;
	
	}
}


