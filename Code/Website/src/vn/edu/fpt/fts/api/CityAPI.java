/**
 * 
 */
package vn.edu.fpt.fts.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import vn.edu.fpt.fts.dao.CityDAO;
import vn.edu.fpt.fts.pojo.City;

/**
 * @author Huy
 *
 */
@Path("City")
public class CityAPI {
	// private final static String TAG = "CityAPI";
	CityDAO cityDao = new CityDAO();

	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<City> getAllCity() {
		List<City> l_city = cityDao.getAllCity();
		return l_city;
	}
}
