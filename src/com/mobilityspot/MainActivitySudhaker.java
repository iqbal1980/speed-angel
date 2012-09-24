package com.mobilityspot;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class MainActivitySudhaker extends Activity {
	private TextView tv;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getAndSetSpeed();
        this.registerReceiver(speedUpdateBroadcastReceiver, new IntentFilter("NEW_SPEED_ARRIVED"));
        displaySharedPreferences();
    }
    
    private BroadcastReceiver speedUpdateBroadcastReceiver= new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			Bundle b= intent.getExtras();
			System.out.println("new speed arrived : " + b.getFloat("speed"));
			tv.setText(String.valueOf(b.getFloat("speed")));
			
		}
	};

	
    private void getAndSetSpeed(){
    	Intent sender=getIntent();
        float extraData = sender.getExtras().getFloat("speed");
        tv = (TextView)  findViewById(R.id.textView2);
        tv.setText(String.valueOf(extraData));
    }
    private void displaySharedPreferences() {
    	TextView preference = (TextView)findViewById(R.id.textView3);
    	   SharedPreferences prefs = PreferenceManager
    	    .getDefaultSharedPreferences(MainActivitySudhaker.this);

    	   boolean enableSpeedTickerService = prefs.getBoolean("enableSpeedTickerService", false);
    	   String listPrefs = prefs.getString("listOfSpeeds", "0");

    	   StringBuilder builder = new StringBuilder();
    	   builder.append("Enable speed ticker service : " + String.valueOf(enableSpeedTickerService) + "\n");
    	   builder.append("Speed max allowable limit preference : " + listPrefs + "mph");

    	   preference.setText(builder.toString());
    	} 
    
   @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.registerReceiver(speedUpdateBroadcastReceiver, new IntentFilter("NEW_SPEED_ARRIVED"));
	}
    
}