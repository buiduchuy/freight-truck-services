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

import vn.edu.fpt.fts.dao.OwnerDAO;
import vn.edu.fpt.fts.pojo.Owner;

/**
 * @author Huy
 *
 */
@Path("Owner")
public class OwnerAPI {
	private final static String TAG = "OwnerAPI";
	OwnerDAO ownerDao = new OwnerDAO();

	@POST
	@Path("getOwnerByEmail")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Owner getDriverByEmail(MultivaluedMap<String, String> params) {

		Owner owner = ownerDao.getOwnerByEmail(params.getFirst("email"));

		return owner;
	}

	@POST
	@Path("getOwnerByID")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Owner getDriverByID(MultivaluedMap<String, String> params) {
		Owner owner;
		try {
			owner = ownerDao.getOwnerById(Integer.valueOf(params
					.getFirst("ownerID")));
			return owner;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return null;
	}

}
