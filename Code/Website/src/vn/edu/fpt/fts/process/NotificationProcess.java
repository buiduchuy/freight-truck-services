/**
 * 
 */
package vn.edu.fpt.fts.process;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.NotificationDAO;
import vn.edu.fpt.fts.pojo.Account;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.Notification;
import vn.edu.fpt.fts.pojo.Order;

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
		notification.setCreateTime(deal.getCreateTime());
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
		notification.setCreateTime(deal.getCreateTime());
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
		notification.setCreateTime(deal.getCreateTime());
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
		notification.setCreateTime(deal.getCreateTime());
		notification.setType("deal");
		notification.setIdOfType(deal.getDealID());
		notification.setStatusOfType(deal.getDealStatusID());

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertOwnerConfirmOrderNotification(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(order.getCreateTime());
		notification
				.setEmail(order.getDeal().getRoute().getDriver().getEmail());
		notification.setMessage("Chủ hàng "
				+ order.getDeal().getGoods().getOwner().getEmail()
				+ " đã xác nhận đơn giao hàng thành công.");
		notification.setStatusOfType(Common.order_owner);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertDriverConfirmOrderNotification(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(order.getCreateTime());
		notification.setEmail(order.getDeal().getGoods().getOwner().getEmail());
		notification.setMessage("Tài xế "
				+ order.getDeal().getRoute().getDriver().getEmail()
				+ " đã xác nhận đơn giao hàng thành công.");
		notification.setStatusOfType(Common.order_driver);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");
		
		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertStaffConfirmOrderNotification(Order order, Account acc) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(order.getCreateTime());

		notification.setEmail(order.getDeal().getGoods().getOwner().getEmail());
		notification.setMessage("Nhân viên " + acc.getEmail()
				+ " đã xác nhận đơn giao hàng thành công.");
		notification.setStatusOfType(Common.order_staff);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");
		
		ret = notificationDao.insertNotification(notification);

		notification
				.setEmail(order.getDeal().getRoute().getDriver().getEmail());

		ret = notificationDao.insertNotification(notification);

		return ret;
	}
	
}
