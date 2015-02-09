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
import vn.edu.fpt.fts.pojo.RouteGoodsCategory;

/**
 * @author Huy
 *
 */
public class RouteGoodsCategoryDAO {
	private final static String TAG = "RouteGoodsCategory";

	public int insertRouteGoodsCategory(RouteGoodsCategory bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO RouteGoodsCategory ( "
					+ "GoodsCategoryID" + ") VALUES (" + "?," + "?" + ")";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setInt(i++, bean.getGoodsCategoryID()); // DealID
			stmt.setInt(i++, bean.getRouteID());// RouteID

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			ret = -1;
			System.out.println("Can't insert to RouteGoodsCategory table");
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
