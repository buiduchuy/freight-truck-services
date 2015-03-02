/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.DealNotificationDAO;
import vn.edu.fpt.fts.dao.DealOrderDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.DealNotification;
import vn.edu.fpt.fts.pojo.DealOrder;
import vn.edu.fpt.fts.pojo.Order;

/**
 * @author Huy
 *
 */
public class DealProcess {
	private final static String TAG = "DealProcess";

	DealDAO dealDao = new DealDAO();
	OrderDAO orderDao = new OrderDAO();
	GoodsDAO goodsDao = new GoodsDAO();
	RouteDAO routeDao = new RouteDAO();
	DealOrderDAO dealOrderDao = new DealOrderDAO();
	DealNotificationDAO dealNotiDao = new DealNotificationDAO();

	public String acceptDeal(int dealID) {

		Deal deal = dealDao.getDealByID(dealID);
		if (deal != null) {
			List<Deal> l_deal = new ArrayList<Deal>();
			List<Deal> l_declineDeal = new ArrayList<Deal>();

			// Update deal with accept status 3
			deal.setDealStatusID(Common.deal_accept);
			int s_update = dealDao.updateDeal(deal);

			if (s_update != 0) {

				// Get list parent deals with condition:
				// Get list deal with same GoodsID
				l_deal = dealDao.getDealByGoodsID(deal.getGoodsID());
				for (int i = 0; i < l_deal.size(); i++) {
					System.out.println(l_deal.get(i).getRefDealID() + " "
							+ l_deal.get(i).getRouteID());
					// RefDealID is NULL AND RouteID not match with others
					if (l_deal.get(i).getRefDealID() == 0
							&& l_deal.get(i).getRouteID() != deal.getRouteID()) {
						l_declineDeal.add(l_deal.get(i));
					}
				}

				// Change list parent deal to decline 4
				if (l_declineDeal.size() > 0) {
					for (int j = 0; j < l_declineDeal.size(); j++) {
						l_declineDeal.get(j).setDealStatusID(
								Common.deal_decline);
						dealDao.updateDeal(l_declineDeal.get(j));
					}
				}

				// Insert order when accept finish
				Order order = new Order();
				order.setPrice(deal.getPrice());
				order.setStaffDeliveryStatus(false);
				order.setDriverDeliveryStatus(false);
				order.setOwnerDeliveryStatus(false);
				order.setCreateTime(deal.getCreateTime());
				order.setOrderStatusID(1);
				int newOrderID = orderDao.insertOrder(order);

				// Insert into DealOrder Table
				DealOrder dealOrder = new DealOrder();
				dealOrder.setOrderID(newOrderID);
				dealOrder.setDealID(dealID);

				int newDealOrderID = dealOrderDao.insertDealOrder(dealOrder);

				if (newDealOrderID == 0) {
					System.out.println("Deal nay da xuat ra Order roi!!");
				}

				// Change goods of this order to deactivate
				goodsDao.updateGoodsStatus(deal.getGoodsID(), Common.deactivate);

				System.out
						.println("Accept - Number of deals will be change to decline: "
								+ l_declineDeal.size());
				return "Accept deal SUCCESS";
			}
			return "Accept deal FAIL";
		}
		return "Wrong dealID";
	}

	public String declineDeal(int dealID) {

		Deal deal = dealDao.getDealByID(dealID);
		if (deal != null) {
			List<Deal> l_deal = new ArrayList<Deal>();

			// Update deal with decline status 4
			deal.setDealStatusID(Common.deal_decline);
			int s_update = dealDao.updateDeal(deal);

			if (s_update != 0) {
				// Get list parent deals with condition:
				// Get list deal with same GoodsID
				l_deal = dealDao.getDealByGoodsID(deal.getGoodsID());

				for (int i = 0; i < l_deal.size(); i++) {

					if (l_deal.get(i).getDealID() == 9) {
						l_deal.get(i).setDealStatusID(Common.deal_decline);
						dealDao.updateDeal(l_deal.get(i));
					}
				}
				System.out
						.println("Decline - Number of deals will be change to decline: "
								+ l_deal.size());
				return "Decline deal SUCCESS";
			}
			return "Decline deal FAIL";
		}
		return "Wrong dealID";

	}

	public String cancelDeal(int dealID) {

		Deal deal = dealDao.getDealByID(dealID);
		if (deal != null) {
			List<Deal> l_deal = new ArrayList<Deal>();

			// Update deal with decline status 4
			deal.setDealStatusID(Common.deal_cancel);
			int s_update = dealDao.updateDeal(deal);

			if (s_update != 0) {
				// Get list parent deals with condition:
				// First get list deal with same GoodsID
				l_deal = dealDao.getDealByGoodsID(deal.getGoodsID());

				for (int i = 0; i < l_deal.size(); i++) {
					if (l_deal.get(i).getDealID() == 9) {
						l_deal.get(i).setDealStatusID(Common.deal_cancel);
						dealDao.updateDeal(l_deal.get(i));
					}
				}
				return "Cancel deal SUCCESS";
			}
			return "Cancel deal FAIL";
		}
		return "Wrong dealID";

	}

