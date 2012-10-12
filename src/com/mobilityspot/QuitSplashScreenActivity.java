package com.mobilityspot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;

public class QuitSplashScreenActivity extends Activity  {
    protected boolean _active = true;
    protected int _splashTime = 5000;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyousplash);
        
        // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
        	private volatile Thread blinker;

            public void safeNonDeprecatedStop() {
                blinker = null;
            }
            
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    //stop();//<--- This a Thread depecrated method use safeNonDeprecatedStop() instead. See above.
                    safeNonDeprecatedStop();
                }
            }
        };
        splashTread.start();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
}