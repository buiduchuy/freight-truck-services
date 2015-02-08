/**
 * 
 */
package vn.edu.fpt.fts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.pojo.Deal;

/**
 * @author Huy
 *
 */
public class DealDAO {

	private final static String TAG = "DealDAO";

	public int insertDeal(Deal bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO Deal ( " + "Price," + "Notes,"
					+ "CreateTime," + "OrderID," + "Sender," + "RouteID,"
					+ "GoodsID," + "Active" + ") VALUES (" + "?, " + "?, "
					+ "?, " + "?, " + "?, " + "?, " + "?, " + "?)";
			stmt = con.prepareStatement(sql);
			int i = 1;

			stmt.setDouble(i++, bean.getPrice()); // Price
			stmt.setString(i++, bean.getNotes()); // Notes
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setInt(i++, bean.getOrderID()); // OrderID
			stmt.setString(i++, bean.getSender()); // Sender
			stmt.setInt(i++, bean.getRouteID()); // RouteID
			stmt.setInt(i++, bean.getGoodsID()); // GoodsID
			stmt.setInt(i++, bean.getActive()); // Active

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			ret = -1;
			System.out.println("Can't insert to Goods table");
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);

		} finally {
			try {
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
		return ret;
	}

}
