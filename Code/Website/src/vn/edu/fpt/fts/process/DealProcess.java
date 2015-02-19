/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Order;
import vn.edu.fpt.fts.pojo.Route;

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

	public int acceptDeal(int dealID) {

		int i_acceptStatus = 3;
		int i_declineStatus = 4;

		Deal deal = dealDao.getDealByID(dealID);
		List<Deal> l_deal = new ArrayList<Deal>();
		List<Deal> l_declineDeal = new ArrayList<Deal>();

		// Update deal with accept status 3
		deal.setDealStatusID(i_acceptStatus);
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
			for (int j = 0; j < l_declineDeal.size(); j++) {
				l_declineDeal.get(j).setDealStatusID(i_declineStatus);
				dealDao.updateDeal(l_declineDeal.get(j));
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

			// Change goods of this order
			Goods goods = new Goods();
			goodsDao.changeGoodsStatus(deal.getGoodsID(), 0);

			// Change route of this order
			Route route = new Route();

			System.out
					.println("Accept - Number of deals will be change to decline: "
							+ l_declineDeal.size());
			return 1;
		}
		return 0;
	}

	public int declineDeal(int dealID) {

		// int i_acceptStatus = 3;
		int i_declineStatus = 4;

		Deal deal = dealDao.getDealByID(dealID);
		List<Deal> l_deal = new ArrayList<Deal>();

		// Update deal with decline status 4
		deal.setDealStatusID(i_declineStatus);
		int s_update = dealDao.updateDeal(deal);

		if (s_update != 0) {
			// Get list parent deals with condition:
			// Get list deal with same GoodsID
			l_deal = dealDao.getDealByGoodsID(deal.getGoodsID());

			for (int i = 0; i < l_deal.size(); i++) {

				if (l_deal.get(i).getDealID() == 9) {
					l_deal.get(i).setDealStatusID(i_declineStatus);
					dealDao.updateDeal(l_deal.get(i));
				}
			}
			System.out
					.println("Decline - Number of deals will be change to decline: "
							+ l_deal.size());
			return 1;
		}
		return 0;
	}

	public int cancelDeal(int dealID) {
		// int i_acceptStatus = 3;
		int i_declineStatus = 4;

		Deal deal = dealDao.getDealByID(dealID);
		List<Deal> l_deal = new ArrayList<Deal>();

		// Update deal with decline status 4
		deal.setDealStatusID(i_declineStatus);
		int s_update = dealDao.updateDeal(deal);

		if (s_update != 0) {
			// Get list parent deals with condition:
			// First get list deal with same GoodsID
			l_deal = dealDao.getDealByGoodsID(deal.getGoodsID());

			for (int i = 0; i < l_deal.size(); i++) {

				if (l_deal.get(i).getDealID() == 9) {
					l_deal.get(i).setDealStatusID(i_declineStatus);
					dealDao.updateDeal(l_deal.get(i));
				}
			}
			return 1;
		}
		return 0;
	}

	public int checkDuplicateDeal() {
		return 0;
	}

}
