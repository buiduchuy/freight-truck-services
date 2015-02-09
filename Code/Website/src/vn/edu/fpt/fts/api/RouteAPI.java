package vn.edu.fpt.fts.api;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.dao.RouteGoodsCategoryDAO;
import vn.edu.fpt.fts.dao.RouteMarkerDAO;
import vn.edu.fpt.fts.pojo.Route;
import vn.edu.fpt.fts.pojo.RouteGoodsCategory;
import vn.edu.fpt.fts.pojo.RouteMarker;

@Path("/Route")
public class RouteAPI {
	private final static String TAG = "RouteAPI";
	RouteDAO routeDao = new RouteDAO();

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

		try {

			route.setStartingAddress(params.getFirst("startingAddress"));
			route.setDestinationAddress(params.getFirst("destinationAddress"));
			route.setStartTime(params.getFirst("startTime"));
			route.setFinishTime(params.getFirst("finishTime"));
			route.setNotes(params.getFirst("notes"));
			route.setWeight(Integer.valueOf(params.getFirst("weight")));
			route.setCreateTime(params.getFirst("createTime"));
			route.setActive(Integer.valueOf(params.getFirst("active")));
			route.setDriverID(Integer.valueOf(params.getFirst("driverID")));

			int ret = routeDao.insertRoute(route);

			// ------------------------------------------------------------------
			RouteMarkerDAO routeMarkerDao = new RouteMarkerDAO();
			RouteMarker routeMarker = new RouteMarker();
			routeMarker.setRouteID(ret);
			routeMarker.setRouteMarkerLocation(params
					.getFirst("routeMarkerLocation1"));

			routeMarkerDao.insertRouteMarker(routeMarker);

			routeMarker.setRouteMarkerLocation(params
					.getFirst("routeMarkerLocation2"));

			routeMarkerDao.insertRouteMarker(routeMarker);

			// ------------------------------------------------------------------
			RouteGoodsCategoryDAO routeGoodsCategoryDao = new RouteGoodsCategoryDAO();
			RouteGoodsCategory routeGoodsCategory = new RouteGoodsCategory();

			routeGoodsCategory.setRouteID(ret);
			routeGoodsCategory.setGoodsCategoryID(Integer.valueOf(params
					.getFirst("")));

			// Get params category true/false
			if (Boolean.parseBoolean(params.getFirst("Food"))) {
				routeGoodsCategory.setGoodsCategoryID(1);
				routeGoodsCategoryDao
						.insertRouteGoodsCategory(routeGoodsCategory);
			}
			if (Boolean.parseBoolean(params.getFirst("Freeze"))) {
				routeGoodsCategory.setGoodsCategoryID(2);
				routeGoodsCategoryDao
						.insertRouteGoodsCategory(routeGoodsCategory);
			}
			if (Boolean.parseBoolean(params.getFirst("Broken"))) {
				routeGoodsCategory.setGoodsCategoryID(4);
				routeGoodsCategoryDao
						.insertRouteGoodsCategory(routeGoodsCategory);
			}
			if (Boolean.parseBoolean(params.getFirst("Flame"))) {
				routeGoodsCategory.setGoodsCategoryID(5);
				routeGoodsCategoryDao
						.insertRouteGoodsCategory(routeGoodsCategory);
			}

			if (ret <= 0) {
				return "Fail";
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}

		return "Success";
	}
}
