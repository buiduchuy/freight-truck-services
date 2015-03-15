/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.NotificationDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.Notification;

/**
 * @author Huy
 *
 */
public class NotificationProcess {

	NotificationDAO notificationDao = new NotificationDAO();
	DealDAO dealDao = new DealDAO();
	NumberFormat nf = new DecimalFormat("#.####");

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
					+ nf.format(db_deal.getPrice()) + " nghìn đồng.";
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			email = db_deal.getRoute().getDriver().getEmail();
			msg = "Tài xế " + email + " đã gửi đề nghị với giá tiền "
					+ nf.format(db_deal.getPrice()) + " nghìn đồng.";
		}
		notification.setMessage(msg);
		notification.setActive(Common.activate);
		notification.setCreateTime(deal.getCreateTime());
		notification.setType("deal");
		notification.setEmail(email);

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

	public int insertDealAcceptNotification(Deal deal) {
		int ret = 0;

		Notification notification = new Notification();

		Deal db_deal = dealDao.getLastDealByGoodsAndRouteID(deal.getRouteID(),
				deal.getGoodsID());

		String msg = "";
		String email = "";
		if (deal.getCreateBy().equalsIgnoreCase("owner")) {
			email = db_deal.getGoods().getOwner().getEmail();
			msg = "Chủ hàng " + email + " đã đồng ý đề nghị với giá tiền "
					+ nf.format(db_deal.getPrice()) + " nghìn đồng.";
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			email = db_deal.getRoute().getDriver().getEmail();
			msg = "Tài xế " + email + " đã đồng ý đề nghị với giá tiền "
					+ nf.format(db_deal.getPrice()) + " nghìn đồng.";
		}
		notification.setMessage(msg);
		notification.setActive(Common.activate);
		notification.setCreateTime(deal.getCreateTime());
		notification.setType("deal");
		notification.setEmail(email);

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
					+ nf.format(db_deal.getPrice()) + " nghìn đồng.";
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			email = db_deal.getRoute().getDriver().getEmail();
			msg = "Tài xế " + email + " đã từ chối đề nghị với giá tiền "
					+ nf.format(db_deal.getPrice()) + " nghìn đồng.";
		}
		notification.setMessage(msg);
		notification.setActive(Common.activate);
		notification.setCreateTime(deal.getCreateTime());
		notification.setType("deal");
		notification.setEmail(email);

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
					+ nf.format(db_deal.getPrice()) + " nghìn đồng.";
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			email = db_deal.getRoute().getDriver().getEmail();
			msg = "Tài xế " + email + " đã hủy thương lượng với giá tiền "
					+ nf.format(db_deal.getPrice()) + " nghìn đồng.";
		}
		notification.setMessage(msg);
		notification.setActive(Common.activate);
		notification.setCreateTime(deal.getCreateTime());
		notification.setType("deal");
		notification.setEmail(email);

		ret = notificationDao.insertNotification(notification);

		return ret;
	}

}
