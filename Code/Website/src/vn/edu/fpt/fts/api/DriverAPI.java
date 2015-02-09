/**
 * 
 */
package vn.edu.fpt.fts.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import vn.edu.fpt.fts.dao.DriverDAO;
import vn.edu.fpt.fts.pojo.Driver;

/**
 * @author Huy
 *
 */
@Path("Driver")
public class DriverAPI {

	@POST
	@Path("getDriverByEmail")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Driver getDriverByEmail(MultivaluedMap<String, String> params) {
		DriverDAO driverDao = new DriverDAO();

		Driver driver = driverDao.getDriverByEmail(params.getFirst("email"));

		return driver;
	}
}
