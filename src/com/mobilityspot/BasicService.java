package com.mobilityspot;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class BasicService extends Service implements LocationListener {

 
   @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    	System.out.println("service created");
    	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service onDestroy() ", Toast.LENGTH_LONG).show();
    }

	@Override
	public void onLocationChanged(Location location) {

		try {
		double speedDbl = location.getSpeed();
    
         String speedStr = Double.toString(speedDbl);

 
         Singleton sgl = Singleton.getInstance();
         System.out.println(">>>>>>>>>>>>>> Speed ===== *****"+speedStr +"SINGLETON ON ===" +sgl.isOn);
		 
         if(speedDbl > -1) { 
			 if(sgl.isOn == false) {
		         Intent iExp = new Intent(this, BasicActivity.class);
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
		// TODO Auto-generated method stub
		
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


