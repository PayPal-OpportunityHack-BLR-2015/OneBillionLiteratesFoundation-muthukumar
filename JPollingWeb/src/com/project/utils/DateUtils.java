package com.project.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date parse(String dateValue){
		Date date = null;
		try{
			date = dateformat.parse(dateValue);
		} catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
	
	public static String format(Date date){
		String dateValue = null;
		try{
			dateValue = dateformat.format(date);
		} catch(Exception e){
			e.printStackTrace();
		}
		return dateValue;
	}
	
	public static void main(String args[]){
		Date date = DateUtils.parse("2015-11-29");
		System.out.println(DateUtils.format(date));
	}
}
