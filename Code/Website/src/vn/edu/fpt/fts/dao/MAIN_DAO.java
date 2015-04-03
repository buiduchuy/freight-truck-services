/**
 * 
 */
package vn.edu.fpt.fts.dao;

import java.util.List;

import vn.edu.fpt.fts.pojo.Route;

/**
 * @author Huy
 *
 */
public class MAIN_DAO {

	public static void main(String[] args) {
		RouteDAO routeDAO = new RouteDAO();
		
		List<Route> list = routeDAO.getListRouteInDealPendingOrAcceptByGoodsID(1170);
		for (Route goods : list) {
			System.out.println(goods.getRouteID());
		}
	}
}
