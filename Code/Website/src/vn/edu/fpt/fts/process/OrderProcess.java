/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.pojo.Order;

/**
 * @author Huy
 *
 */
public class OrderProcess {
	private final static String TAG = "OrderProcess";

	OrderDAO orderDao = new OrderDAO();

	@SuppressWarnings("deprecation")
	public void checkDelivery() {
		List<Order> l_order = orderDao.getAllOrder();
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = new Date();
			System.out.println("CHECK DELIVERY AFTER: " + Common.periodDay
					+ " DAY(s)");
			for (int i = 0; i < l_order.size(); i++) {
				Date deliveryDate = dateFormat.parse(l_order.get(i).getDeal()
						.getGoods().getDeliveryTime());
				if ((today.getDate() - Common.periodDay) == deliveryDate
						.getDate()) {
					int orderID = l_order.get(i).getOrderID();
					orderDao.updateOrderStatusID(orderID, Common.order_staff);
					System.out.println("Auto accept orderID: " + orderID);
				}
			}
		} catch (ParseException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
	}
}
