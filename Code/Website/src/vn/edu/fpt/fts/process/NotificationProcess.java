/**
 * 
 */
package vn.edu.fpt.fts.process;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.NotificationDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Notification;
import vn.edu.fpt.fts.pojo.Order;
import vn.edu.fpt.fts.pojo.Route;

/**
 * @author Huy
 *
 */
public class NotificationProcess {

	NotificationDAO notificationDao = new NotificationDAO();
	DealDAO dealDao = new DealDAO();

	public int insertDealSendNotification(Deal deal) {
		int ret = 0;

		Notification notification = new Notification();

		Deal db_deal = dealDao.getLastDealByGoodsAndRouteID(deal.getRouteID(),
				deal.getGoodsID());

		String msg = "";
		String email = "";
		if (deal.getCreateBy().equalsIgnoreCase("owner")) {
			email = db_deal.getGoods().getOwner().getEmail();
			msg = "Chủ hàng " + email + " đã gửi đề nghị với giá tiền "
					+ Common.formatNumber(db_deal.getPrice()) + " nghìn đồng.";
			notification.setEmail(db_deal.getRoute().getDriver().getEmail());
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			email = db_deal.getRoute().getDriver().getEmail();
			msg = "Tài xế " + email + " đã gửi đề nghị với giá tiền "
					+ Common.formatNumber(db_deal.getPrice()) + " nghìn đồng.";
			notification.setEmail(db_deal.getGoods().getOwner().getEmail());
		}
		notification.setMessage(msg);
		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification.setType("deal");
		notification.setIdOfType(deal.getDealID());
		notification.setStatusOfType(deal.getDealStatusID());

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertDealAcceptNotification(Deal deal, int orderID) {
		int ret = 0;

		Notification notification = new Notification();

		Deal db_deal = dealDao.getLastDealByGoodsAndRouteID(deal.getRouteID(),
				deal.getGoodsID());

		String msg = "";
		String email = "";
		if (deal.getCreateBy().equalsIgnoreCase("owner")) {
			email = db_deal.getGoods().getOwner().getEmail();
			msg = "Chủ hàng " + email + " đã đồng ý đề nghị với giá tiền "
					+ Common.formatNumber(db_deal.getPrice()) + " nghìn đồng.";
			notification.setEmail(db_deal.getRoute().getDriver().getEmail());
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			email = db_deal.getRoute().getDriver().getEmail();
			msg = "Tài xế " + email + " đã đồng ý đề nghị với giá tiền "
					+ Common.formatNumber(db_deal.getPrice()) + " nghìn đồng.";
			notification.setEmail(db_deal.getGoods().getOwner().getEmail());
		}
		notification.setMessage(msg);
		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification.setType("order");
		notification.setIdOfType(orderID);
		notification.setStatusOfType(deal.getDealStatusID());

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertDealDeclineNotification(Deal deal) {
		int ret = 0;

		Notification notification = new Notification();

		Deal db_deal = dealDao.getLastDealByGoodsAndRouteID(deal.getRouteID(),
				deal.getGoodsID());

		String msg = "";
		String email = "";
		if (deal.getCreateBy().equalsIgnoreCase("owner")) {
			email = db_deal.getGoods().getOwner().getEmail();
			msg = "Chủ hàng " + email + " đã từ chối đề nghị với giá tiền "
					+ Common.formatNumber(db_deal.getPrice()) + " nghìn đồng.";
			notification.setEmail(db_deal.getRoute().getDriver().getEmail());
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			email = db_deal.getRoute().getDriver().getEmail();
			msg = "Tài xế " + email + " đã từ chối đề nghị với giá tiền "
					+ Common.formatNumber(db_deal.getPrice()) + " nghìn đồng.";
			notification.setEmail(db_deal.getGoods().getOwner().getEmail());
		}
		notification.setMessage(msg);
		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification.setType("deal");
		notification.setIdOfType(deal.getDealID());
		notification.setStatusOfType(deal.getDealStatusID());

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertDealCancelNotification(Deal deal) {
		int ret = 0;

		Notification notification = new Notification();

		Deal db_deal = dealDao.getLastDealByGoodsAndRouteID(deal.getRouteID(),
				deal.getGoodsID());

		String msg = "";
		String email = "";
		if (deal.getCreateBy().equalsIgnoreCase("owner")) {
			email = db_deal.getGoods().getOwner().getEmail();
			msg = "Chủ hàng " + email + " đã hủy thương lượng với giá tiền "
					+ Common.formatNumber(db_deal.getPrice()) + " nghìn đồng.";
			notification.setEmail(db_deal.getRoute().getDriver().getEmail());
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			email = db_deal.getRoute().getDriver().getEmail();
			msg = "Tài xế " + email + " đã hủy thương lượng với giá tiền "
					+ Common.formatNumber(db_deal.getPrice()) + " nghìn đồng.";
			notification.setEmail(db_deal.getGoods().getOwner().getEmail());
		}
		notification.setMessage(msg);
		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification.setType("deal");
		notification.setIdOfType(deal.getDealID());
		notification.setStatusOfType(deal.getDealStatusID());

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertOwnerPayOrder(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification
				.setEmail(order.getDeal().getRoute().getDriver().getEmail());
		notification
				.setMessage("Chủ hàng đã thanh toán cho hệ thống.<br/>Tài xế có thể tiến hành vận chuyển hàng.");
		notification.setStatusOfType(Common.order_paid);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertOwnerCancelOrder(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification
				.setEmail(order.getDeal().getRoute().getDriver().getEmail());
		notification.setMessage("Chủ hàng "
				+ order.getDeal().getGoods().getOwner().getFirstName()
				+ " đã hủy đơn hàng vận chuyển #OD" + order.getOrderID());
		notification.setStatusOfType(Common.order_cancelled);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertOwnerReportOrder(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification.setEmail("staff@fts.vn");
		notification.setMessage("Chủ hàng "
				+ order.getDeal().getGoods().getOwner().getEmail()
				+ " báo mất hàng. Mã hóa đơn: " + order.getOrderID() + ".");
		notification.setStatusOfType(Common.order_delivered);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertStaffPayOrderForDriver(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification
				.setEmail(order.getDeal().getRoute().getDriver().getEmail());
		notification.setMessage("Hệ thống đã chuyển tiền " + Common.formatNumber(order.getPrice())
				+ " VNĐ cho quý khách.");
		notification.setStatusOfType(Common.order_finish);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertStaffRefundForOwner(Order order, double refundPrice) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification.setEmail(order.getDeal().getGoods().getOwner().getEmail());
		notification.setMessage("Hệ thống đã hoàn trả tiền " + Common.formatNumber(refundPrice)
				+ " VNĐ cho quý khách.");
		notification.setStatusOfType(Common.order_finish);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertSystemCancelOrderTimeout(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification.setEmail(order.getDeal().getGoods().getOwner().getEmail());
		notification.setMessage("Hệ thống đã tự động hủy đơn hàng #OD"
				+ order.getOrderID() + ".");
		notification.setStatusOfType(Common.order_cancelled);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertSystemChangeDeliveringOrder(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification.setEmail(order.getDeal().getGoods().getOwner().getEmail());
		notification
				.setMessage("Hệ thống đã đổi trạng thái đơn hàng #OD"
						+ order.getOrderID()
						+ " là đang vận chuyển.<br/>Xin liên hệ nhân viên nếu chưa có tài xế nhận hàng.");
		notification.setStatusOfType(Common.order_delivering);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertSystemChangeDeliveredOrder(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification.setEmail(order.getDeal().getGoods().getOwner().getEmail());
		notification
				.setMessage("Hệ thống đã đổi trạng thái đơn hàng #OD"
						+ order.getOrderID()
						+ " là đã giao hàng.<br/>Xin liên hệ nhân viên nếu tài xế chưa giao hàng.");
		notification.setStatusOfType(Common.order_delivered);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}
	
	public int insertSystemCancelDeal(Deal deal) {
		int ret = 0;

		Notification notification = new Notification();

		String msg = "";
		String email = "";
		if (deal.getCreateBy().equalsIgnoreCase("owner")) {
			email = deal.getGoods().getOwner().getEmail();
			msg = "Hệ thống đã hủy thương lượng.<br/>Lộ trình: "
					+ deal.getGoods().getPickupAddress() + " - "
					+ deal.getGoods().getDeliveryAddress() + ".";

			// msg = "Chủ hàng " + email + " đã hủy thương lượng với giá tiền "
			// + Common.formatNumber(deal.getPrice()) + " nghìn đồng.";
			notification.setEmail(email);
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			email = deal.getRoute().getDriver().getEmail();
			msg = "Hệ thống đã hủy thương lượng.<br/>Lộ trình: "
					+ deal.getRoute().getStartingAddress() + " - "
					+ deal.getRoute().getDestinationAddress() + ".";
			notification.setEmail(email);
		}
		notification.setMessage(msg);
		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification.setType("deal");
		notification.setIdOfType(deal.getDealID());
		notification.setStatusOfType(deal.getDealStatusID());

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertSystemDeactiveGoods(Goods goods) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification.setEmail(goods.getOwner().getEmail());
		String pickupDate = Common.changeFormatDate(goods.getPickupTime(),
				"yyyy-MM-dd HH:mm:ss.SSS", "dd/MM/yyyy");
		String deliveryDate = Common.changeFormatDate(goods.getDeliveryTime(),
				"yyyy-MM-dd HH:mm:ss.SSS", "dd/MM/yyyy");
		notification
				.setMessage("Hệ thống đã tự động xóa hàng. Thời gian: "
						+ pickupDate + " đến " + deliveryDate
						+ ".<br/>Lộ trình: " + goods.getPickupAddress() + " - "
						+ goods.getDeliveryAddress());
		notification.setStatusOfType(Common.deactivate);
		notification.setIdOfType(goods.getGoodsID());
		notification.setType("goods");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertSystemDeactiveRoute(Route route) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(Common.getCreateTime());
		notification.setEmail(route.getDriver().getEmail());
		String startDate = Common.changeFormatDate(route.getStartTime(),
				"yyyy-MM-dd HH:mm:ss.SSS", "dd/MM/yyyy");
		String finishDate = Common.changeFormatDate(route.getFinishTime(),
				"yyyy-MM-dd HH:mm:ss.SSS", "dd/MM/yyyy");
		notification
				.setMessage("Hệ thống đã tự động xóa tuyến đường vì quá hạn. Thời gian: "
						+ startDate
						+ " đến "
						+ finishDate
						+ ".<br/>Lộ trình: "
						+ route.getStartingAddress()
						+ " - "
						+ route.getDestinationAddress());
		notification.setStatusOfType(Common.deactivate);
		notification.setIdOfType(route.getRouteID());
		notification.setType("route");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

}
