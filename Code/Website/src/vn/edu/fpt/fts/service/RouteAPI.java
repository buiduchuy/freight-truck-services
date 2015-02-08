package vn.edu.fpt.fts.service;

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
import vn.edu.fpt.fts.model.Route;

@Path("/Route")
public class RouteAPI {
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
	public String createRoute(MultivaluedMap<String, String> goodsParams) {

		Route route = new Route();
		try {

			route.setStartingAddress(goodsParams.getFirst("startingAddress"));
			route.setDestinationAddress(goodsParams
					.getFirst("destinationAddress"));
			route.setStartTime(goodsParams.getFirst("startTime"));
			route.setFinishTime(goodsParams.getFirst("finishTime"));
			route.setNotes(goodsParams.getFirst("notes"));
			route.setWeight(Integer.valueOf(goodsParams.getFirst("weight")));
			route.setCreateTime(goodsParams.getFirst("createTime"));
			route.setActive(Integer.valueOf(goodsParams.getFirst("active")));
			route.setDriverID(Integer.valueOf(goodsParams.getFirst("driverID")));

			int ret = routeDao.insertRoute(route);

			if (ret <= 0) {
				return "Fail";
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(RouteAPI.class.getName()).log(Level.SEVERE, null,
					e);
		}
		return "Success";
	}
}
