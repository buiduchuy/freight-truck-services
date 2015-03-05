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
import vn.edu.fpt.fts.pojo.RouteGoodsCategory;

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

			// Condition Goods Category
			List<Route> l_routeOrigin = routeDao
					.getListActiveRouteNotByCategoryID(goods
							.getGoodsCategoryID());
			if (l_routeOrigin == null) {
				l_routeOrigin = routeDao.getListActiveRoute();
			}

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
						System.out.println(routeStartDate.getTime() + " <= "
								+ pickupDate.getTime() + " <= "
								+ deliveryDate.getTime() + " <= "
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
		List<Goods> l_goodsFilter1 = new ArrayList<Goods>();

		if (route != null) {

			List<Goods> l_goodsBefore = goodsDao.getListActiveGoodsNotByRoute(routeID);

			// Condition Goods Category
//			List<RouteGoodsCategory> l_routeGoodsCategory = route
//					.getRouteGoodsCategory();

			// List<Goods> l_dbGoods = goodsDao.getListActiveGoods();
			//
			// for (int i = 0; i < l_dbGoods.size(); i++) {
			// int goodsCategoryID = l_dbGoods.get(i).getGoodsCategoryID();
			// for (int j = 0; j < l_routeGoodsCategory.size(); i++) {
			// if (goodsCategoryID != l_routeGoodsCategory.get(j)
			// .getGoodsCategoryID()) {
			// l_goodsBefore.add(l_dbGoods.get(i));
			// }
			// }
			//
			// }

//			if (l_routeGoodsCategory.size() == 0) {
//				l_goodsBefore = goodsDao.getListActiveGoods();
//			} else {
//				List<Goods> l_goodsTemp = new ArrayList<Goods>();
//				for (int i = 0; i < l_routeGoodsCategory.size(); i++) {
//					l_goodsTemp = goodsDao
//							.getListActiveGoodsNotByCategoryID(l_routeGoodsCategory
//									.get(i).getGoodsCategoryID());
//					l_goodsBefore.containsAll(l_goodsTemp);
//				}
//			}

			// Condition datetime
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date startingDate = sdf.parse(route.getStartTime());
				Date finishDate = sdf.parse(route.getFinishTime());
				for (int i = 0; i < l_goodsBefore.size(); i++) {
					Date goodsPickup = sdf.parse(l_goodsBefore.get(i)
							.getPickupTime());
					Date goodsDelivery = sdf.parse(l_goodsBefore.get(i)
							.getDeliveryTime());
					if (goodsPickup.compareTo(startingDate) >= 0
							&& goodsDelivery.compareTo(finishDate) <= 0) {
						l_goodsFilter1.add(l_goodsBefore.get(i));
						System.out.println(startingDate.getTime() + " <= "
								+ goodsPickup.getTime() + " <= "
								+ goodsDelivery.getTime() + " <= "
								+ finishDate.getTime());
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (l_goodsFilter1.size() != 0) {
				for (int i = 0; i < l_goodsFilter1.size(); i++) {
					LatLng goodsStartLocation = new LatLng();
					goodsStartLocation.setLatitude(l_goodsFilter1.get(i)
							.getPickupMarkerLatidute());
					goodsStartLocation.setLongitude((l_goodsFilter1.get(i)
							.getPickupMarkerLongtitude()));

					LatLng goodsFinishLocation = new LatLng();
					goodsFinishLocation.setLatitude((l_goodsFilter1.get(i)
							.getDeliveryMarkerLatidute()));
					goodsFinishLocation.setLongitude((l_goodsFilter1.get(i)
							.getDeliveryMarkerLongtitude()));

					if (mapProcess.checkDistance(routeID, goodsStartLocation,
							goodsFinishLocation, Common.maxAllowDistance)) {
						l_goods.add(l_goodsFilter1.get(i));
					}
				}
			}
		}
		return l_goods;
	}
}
