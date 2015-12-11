package vn.edu.fpt.fts.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

public final class Common {
	 public static final String IP_URL = "http://192.168.1.200:8080/FTS/api/";
public static final String Service_Account_CreateOwnerAccount = "Account/CreateOwnerAccount";
	public static final String Service_Deal_Accept = "Deal/accept";
	public static final String Service_Deal_Cancel = "Deal/cancel";
	public static final String Service_Deal_Create = "Deal/Create";
	public static final String Service_Deal_Decline = "Deal/decline";
	public static final String Service_Deal_GetByGoodsID = "Deal/getDealByGoodsID";
	public static final String Service_Deal_getDealByID = "Deal/getDealByID";
	public static final String Service_Deal_getDealByOwnerID = "Deal/getDealByOwnerID";
	//	public static final String IP_URL = "http://huybd-capstone.cloudapp.net/FTS/api/";
	public static final String Service_Goods_Create = "Goods/Create";
	public static final String Service_Goods_Delete = "Goods/Delete";
	public static final String Service_Goods_Get = "Goods/getListGoodsByOwnerID";
	public static final String Service_Goods_GetByID = "Route/getRouteByID";
	public static final String Service_Goods_getGoodsByID = "Goods/getGoodsByID";
	public static final String Service_Goods_Update = "Goods/Update";
	public static final String Service_GoodsCategory_Get = "GoodsCategory/get";
	public static final String Service_Login = "Account/OwnerLogin";
	public static final String Service_Notification_getNotificationByEmail = "Notification/getNotificationByEmail";
	public static final String Service_Order_get = "Order/getOrderByOwnerID";
	public static final String Service_Order_getOrderByID = "Order/getOrderByID";
	public static final String Service_Order_getOrderByOwnerID = "Order/getOrderByOwnerID";
	public static final String Service_Order_ownerCancelOrder = "Order/ownerCancelOrder";
	public static final String Service_Order_ownerNoticeLostGoods = "Order/ownerReportOrder";
	public static final String Service_Order_PayOrder = "Order/ownerPayOrder";
	public static final String Service_Owner_getName = "Owner/getOwnerByEmail";
	public static final String Service_Route_Get = "Route/get";
	public static final String Service_Route_GetByID = "Route/getRouteByID";
	public static final String Service_Suggest_Route = "Route/getSuggestionRoute";
	
	public static int calculateGoodsPrice(int weight, double distance) {
		int price = 22;
		if (distance <= 300) {
			if (weight <= 500) {
				price += (weight - 2) * 3.5;
			}
			if (weight <= 1000) {
				price += 448 * 3.5 + (weight - 500) * 3.3;
			}
			if (weight > 1000) {
				price += 448 * 3.5 + 500 * 3.3 + (weight - 1000) * 3;
			}

		} else {

			if (weight <= 500) {
				price += (weight - 2) * 4.2;
			}
			if (weight <= 1000) {
				price += 448 * 4.2 + (weight - 500) * 3.8;
			}
			if (weight > 1000) {
				price += 448 * 4.2 + 500 * 3.8 + (weight - 1000) * 3.5;
			}
		}
		return price;
	}
	
	public static boolean equalDate(String date) {
		Date todayDate = null;
		Date inputDate = null;
		Date thirdDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());		
		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, -1);		
//		String today = sdf.format(cal.getTime());
		try {
			todayDate = sdf.parse(today);
			inputDate = sdf.parse(date);
			cal.setTime(inputDate);
			cal.add(Calendar.DATE, 3);
			thirdDate = cal.getTime();
			if (todayDate.compareTo(inputDate) >= 0 && todayDate.compareTo(thirdDate) < 0) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

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

	public static String formatDate(Calendar calendar) {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		return sdf.format(calendar.getTime());
	}

	public static String formatDateFromString(String input) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		String result = sdf.format(date);
		return result;
	}

	public static String formatLocation(String input) {
		String keywords[] = { "Việt Nam", "vietnam", "Viet Nam", "Province",
				"City", "Vietnam", "District" };
		for (int i = 0; i < keywords.length; i++) {
			if (input.contains(keywords[i])) {
				input = input.replaceAll(", " + keywords[i], "");
				input = input.replaceAll(keywords[i], "");

			}
		}
		return input;
	}

	public static String formatNumber(int number) {
		DecimalFormat formatter = new DecimalFormat();
		DecimalFormatSymbols symbol = new DecimalFormatSymbols();
		symbol.setGroupingSeparator('.');
		formatter.setDecimalFormatSymbols(symbol);
		return formatter.format(number);
	}

	public static void logout(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("MyPrefs",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancelAll();
	}

	public static void updateLabel(EditText et, Calendar calendar) {
		String format = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		et.setText(sdf.format(calendar.getTime()));
	}
}
