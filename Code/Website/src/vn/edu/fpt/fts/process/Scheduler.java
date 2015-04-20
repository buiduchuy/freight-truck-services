/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.Date;
import java.util.List;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.pojo.Order;

/**
 * @author Duc Huy
 *
 */
public class Scheduler {

	public static void main(String[] args) {
		Date currentDate = Common.convertStringToDate(
				"2015/04/22 00:00:00.000", "yyyy/MM/dd HH:mm:ss");

		Date checkDate = Common.convertStringToDate("2015-04-21 00:00:00.000",
				"yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(currentDate);
		System.out.println(checkDate);

		long dateDifference = Common.getTimeDifference(currentDate, checkDate,
				"day");

		System.out.println("Difference: " + dateDifference);
	}

	private final static String TAG = "Scheduler";
	OrderDAO orderDao = new OrderDAO();
	DealDAO dealDao = new DealDAO();
	NotificationProcess notificationProcess = new NotificationProcess();

	public void orderScheduler() {
		List<Order> listOrder = orderDao.getAllOrder();
		Date currentDate = Common.convertStringToDate(Common.getCreateTime(),
				"yyyy/MM/dd HH:mm:ss");

		System.out.println("---- Order Scheduler " + currentDate + "----");

		for (int i = 0; i < listOrder.size(); i++) {
			Order orderItem = listOrder.get(i);
			Date orderDate = Common.convertStringToDate(
					orderItem.getCreateTime(), "yyyy-MM-dd HH:mm:ss.SSS");

			// Check timeout paid of owner
			long dateDifferencePaid = Common.getTimeDifference(currentDate,
					orderDate, "day");
			if (dateDifferencePaid > 1) {
				orderDao.updateOrderStatusID(orderItem.getOrderID(),
						Common.order_cancelled);
				notificationProcess.insertSystemCancelOrderTimeout(orderItem);
			}

			// Check delivering
			Date goodsPickupDate = Common.convertStringToDate(orderItem
					.getDeal().getGoods().getPickupTime(),
					"yyyy-MM-dd HH:mm:ss.SSS");
			long dateDifferenceDelivering = Common.getTimeDifference(
					goodsPickupDate, orderDate, "day");
			if (dateDifferenceDelivering == 0) {
				if (orderItem.getOrderStatusID() == Common.order_paid) {
					// Change order status and create notification
					orderDao.updateOrderStatusID(orderItem.getOrderID(),
							Common.order_delivering);
					notificationProcess.insertSystemChangeDeliveringOrder(orderItem);
				}
			}

			// Check delivered
			Date goodsDeliveryDate = Common.convertStringToDate(orderItem
					.getDeal().getGoods().getDeliveryTime(),
					"yyyy-MM-dd HH:mm:ss.SSS");
			long dateDifferenceDelivered = Common.getTimeDifference(
					goodsDeliveryDate, orderDate, "day");
			if (dateDifferenceDelivered == 1) {
				if (orderItem.getOrderStatusID() == Common.order_delivering) {
					// Change order status and create notification
					orderDao.updateOrderStatusID(orderItem.getOrderID(),
							Common.order_delivered);
					notificationProcess.insertSystemChangeDeliveredOrder(orderItem);
				}
			}
		}
	}

	public void dealScheduler() {

	}

}
