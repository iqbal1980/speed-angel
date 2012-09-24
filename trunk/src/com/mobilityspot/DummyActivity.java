package com.mobilityspot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DummyActivity extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dummy);
	}
	public void launchService(View view){
		if(view.getId() == R.id.button1) {
			Intent intent = new Intent();
			intent.setClass(this,SpeedRetrieverService.class);
			this.startService(intent);
		}
	}
	
}
