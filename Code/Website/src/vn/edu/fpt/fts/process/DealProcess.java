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
	NotificationProcess notiProcess = new NotificationProcess();

	public DealProcess() {
		// TODO Auto-generated constructor stub
	}

	// public String acceptDeal(int dealID) {
	//
	// Deal deal = dealDao.getDealByID(dealID);
	// if (deal != null) {
	// List<Deal> l_deal = new ArrayList<Deal>();
	// List<Deal> l_declineDeal = new ArrayList<Deal>();
	//
	// // Update deal with accept status 3
	// deal.setDealStatusID(Common.deal_accept);
	// int s_update = dealDao.updateDeal(deal);
	//
	// if (s_update != 0) {
	//
	// // Get list parent deals with condition:
	// // Get list deal with same GoodsID
	// l_deal = dealDao.getDealByGoodsID(deal.getGoodsID());
	// for (int i = 0; i < l_deal.size(); i++) {
	// System.out.println(l_deal.get(i).getRefDealID() + " "
	// + l_deal.get(i).getRouteID());
	// // RefDealID is NULL AND RouteID not match with others
	// if (l_deal.get(i).getRefDealID() == 0
	// && l_deal.get(i).getRouteID() != deal.getRouteID()) {
	// l_declineDeal.add(l_deal.get(i));
	// }
	// }
	//
	// // Change list parent deal to decline 4
	// if (l_declineDeal.size() > 0) {
	// for (int j = 0; j < l_declineDeal.size(); j++) {
	// l_declineDeal.get(j).setDealStatusID(
	// Common.deal_decline);
	// dealDao.updateDeal(l_declineDeal.get(j));
	// }
	// }
	//
	// // Insert order when accept finish
	// Order order = new Order();
	// order.setPrice(deal.getPrice());
	// order.setCreateTime(deal.getCreateTime());
	// order.setOrderStatusID(1);
	// int newOrderID = orderDao.insertOrder(order);
	//
	// // Insert into DealOrder Table
	// DealOrder dealOrder = new DealOrder();
	// dealOrder.setOrderID(newOrderID);
	// dealOrder.setDealID(dealID);
	//
	// int newDealOrderID = dealOrderDao.insertDealOrder(dealOrder);
	//
	// if (newDealOrderID == 0) {
	// System.out.println("Deal nay da xuat ra Order roi!!");
	// }
	//
	// // Change goods of this order to deactivate
	// goodsDao.updateGoodsStatus(deal.getGoodsID(), Common.deactivate);
	//
	// System.out
	// .println("Accept - Number of deals will be change to decline: "
	// + l_declineDeal.size());
	// return "Accept deal SUCCESS";
	// }
	// return "Accept deal FAIL";
	// }
	// return "Wrong dealID";
	// }

	// public String declineDeal(int dealID) {
	//
	// Deal deal = dealDao.getDealByID(dealID);
	// if (deal != null) {
	// List<Deal> l_deal = new ArrayList<Deal>();
	//
	// // Update deal with decline status 4
	// deal.setDealStatusID(Common.deal_decline);
	// int s_update = dealDao.updateDeal(deal);
	//
	// if (s_update != 0) {
	// // Get list parent deals with condition:
	// // Get list deal with same GoodsID
	// l_deal = dealDao.getDealByGoodsID(deal.getGoodsID());
	//
	// for (int i = 0; i < l_deal.size(); i++) {
	//
	// if (l_deal.get(i).getDealID() == 9) {
	// l_deal.get(i).setDealStatusID(Common.deal_decline);
	// dealDao.updateDeal(l_deal.get(i));
	// }
	// }
	// System.out
	// .println("Decline - Number of deals will be change to decline: "
	// + l_deal.size());
	// return "Decline deal SUCCESS";
	// }
	// return "Decline deal FAIL";
	// }
	// return "Wrong dealID";
	//
	// }

	// public String cancelDeal(int dealID) {
	//
	// Deal deal = dealDao.getDealByID(dealID);
	// if (deal != null) {
	// List<Deal> l_deal = new ArrayList<Deal>();
	//
	// // Update deal with decline status 4
	// deal.setDealStatusID(Common.deal_cancel);
	// int s_update = dealDao.updateDeal(deal);
	//
	// if (s_update != 0) {
	// // Get list parent deals with condition:
	// // First get list deal with same GoodsID
	// l_deal = dealDao.getDealByGoodsID(deal.getGoodsID());
	//
	// for (int i = 0; i < l_deal.size(); i++) {
	// if (l_deal.get(i).getDealID() == 9) {
	// l_deal.get(i).setDealStatusID(Common.deal_cancel);
	// dealDao.updateDeal(l_deal.get(i));
	// }
	// }
	// return "Cancel deal SUCCESS";
	// }
	// return "Cancel deal FAIL";
	// }
	// return "Wrong dealID";
	//
	// }

	public int acceptDealFirst(Deal deal) {
		int ret = 0;
		try {
			int goodsID = deal.getGoodsID();
			int routeID = deal.getRouteID();
			if (dealDao.getLastDealByGoodsAndRouteID(routeID, goodsID) == null) {
				// Insert new deal with accept status and CreateTime
				int newDealID = dealDao.insertDeal(deal);

				List<Deal> listDealCheckForOwner = dealDao
						.getListOtherDealSameGoodsAndOtherRoute(
								deal.getGoodsID(), deal.getRouteID());

				if (listDealCheckForOwner != null) {
					for (int i = 0; i < listDealCheckForOwner.size(); i++) {
						Deal item = listDealCheckForOwner.get(i);
						if (item.getDealStatusID() == Common.deal_pending) {
							if (listDealCheckForOwner.get(i).getCreateBy()
									.equalsIgnoreCase(deal.getCreateBy())) {
								cancelDeal1(listDealCheckForOwner.get(i));
							} else {
								declineDeal1(listDealCheckForOwner.get(i));
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
						Deal item = listDealCheckForOwner.get(i);
						if (item.getDealStatusID() == Common.deal_pending) {
							// Tải trọng hoặc Loại hàng
							if (item.getGoods().getWeight() >= remainPayloads
									|| item.getGoods().getGoodsCategoryID() != goodsCategoryIDOfDealAccept) {
								if (listDealCheckForDriver.get(i).getCreateBy()
										.equalsIgnoreCase(deal.getCreateBy())) {
									cancelDeal1(listDealCheckForDriver.get(i));
								} else {
									declineDeal1(listDealCheckForDriver.get(i));
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
				ret = 2;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}

		return ret;
	}

	public int acceptDeal1(Deal deal) {
		int ret = 0;
		try {
			Deal db_deal = dealDao.getDealByID(deal.getDealID());
			// Current deal must have pending status
			if (db_deal.getDealStatusID() == Common.deal_pending) {
				// Update current deal
				// dealDao.updateDealStatus(deal.getDealID(),
				// Common.deal_accept);
				deal.setDealStatusID(Common.deal_accept);
				ret = dealDao.updateDeal(deal);

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
							if (listDealCheckForOwner.get(i).getCreateBy()
									.equalsIgnoreCase(deal.getCreateBy())) {
								cancelDeal1(listDealCheckForOwner.get(i));
							} else {
								declineDeal1(listDealCheckForOwner.get(i));
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
						Deal item = listDealCheckForOwner.get(i);
						if (item.getDealStatusID() == Common.deal_pending) {
							// Tải trọng hoặc Loại hàng
							if (item.getGoods().getWeight() >= remainPayloads
									|| item.getGoods().getGoodsCategoryID() != goodsCategoryIDOfDealAccept) {
								if (listDealCheckForDriver.get(i).getCreateBy()
										.equalsIgnoreCase(deal.getCreateBy())) {
									cancelDeal1(listDealCheckForDriver.get(i));
								} else {
									declineDeal1(listDealCheckForDriver.get(i));
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
				ret = 1;
				if (newDealOrderID == 0) {
					System.out.println("Deal nay da xuat Order roi!!");
					ret = 0;
				}

				// Insert Notification
				notiProcess.insertDealAcceptNotification(deal, newOrderID);
			} else {
				ret = 2;
				System.out.println("Current Status of deal isn't Pending");
			}

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
				ret = 2;
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
				ret = 2;
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
}
