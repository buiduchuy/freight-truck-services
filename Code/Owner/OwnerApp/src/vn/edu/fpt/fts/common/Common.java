package vn.edu.fpt.fts.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.widget.EditText;

public final class Common {
//	public static final String IP_URL = "http://192.168.43.10:8080/FTS/api/";
	public static final String IP_URL = "http://huybd-capstone.cloudapp.net/FTS/api/";
	public static final String Service_Goods_Create = "Goods/Create";
	public static final String Service_Login = "Account/OwnerLogin";
	public static final String Service_GoodsCategory_Get = "GoodsCategory/get";
	public static final String Service_Route_Get = "Route/get";
	public static final String Service_Goods_Get = "Goods/getListGoodsByOwnerID";
	public static final String Service_Route_GetByID = "Route/getRouteByID";
	public static final String Service_Deal_Create = "Deal/Create";
	public static final String Service_Deal_GetByGoodsID = "Deal/getDealByGoodsID";
	public static final String Service_Goods_GetByID = "Route/getRouteByID";
	public static final String Service_Deal_Accept = "Deal/accept";
	public static final String Service_Deal_Decline = "Deal/decline";
	public static final String Service_Deal_Cancel = "Deal/cancel";
	public static final String Service_Suggest_Route = "Route/getSuggestionRoute";
	public static final String Service_Order_get = "Order/getOrderByOwnerID";
	public static final String Service_Order_getOrderByID = "Order/getOrderByID";
	public static final String Service_Order_ConfirmDelivery = "Order/ownerConfirmDelivery";
	public static final String Service_Goods_getGoodsByID = "Goods/getGoodsByID";
	public static final String Service_Goods_Update = "Goods/Update";
	public static final String Service_Notification_getNotificationByEmail = "Notification/getNotificationByEmail";
	public static final String Service_Deal_getDealByID = "Deal/getDealByID";
	public static final String Service_Deal_getDealByOwnerID = "Deal/getDealByOwnerID";
	public static final String Service_Order_getOrderByOwnerID = "Order/getOrderByOwnerID";
	public static final String Service_Order_ownerNoticeLostGoods = "Order/ownerNoticeLostGoods";
	
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
	
	public static void updateLabel(EditText et, Calendar calendar) {
		String format = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		et.setText(sdf.format(calendar.getTime()));
	}

	public static String formatDate(Calendar calendar) {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		return sdf.format(calendar.getTime());
	}
	
	public static String formatNumber(int number) {
		DecimalFormat formatter = new DecimalFormat();
		DecimalFormatSymbols symbol = new DecimalFormatSymbols();
		symbol.setGroupingSeparator('.');
		formatter.setDecimalFormatSymbols(symbol);
		return formatter.format(number);
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
}
