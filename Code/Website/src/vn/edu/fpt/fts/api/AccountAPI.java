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

import vn.edu.fpt.fts.dao.AccountDAO;
import vn.edu.fpt.fts.dao.DriverDAO;
import vn.edu.fpt.fts.dao.OwnerDAO;
import vn.edu.fpt.fts.dao.RoleDAO;
import vn.edu.fpt.fts.pojo.Account;
import vn.edu.fpt.fts.pojo.Driver;
import vn.edu.fpt.fts.pojo.Owner;

/**
 * @author Huy
 *
 */
@Path("/Account")
public class AccountAPI {

	// private final static String TAG = "AccountAPI";

	@POST
	@Path("Login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String checkLogin(MultivaluedMap<String, String> goodsParams) {
		AccountDAO accountDao = new AccountDAO();
		RoleDAO roleDao = new RoleDAO();
		Account account = accountDao
				.checkLoginAccount(goodsParams.getFirst("email"),
						goodsParams.getFirst("password"));

		if (account != null) {
			String roleName = roleDao.getRoleNameById(account.getAccountID());
			return roleName;
		}
		return null;
	}

	@POST
	@Path("DriverLogin")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public int checkDriverLogin(MultivaluedMap<String, String> goodsParams) {
		AccountDAO accountDao = new AccountDAO();
		DriverDAO driverDao = new DriverDAO();

		Driver driver = new Driver();

		Account account = accountDao
				.checkLoginAccount(goodsParams.getFirst("email"),
						goodsParams.getFirst("password"));

		if (account != null) {
			driver = driverDao.getDriverByEmail(account.getEmail());
			return driver.getDriverID();
		}
		return 0;
	}

	@POST
	@Path("OwnerLogin")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public int checkOwnerLogin(MultivaluedMap<String, String> goodsParams) {
		AccountDAO accountDao = new AccountDAO();
		OwnerDAO ownerDao = new OwnerDAO();

		Owner owner = new Owner();

		Account account = accountDao
				.checkLoginAccount(goodsParams.getFirst("email"),
						goodsParams.getFirst("password"));

		if (account != null) {
			owner = ownerDao.getOwnerByEmail(account);
			return owner.getOwnerID();
		}
		return 0;
	}

}
