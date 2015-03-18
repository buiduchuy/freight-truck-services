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

import vn.edu.fpt.fts.dao.AccountDAO;
import vn.edu.fpt.fts.dao.DriverDAO;
import vn.edu.fpt.fts.dao.OwnerDAO;
import vn.edu.fpt.fts.dao.RoleDAO;
import vn.edu.fpt.fts.pojo.Account;
import vn.edu.fpt.fts.pojo.Driver;
import vn.edu.fpt.fts.pojo.Owner;
import vn.edu.fpt.fts.process.AccountProcess;

/**
 * @author Huy
 *
 */
@Path("Account")
public class AccountAPI {

	private final static String TAG = "AccountAPI";
	AccountProcess accountProcess = new AccountProcess();

	@POST
	@Path("Login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String checkLogin(MultivaluedMap<String, String> params) {
		AccountDAO accountDao = new AccountDAO();
		RoleDAO roleDao = new RoleDAO();
		Account account = accountDao.checkLoginAccount(
				params.getFirst("email"), params.getFirst("password"));

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
	public String checkDriverLogin(MultivaluedMap<String, String> params) {
		AccountDAO accountDao = new AccountDAO();
		DriverDAO driverDao = new DriverDAO();
		int ret = 0;

		Driver driver = new Driver();

		Account account = accountDao.checkLoginAccount(
				params.getFirst("email"), params.getFirst("password"));

		if (account != null) {
			driver = driverDao.getDriverByEmail(account.getEmail());
			ret = driver.getDriverID();
		}
		return String.valueOf(ret);
	}

	@POST
	@Path("OwnerLogin")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String checkOwnerLogin(MultivaluedMap<String, String> params) {
		AccountDAO accountDao = new AccountDAO();
		OwnerDAO ownerDao = new OwnerDAO();
		int ret = 0;

		Owner owner = new Owner();

		Account account = accountDao.checkLoginAccount(
				params.getFirst("email"), params.getFirst("password"));

		if (account != null) {
			owner = ownerDao.getOwnerByEmail(account.getEmail());
			ret = owner.getOwnerID();
		}
		return String.valueOf(ret);
	}

	@POST
	@Path("CreateOwnerAccount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String createOwnerAccount(MultivaluedMap<String, String> params) {
		int ret = 0;
		try {
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}

	@POST
	@Path("CreateDriverAccount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String createDriverAccount(MultivaluedMap<String, String> params) {
		int ret = 0;
		try {
			String email = params.getFirst("email");
			String password = params.getFirst("password");
			String firstName = params.getFirst("firstName");
			String lastName = params.getFirst("lastName");
			int gender = Integer.valueOf(params.getFirst("gender"));
			String phone = params.getFirst("phone");
			String createBy = params.getFirst("createBy");
			String createTime = params.getFirst("createTime");
			int age = Integer.valueOf(params.getFirst("age"));
			String image = params.getFirst("image");

			ret = accountProcess.createDriverAccount(email, password,
					firstName, lastName, gender, phone, createBy, createTime,
					age, image);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}
}
