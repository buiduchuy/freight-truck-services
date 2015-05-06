package vn.edu.fpt.fts.dao;

/**
 * @author Huy
 *
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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

	public int insertAccount(Account account, Connection con)
			throws SQLException {
		PreparedStatement stmt = null;
		int ret = 0;
		String sql = "INSERT INTO Account ( " + "Email," + "Password,"
				+ "RoleID" + ") VALUES (" + "?, " + "?, " + "?)";
		stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		int i = 1;
		stmt.setString(i++, account.getEmail()); // Email
		stmt.setString(i++, account.getPassword()); // Password
		stmt.setInt(i++, account.getRoleID()); // RoleID
		stmt.executeUpdate();

		ResultSet rs = stmt.getGeneratedKeys();
		if (rs != null && rs.next()) {
			ret = (int) rs.getLong(1);
		}
		return ret;
	}

	public List<Account> getAll() {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Account";
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			List<Account> list = new ArrayList<Account>();
			Account account;
			while (rs.next()) {
				account = new Account();

				account.setAccountID(rs.getInt("AccountID"));
				account.setEmail(rs.getString("Email"));
				// account.setPassword(rs.setString("Password"));
				account.setRoleID(rs.getInt("RoleID"));
				list.add(account);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Account table");
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
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
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		return null;
	}

	public Account getAccountByEmail(String s_email) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM [Account] WHERE Email=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, s_email);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Account account = new Account();
				account.setAccountID(rs.getInt("AccountID"));
				account.setEmail(rs.getString("Email"));
				// account.setPassword(rs.getString("Password"));
				account.setRoleID(rs.getInt("RoleID"));
				return account;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Account table");
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
