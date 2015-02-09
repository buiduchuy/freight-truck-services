/**
 * 
 */
package vn.edu.fpt.fts.api;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
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

	@POST
	@Path("Create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String createRouteMarker(MultivaluedMap<String, String> goodsParams) {
		RouteMarkerDAO routeMarkerDao = new RouteMarkerDAO();
		RouteMarker routeMarker = new RouteMarker();
		try {
			routeMarker.setRouteMarkerLocation(goodsParams
					.getFirst("routeMarkerLocation"));
			routeMarker.setRouteID(Integer.valueOf(goodsParams
					.getFirst("routeId")));
			int ret = routeMarkerDao.insertRouteMarker(routeMarker);
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
