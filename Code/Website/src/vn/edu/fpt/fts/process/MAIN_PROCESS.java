/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.List;

import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Route;

/**
 * @author Huy
 *
 */
public class MAIN_PROCESS {

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MatchingProcess matchingProcess = new MatchingProcess();

		// Vung Tau
		// Double latGoods = 10.4025053;
		// Double longGoods = 107.1255859;

		// Quang Trung, TP.HCM
		// Double latGoods = 10.853132;
		// Double longGoods = 106.626289;

		// Xuan Loc, Dong Nai
		// Double latEnd = 10.9234099;
		// Double longEnd = 107.4084806;

		// GoodsDAO goodsDao = new GoodsDAO();
		// LatLng goodsStartLocation = new LatLng();
		//
		// LatLng goodsFinishLocation = new LatLng();
		// Goods goods = goodsDao.getGoodsByID(79);
		//
		// if (goods != null) {
		// goodsFinishLocation.setLatitude(goods.getDeliveryMarkerLatidute());
		// goodsFinishLocation.setLongitude(goods
		// .getDeliveryMarkerLongtitude());
		// goodsStartLocation.setLatitude(goods.getPickupMarkerLatidute());
		// goodsStartLocation.setLongitude(goods.getPickupMarkerLongtitude());
		// RouteDAO routeDao = new RouteDAO();
		// List<Route> l_route = routeDao.getAllRoute();
		// if (l_route.size() != 0) {
		// for (int j = 0; j < l_route.size(); j++) {
		// System.out.println(mapProcess.checkDistance(l_route.get(j)
		// .getRouteID(), goodsStartLocation,
		// goodsFinishLocation, 2));
		// }
		//
		// }
		// }
		List<Route> routeList = matchingProcess.getSuggestionRoute(11);
		System.out.println("------------------- Number of Route: "
				+ routeList.size());

		List<Goods> goodsList = matchingProcess.getSuggestionGoods(8);
		System.out.println("------------------- Number of Goods: "
				+ goodsList.size());
		// goodsStartLocation.setLatitude(21.0248455);
		// goodsStartLocation.setLongitude(105.8287365);
		//
		// goodsFinishLocation.setLatitude(10.824391);
		// goodsFinishLocation.setLongitude(106.628505);

		// System.out.println(mapProcess.checkDistance(9, goodsStartLocation,
		// goodsFinishLocation, 2));

	}
}
