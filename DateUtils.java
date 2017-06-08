package SWE645_Assignment4_varun;

/*
 * Name: Varun Rajavelu
 * This class converts the given date into a proper format
 * */

import java.util.Date;

public class DateUtils {

	public static String formatDay(Date date) {
		if (date == null) {
			return("");
		} 
		else {
			return(String.format("%tA, %tB%te, %tY", date, date, date, date));
		}
	}	
	
	private DateUtils() {} // Uninstantiatableclass
}
