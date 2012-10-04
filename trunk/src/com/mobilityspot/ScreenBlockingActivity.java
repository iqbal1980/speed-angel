package com.mobilityspot;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;

public class ScreenBlockingActivity extends Activity {
 
	public TextView tvMph;
	public TextView tvKph;
	public TextView tvMps;
	
	public ActivityOnTopStatusSingleton sgl2 = ActivityOnTopStatusSingleton.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sgl2.isActivityOnTop = true;
        setContentView(R.layout.blocker);
        Intent sender=getIntent();
        String extraDataMPS = sender.getExtras().getString("speed1");
        String extraDataKPH = SpeedUnitsConversion.mpsToKph(extraDataMPS);
        String extraDataMPH = SpeedUnitsConversion.mpsToMph(extraDataMPS);
        
        tvMps = (TextView)  findViewById(R.id.textView_mps);
        tvMps.setText(extraDataMPS);
        
        tvKph = (TextView)  findViewById(R.id.textView_kph);
        tvKph.setText(extraDataKPH);
        
        tvMph = (TextView)  findViewById(R.id.textView_mph);
        tvMph.setText(extraDataMPH);
        
        //LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("speedExceeded"));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		sgl2.isActivityOnTop = true;
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("speedExceeded"));
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		sgl2.isActivityOnTop = false;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		sgl2.isActivityOnTop = false;
	}
	
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        String currentSpeedMPS = intent.getStringExtra("speed");
	        String currenSpeedKPH = SpeedUnitsConversion.mpsToKph(currentSpeedMPS);
	        String currenSpeedMPH = SpeedUnitsConversion.mpsToMph(currentSpeedMPS);
	         
	        
	        tvMps = (TextView)  findViewById(R.id.textView_mps);
	        tvMps.setText(currentSpeedMPS + "m/s");
	        
	        tvKph = (TextView)  findViewById(R.id.textView_kph);
	        tvKph.setText(currenSpeedKPH + "Kph");
	        
	        tvMph = (TextView)  findViewById(R.id.textView_mph);
	        tvMph.setText(currenSpeedMPH + "Mph");
	    }
	};


	 
}
