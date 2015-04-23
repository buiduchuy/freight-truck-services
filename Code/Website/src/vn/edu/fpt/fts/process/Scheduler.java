/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Order;
import vn.edu.fpt.fts.pojo.Route;

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

	// private final static String TAG = "Scheduler";
	GoodsDAO goodsDao = new GoodsDAO();
	RouteDAO routeDao = new RouteDAO();
	OrderDAO orderDao = new OrderDAO();
	DealDAO dealDao = new DealDAO();
	NotificationProcess notificationProcess = new NotificationProcess();
	
	Logger logger = Logger.getLogger("CHECK ORDER STATUS AFTER "
			+ Common.periodDay + " DAY(s)" + " --- TIME: "
			+ new Date().toString());

	public void orderScheduler() {
		List<Order> listOrder = orderDao.getAllOrder();
		Date currentDate = Common.convertStringToDate(Common.getCreateTime(),
				"yyyy/MM/dd HH:mm:ss");

		// System.out.println("---- Order Scheduler " + currentDate + "----");

		for (int i = 0; i < listOrder.size(); i++) {
			Order orderItem = listOrder.get(i);
			Date orderDate = Common.convertStringToDate(
					orderItem.getCreateTime(), "yyyy-MM-dd HH:mm:ss.SSS");

			// Check timeout paid of owner
			long dateDifferencePaid = Common.getTimeDifference(currentDate,
					orderDate, "day");
			if (dateDifferencePaid > 1) {
				if (orderItem.getOrderStatusID() == Common.order_unpaid) {
					orderDao.updateOrderStatusID(orderItem.getOrderID(),
							Common.order_cancelled);
					notificationProcess
							.insertSystemCancelOrderTimeout(orderItem);
				}
			}

			// Check delivering
			Date goodsPickupDate = Common.convertStringToDate(orderItem
					.getDeal().getGoods().getPickupTime(),
					"yyyy-MM-dd HH:mm:ss.SSS");
			long dateDifferenceDelivering = Common.getTimeDifference(
					currentDate, goodsPickupDate, "day");
			if (dateDifferenceDelivering >= 1) {
				if (orderItem.getOrderStatusID() == Common.order_paid) {
					// Change order status and create notification
					orderDao.updateOrderStatusID(orderItem.getOrderID(),
							Common.order_delivering);
					notificationProcess
							.insertSystemChangeDeliveringOrder(orderItem);
				}
			}

			// Check delivered
			Date goodsDeliveryDate = Common.convertStringToDate(orderItem
					.getDeal().getGoods().getDeliveryTime(),
					"yyyy-MM-dd HH:mm:ss.SSS");
			long dateDifferenceDelivered = Common.getTimeDifference(
					currentDate, goodsDeliveryDate, "day");
			if (dateDifferenceDelivered >= 1) {
				if (orderItem.getOrderStatusID() == Common.order_delivering) {
					// Change order status and create notification
					orderDao.updateOrderStatusID(orderItem.getOrderID(),
							Common.order_delivered);
					notificationProcess
							.insertSystemChangeDeliveredOrder(orderItem);
				}
			}
		}
	}

	public void dealScheduler() {
		List<Deal> listDeal = dealDao.getAllDeal();
		Date currentDate = Common.convertStringToDate(Common.getCreateTime(),
				"yyyy/MM/dd HH:mm:ss");

		for (int i = 0; i < listDeal.size(); i++) {
			Deal dealItem = listDeal.get(i);
			Date dealDate = Common.convertStringToDate(
					dealItem.getCreateTime(), "yyyy-MM-dd HH:mm:ss.SSS");
			Date goodsDeliveryDate = Common.convertStringToDate(dealItem.getGoods().getDeliveryTime(), "yyyy-MM-dd HH:mm:ss.SSS");
			
			long differenceDate = Common.getTimeDifference(currentDate,
					dealDate, "day");

			if (differenceDate >= 1) {
				if (dealItem.getDealStatusID() == Common.deal_pending) {
					dealDao.updateDealStatus(dealItem.getDealID(),
							Common.deal_cancel);
					notificationProcess.insertSystemCancelDeal(dealItem);
				}
			}
		}
	}
	
	public void itemsScheduler() {
		Date currentDate = Common.convertStringToDate(Common.getCreateTime(),
				"yyyy/MM/dd HH:mm:ss");
		
		// Deactivate Goods
		List<Goods> listGoods = goodsDao.getListActiveGoods();
		for (int i = 0; i < listGoods.size(); i++) {
			Goods goodsItem = listGoods.get(i);
			Date goodsDeliveryDate = Common.convertStringToDate(goodsItem.getDeliveryTime(),
					"yyyy-MM-dd HH:mm:ss.SSS");
			
			long differenceDateGoods = Common.getTimeDifference(currentDate,
					goodsDeliveryDate, "day");
			if (differenceDateGoods >= 1) {
				goodsDao.updateGoodsStatus(goodsItem.getGoodsID(), Common.deactivate);
				notificationProcess.insertSystemDeactiveGoods(goodsItem);
			}
		}
		
		// Deactivate Route
		List<Route> listRoute = routeDao.getListActiveRoute();
		
		for (int i = 0; i < listRoute.size(); i++) {
			Route routeItem = listRoute.get(i);
			Date routeFinishDate = Common.convertStringToDate(routeItem.getFinishTime(),
					"yyyy-MM-dd HH:mm:ss.SSS");
			
			long differenceDateRoute = Common.getTimeDifference(currentDate,
					routeFinishDate, "day");
			
			if (differenceDateRoute >= 1) {
				routeDao.updateRouteStatus(routeItem.getRouteID(), Common.deactivate);
				notificationProcess.insertSystemDeactiveRoute(routeItem);
			}
		}
	}

}
