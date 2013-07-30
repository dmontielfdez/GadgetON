package com.dmontielfdez.gadgeton.util;


import android.annotation.SuppressLint;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * This class provides methods useful for use in the project
 * @author dmontielfdez
 *
 */
public class Utils {
	
	/**
	 * Copy a stream
	 * @param is Input Stream
	 * @param os Output Stream
	 */
	public static void CopyStream(InputStream is, OutputStream os){
		final int buffer_size=1024;
		try{
			byte[] bytes=new byte[buffer_size];
			for(;;){
				int count=is.read(bytes, 0, buffer_size);
				if(count==-1)
					break;
				os.write(bytes, 0, count);
			}
		}
		catch(Exception ex){}
	}
	
	/**
	 * Format a double number with n digits
	 * @param number Number to format
	 * @param digits Digits to use in the format
	 * @return The number formated
	 */
	@SuppressLint("NewApi")
	public static String numberFormat(Double number, int digits) {

		NumberFormat nF = NumberFormat.getInstance();
		nF.setMaximumFractionDigits(digits);
		nF.setRoundingMode(RoundingMode.DOWN);

		return nF.format(number);

	}

	/**
	 * Format a date
	 * Example: 1991-03-10 12:12:10 -> 10-03-1991
	 * @param dateIn Date to format
	 * @return Date formated
	 */
	public static String formatDate(String dateIn){
		String d ="";
		SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS", Locale.ENGLISH);
		Date date = null;
		try {
			date = formatIn.parse(dateIn);

			SimpleDateFormat formatOut = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
			// formateo
			d = formatOut.format(date);

		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return d;
	}
}