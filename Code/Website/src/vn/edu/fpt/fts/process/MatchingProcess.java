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

	public boolean checkTimeOverlaps(Date startDate1, Date endDate1,
			Date startDate2, Date endDate2) {
		if (startDate1 == null || endDate1 == null || startDate2 == null
				|| endDate2 == null)
			return false;
		if ((startDate1.getTime() <= endDate2.getTime())
				&& (startDate2.getTime() <= endDate1.getTime()))
			return true;
		return false;
	}

	public List<Route> getSuggestionRoute(int goodsID) {

		LatLng goodsStartLocation = new LatLng();
		LatLng goodsFinishLocation = new LatLng();
		List<Route> l_routes = new ArrayList<Route>();
		List<Route> l_routeFilter1 = new ArrayList<Route>();
		List<Route> l_routeOrigin1 = new ArrayList<Route>();
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

			// Condition Weight
			int goodsWeight = goods.getWeight();
			for (int i = 0; i < l_routeOrigin.size(); i++) {
				int totalWeight = goodsDao
						.getTotalWeightByRouteID(l_routeOrigin.get(i)
								.getRouteID());
				int routeWeight = l_routeOrigin.get(i).getWeight();
				if (goodsWeight <= (routeWeight - totalWeight)) {
					l_routeOrigin1.add(l_routeOrigin.get(i));
					System.out.println("Goods Weight: " + goodsWeight
							+ " -- Route Weight:" + routeWeight
							+ " -- Remaining Weight: "
							+ (routeWeight - totalWeight));
				}
			}

			// Condition datetime
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date pickupDate = sdf.parse(goods.getPickupTime().toString());
				Date deliveryDate = sdf.parse(goods.getDeliveryTime()
						.toString());
				for (int i = 0; i < l_routeOrigin1.size(); i++) {
					Date routeStartDate = sdf.parse(l_routeOrigin1.get(i)
							.getStartTime());
					Date routeFinishDate = sdf.parse(l_routeOrigin1.get(i)
							.getFinishTime());
					if (checkTimeOverlaps(pickupDate, deliveryDate,
							routeStartDate, routeFinishDate)) {
						l_routeFilter1.add(l_routeOrigin1.get(i));
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

			// Remove list route in deal with status pending or accept
			List<Route> listRouteInDeal = routeDao
					.getListRouteByDealPendingOrAcceptAndGoodsID(goodsID);
			List<Route> l_routeFilter2 = new ArrayList<Route>();
			for (Route route : l_routeFilter1) {
				boolean isRouteInDeal = false;
				for (Route routeInDeal : listRouteInDeal) {
					if (route.getRouteID() == routeInDeal.getRouteID()) {
						isRouteInDeal = true;
					}
				}
				if (!isRouteInDeal) {
					l_routeFilter2.add(route);
				}
			}

			// Condition route
			if (l_routeFilter2.size() != 0) {
				for (int i = 0; i < l_routeFilter2.size(); i++) {
					if (mapProcess.checkDistance(l_routeFilter2.get(i)
							.getRouteID(), goodsStartLocation,
							goodsFinishLocation, Common.maxAllowDistance)) {
						l_routes.add(l_routeFilter2.get(i));
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
		List<Goods> l_goodsBefore1 = new ArrayList<Goods>();
		if (route != null) {

			// Condition Goods Category
			List<Goods> l_goodsBefore = goodsDao
					.getListActiveGoodsNotByRoute(routeID);

			// Condition Weight
			int totalWeight = goodsDao.getTotalWeightByRouteID(routeID);
			int routeWeight = route.getWeight();
			for (int i = 0; i < l_goodsBefore.size(); i++) {
				int goodsWeight = l_goodsBefore.get(i).getWeight();
				if (goodsWeight <= (routeWeight - totalWeight)) {
					l_goodsBefore1.add(l_goodsBefore.get(i));
					System.out.println("Goods Weight: " + goodsWeight
							+ " -- Route Weight:" + routeWeight
							+ " -- Remaining Weight: "
							+ (routeWeight - totalWeight));
				}
			}

			// Condition datetime
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date startingDate = sdf.parse(route.getStartTime());
				Date finishDate = sdf.parse(route.getFinishTime());
				for (int i = 0; i < l_goodsBefore1.size(); i++) {
					Date goodsPickup = sdf.parse(l_goodsBefore1.get(i)
							.getPickupTime());
					Date goodsDelivery = sdf.parse(l_goodsBefore1.get(i)
							.getDeliveryTime());
					if (checkTimeOverlaps(startingDate, finishDate,
							goodsPickup, goodsDelivery)) {
						l_goodsFilter1.add(l_goodsBefore1.get(i));
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

			// Remove list goods in deal with status pending or accept
			List<Goods> l_goodsFilter2 = new ArrayList<Goods>();
			List<Goods> listGoodsInDeal = goodsDao
					.getListGoodsByDealPendingOrAcceptAndRouteID(routeID);
			for (Goods goods : l_goodsFilter1) {
				boolean isGoodsInDeal = false;
				for (Goods goodsInDeal : listGoodsInDeal) {
					if (goods.getGoodsID() == goodsInDeal.getGoodsID()) {
						isGoodsInDeal = true;
					}
				}
				if (!isGoodsInDeal) {
					l_goodsFilter2.add(goods);
				}
			}

			// Condition route
			if (l_goodsFilter2.size() != 0) {
				for (int i = 0; i < l_goodsFilter2.size(); i++) {
					LatLng goodsStartLocation = new LatLng();
					goodsStartLocation.setLatitude(l_goodsFilter2.get(i)
							.getPickupMarkerLatidute());
					goodsStartLocation.setLongitude((l_goodsFilter2.get(i)
							.getPickupMarkerLongtitude()));

					LatLng goodsFinishLocation = new LatLng();
					goodsFinishLocation.setLatitude((l_goodsFilter2.get(i)
							.getDeliveryMarkerLatidute()));
					goodsFinishLocation.setLongitude((l_goodsFilter2.get(i)
							.getDeliveryMarkerLongtitude()));

					if (mapProcess.checkDistance(routeID, goodsStartLocation,
							goodsFinishLocation, Common.maxAllowDistance)) {
						l_goods.add(l_goodsFilter2.get(i));
					}
				}
			}
		}
		return l_goods;
	}
}
