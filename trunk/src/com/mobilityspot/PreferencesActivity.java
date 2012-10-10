/*
 ******************************************************************************
 * Parts of this code sample are licensed under Apache License, Version 2.0   *
 * Copyright (c) 2009, Android Open Handset Alliance. All rights reserved.    *
 *                                                                            *                                                                         *
 * Except as noted, this code sample is offered under a modified BSD license. *
 * Copyright (C) 2010, Motorola Mobility, Inc. All rights reserved.           *
 *                                                                            *
 * For more details, see MOTODEV_Studio_for_Android_LicenseNotices.pdf        * 
 * in your installation folder.                                               *
 ******************************************************************************
 */

package com.mobilityspot;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

/***
 * PreferenceActivity is a built-in Activity for preferences management
 * 
 * To retrieve the values stored by this activity in other activities use the
 * following snippet:
 * 
 * SharedPreferences sharedPreferences =
 * PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
 * <Preference Type> preferenceValue = sharedPreferences.get<Preference
 * Type>("<Preference Key>",<default value>);
 */
public class PreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener  {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.samplepreferences);
		SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(this);

			prefs.registerOnSharedPreferenceChangeListener(this);
	}
	
	private Boolean isServiceRunning(String serviceName) {
		
		 ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		    for (RunningServiceInfo runningServiceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {
		        if (serviceName.equals(runningServiceInfo.service.getClassName())) {
		        	return true;
		        }
		    }
		    return false;
	}


	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		//Todos by Sudhaker
		
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean serviceShouldStart =  SP.getBoolean("enableSpeedTickerService", false);
		boolean isServiceRunning = isServiceRunning("SpeedTrackingService");
		
		Toast.makeText(this, "Preferences changed" + serviceShouldStart, Toast.LENGTH_LONG).show();
		Intent iServ = new Intent();
		iServ.setClass(this, SpeedTrackingService.class); 
		
		if(serviceShouldStart == true) {
			this.stopService(iServ);
			this.startService(iServ);
		} else {
				this.stopService(iServ);

		}
		


	}
}