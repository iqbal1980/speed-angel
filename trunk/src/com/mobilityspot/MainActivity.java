package com.mobilityspot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					Intent iExp = new Intent(MainActivity.this, BasicActivity.class);
					 iExp.putExtra("speed1", "0");
					startActivity(iExp);
				}
				catch(Exception err) {
					
				}
			}
        	
        });
        
    }
}