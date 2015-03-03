/**
 * 
 */
package vn.edu.fpt.fts.process;

import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.pojo.Order;

/**
 * @author Huy
 *
 */
public class OrderProcess {
//	private final static String TAG = "OrderProcess";

	OrderDAO orderDao = new OrderDAO();

	public boolean checkDelivery(int orderID) {
		Order order = orderDao.getOrderByID(orderID);
		if (order != null) {
			if (order.isStaffDeliveryStatus() || order.isOwnerDeliveryStatus()) {
				return true;
			}
		}
		return false;
	}
}
