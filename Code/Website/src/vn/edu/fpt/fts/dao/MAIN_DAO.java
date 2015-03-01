/**
 * 
 */
package vn.edu.fpt.fts.dao;

import vn.edu.fpt.fts.pojo.Deal;

/**
 * @author Huy
 *
 */
public class MAIN_DAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// RouteGoodsCategoryDAO dao = new RouteGoodsCategoryDAO();
		DealDAO dealDao = new DealDAO();

		// System.out.println(dao.insertRouteGoodsCategory(3,
		// "Hàng dễ cháy nổ"));
		Deal deal = dealDao.getLastDealByGoodsAndRouteID(22, 32);
		System.out.println(deal.getNotes());
	}

}
