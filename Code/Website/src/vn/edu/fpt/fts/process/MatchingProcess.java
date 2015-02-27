/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Route;

/**
 * @author Huy
 *
 */
public class MatchingProcess {
	MapsProcess mapProcess = new MapsProcess();
	GoodsDAO goodsDao = new GoodsDAO();
	RouteDAO routeDao = new RouteDAO();

	public List<Route> getSuggestionRoute(int goodsID) {

		LatLng goodsStartLocation = new LatLng();
		LatLng goodsFinishLocation = new LatLng();
		List<Route> l_routes = new ArrayList<Route>();
		Goods goods = goodsDao.getActiveGoodsByID(goodsID);

		if (goods != null) {
			goodsFinishLocation.setLatitude(goods.getDeliveryMarkerLatidute());
			goodsFinishLocation.setLongitude(goods
					.getDeliveryMarkerLongtitude());
			goodsStartLocation.setLatitude(goods.getPickupMarkerLatidute());
			goodsStartLocation.setLongitude(goods.getPickupMarkerLongtitude());
			List<Route> l_routeBefore = routeDao.getListActiveRoute();
			if (l_routeBefore.size() != 0) {
				for (int i = 0; i < l_routeBefore.size(); i++) {
					if (mapProcess.checkDistance(l_routeBefore.get(i)
							.getRouteID(), goodsStartLocation,
							goodsFinishLocation, Common.maxAllowDistance)) {
						l_routes.add(l_routeBefore.get(i));
					}
				}
			}
		}
		return l_routes;
	}

	public List<Goods> getSuggestionGoods(int routeID) {
		Route route = routeDao.getActiveRouteByID(routeID);
		List<Goods> l_goods = new ArrayList<Goods>();

		if (route != null) {
			List<Goods> l_goodsBefore = goodsDao.getListActiveGoods();
			if (l_goodsBefore.size() != 0) {
				for (int i = 0; i < l_goodsBefore.size(); i++) {
					LatLng goodsStartLocation = new LatLng();
					goodsStartLocation.setLatitude(l_goodsBefore.get(i)
							.getPickupMarkerLatidute());
					goodsStartLocation.setLongitude((l_goodsBefore.get(i)
							.getPickupMarkerLongtitude()));

					LatLng goodsFinishLocation = new LatLng();
					goodsFinishLocation.setLatitude((l_goodsBefore.get(i)
							.getDeliveryMarkerLatidute()));
					goodsFinishLocation.setLongitude((l_goodsBefore.get(i)
							.getDeliveryMarkerLongtitude()));

					if (mapProcess.checkDistance(routeID, goodsStartLocation,
							goodsFinishLocation, Common.maxAllowDistance)) {
						l_goods.add(l_goodsBefore.get(i));
					}

				}
			}
		}
		return l_goods;
	}
}
