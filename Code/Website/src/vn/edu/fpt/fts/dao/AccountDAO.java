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
import vn.edu.fpt.fts.model.Account;

public class AccountDAO {
	public Account checkLoginAccount(String email, String password) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Account WHERE Email=? AND Password=?";

			stm = con.prepareStatement(sql);

			stm.setString(1, email);
			stm.setString(2, password);

			rs = stm.executeQuery();
			Account account = new Account();
			account.setAccountID(rs.getInt("AccountID"));
			account.setEmail(rs.getString("Email"));
			account.setPassword(rs.getString("Password"));
			account.setRoleID(rs.getInt("RoleID"));

			if (rs.next()) {
				return account;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE,
					null, e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stm != null) {
					stm.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return null;
	}

}
