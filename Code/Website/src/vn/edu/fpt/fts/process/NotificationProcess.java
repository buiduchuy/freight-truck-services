/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.ArrayList;
import java.util.List;

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
			msg = "Chủ hàng " + db_deal.getGoods().getOwner().getEmail()
					+ " đã gửi đề nghị với giá tiền " + db_deal.getPrice()
					+ " nghìn đồng.";
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			msg = "Tài xế " + db_deal.getRoute().getDriver().getEmail()
					+ " đã gửi đề nghị với giá tiền " + db_deal.getPrice()
					+ " nghìn đồng.";
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
					+ " đã đồng ý đề nghị với giá tiền " + db_deal.getPrice()
					+ " nghìn đồng.";
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			msg = "Tài xế " + db_deal.getRoute().getDriver().getEmail()
					+ " đã đồng ý đề nghị với giá tiền " + db_deal.getPrice()
					+ " nghìn đồng.";
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
					+ " đã từ chối đề nghị với giá tiền " + db_deal.getPrice()
					+ " nghìn đồng.";
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			msg = "Tài xế " + db_deal.getRoute().getDriver().getEmail()
					+ " đã từ chối đề nghị với giá tiền " + db_deal.getPrice()
					+ " nghìn đồng.";
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
			msg = "Chủ hàng " + db_deal.getGoods().getOwner().getEmail()
					+ " đã hủy thương lượng với giá tiền " + db_deal.getPrice()
					+ " nghìn đồng.";
		} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
			msg = "Tài xế " + db_deal.getRoute().getDriver().getEmail()
					+ " đã hủy thương lượng với giá tiền " + db_deal.getPrice()
					+ " nghìn đồng.";
		}
		dealNoti.setMessage(msg);
		dealNoti.setActive(Common.activate);
		dealNoti.setCreateTime(deal.getCreateTime());
		dealNoti.setDealID(deal.getDealID());
		ret = dealNotiDao.insertDealNotification(dealNoti);
		return ret;
	}

	public List<DealNotification> getListDealNotiByDriver(int driverID) {
		List<DealNotification> l_dealNoti = new ArrayList<DealNotification>();
		List<Deal> l_deal = dealDao.getDealByDriverID(driverID);
		for (int i = 0; i < l_deal.size(); i++) {
			if (l_deal.get(i).getCreateBy().equalsIgnoreCase("driver")) {
				l_dealNoti
						.addAll(dealNotiDao.getDealNotificationByDealID(l_deal
								.get(i).getDealID()));
			}
		}

		return l_dealNoti;
	}

	public List<DealNotification> getListDealNotiByOwner(int ownerID) {
		List<DealNotification> l_dealNoti = new ArrayList<DealNotification>();
		List<Deal> l_deal = dealDao.getDealByOwnerID(ownerID);
		for (int i = 0; i < l_deal.size(); i++) {
			if (l_deal.get(i).getCreateBy().equalsIgnoreCase("owner")) {
				l_dealNoti
						.addAll(dealNotiDao.getDealNotificationByDealID(l_deal
								.get(i).getDealID()));
			}
		}
		return l_dealNoti;
	}

}
