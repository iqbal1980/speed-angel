package com.mobilityspot;

public class SpeedUnitsConversion {
	  public static double mphToMeterPerSecond(Double mphSpeed) {
		double msSpeed = (mphSpeed * 1609.34) /3600; 
		return msSpeed; 
	  }
	  
	  public static double kphToMeterPerSecond(Double kphSpeed) {
		double msSpeed = (kphSpeed * 1000) /3600; 
		return msSpeed;  
	  }
}
