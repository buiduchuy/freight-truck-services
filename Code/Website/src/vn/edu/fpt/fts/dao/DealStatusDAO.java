/**
 * 
 */
package vn.edu.fpt.fts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.pojo.DealStatus;

/**
 * @author Huy
 *
 */
public class DealStatusDAO {
	private final static String TAG = "DealStatusDAO";

	public DealStatus getDealStatusByID(int dealId) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM DealStatus WHERE DealStatusID=?";
			stm = con.prepareStatement(sql);

			stm.setInt(1, dealId);

			rs = stm.executeQuery();

			DealStatus dealStatus;
			while (rs.next()) {
				dealStatus = new DealStatus();

				dealStatus.setDealStatusID(dealId);
				dealStatus.setDealStatusName(rs.getString("DealStatusName"));

				return dealStatus;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from DealStatus table");
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

}
