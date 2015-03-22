/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.List;

import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.pojo.Order;

/**
 * @author Huy
 *
 */
public class OrderProcess {
	// private final static String TAG = "OrderProcess";

	OrderDAO orderDao = new OrderDAO();

	public boolean checkDelivery() {

		List<Order> l_order = orderDao.getAllOrder();
		// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Date date = new Date();

		for (int i = 0; i < l_order.size(); i++) {
			System.out.println(l_order.get(i).getDeal().getGoods()
					.getDeliveryTime());
		}
		// Order order = orderDao.getOrderByID(orderID);
		// if (order != null) {
		// if (order.isStaffDeliveryStatus() ||
		// order.isOwnerDeliveryStatus()) {
		// return true;
		// }
		// }

		return false;
	}

}
