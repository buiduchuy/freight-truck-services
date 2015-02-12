/**
 * 
 */
package vn.edu.fpt.fts.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import vn.edu.fpt.fts.process.StepRoute;

/**
 * @author Huy
 *
 */
@Path("GetRouteFromGMaps")
public class GetRouteFromGMapsAPI {

	@POST
	@Path("send")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getRouteFromGmaps(MultivaluedMap<String, String> params) {
		StepRoute stepRoute = new StepRoute();

		List<StepRoute> listStepRoute = new ArrayList<StepRoute>();
		
		
		
		return "";
	}

}
