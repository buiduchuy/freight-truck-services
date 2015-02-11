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

	public static void main(String[] args) {

		changeDealOwnerAccept(1);
	}

	public static int changeDealOwnerAccept(int ownerID) {
		DealDAO dealDao = new DealDAO();
		Deal deal = new Deal();
		List<Deal> listDeal = new ArrayList<Deal>();
		List<Deal> l_declineDeal = new ArrayList<Deal>();
		// bean.setDealStatusID(3);

		int s_update = 1;
		// DealDAO.updateDeal(bean);

		if (s_update != 0) {
			listDeal = dealDao.getDealByOwnerID(ownerID);
			for (int i = 0; i < listDeal.size(); i++) {
				if (listDeal.get(i).getDealStatusID() != 3) {

					deal = listDeal.get(i);
					l_declineDeal.add(deal);

				}
			}
		}

		return 1;
	}

	public static int updateDeal() {
		return 1;
	}

}
