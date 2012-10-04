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


	  public static String mpsToKph(String speed) {
			Double speedKph = Double.valueOf(speed) * (3.6);
			return speedKph.toString();  
	  }
	  
	  public static String mpsToMph(String speed) {
		Double speedMps = Double.valueOf(speed) * (2.23694);
		return speedMps.toString();  
	  }


}
