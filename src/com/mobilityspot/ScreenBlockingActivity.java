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
 
	public TextView tv;
	public ActivityOnTopStatusSingleton sgl2 = ActivityOnTopStatusSingleton.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sgl2.isActivityOnTop = true;
        setContentView(R.layout.blocker);
        Intent sender=getIntent();
        String extraData = sender.getExtras().getString("speed1");
        tv = (TextView)  findViewById(R.id.blocktext);
        tv.setText(extraData);
        
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
	        String currentSpeed = intent.getStringExtra("speed");
	         tv = (TextView)  findViewById(R.id.blocktext);
	        tv.setText(currentSpeed);
	    }
	};


	 
}
