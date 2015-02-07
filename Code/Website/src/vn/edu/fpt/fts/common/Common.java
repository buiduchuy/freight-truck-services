/**
 * 
 */
package vn.edu.fpt.fts.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Huy
 *
 */
public final class Common {

	public static final String CLASSSQLSERVERDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String CONNECTION = "jdbc:sqlserver://localhost:1433;databaseName=FTS";
	public static final String usernamedb = "sa";
	public static final String passworddb = "123456";
	
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
