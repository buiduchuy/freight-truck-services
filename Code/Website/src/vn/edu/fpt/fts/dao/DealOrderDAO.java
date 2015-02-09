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
import vn.edu.fpt.fts.pojo.DealOrder;

/**
 * @author Huy
 *
 */
public class DealOrderDAO {

	private final static String TAG = "DealOrderDAO";

	public int insertDealOrder(DealOrder bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO DealOrder ( " + "DealID," + "OrderID"
					+ ") VALUES (" + "?, " + "?)";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setInt(i++, bean.getDealID()); // DealID
			stmt.setInt(i++, bean.getOrderID()); // OrderID

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			ret = -1;
			System.out.println("Can't insert to DealOrder table");
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
