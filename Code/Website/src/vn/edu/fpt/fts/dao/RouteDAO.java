package vn.edu.fpt.fts.dao;

/**
 * @author Huy
 *
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.model.Route;

public class RouteDAO {

	public int insertRoute(Route bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO Route ( " + "StartingAddress,"
					+ "RouteMarkerID," + "DestinationAddress,"
					+ "StartTime," + "FinishTime," + "Notes," + "CreateTime,"
					+ "Active," + "VehicleID," + "DriverID" + ") VALUES ("
					+ "?, " + "?, " + "?, " + "?, " + "?, " + "?, " + "?, "
					+ "?, " + "?, " + "?, " + "?)";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setString(i++, bean.getStartingAddress()); // StartingAddress
			stmt.setInt(i++, bean.getRouteMarkerID()); // Marker1
			stmt.setString(i++, bean.getDestinationAddress()); // DestinationAddress
			stmt.setString(i++, bean.getStartTime()); // StartTime
			stmt.setString(i++, bean.getFinishTime()); // FinishTime
			stmt.setString(i++, bean.getNotes()); // Notes
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setInt(i++, bean.getActive()); // Active
			stmt.setInt(i++, bean.getVehicleID()); // VehicleID
			stmt.setInt(i++, bean.getDriverID()); // DriverID

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			ret = -1;
			System.out.println("Can't insert to Route table");
			e.printStackTrace();
			Logger.getLogger(RouteDAO.class.getName()).log(Level.SEVERE, null,
					e);
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
				Logger.getLogger(RouteDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return ret;
	}

	public List<Route> getAllRoute() {
		DateFormat format = new SimpleDateFormat("dd/mm/yyyy");

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Route";
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			List<Route> list = new ArrayList<Route>();
			Route route;
			while (rs.next()) {
				route = new Route();

				route.setRouteID(rs.getInt("RouteID"));
				route.setStartingAddress(rs.getString("StartingAddress"));
				route.setRouteMarkerID(rs.getInt("RouteMarkerID"));
				route.setDestinationAddress(rs.getString("DestinationAddress"));
				route.setStartTime(rs.getTimestamp("StartTime").toString());
				route.setFinishTime(rs.getTimestamp("FinishTime").toString());
				route.setNotes(rs.getString("Notes"));
				route.setCreateTime(rs.getTimestamp("CreateTime").toString());
				route.setActive(rs.getInt("Active"));
				route.setVehicleID(rs.getInt("VehicleID"));
				route.setDriverID(rs.getInt("DriverID"));

				list.add(route);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Can't load data from Route table");
			e.printStackTrace();
			Logger.getLogger(RouteDAO.class.getName()).log(Level.SEVERE, null,
					e);
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
				System.out.println("Can't load data from Route table");
				Logger.getLogger(RouteDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return null;
	}
}
