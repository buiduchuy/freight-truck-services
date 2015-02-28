/**
 * 
 */
package vn.edu.fpt.fts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

	public int insertRouteGoodsCategory(int routeID, String goodsCategoryName) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO RouteGoodsCategory ( "
					+ "RouteID,"
					+ "GoodsCategoryID"
					+ ") VALUES ("
					+ routeID
					+ ", (SELECT GoodsCategoryID FROM GoodsCategory WHERE Name = N'"
					+ goodsCategoryName + "'))";
			stmt = con.prepareStatement(sql);
			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
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

	public List<RouteGoodsCategory> getListRouteGoodsCategoryByRouteID(
			int routeID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM RouteGoodsCategory where RouteID=?";
			stm = con.prepareStatement(sql);
			stm.setInt(1, routeID);
			rs = stm.executeQuery();

			List<RouteGoodsCategory> list = new ArrayList<RouteGoodsCategory>();
			RouteGoodsCategory routeGoodsCategory;

			while (rs.next()) {
				routeGoodsCategory = new RouteGoodsCategory();

				routeGoodsCategory.setRouteID(rs.getInt("RouteID"));
				routeGoodsCategory.setGoodsCategoryID(rs
						.getInt("GoodsCategoryID"));

				list.add(routeGoodsCategory);

			}
			return list;

		} catch (SQLException e) {
			System.out.println("Can't load data from RouteGoodsCategory table");
			e.printStackTrace();
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

	public int deleteRouteGoodsCategory(int routeID, String goodsCategoryName) {
		Connection con = null;
		PreparedStatement stm = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();
			String sql = "DELETE FROM RouteGoodsCategory "
					+ " WHERE RouteID = '"
					+ routeID
					+ "' AND GoodsCategoryID = "
					+ "(SELECT GoodsCategoryID FROM GoodsCategory WHERE Name = N'"
					+ goodsCategoryName + "')";
			stm = con.prepareStatement(sql);
			ret = stm.executeUpdate();
		} catch (SQLException e) {
			System.out
					.println("Can't delete data from RouteGoodsCategory table");
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		} finally {
			try {
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
		return ret;
	}

}
