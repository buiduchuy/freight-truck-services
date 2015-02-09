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

import vn.edu.fpt.fts.dao.DriverDAO;
import vn.edu.fpt.fts.pojo.Driver;

/**
 * @author Huy
 *
 */
@Path("Driver")
public class DriverAPI {
	private final static String TAG = "DriverAPI";
	DriverDAO driverDao = new DriverDAO();

	@POST
	@Path("getDriverByEmail")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Driver getDriverByEmail(MultivaluedMap<String, String> params) {

		Driver driver = driverDao.getDriverByEmail(params.getFirst("email"));

		return driver;
	}

	@POST
	@Path("getDriverByID")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Driver getDriverByID(MultivaluedMap<String, String> params) {
		Driver driver;
		try {
			driver = driverDao.getDriverById(Integer.valueOf(params
					.getFirst("driverID")));
			return driver;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return null;
	}

}
