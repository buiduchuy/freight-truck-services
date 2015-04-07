/**
 * 
 */
package vn.edu.fpt.fts.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

	// public static final String CONNECTION =
	// "jdbc:sqlserver://fts2015.cloudapp.net:1433;databaseName=FTS";
	// public static final String usernamedb = "duchuy";
	// public static final String passworddb = "huy2108.";

	// public static final String CONNECTION =
	// "jdbc:sqlserver://huybd-capstone.cloudapp.net:1433;databaseName=FTS";
	// public static final String usernamedb = "duchuy";
	// public static final String passworddb = "huy2108.";

	// Role of Account
	public static final int role_owner = 1;
	public static final int role_driver = 2;
	public static final int role_staff = 3;
	public static final int role_admin = 4;

	// Status of Goods, Route, Deal, Order
	public static final int deactivate = 0;
	public static final int activate = 1;

	// Status of Deal
	public static final int deal_pending = 1;
	public static final int deal_accept = 2;
	public static final int deal_decline = 3;
	public static final int deal_cancel = 4;

	// Status of Order
	public static final int order_pending = 1;
	public static final int order_driver = 2;
	public static final int order_owner = 3;
	public static final int order_staff = 4;
	public static final int order_lost = 5;

	// Max allow distance for matching goods and routes (km)
	public static final int maxAllowDistance = 50;

	// Period day for delivery (day)
	public static final int periodDay = 3;

	// Fee for create goods
	public static final int priceCreateGood = 0;

	public static final String API_KEY = "AIzaSyD_etqEdI3WY_xfwnnJNuzT8uLalBofaT0";

	public static String changeFormatDate(String dateInput, String oldFormat,
			String newFormat) {
		try {
			SimpleDateFormat sdfSource = new SimpleDateFormat(oldFormat);
			Date date = sdfSource.parse(dateInput);
			SimpleDateFormat sdfDestination = new SimpleDateFormat(newFormat);
			dateInput = sdfDestination.format(date);
			return dateInput.toString();

		} catch (ParseException pe) {
			pe.printStackTrace();
			return dateInput;
		}
	}

	public static String formatNumber(double number) {
		DecimalFormat formatter = new DecimalFormat();
		DecimalFormatSymbols symbol = new DecimalFormatSymbols();
		symbol.setGroupingSeparator('.');
		formatter.setDecimalFormatSymbols(symbol);
		return formatter.format(number);
	}

}
