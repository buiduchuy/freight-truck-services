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
import vn.edu.fpt.fts.pojo.GoodsCategory;
import vn.edu.fpt.fts.pojo.Route;
import vn.edu.fpt.fts.pojo.RouteGoodsCategory;

public class RouteDAO {

	private final static String TAG = "RouteDAO";

	public int insertRoute(Route bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO Route ( " + "StartingAddress,"
					+ "DestinationAddress," + "StartTime," + "FinishTime,"
					+ "Notes," + "Weight," + "CreateTime," + "Active,"
					+ "DriverID" + ") VALUES (" + "?, " + "?, " + "?, " + "?, "
					+ "?, " + "?, " + "?, " + "?, " + "?)";
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			stmt.setString(i++, bean.getStartingAddress()); // StartingAddress
			stmt.setString(i++, bean.getDestinationAddress()); // DestinationAddress
			stmt.setString(i++, bean.getStartTime()); // StartTime
			stmt.setString(i++, bean.getFinishTime()); // FinishTime
			stmt.setString(i++, bean.getNotes()); // Notes
			stmt.setInt(i++, bean.getWeight()); // Weight
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setInt(i++, bean.getActive()); // Active
			stmt.setInt(i++, bean.getDriverID()); // DriverID

			ret = stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = (int) rs.getLong(1);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			ret = -1;
			System.out.println("Can't insert to Route table");
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

	public List<Route> getAllRoute() {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Route";
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			List<Route> list = new ArrayList<Route>();
			RouteMarkerDAO routeMarkderDAO = new RouteMarkerDAO();
			VehicleDAO vehicleDAO = new VehicleDAO();
			Route route;
			while (rs.next()) {
				route = new Route();

				route.setRouteID(rs.getInt("RouteID"));
				route.setStartingAddress(rs.getString("StartingAddress"));
				route.setDestinationAddress(rs.getString("DestinationAddress"));
				route.setStartTime(rs.getString("StartTime"));
				route.setFinishTime(rs.getString("FinishTime"));
				route.setNotes(rs.getString("Notes"));
				route.setWeight(Integer.valueOf(rs.getString("Weight")));
				route.setCreateTime(rs.getString("CreateTime"));
				route.setActive(Integer.valueOf(rs.getString("Active")));
				route.setDriverID(Integer.valueOf(rs.getString("DriverID")));
				route.setRouteMarkers(routeMarkderDAO
						.getAllRouteMarkerByRouteID(route.getRouteID()));
				route.setVehicles(vehicleDAO.getAllVehicleByRouteID(route
						.getRouteID()));
				list.add(route);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Can't load data from Route table");
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
				System.out.println("Can't load data from Route table");
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		return null;
	}

	public Route getRouteById(int Id) {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Route where RouteID=?";
			stm = con.prepareStatement(sql);
			stm.setInt(1, Id);
			rs = stm.executeQuery();
			Route route;
			List<GoodsCategory> listGoodsCategory = new ArrayList<GoodsCategory>();
			List<RouteGoodsCategory> listRouteGoodsCategory = new ArrayList<RouteGoodsCategory>();
			
			GoodsCategory goodsCategory = new GoodsCategory();
			GoodsCategoryDAO goodsCategoryDao = new GoodsCategoryDAO();
			
			RouteGoodsCategoryDAO routeGoodsCategoryDao = new RouteGoodsCategoryDAO();
			RouteMarkerDAO routeMarkerDao = new RouteMarkerDAO();

			while (rs.next()) {
				route = new Route();

				route.setRouteID(Id);
				route.setStartingAddress(rs.getString("StartingAddress"));
				route.setDestinationAddress(rs.getString("DestinationAddress"));
				route.setStartTime(rs.getString("StartTime"));
				route.setFinishTime(rs.getString("FinishTime"));
				route.setNotes(rs.getString("Notes"));
				route.setWeight(Integer.valueOf(rs.getString("Weight")));
				route.setCreateTime(rs.getString("CreateTime"));
				route.setActive(Integer.valueOf(rs.getString("Active")));
				route.setDriverID(Integer.valueOf(rs.getString("DriverID")));
				route.setRouteMarkers(routeMarkerDao.getAllRouteMarkerByRouteID(Id));
				
				listRouteGoodsCategory = routeGoodsCategoryDao
						.getListRouteGoodsCategoryByRouteID(Id);
				
				for (int i = 0; i <= listRouteGoodsCategory.size() - 1; i++) {
					goodsCategory = goodsCategoryDao
							.getGoodsCategoryByID(listRouteGoodsCategory.get(i)
									.getGoodsCategoryID());
					listGoodsCategory.add(goodsCategory);
				}

				route.setGoodsCategory(listGoodsCategory);
				return route;
			}

		} catch (SQLException e) {
			System.out.println("Can't load data from Route table");
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
				System.out.println("Can't load data from Route table");
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		return null;
	}
}
