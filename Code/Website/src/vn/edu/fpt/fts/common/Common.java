/**
 * 
 */
package vn.edu.fpt.fts.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

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

//	 public static final String CONNECTION =
//	 "jdbc:sqlserver://huybd-capstone.cloudapp.net:1433;databaseName=FTS";
//	 public static final String usernamedb = "duchuy";
//	 public static final String passworddb = "huy2108.";

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
	public static final int order_delivering = 2;
	public static final int order_accept = 3;
	public static final int order_report = 3;
	public static final int order_deactive = 3;
	
	// Status of Payment
	public static final int payment_deposit = 1;
	public static final int payment_full = 2;
	public static final int payment_driver = 3;
	public static final int payment_refund = 4;
	

	// Max allow distance for matching goods and routes (km)
	public static final int maxAllowDistance = 50;

	// Period day for delivery (day)
	public static final int periodDay = 1;

	// Counter when send deal or counter
	public static final int maxCounterTime = 10;

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

	// Weight kg, distance km
	public static int calculateGoodsPrice(int weight, double distance) {
		int price = 22;
		if (distance <= 300) {
			if (weight <= 500) {
				price += (weight - 2) * 3.5;
			}
			if (weight <= 1000 && weight > 500) {
				price += 448 * 3.5 + (weight - 500) * 3.3;
			}
			if (weight > 1000) {
				price += 448 * 3.5 + 500 * 3.3 + (weight - 1000) * 3;
			}

		} else {

			if (weight <= 500) {
				price += (weight - 2) * 4.2;
			}
			if (weight <= 1000 && weight > 500) {
				price += 448 * 4.2 + (weight - 500) * 3.8;
			}
			if (weight > 1000) {
				price += 448 * 4.2 + 500 * 3.8 + (weight - 1000) * 3.5;
			}
		}
		return price;
	}

	public String getPropValues() throws IOException {

		String result = "";
		Properties prop = new Properties();

		prop.load(new FileInputStream("config.properties"));

		Date time = new Date(System.currentTimeMillis());

		// get the property value and print it out
		String user = prop.getProperty("user");
		String company1 = prop.getProperty("company1");
		String company2 = prop.getProperty("company2");
		String company3 = prop.getProperty("company3");

		result = "Company List = " + company1 + ", " + company2 + ", "
				+ company3;
		System.out.println(result + "\nProgram Ran on " + time + " by user="
				+ user);
		return result;
	}

	public static final String getCreateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
