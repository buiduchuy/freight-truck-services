/**
 * 
 */
package vn.edu.fpt.fts.process;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.NotificationDAO;
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

	public int insertAcceptOrderNotification(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(order.getCreateTime());
		notification.setEmail(order.getDeal().getGoods().getOwner().getEmail());
		notification.setMessage("Hệ thống đã đổi trạng thái đơn hàng #OD"
				+ order.getOrderID() + " chấp nhận!");
		notification.setStatusOfType(Common.order_unpaid);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertOwnerCancelOrderWhenPaid(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(order.getCreateTime());
		notification.setEmail(order.getDeal().getGoods().getOwner().getEmail());
		notification.setMessage("Đơn hàng #OD" + order.getOrderID()
				+ " đã hủy. Bạn phải chịu một khoản tiền phạt nhất định.");
		notification.setStatusOfType(Common.order_cancelled);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertOwnerCancelOrderWhenUnpaid(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(order.getCreateTime());
		notification.setEmail(order.getDeal().getGoods().getOwner().getEmail());
		notification.setMessage("Đơn hàng #OD" + order.getOrderID()
				+ " đã hủy!");
		notification.setStatusOfType(Common.order_cancelled);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}
	
	public int insertSystemCancelOrderTimeout(Order order) {
		int ret = 0;

		Notification notification = new Notification();

		notification.setActive(Common.activate);
		notification.setCreateTime(order.getCreateTime());
		notification.setEmail(order.getDeal().getGoods().getOwner().getEmail());
		notification.setMessage("Hệ thống đã tự động hủy đơn hàng #OD" + order.getOrderID()
				+ ".");
		notification.setStatusOfType(Common.order_cancelled);
		notification.setIdOfType(order.getOrderID());
		notification.setType("order");

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

}
