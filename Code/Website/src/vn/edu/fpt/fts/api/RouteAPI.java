package vn.edu.fpt.fts.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.dao.RouteGoodsCategoryDAO;
import vn.edu.fpt.fts.dao.RouteMarkerDAO;
import vn.edu.fpt.fts.pojo.Route;
import vn.edu.fpt.fts.pojo.RouteGoodsCategory;
import vn.edu.fpt.fts.pojo.RouteMarker;
import vn.edu.fpt.fts.process.MatchingProcess;
import vn.edu.fpt.fts.process.RouteProcess;

@Path("Route")
public class RouteAPI {
	private final static String TAG = "RouteAPI";
	GoodsDAO goodsDao = new GoodsDAO();
	RouteDAO routeDao = new RouteDAO();
	DealDAO dealDao = new DealDAO();
	RouteProcess routeProcess = new RouteProcess();
	MatchingProcess matchingProcess = new MatchingProcess();

	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Route> JSON() {
		List<Route> l_routes = routeDao.getAllRoute();
		return l_routes;
	}

	@POST
	@Path("Create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String createRoute(MultivaluedMap<String, String> params) {

		Route route = new Route();
		int ret = 0;

		try {

			route.setStartingAddress(params.getFirst("startingAddress"));
			route.setDestinationAddress(params.getFirst("destinationAddress"));
			route.setStartTime(params.getFirst("startTime"));
			route.setFinishTime(params.getFirst("finishTime"));
			route.setNotes(params.getFirst("notes"));
			route.setWeight(Integer.valueOf(params.getFirst("weight")));
			route.setCreateTime(Common.getCreateTime());
			route.setActive(Integer.valueOf(params.getFirst("active")));
			route.setDriverID(Integer.valueOf(params.getFirst("driverID")));
			System.out.println(route.getStartTime());
			System.out.println(route.getFinishTime());
			Route routeDB = routeDao.getLastActiveRouteByDriverID(route.getDriverID());
			
//			if (!routeProcess.checkTimeRouteOverlaps(route.getStartTime(), route.getFinishTime(), routeDB.getStartTime(), routeDB.getFinishTime())) {

				ret = routeDao.insertRoute(route);

				// ------------------------------------------------------------------
				RouteMarkerDAO routeMarkerDao = new RouteMarkerDAO();
				RouteMarker routeMarker = new RouteMarker();
				routeMarker.setRouteID(ret);

				String marker1 = params.getFirst("routeMarkerLocation1");
				String marker2 = params.getFirst("routeMarkerLocation2");

				if (!marker1.isEmpty()) {
					routeMarker.setNumbering(1);
					routeMarker.setRouteMarkerLocation(marker1);

					routeMarkerDao.insertRouteMarker(routeMarker);
				}
				if (!marker2.isEmpty()) {
					routeMarker.setNumbering(2);
					routeMarker.setRouteMarkerLocation(marker2);

					routeMarkerDao.insertRouteMarker(routeMarker);
				}
				// ------------------------------------------------------------------

				RouteGoodsCategoryDAO routeGoodsCategoryDao = new RouteGoodsCategoryDAO();
				RouteGoodsCategory routeGoodsCategory = new RouteGoodsCategory();

				routeGoodsCategory.setRouteID(ret);

				// Get params category true/false
				String goodsCategoryName = "";
				if (Boolean.parseBoolean(params.getFirst("Food"))) {
					goodsCategoryName = "Hàng thực phẩm";
					routeGoodsCategoryDao.insertRouteGoodsCategory(ret,
							goodsCategoryName);

				}
				if (Boolean.parseBoolean(params.getFirst("Freeze"))) {
					goodsCategoryName = "Hàng đông lạnh";
					routeGoodsCategoryDao.insertRouteGoodsCategory(ret,
							goodsCategoryName);

				}
				if (Boolean.parseBoolean(params.getFirst("Broken"))) {
					goodsCategoryName = "Hàng dễ vỡ";
					routeGoodsCategoryDao.insertRouteGoodsCategory(ret,
							goodsCategoryName);
				}
				if (Boolean.parseBoolean(params.getFirst("Flame"))) {
					goodsCategoryName = "Hàng dễ cháy nổ";
					routeGoodsCategoryDao.insertRouteGoodsCategory(ret,
							goodsCategoryName);
				}
//			} else {
//				ret = -1;
//			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}

	@POST
	@Path("Update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateRoute(MultivaluedMap<String, String> params) {
		int ret = 0;

		Route route = new Route();
		List<RouteMarker> listRouteMarker = new ArrayList<RouteMarker>();
		Map<String, Boolean> routeGoodsCategoryMap = new HashMap<String, Boolean>();

		try {
			// Truyền thêm routeID về
			int routeID = Integer.valueOf(params.getFirst("routeID"));

			if (goodsDao.getListGoodsInDealByRouteID(routeID).size() > 0) {
				ret = 2;
			} else {
				route.setRouteID(routeID);
				route.setStartingAddress(params.getFirst("startingAddress"));
				route.setDestinationAddress(params
						.getFirst("destinationAddress"));
				route.setStartTime(params.getFirst("startTime"));
				route.setFinishTime(params.getFirst("finishTime"));
				route.setNotes(params.getFirst("notes"));
				route.setWeight(Integer.valueOf(params.getFirst("weight")));
				route.setCreateTime(params.getFirst("createTime"));
				route.setActive(Integer.valueOf(params.getFirst("active")));
				route.setDriverID(Integer.valueOf(params.getFirst("driverID")));

				// RouteMarker
				RouteMarker routeMarker1 = new RouteMarker();
				RouteMarker routeMarker2 = new RouteMarker();

				routeMarker1.setRouteID(routeID);
				routeMarker1.setNumbering(1);
				routeMarker1.setRouteMarkerLocation(params
						.getFirst("routeMarkerLocation1"));
				listRouteMarker.add(routeMarker1);

				routeMarker2.setRouteID(routeID);
				routeMarker2.setNumbering(2);
				routeMarker2.setRouteMarkerLocation(params
						.getFirst("routeMarkerLocation2"));
				listRouteMarker.add(routeMarker2);

				// RouteGoodsCategory
				routeGoodsCategoryMap.put("Food",
						Boolean.parseBoolean(params.getFirst("Food")));
				routeGoodsCategoryMap.put("Freeze",
						Boolean.parseBoolean(params.getFirst("Freeze")));
				routeGoodsCategoryMap.put("Broken",
						Boolean.parseBoolean(params.getFirst("Broken")));
				routeGoodsCategoryMap.put("Flame",
						Boolean.parseBoolean(params.getFirst("Flame")));

				// Update
				ret = routeProcess.updateRoute(route, routeGoodsCategoryMap,
						listRouteMarker);
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}

	@POST
	@Path("deactiveRoute")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String deactiveRoute(MultivaluedMap<String, String> params) {
		int ret = 0;
		try {
			int routeID = Integer.valueOf(params.getFirst("routeID"));
			if (dealDao.getDealByRouteID(routeID).size() == 0) {
				ret = routeDao.updateRouteStatus(routeID, Common.deactivate);
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}

	@POST
	@Path("getRouteByID")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Route getRouteByID(MultivaluedMap<String, String> params) {
		Route route = new Route();
		try {
			route = routeDao.getRouteByID(Integer.valueOf(params
					.getFirst("routeID")));
			return route;
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return null;
	}

	@POST
	@Path("getListRouteByDriverID")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Route> getListRouteByDriverID(
			MultivaluedMap<String, String> params) {
		List<Route> l_route = new ArrayList<Route>();
		try {
			l_route = routeDao.getListRouteByDriverID(Integer.valueOf(params
					.getFirst("driverID")));
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return l_route;
	}

	@POST
	@Path("getSuggestionRoute")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Route> getSuggestionRoute(MultivaluedMap<String, String> params) {
		List<Route> list = new ArrayList<Route>();
		try {
			list = matchingProcess.getSuggestionRoute(Integer.valueOf(params
					.getFirst("goodsID")));
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return list;
	}

	@POST
	@Path("Delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String removeRoute(MultivaluedMap<String, String> params) {
		int ret = 0;
		try {
			int routeID = Integer.valueOf(params.getFirst("routeID"));
			if (goodsDao.getListGoodsInDealPendingOrAcceptByRouteID(routeID)
					.size() > 0) {
				ret = 2;
			} else {
				ret = routeDao.updateRouteStatus(routeID, Common.deactivate);
			}

		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}
}
