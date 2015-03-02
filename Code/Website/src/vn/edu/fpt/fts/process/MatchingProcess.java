/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		List<Route> l_routesFilter1 = new ArrayList<Route>();
		Goods goods = goodsDao.getActiveGoodsByID(goodsID);

		if (goods != null) {
			goodsFinishLocation.setLatitude(goods.getDeliveryMarkerLatidute());
			goodsFinishLocation.setLongitude(goods
					.getDeliveryMarkerLongtitude());
			goodsStartLocation.setLatitude(goods.getPickupMarkerLatidute());
			goodsStartLocation.setLongitude(goods.getPickupMarkerLongtitude());

			List<Route> l_routeOrigin = routeDao.getListActiveRoute();

			// Condition datetime
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date pickupDate = sdf.parse(goods.getPickupTime().toString());
				Date deliveryDate = sdf.parse(goods.getDeliveryTime()
						.toString());
				for (int i = 0; i < l_routeOrigin.size(); i++) {
					Date routeStartDate = sdf.parse(l_routeOrigin.get(i)
							.getStartTime());
					Date routeFinishDate = sdf.parse(l_routeOrigin.get(i)
							.getFinishTime());
					if (pickupDate.compareTo(routeStartDate) >= 0
							&& deliveryDate.compareTo(routeFinishDate) <= 0) {
						l_routesFilter1.add(l_routeOrigin.get(i));
						System.out.println(routeStartDate.getTime() + " <= " + pickupDate.getTime()
								+ " <= " + deliveryDate.getTime() + " <= "
								+ routeFinishDate.getTime());
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Condition route
			if (l_routesFilter1.size() != 0) {
				for (int i = 0; i < l_routesFilter1.size(); i++) {

					if (mapProcess.checkDistance(l_routesFilter1.get(i)
							.getRouteID(), goodsStartLocation,
							goodsFinishLocation, Common.maxAllowDistance)) {
						l_routes.add(l_routesFilter1.get(i));
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
