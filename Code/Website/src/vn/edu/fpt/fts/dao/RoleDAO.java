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

public class RoleDAO {

	public String getRoleNameById(int accountId) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT RoleName FROM Role WHERE RoleID=?";

			stm = con.prepareStatement(sql);

			stm.setInt(1, accountId);

			rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getString("RoleName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Role table");
			Logger.getLogger(RoleDAO.class.getName())
					.log(Level.SEVERE, null, e);
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
				Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return null;
	}
}
