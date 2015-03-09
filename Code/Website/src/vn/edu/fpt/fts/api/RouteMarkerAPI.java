/**
 * 
 */
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

import vn.edu.fpt.fts.dao.RouteMarkerDAO;
import vn.edu.fpt.fts.pojo.RouteMarker;

/**
 * @author Huy
 *
 */
@Path("RouteMarker")
public class RouteMarkerAPI {
	private final static String TAG = "RouteAPI";
	RouteMarkerDAO routeMarkerDao = new RouteMarkerDAO();

	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RouteMarker> JSON() {
		List<RouteMarker> l_routeMarkers = routeMarkerDao.getAllRouteMarker();
		return l_routeMarkers;
	}

	@POST
	@Path("Create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String createRouteMarker(MultivaluedMap<String, String> goodsParams) {
		RouteMarker routeMarker = new RouteMarker();
		int ret = 0;
		try {
			routeMarker.setRouteMarkerLocation(goodsParams
					.getFirst("routeMarkerLocation"));
			routeMarker.setRouteID(Integer.valueOf(goodsParams
					.getFirst("routeId")));
			ret = routeMarkerDao.insertRouteMarker(routeMarker);

		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}
}
