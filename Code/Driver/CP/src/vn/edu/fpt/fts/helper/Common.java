/**
 * 
 */
package vn.edu.fpt.fts.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Huy
 * 
 */
public final class Common {

	public String changeFormatDate(String dateInput) {
		try {
			SimpleDateFormat sdfSource = new SimpleDateFormat("dd-MM-yyyy");
			Date date = sdfSource.parse(dateInput);
			SimpleDateFormat sdfDestination = new SimpleDateFormat("MM-dd-yyyy");
			dateInput = sdfDestination.format(date);
			return dateInput.toString();

		} catch (ParseException pe) {
			return dateInput;
		}
	}

	public static String formatDate(String dateInput) {
		try {
			SimpleDateFormat sdfSource = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			Date date = sdfSource.parse(dateInput);
			SimpleDateFormat sdfDestination = new SimpleDateFormat("dd-MM-yyyy");
			dateInput = sdfDestination.format(date);
			return dateInput.toString();

		} catch (ParseException pe) {
			return dateInput;
		}
	}

	public static String formatLocation(String input) {
		String keywords[] = { "Việt Nam", "vietnam", "Viet Nam", "Province",
				"City", "Vietnam", "Unnamed Road" };
		for (int i = 0; i < keywords.length; i++) {
			if (input.contains(keywords[i])) {
				input = input.replaceAll(", " + keywords[i], "");
				input = input.replaceAll(keywords[i] + ", ", "");
				input = input.replaceAll(keywords[i], "");
			}
		}
		return input;
	}

	public static boolean expireDate(String date) {
		Date todayDate = null;
		Date inputDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());
		try {
			todayDate = sdf.parse(today);
			inputDate = sdf.parse(date);
			if (todayDate.compareTo(inputDate) <= 0) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
}
