package com.mobilityspot;

import java.util.* ;
class Singleton{
	private static Singleton instance= new Singleton();
	public boolean isOn = false;
	private List maList;
	private Boolean etat;
	private Singleton(){
		maList= new ArrayList();
		etat = true;
	}
	public static Singleton getInstance(){
		return instance;
	}
}