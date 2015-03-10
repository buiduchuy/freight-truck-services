/**
 * 
 */
package vn.edu.fpt.fts.dao;

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
import vn.edu.fpt.fts.pojo.RouteMarker;

/**
 * @author Huy
 *
 */
public class RouteMarkerDAO {
	private final static String TAG = "RouteMarkerDAO";

	public List<RouteMarker> getAllRouteMarkerByRouteID(int routeID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<RouteMarker> list = new ArrayList<RouteMarker>();

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM RouteMarker WHERE RouteID=?";
			stm = con.prepareStatement(sql);

			stm.setInt(1, routeID);

			rs = stm.executeQuery();
			RouteMarker routeMarker;
			while (rs.next()) {
				routeMarker = new RouteMarker();

				routeMarker.setRouteMarkerID(rs.getInt("RouteMarkerID"));
				routeMarker.setRouteID(rs.getInt("RouteID"));
				routeMarker.setRouteMarkerLocation(rs
						.getString("RouteMarkerLocation"));
				routeMarker.setNumbering(rs.getInt("Numbering"));

				list.add(routeMarker);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from RouteMarker table");
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
		return list;
	}

	public List<RouteMarker> getAllRouteMarker() {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<RouteMarker> list = new ArrayList<RouteMarker>();

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM RouteMarker";
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			RouteMarker routeMarker;
			while (rs.next()) {
				routeMarker = new RouteMarker();

				routeMarker.setRouteMarkerID(rs.getInt("RouteMarkerID"));
				routeMarker.setRouteID(rs.getInt("RouteID"));
				routeMarker.setRouteMarkerLocation(rs
						.getString("RouteMarkerLocation"));
				routeMarker.setNumbering(rs.getInt("Numbering"));

				list.add(routeMarker);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from RouteMarker table");
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
		return list;
	}

	public int insertRouteMarker(RouteMarker bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO RouteMarker ( " + "RouteMarkerLocation,"
					+ "RouteID," + "Numbering" + ") VALUES (" + "?, " + "?, "
					+ "?)";
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			stmt.setString(i++, bean.getRouteMarkerLocation()); // RouteMarkerLocation
			stmt.setInt(i++, bean.getRouteID()); // RouteID
			stmt.setInt(i++, bean.getNumbering()); // Numbering

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = (int) rs.getLong(1);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Can't insert to RouteMarker table");
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

	public int updateRouteMarkerByNumeringAndRouteID(RouteMarker routeMarker) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE RouteMarker SET " + " RouteMarkerLocation = ?"
					+ " WHERE Numbering = '" + routeMarker.getNumbering()
					+ "' AND RouteID = '" + routeMarker.getRouteID() + "'";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setString(i++, routeMarker.getRouteMarkerLocation()); // RouteMarkerLocation

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out
					.println("Can't update RouteMarkerLocation to RouteMarker table");
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

	public int deleteRouteMarkerByRouteID(int routeID) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();
			String sql = "DELETE FROM RouteMarker " + " WHERE RouteID = '"
					+ routeID + "' ";
			stmt = con.prepareStatement(sql);

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out
					.println("Can't delete RouteMarker record form RouteMarker table");
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
