/**
 * 
 */
package vn.edu.fpt.fts.process;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.DealNotificationDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.DealNotification;

/**
 * @author Huy
 *
 */
public class NotificationProcess {

	DealNotificationDAO dealNotiDao = new DealNotificationDAO();
	DealDAO dealDao = new DealDAO();
	DealNotification dealNoti = new DealNotification();

	public int insertSendDealNoti(Deal deal) {
		int ret = 0;
		Deal db_deal = dealDao.getLastDealByGoodsAndRouteID(deal.getRouteID(),
				deal.getGoodsID());
		String msg = "";
		if (deal.getCreateBy().equalsIgnoreCase("owner")) {
			msg = "Bạn đã gửi đề nghị cho tài xế"
					+ db_deal.getRoute().getDriver().getEmail();
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			msg = "Bạn đã gửi đề nghị cho chủ hàng"
					+ db_deal.getGoods().getOwner().getEmail();
		}
		dealNoti.setMessage(msg);
		dealNoti.setActive(Common.activate);
		dealNoti.setCreateTime(deal.getCreateTime());
		dealNoti.setDealID(deal.getDealID());
		ret = dealNotiDao.insertDealNotification(dealNoti);
		return ret;
	}

	public int insertAcceptDealNoti(Deal deal) {
		int ret = 0;

		Deal db_deal = dealDao.getLastDealByGoodsAndRouteID(deal.getRouteID(),
				deal.getGoodsID());
		String msg = "";
		if (deal.getCreateBy().equalsIgnoreCase("owner")) {
			msg = "Chủ hàng " + db_deal.getGoods().getOwner().getEmail()
					+ " đã đồng ý đề nghị của bạn với giá tiền "
					+ db_deal.getPrice() + " nghìn đồng.";
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			msg = "Tài xế " + db_deal.getRoute().getDriver().getEmail()
					+ " đã đồng ý đề nghị của bạn với giá tiền "
					+ db_deal.getPrice() + " nghìn đồng.";
		}
		dealNoti.setMessage(msg);
		dealNoti.setActive(Common.activate);
		dealNoti.setCreateTime(deal.getCreateTime());
		dealNoti.setDealID(deal.getDealID());
		ret = dealNotiDao.insertDealNotification(dealNoti);
		return ret;
	}

	public int insertDeclineDealNoti(Deal deal) {
		int ret = 0;
		Deal db_deal = dealDao.getLastDealByGoodsAndRouteID(deal.getRouteID(),
				deal.getGoodsID());
		String msg = "";
		if (deal.getCreateBy().equalsIgnoreCase("owner")) {
			msg = "Chủ hàng " + db_deal.getGoods().getOwner().getEmail()
					+ " đã từ chối đề nghị của bạn với giá tiền "
					+ db_deal.getPrice() + " nghìn đồng.";
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			msg = "Tài xế " + db_deal.getRoute().getDriver().getEmail()
					+ " đã từ chối đề nghị của bạn với giá tiền "
					+ db_deal.getPrice() + " nghìn đồng.";
		}
		dealNoti.setMessage(msg);
		dealNoti.setActive(Common.activate);
		dealNoti.setCreateTime(deal.getCreateTime());
		dealNoti.setDealID(deal.getDealID());
		ret = dealNotiDao.insertDealNotification(dealNoti);
		return ret;
	}

	public int insertCancelDealNoti(Deal deal) {
		int ret = 0;
		Deal db_deal = dealDao.getLastDealByGoodsAndRouteID(deal.getRouteID(),
				deal.getGoodsID());
		DealNotification dealNoti = new DealNotification();
		String msg = "";
		if (deal.getCreateBy().equalsIgnoreCase("owner")) {
			msg = "Bạn đã hủy thương lượng với tài xế "
					+ db_deal.getRoute().getDriver().getEmail();
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			msg = "Bạn đã hủy thương lượng với chủ hàng "
					+ db_deal.getGoods().getOwner().getEmail();
		}
		dealNoti.setMessage(msg);
		dealNoti.setActive(Common.activate);
		dealNoti.setCreateTime(deal.getCreateTime());
		dealNoti.setDealID(deal.getDealID());
		ret = dealNotiDao.insertDealNotification(dealNoti);
		return ret;
	}

}
