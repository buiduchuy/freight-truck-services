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

import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.Route;

/**
 * @author Huy
 *
 */
@Path("Deal")
public class DealAPI {

	private final static String TAG = "DealAPI";
	DealDAO dealDao = new DealDAO();

	@POST
	@Path("getDealByRouteID")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deal> getDealByRouteID(MultivaluedMap<String, String> params) {
		List<Deal> l_deals = dealDao.getDealByRouteID(Integer.valueOf(params
				.getFirst("routeID")));
		return l_deals;
	}

	@GET
	@Path("getDealByGoodsID")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deal> getDealByGoodsID(MultivaluedMap<String, String> params) {
		List<Deal> l_deals = dealDao.getDealByGoodsID(Integer.valueOf(params
				.getFirst("gouteID")));
		return l_deals;
	}

	@POST
	@Path("Create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String createRoute(MultivaluedMap<String, String> params) {

		Route route = new Route();
		try {

			// route.setStartingAddress(goodsParams.getFirst("startingAddress"));
			// route.setDestinationAddress(goodsParams
			// .getFirst("destinationAddress"));
			// route.setStartTime(goodsParams.getFirst("startTime"));
			// route.setFinishTime(goodsParams.getFirst("finishTime"));
			// route.setNotes(goodsParams.getFirst("notes"));
			// route.setWeight(Integer.valueOf(goodsParams.getFirst("weight")));
			// route.setCreateTime(goodsParams.getFirst("createTime"));
			// route.setActive(Integer.valueOf(goodsParams.getFirst("active")));
			// route.setDriverID(Integer.valueOf(goodsParams.getFirst("driverID")));
			//
			// int ret = routeDao.insertRoute(route);
			//
			// if (ret <= 0) {
			// return "Fail";
			// }
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return "Success";
	}
}
