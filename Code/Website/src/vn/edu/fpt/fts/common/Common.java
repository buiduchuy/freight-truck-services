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

	// Status of Goods, Route, Deal, Order
	public static final int deactivate = 0;
	public static final int activate = 1;

	// Status of Deal
	public static final int deal_waitForDriver = 1;
	public static final int deal_waitForOwner = 2;
	public static final int deal_accept = 3;
	public static final int deal_decline = 4;
	public static final int deal_cancel = 0;

	// Status of Order
	public static final int order_delivered = 1;
	public static final int order_notdelivered = 2;

	public String changeFormatDate(String dateInput, String oldFormat,
			String newFormat) {
		try {
			SimpleDateFormat sdfSource = new SimpleDateFormat(oldFormat);
			Date date = sdfSource.parse(dateInput);
			SimpleDateFormat sdfDestination = new SimpleDateFormat(newFormat);
			dateInput = sdfDestination.format(date);
			return dateInput.toString();

		} catch (ParseException pe) {
			return dateInput;
		}
	}
}
