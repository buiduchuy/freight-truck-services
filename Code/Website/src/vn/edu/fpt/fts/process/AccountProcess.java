/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.dao.AccountDAO;
import vn.edu.fpt.fts.dao.DriverDAO;
import vn.edu.fpt.fts.dao.OwnerDAO;
import vn.edu.fpt.fts.pojo.Account;
import vn.edu.fpt.fts.pojo.Driver;
import vn.edu.fpt.fts.pojo.Owner;

/**
 * @author Huy
 *
 */
public class AccountProcess {
	private final static String TAG = "AccountProcess";

	AccountDAO accountDao = new AccountDAO();
	DriverDAO driverDao = new DriverDAO();
	OwnerDAO ownerDao = new OwnerDAO();

	public int createDriverAccount(String email, String password,
			String firstName, String lastName, int gender, String phone,
			String createBy, String createTime, String dateOfBirth, String image) {
		int ret = 0;
		Connection con = null;
		try {
			con = DBAccess.makeConnection();
			con.setAutoCommit(false);

			Account account = new Account();
			account.setEmail(email);
			account.setPassword(password);
			account.setRoleID(Common.role_driver);
			accountDao.insertAccount(account, con);

			Driver driver = new Driver();
			driver.setActive(Common.deactivate);
			driver.setDateOfBirth(dateOfBirth);
			driver.setCreateBy(createBy);
			driver.setCreateTime(createTime);
			driver.setEmail(email);
			driver.setFirstName(firstName);
			driver.setGender(gender);
			driver.setImage(image);
			driver.setLastName(lastName);
			driver.setPhone(phone);
			driver.setPoint(0);
			driver.setUpdateBy(createBy);
			driver.setUpdateTime(createTime);
			ret = driverDao.insertDriver(driver, con);

			con.commit();
		} catch (SQLException e) {
			// TODO: handle exception
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		return ret;
	}

	public int createOwnerAccount(String email, String password,
			String firstName, String lastName, int gender, String phone,
			String address, String createBy, String createTime,
			String dateOfBirth) {
		int ret = 0;
		Connection con = null;
		try {
			con = DBAccess.makeConnection();
			con.setAutoCommit(false);

			Account account = new Account();
			account.setEmail(email);
			account.setPassword(password);
			account.setRoleID(Common.role_owner);
			accountDao.insertAccount(account, con);

			Owner owner = new Owner();
			owner.setActive(Common.deactivate);
			owner.setAddress(address);
			owner.setDateOfBirth(dateOfBirth);
			owner.setCreateBy(createBy);
			owner.setCreateTime(createTime);
			owner.setEmail(email);
			owner.setFirstName(firstName);
			owner.setGender(gender);
			owner.setLastName(lastName);
			owner.setPhone(phone);
			owner.setUpdateBy(createBy);
			owner.setUpdateTime(createTime);
			ret = ownerDao.insertOwner(owner, con);
			
			con.commit();
		} catch (SQLException e) {
			// TODO: handle exception
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}

		return ret;
	}
}
