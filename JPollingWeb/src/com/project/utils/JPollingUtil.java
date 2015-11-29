/**
* Project development
**/
package com.project.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpSession;

/**
 * @author P.Ayyasamy
 * @since 1.0
 */
public class JPollingUtil {

	public static String getDate() {
		StringBuffer date = new StringBuffer();
		try {
			Calendar cal = new GregorianCalendar();
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			date.append(day).append( "-").append(month + 1).append("-").append(year);
		} catch (Exception e) {
			System.out.println("Exception in getting date");
		}
		return date.toString();
	}
	
	public static java.util.Date getDateTime() {
		//java.util.Date dat = new Date();
		try {
			
			Calendar cal = new GregorianCalendar();
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int hrs =cal.get(Calendar.HOUR);
			int min = cal.get(Calendar.MINUTE);
			int sec = cal.get(Calendar.SECOND);
			//dat.append(day).append( "-").append(month + 1).append("-").append(year).append(" ")
			//.append(hrs).append(":").append(min);//.append(":").append(sec);
		} catch (Exception e) {
			System.out.println("Exception in getting date");
		}
		return new Date(System.currentTimeMillis());
	}
	
	public static Date  getSqlDateTime() {
		java.sql.Date dat = null; 
		try {
			
			Calendar cal = new GregorianCalendar();
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			//int hrs =cal.get(Calendar.HOUR);
			//int min = cal.get(Calendar.MINUTE);
			//int sec = cal.get(Calendar.SECOND);
			dat = new java.sql.Date(day, month, year);
				//Date.UTC(year, month, day, hrs, min, sec);	
		} catch (Exception e) {
			System.out.println("Exception in getting date");
		}
		return dat;
	}
	
	public static void main(String[] args) throws ParseException {
		Calendar cal = new GregorianCalendar();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hrs =cal.get(Calendar.HOUR);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		
	    java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.UTC(year, month, day, hrs, min, sec));
	    System.out.println("utilDate:" + utilDate);
	    System.out.println("sqlDate:" + sqlDate);
	    System.out.println("my date:" + getSqlDateTime());
	    
	    String datetimeString = null;
	    java.util.Date result;
	    SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yy hh:mm:ss");
	    result = formatter.parse (datetimeString);
	    System.out.println(result);
	  }

	public String getValueFromSession(HttpSession session, String keyName){
		try {
			/*Map valueMap = (Map) session.getAttribute("valueMap");
			 Iterator i = valueMap.keySet().iterator();
			 while ( i.hasNext() )
		      {
		        String key = (String) i.next();
		        if(key.equals(keyName)){
		        	String value = ((String[]) valueMap.get( key ))[ 0 ];
		        	return value;
		        }
		      }*/
			
			return (String) session.getAttribute(keyName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
