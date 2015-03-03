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
	
	public String changeFormatDate(String dateInput){
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
}
