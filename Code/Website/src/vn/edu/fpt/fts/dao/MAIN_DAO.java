/**
 * 
 */
package vn.edu.fpt.fts.dao;

/**
 * @author Huy
 *
 */
public class MAIN_DAO {

	public static void main(String[] args) {
		GoodsDAO goodsDao = new GoodsDAO();
		System.out.println(goodsDao.getRemainingWeightByRouteID(38));
		System.out.println(goodsDao.getTotalWeightByRouteID(38));
	}

}
