/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.Order;

/**
 * @author Huy
 *
 */
public class DealProcess {

	DealDAO dealDao = new DealDAO();
	OrderDAO orderDao = new OrderDAO();
	GoodsDAO goodsDao = new GoodsDAO();
	RouteDAO routeDao = new RouteDAO();

	public static void main(String[] args) {
		DealProcess dp = new DealProcess();
		dp.declineDeal(30);
	}

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
				orderDao.insertOrder(order);

				// Change goods of this order to deactivate
				goodsDao.updateGoodsStatus(deal.getGoodsID(), Common.deactivate);

				// Change route of this order to deactivate
				routeDao.updateRouteStatus(deal.getRouteID(), Common.deactivate);

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

	public int checkDuplicateDeal(int driverID) {
		return 0;
	}
}
