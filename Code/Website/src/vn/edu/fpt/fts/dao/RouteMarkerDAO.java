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
import vn.edu.fpt.fts.model.Route;
import vn.edu.fpt.fts.model.RouteMarker;

/**
 * @author Huy
 *
 */
public class RouteMarkerDAO {

	public List<RouteMarker> getAllRouteMarkerByRouteID(int routeID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM RouteMarker WHERE RouteID=?";
			stm = con.prepareStatement(sql);

			stm.setInt(1, routeID);

			rs = stm.executeQuery();
			List<RouteMarker> list = new ArrayList<RouteMarker>();
			RouteMarker routeMarker;
			while (rs.next()) {
				routeMarker = new RouteMarker();

				routeMarker.setRouteMarkerID(rs.getInt("RouteMarkerID"));
				routeMarker.setRouteID(rs.getInt("RouteID"));
				routeMarker.setRouteMarkerLocation(rs
						.getString("RouteMarkerLocation"));

				list.add(routeMarker);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Can't load data from RouteMarker table");
			e.printStackTrace();
			Logger.getLogger(RouteMarkerDAO.class.getName()).log(Level.SEVERE,
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
				System.out.println("Can't load data from RouteMarker table");
				Logger.getLogger(RouteMarkerDAO.class.getName()).log(
						Level.SEVERE, null, e);
			}
		}
		return null;
	}
}
