/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.DealOrderDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.dao.RouteGoodsCategoryDAO;
import vn.edu.fpt.fts.pojo.Deal;
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
	RouteGoodsCategoryDAO routeGoodsCategoryDao = new RouteGoodsCategoryDAO();
	NotificationProcess notiProcess = new NotificationProcess();

	public DealProcess() {
		// TODO Auto-generated constructor stub
	}

	public int acceptDealFirst(Deal deal) {
		int ret = 0;
		try {
			int goodsID = deal.getGoodsID();
			int routeID = deal.getRouteID();
			if (dealDao.getLastDealByGoodsAndRouteID(routeID, goodsID)
					.getDealStatusID() != Common.deal_pending) {
				// Insert new deal with accept status and CreateTime
				int newDealID = dealDao.insertDeal(deal);

				List<Deal> listDealCheckForOwner = dealDao
						.getListOtherDealSameGoodsAndOtherRoute(
								deal.getGoodsID(), deal.getRouteID());

				if (listDealCheckForOwner != null) {
					for (int i = 0; i < listDealCheckForOwner.size(); i++) {
						Deal item = listDealCheckForOwner.get(i);
						if (item.getDealStatusID() == Common.deal_pending) {
							if (item.getCreateBy().equalsIgnoreCase("owner")) {
								cancelDeal1(item);
							} else {
								item.setCreateBy("owner");
								declineDealForOther(item);
							}
						}
					}
				}

				int totalGoodsWeightOfRoute = goodsDao
						.getTotalWeightByRouteID(deal.getRouteID());
				int weightRoute = routeDao
						.getActiveRouteByID(deal.getRouteID()).getWeight();

				int remainPayloads = weightRoute - totalGoodsWeightOfRoute;

				Deal db_deal = dealDao.getDealByID(newDealID);

				int goodsCategoryIDOfDealAccept = db_deal.getGoods()
						.getGoodsCategoryID();

				List<Deal> listDealCheckForDriver = dealDao
						.getListOtherDealSameRouteAndOtherGoods(
								deal.getGoodsID(), deal.getRouteID());
				if (listDealCheckForDriver != null) {
					for (int i = 0; i < listDealCheckForDriver.size(); i++) {
						Deal item = listDealCheckForDriver.get(i);
						if (item.getDealStatusID() == Common.deal_pending) {
							// Tải trọng hoặc Loại hàng, kiểm tra lại chỗ này,
							// nếu cancel hoặc decline thì thuộc về driver?
							if (item.getGoods().getWeight() >= remainPayloads
									|| item.getGoods().getGoodsCategoryID() != goodsCategoryIDOfDealAccept) {
								if (item.getCreateBy().equalsIgnoreCase(
										"driver")) {
									cancelDeal1(item);
								} else {
									item.setCreateBy("driver");
									declineDealForOther(item);
								}
							}
						}
					}
				}

				// Deactivate goods of this deal
				goodsDao.updateGoodsStatus(deal.getGoodsID(), Common.deactivate);

				// Insert new order
				Order order = new Order();
				order.setPrice(deal.getPrice());
				order.setCreateTime(deal.getCreateTime());
				order.setOrderStatusID(Common.order_unpaid);
				ret = orderDao.insertOrder(order);

				// Insert into DealOrder Table
				DealOrder dealOrder = new DealOrder();
				dealOrder.setOrderID(ret);
				dealOrder.setDealID(newDealID);
				int newDealOrderID = dealOrderDao.insertDealOrder(dealOrder);
				if (newDealOrderID == 0) {
					System.out.println("Deal nay da xuat Order roi!!");
				}

				// Insert Notification
				notiProcess.insertDealAcceptNotification(deal, ret);
			} else {
				ret = -1;
			}

		} catch (Exception e) {
			// TODO: handle exception
			ret = 0;
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return ret;
	}

	public int acceptDeal1(Deal deal) {
		int ret = 1;
		try {
			Deal db_deal = dealDao.getDealByID(deal.getDealID());
			// Current deal must have pending status
			if (db_deal.getDealStatusID() == Common.deal_pending) {
				// Update current deal
				// dealDao.updateDealStatus(deal.getDealID(),
				// Common.deal_accept);
				deal.setDealStatusID(Common.deal_accept);
				dealDao.updateDeal(deal);

				// Insert new deal with accept status and CreateTime
				// int newDealID = dealDao.insertDeal(deal);

				// Change Cancel status to other deal

				// Xử lí cho owner
				List<Deal> listDealCheckForOwner = dealDao
						.getListOtherDealSameGoodsAndOtherRoute(
								db_deal.getGoodsID(), db_deal.getRouteID());

				if (listDealCheckForOwner != null) {
					for (int i = 0; i < listDealCheckForOwner.size(); i++) {
						Deal item = listDealCheckForOwner.get(i);
						if (item.getDealStatusID() == Common.deal_pending) {
							// Kiểm tra lại chỗ này, nếu cancel hoặc decline thì
							// thuộc về owner?
							if (item.getCreateBy().equalsIgnoreCase("owner")) {
								cancelDeal1(item);
							} else {
								item.setCreateBy("owner");
								declineDealForOther(item);
							}
						}
					}
				}

				// Xử lí cho driver
				int totalGoodsWeightOfRoute = goodsDao
						.getTotalWeightByRouteID(db_deal.getRouteID());
				int weightRoute = routeDao.getActiveRouteByID(
						db_deal.getRouteID()).getWeight();

				int remainPayloads = weightRoute - totalGoodsWeightOfRoute;

				int goodsCategoryIDOfDealAccept = db_deal.getGoods()
						.getGoodsCategoryID();

				List<Deal> listDealCheckForDriver = dealDao
						.getListOtherDealSameRouteAndOtherGoods(
								db_deal.getGoodsID(), db_deal.getRouteID());
				if (listDealCheckForDriver != null) {
					for (int i = 0; i < listDealCheckForDriver.size(); i++) {
						Deal item = listDealCheckForDriver.get(i);
						if (item.getDealStatusID() == Common.deal_pending) {
							// Tải trọng hoặc Loại hàng, kiểm tra lại chỗ này,
							// nếu cancel hoặc decline thì thuộc về driver?
							if (item.getGoods().getWeight() >= remainPayloads
									|| item.getGoods().getGoodsCategoryID() != goodsCategoryIDOfDealAccept) {
								if (item.getCreateBy().equalsIgnoreCase(
										"driver")) {
									cancelDeal1(item);
								} else {
									item.setCreateBy("driver");
									declineDealForOther(item);
								}
							}
						}
					}
				}

				// Deactivate goods of this deal
				goodsDao.updateGoodsStatus(deal.getGoodsID(), Common.deactivate);

				// Insert new order
				Order order = new Order();
				order.setPrice(deal.getPrice());
				order.setCreateTime(deal.getCreateTime());
				order.setOrderStatusID(Common.order_unpaid);
				int newOrderID = orderDao.insertOrder(order);

				// Insert into DealOrder Table
				DealOrder dealOrder = new DealOrder();
				dealOrder.setOrderID(newOrderID);
				// dealOrder.setDealID(newDealID);
				dealOrder.setDealID(deal.getDealID());
				int newDealOrderID = dealOrderDao.insertDealOrder(dealOrder);
				if (newDealOrderID == 0) {
					System.out.println("Deal nay da xuat Order roi!!");
					ret = 0;
				}

				// Insert Notification
				notiProcess.insertDealAcceptNotification(deal, newOrderID);
			} else {
				ret = -1;
				System.out.println("Current Status of deal isn't Pending");
			}

		} catch (Exception e) {
			// TODO: handle exception
			ret = 0;
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return ret;
	}

	public int declineDeal1(Deal deal) {
		int ret = 0;
		try {
			Deal db_deal = dealDao.getDealByID(deal.getDealID());
			// Current deal must have pending status
			if (db_deal.getDealStatusID() == Common.deal_pending) {
				// Update current deal
				// dealDao.updateDealStatus(deal.getDealID(),
				// Common.deal_decline);
				if (db_deal.getCreateBy().equalsIgnoreCase("owner")) {
					deal.setCreateBy("driver");
				} else {
					deal.setCreateBy("owner");
				}
				deal.setDealStatusID(Common.deal_decline);

				ret = dealDao.updateDeal(deal);

				// Insert new deal with decline status
				notiProcess.insertDealDeclineNotification(deal);
			} else {
				ret = -1;
				System.out.println("Current Status of deal isn't Pending");
			}
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
			// Current status of deal is pending and same createBy
			if (db_deal.getCreateBy().equalsIgnoreCase(deal.getCreateBy())
					&& db_deal.getDealStatusID() == Common.deal_pending) {
				// Update current deal
				deal.setDealStatusID(Common.deal_cancel);
				ret = dealDao.updateDeal(deal);

				// Insert Notification
				notiProcess.insertDealCancelNotification(deal);
			} else {
				ret = -1;
				System.out
						.println("Current status of deal isn't pending or same with createBy");
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
			deal.setDealID(ret); // Update last dealID
			notiProcess.insertDealSendNotification(deal);
		} else {
			int db_dealStatusID = db_deal.getDealStatusID();

			if (db_dealStatusID == Common.deal_pending) {

				Deal currentDeal = dealDao.getDealByID(deal.getDealID());
				if (currentDeal != null) {
					// Update status
					currentDeal.setDealStatusID(Common.deal_decline);
					if (dealDao.updateDeal(currentDeal) != 0) {
						// Insert new deal with pending status
						ret = dealDao.insertDeal(deal);

						// Insert Notification
						deal.setDealID(ret); // Update last dealID
						notiProcess.insertDealSendNotification(deal);
					}
				} else {
					ret = 0;
				}
			} else if (db_dealStatusID == Common.deal_accept) {
				ret = 0;
			} else if (db_dealStatusID == Common.deal_decline) {
				ret = dealDao.insertDeal(deal);

				// Insert Notification
				deal.setDealID(ret); // Update last dealID
				notiProcess.insertDealSendNotification(deal);
			} else if (db_dealStatusID == Common.deal_cancel) {
				ret = dealDao.insertDeal(deal);

				// Insert Notification
				deal.setDealID(ret); // Update last dealID
				notiProcess.insertDealSendNotification(deal);
			}
		}
		return ret;
	}

	public int declineDealForOther(Deal deal) {
		int ret = 0;
		try {
			Deal db_deal = dealDao.getDealByID(deal.getDealID());
			// Current deal must have pending status
			if (db_deal.getDealStatusID() == Common.deal_pending) {
				// Update current deal
				// dealDao.updateDealStatus(deal.getDealID(),
				// Common.deal_decline);
				deal.setDealStatusID(Common.deal_decline);

				ret = dealDao.updateDeal(deal);

				// Insert new deal with decline status
				notiProcess.insertDealDeclineNotification(deal);
			} else {
				ret = -1;
				System.out.println("Current Status of deal isn't Pending");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return ret;
	}

}
