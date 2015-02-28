/**
 * 
 */
package vn.edu.fpt.fts.dao;

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
		RouteGoodsCategoryDAO dao = new RouteGoodsCategoryDAO();

		System.out.println(dao.insertRouteGoodsCategory(3, "Hàng dễ cháy nổ"));
	}

}
