package com.nguyenvanai.app.managers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversionManager {

	// convert date to string dd/MM/yyyy
	public static String convertDateToString(Date date){
		String s = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		s = format.format(date);
		return s;
	}
	
	// convert date to string without slashes  ddMMyyyy
	public static String convertDateToStringWithoutSlashes(Date date){
		String s = "";
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		s = format.format(date);
		return s;
	}
}