	public int acceptDeal1(Deal deal) {
		int ret = 0;
		try {
			// Update current deal
			// dealDao.changeDealStatus(deal.getDealID(), Common.deal_accept);

			// Insert new deal with accept status and CreateTime
			int newDealID = dealDao.insertDeal(deal);

			// Update other deal
			int n = dealDao.updateStatusOfOtherDeal(Common.deal_cancel,
					deal.getGoodsID(), deal.getRouteID());
			System.out.println("Co " + n
					+ " deal da thay doi trang thai la cancel");

			// Deactivate goods of this deal
			goodsDao.updateGoodsStatus(deal.getGoodsID(), Common.deactivate);

			// Insert new order
			Order order = new Order();
			order.setPrice(deal.getPrice());
			order.setStaffDeliveryStatus(false);
			order.setDriverDeliveryStatus(false);
			order.setOwnerDeliveryStatus(false);
			order.setCreateTime(deal.getCreateTime());
			order.setOrderStatusID(Common.order_pending);
			int newOrderID = orderDao.insertOrder(order);

			// Insert into DealOrder Table
			DealOrder dealOrder = new DealOrder();
			dealOrder.setOrderID(newOrderID);
			dealOrder.setDealID(newDealID);
			int newDealOrderID = dealOrderDao.insertDealOrder(dealOrder);
			ret = 1;
			if (newDealOrderID == 0) {
				System.out.println("Deal nay da xuat Order roi!!");
				ret = 0;
			}

			// Insert Notification
			DealNotification dealNoti = new DealNotification();
			String msg = "";
			if (deal.getCreateBy().equalsIgnoreCase("owner")) {
				msg = "Chủ hàng "
						+ goodsDao.getActiveGoodsByID(deal.getGoodsID())
								.getOwner().getEmail()
						+ " đã đồng ý đề nghị của bạn với giá tiền "
						+ deal.getPrice() + " nghìn đồng.";
			} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
				msg = "Tài xế "
						+ routeDao.getActiveRouteByID(deal.getRouteID())
								.getDriver().getEmail()
						+ " đã đồng ý đề nghị của bạn với giá tiền "
						+ deal.getPrice() + " nghìn đồng.";
			}
			dealNoti.setMessage(msg);
			dealNoti.setActive(Common.activate);
			dealNoti.setCreateTime(deal.getCreateTime());
			dealNoti.setDealID(newDealID);
			dealNotiDao.insertDealNotification(dealNoti);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return ret;
	}

	public int declineDeal1(Deal deal) {
		int ret = 0;
		try {
			// Update current deal
			// dealDao.changeDealStatus(deal.getDealID(), Common.deal_decline);

			// Insert new deal with decline status
			int newDealID = dealDao.insertDeal(deal);
			ret = 1;
			if (newDealID == 0) {
				System.out.println("Decline deal FAIL!");
				ret = 0;
			}

			// Insert Notification
			DealNotification dealNoti = new DealNotification();
			String msg = "";
			if (deal.getCreateBy().equalsIgnoreCase("owner")) {
				msg = "Chủ hàng "
						+ goodsDao.getActiveGoodsByID(deal.getGoodsID())
								.getOwner().getEmail()
						+ " đã từ chối đề nghị của bạn với giá tiền "
						+ deal.getPrice() + " nghìn đồng.";
			} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
				msg = "Tài xế "
						+ routeDao.getActiveRouteByID(deal.getRouteID())
								.getDriver().getEmail()
						+ " đã từ chối đề nghị của bạn với giá tiền "
						+ deal.getPrice() + " nghìn đồng.";
			}
			dealNoti.setMessage(msg);
			dealNoti.setActive(Common.activate);
			dealNoti.setCreateTime(deal.getCreateTime());
			dealNoti.setDealID(newDealID);
			dealNotiDao.insertDealNotification(dealNoti);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return ret;
	}

