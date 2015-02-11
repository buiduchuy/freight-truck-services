/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.pojo.Deal;

/**
 * @author Huy
 *
 */
public class UpdateDealProcess {

	DealDAO dealDao = new DealDAO();

	public static void main(String[] args) {
		UpdateDealProcess udp = new UpdateDealProcess();
		udp.changeDealOwnerAccept(1);
	}

	public int changeDealOwnerAccept(int ownerID) {

		Deal deal = new Deal();
		List<Deal> l_deal = new ArrayList<Deal>();

		List<Deal> l_declineDeal = new ArrayList<Deal>();

		// Update deal with accept status 3
		// bean.setDealStatusID(3);
		int s_update = 1;
		// dealDao.updateDeal(bean);

		if (s_update != 0) {
			// Get list deal by OwnerID with different accept status 3
			l_deal = dealDao.getDealByOwnerID(ownerID);
			for (int i = 0; i < l_deal.size(); i++) {
				if (l_deal.get(i).getDealStatusID() != 3) {
					deal = l_deal.get(i);
					l_declineDeal.add(deal);
				}
			}

			// Change list deal with different accept status to decline 4
			for (int j = 0; j < l_declineDeal.size(); j++) {
				l_declineDeal.get(j).setDealStatusID(4);
				dealDao.updateDeal(l_declineDeal.get(j));
			}
		}
		return 1;
	}

	public int updateDeal() {
		return 1;
	}

}
