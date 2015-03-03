package vn.edu.fpt.fts.dao;

/**
 * @author Huy
 *
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.pojo.Account;

public class AccountDAO {

	private final static String TAG = "AccountDAO";

	public Account checkLoginAccount(String email, String password) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Account WHERE Email=" + "? "
					+ "AND Password=" + "?";

			stmt = con.prepareStatement(sql);

			int i = 1;
			stmt.setString(i++, email);
			stmt.setString(i++, password);
			rs = stmt.executeQuery();

			Account account = new Account();

			if (rs.next()) {
				account.setAccountID(rs.getInt("AccountID"));
				account.setEmail(rs.getString("Email"));
				account.setPassword(rs.getString("Password"));
				account.setRoleID(rs.getInt("RoleID"));
				return account;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		return null;
	}

}