	public int cancelDeal1(Deal deal) {
		int ret = 0;
		try {
			Deal db_deal = dealDao.getDealByID(deal.getDealID());
			// Dieu kien deal hien tai la deal cua nguoi do va deal dang pending
			if (db_deal.getCreateBy().equalsIgnoreCase(deal.getCreateBy())
					&& db_deal.getDealStatusID() == Common.deal_pending) {
				// Update current deal
				ret = dealDao.updateDeal(deal);

				// Insert Notification
				DealNotification dealNoti = new DealNotification();
				String msg = "";
				if (deal.getCreateBy().equalsIgnoreCase("owner")) {
					msg = "Bạn đã hủy thương lượng với tài xế "
							+ routeDao.getActiveRouteByID(deal.getRouteID())
									.getDriver().getEmail();
				} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
					msg = "Bạn đã hủy thương lượng với chủ hàng "
							+ goodsDao.getActiveGoodsByID(deal.getGoodsID())
									.getOwner().getEmail();
				}
				dealNoti.setMessage(msg);
				dealNoti.setActive(Common.activate);
				dealNoti.setCreateTime(deal.getCreateTime());
				dealNoti.setDealID(deal.getDealID());
				dealNotiDao.insertDealNotification(dealNoti);
			} else {
				System.out
						.println("Khong thoa man de doi trang thai deal thanh cancel");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return ret;
	}

	public int sendDeal(Deal deal) {
		int ret = 0;
		Deal db_deal = dealDao.getLastDealByGoodsAndRouteID(deal.getRouteID(),
				deal.getGoodsID());

		if (db_deal == null) {
			// Not exist will insert new deal
			ret = dealDao.insertDeal(deal);

			// Insert Notification
			DealNotification dealNoti = new DealNotification();
			String msg = "";
			if (deal.getCreateBy().equalsIgnoreCase("owner")) {
				msg = "Bạn đã gửi đề nghị cho tài xế"
						+ routeDao.getActiveRouteByID(deal.getRouteID())
								.getDriver().getEmail();
			} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
				msg = "Bạn đã gửi đề nghị cho chủ hàng"
						+ goodsDao.getActiveGoodsByID(deal.getGoodsID())
								.getOwner().getEmail();
			}
			dealNoti.setMessage(msg);
			dealNoti.setActive(Common.activate);
			dealNoti.setCreateTime(deal.getCreateTime());
			dealNoti.setDealID(deal.getDealID());
			dealNotiDao.insertDealNotification(dealNoti);

		} else {
			int db_dealStatusID = db_deal.getDealStatusID();

			if (db_dealStatusID == Common.deal_pending) {

				// Get current deal
				Deal currentDeal = dealDao.getDealByID(deal.getDealID());
				// Update status
				currentDeal.setDealStatusID(Common.deal_decline);
				if (dealDao.updateDeal(currentDeal) != 0) {
					// Insert new deal with pending status
					ret = dealDao.insertDeal(deal);

					// Insert Notification
					DealNotification dealNoti = new DealNotification();
					String msg = "";
					if (deal.getCreateBy().equalsIgnoreCase("owner")) {
						msg = "Bạn đã gửi đề nghị cho tài xế "
								+ db_deal.getRoute().getDriver().getEmail();
					} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
						msg = "Bạn đã gửi đề nghị cho chủ hàng "
								+ db_deal.getGoods().getOwner().getEmail();
					}
					dealNoti.setMessage(msg);
					dealNoti.setActive(Common.activate);
					dealNoti.setCreateTime(deal.getCreateTime());
					dealNoti.setDealID(deal.getDealID());
					dealNotiDao.insertDealNotification(dealNoti);

				}
			} else if (db_dealStatusID == Common.deal_accept) {
				ret = 0;
			} else if (db_dealStatusID == Common.deal_decline) {
				ret = dealDao.insertDeal(deal);

				// Insert Notification
				DealNotification dealNoti = new DealNotification();
				String msg = "";
				if (deal.getCreateBy().equalsIgnoreCase("owner")) {
					msg = "Bạn đã gửi đề nghị cho tài xế "
							+ db_deal.getRoute().getDriver().getEmail();
				} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
					msg = "Bạn đã gửi đề nghị cho chủ hàng "
							+ db_deal.getGoods().getOwner().getEmail();
				}
				dealNoti.setMessage(msg);
				dealNoti.setActive(Common.activate);
				dealNoti.setCreateTime(deal.getCreateTime());
				dealNoti.setDealID(deal.getDealID());
				dealNotiDao.insertDealNotification(dealNoti);
			} else if (db_dealStatusID == Common.deal_cancel) {
				ret = dealDao.insertDeal(deal);

				// Insert Notification
				DealNotification dealNoti = new DealNotification();
				String msg = "";
				if (deal.getCreateBy().equalsIgnoreCase("owner")) {
					msg = "Bạn đã gửi đề nghị cho tài xế "
							+ db_deal.getRoute().getDriver().getEmail();
				} else if (deal.getCreateBy().equalsIgnoreCase("driver")) {
					msg = "Bạn đã gửi đề nghị cho chủ hàng "
							+ db_deal.getGoods().getOwner().getEmail();
				}
				dealNoti.setMessage(msg);
				dealNoti.setActive(Common.activate);
				dealNoti.setCreateTime(deal.getCreateTime());
				dealNoti.setDealID(deal.getDealID());
				dealNotiDao.insertDealNotification(dealNoti);
			}
		}
		return ret;
	}
}
