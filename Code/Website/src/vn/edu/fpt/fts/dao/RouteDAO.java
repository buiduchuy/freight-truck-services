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

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.pojo.GoodsCategory;
import vn.edu.fpt.fts.pojo.Route;
import vn.edu.fpt.fts.pojo.RouteGoodsCategory;

public class RouteDAO {

	private final static String TAG = "RouteDAO";

	DriverDAO driverDao = new DriverDAO();
	VehicleDAO vehicleDAO = new VehicleDAO();
	RouteMarkerDAO routeMarkerDao = new RouteMarkerDAO();
	GoodsCategoryDAO goodsCategoryDao = new GoodsCategoryDAO();
	RouteGoodsCategoryDAO routeGoodsCategoryDao = new RouteGoodsCategoryDAO();

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
			e.printStackTrace();
			System.out.println("Can't insert to Route table");
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
			DriverDAO driverDao = new DriverDAO();
			Route route;
			while (rs.next()) {
				route = new Route();

				route.setRouteID(rs.getInt("RouteID"));
				route.setStartingAddress(rs.getString("StartingAddress"));
				route.setDestinationAddress(rs.getString("DestinationAddress"));
				route.setStartTime(rs.getString("StartTime"));
				route.setFinishTime(rs.getString("FinishTime"));
				route.setNotes(rs.getString("Notes"));
				route.setWeight(rs.getInt("Weight"));
				route.setCreateTime(rs.getString("CreateTime"));
				route.setActive(rs.getInt("Active"));

				route.setDriverID(rs.getInt("DriverID"));
				route.setDriver(driverDao.getDriverById(rs.getInt("DriverID")));

				route.setRouteMarkers(routeMarkderDAO
						.getAllRouteMarkerByRouteID(route.getRouteID()));
				route.setVehicles(vehicleDAO.getAllVehicleByRouteID(route
						.getRouteID()));
				list.add(route);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Route table");
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

	public Route getRouteByID(int routeID) {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Route where RouteID=?";
			stm = con.prepareStatement(sql);
			int i = 1;
			stm.setInt(i++, routeID);
			rs = stm.executeQuery();
			Route route;
			List<GoodsCategory> listGoodsCategory = new ArrayList<GoodsCategory>();
			List<RouteGoodsCategory> listRouteGoodsCategory = new ArrayList<RouteGoodsCategory>();

			GoodsCategory goodsCategory = new GoodsCategory();

			while (rs.next()) {
				route = new Route();

				route.setRouteID(routeID);
				route.setStartingAddress(rs.getString("StartingAddress"));
				route.setDestinationAddress(rs.getString("DestinationAddress"));
				route.setStartTime(rs.getString("StartTime"));
				route.setFinishTime(rs.getString("FinishTime"));
				route.setNotes(rs.getString("Notes"));
				route.setWeight(rs.getInt("Weight"));
				route.setCreateTime(rs.getString("CreateTime"));
				route.setActive(rs.getInt("Active"));

				route.setDriverID(rs.getInt("DriverID"));
				route.setDriver(driverDao.getDriverById(rs.getInt("DriverID")));

				route.setRouteMarkers(routeMarkerDao
						.getAllRouteMarkerByRouteID(routeID));

				listRouteGoodsCategory = routeGoodsCategoryDao
						.getListRouteGoodsCategoryByRouteID(routeID);

				for (int j = 0; j < listRouteGoodsCategory.size(); j++) {
					goodsCategory = goodsCategoryDao
							.getGoodsCategoryByID(listRouteGoodsCategory.get(j)
									.getGoodsCategoryID());
					listGoodsCategory.add(goodsCategory);
				}

				route.setGoodsCategory(listGoodsCategory);
				return route;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Route table");
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

	public int updateRoute(Route bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE Route SET " + " StartingAddress = ?,"
					+ " DestinationAddress = ?," + " StartTime = ?,"
					+ " FinishTime = ?," + " Notes = ?," + " Weight = ?,"
					+ " CreateTime = ?," + " Active = ?," + " DriverID = ? "
					+ " WHERE RouteID = '" + bean.getRouteID() + "' ";
			stmt = con.prepareStatement(sql);
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

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't load data from Route table");
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

	public int updateRouteStatus(int routeID, int status) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE Route SET " + " Active = ?"
					+ " WHERE RouteID = '" + routeID + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setInt(i++, status); // Active

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't update data from Route table");
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

	public List<Route> getListActiveRoute() {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Route WHERE Active=?";
			stm = con.prepareStatement(sql);
			int i = 1;
			stm.setInt(i++, Common.activate);
			rs = stm.executeQuery();

			List<GoodsCategory> listGoodsCategory = new ArrayList<GoodsCategory>();
			List<RouteGoodsCategory> listRouteGoodsCategory = new ArrayList<RouteGoodsCategory>();
			List<Route> list = new ArrayList<Route>();
			GoodsCategory goodsCategory = new GoodsCategory();
			Route route;
			while (rs.next()) {
				route = new Route();

				route.setRouteID(rs.getInt("RouteID"));
				route.setStartingAddress(rs.getString("StartingAddress"));
				route.setDestinationAddress(rs.getString("DestinationAddress"));
				route.setStartTime(rs.getString("StartTime"));
				route.setFinishTime(rs.getString("FinishTime"));
				route.setNotes(rs.getString("Notes"));
				route.setWeight(rs.getInt("Weight"));
				route.setCreateTime(rs.getString("CreateTime"));
				route.setActive(rs.getInt("Active"));
				route.setDriverID(rs.getInt("DriverID"));

				route.setRouteMarkers(routeMarkerDao
						.getAllRouteMarkerByRouteID(route.getRouteID()));

				route.setVehicles(vehicleDAO.getAllVehicleByRouteID(route
						.getRouteID()));

				listRouteGoodsCategory = routeGoodsCategoryDao
						.getListRouteGoodsCategoryByRouteID(rs
								.getInt("RouteID"));

				for (int j = 0; j < listRouteGoodsCategory.size(); j++) {
					goodsCategory = goodsCategoryDao
							.getGoodsCategoryByID(listRouteGoodsCategory.get(j)
									.getGoodsCategoryID());
					listGoodsCategory.add(goodsCategory);
				}

				route.setGoodsCategory(listGoodsCategory);

				list.add(route);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Route table");
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

	public Route getActiveRouteByID(int routeID) {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Route where RouteID=? AND Active=?";

			stm = con.prepareStatement(sql);
			int i = 1;
			stm.setInt(i++, routeID);
			stm.setInt(i++, Common.activate);

			rs = stm.executeQuery();
			Route route;
			List<GoodsCategory> listGoodsCategory = new ArrayList<GoodsCategory>();
			List<RouteGoodsCategory> listRouteGoodsCategory = new ArrayList<RouteGoodsCategory>();

			GoodsCategory goodsCategory = new GoodsCategory();

			while (rs.next()) {
				route = new Route();

				route.setRouteID(routeID);
				route.setStartingAddress(rs.getString("StartingAddress"));
				route.setDestinationAddress(rs.getString("DestinationAddress"));
				route.setStartTime(rs.getString("StartTime"));
				route.setFinishTime(rs.getString("FinishTime"));
				route.setNotes(rs.getString("Notes"));
				route.setWeight(rs.getInt("Weight"));
				route.setCreateTime(rs.getString("CreateTime"));
				route.setActive(rs.getInt("Active"));

				route.setDriverID(rs.getInt("DriverID"));
				route.setDriver(driverDao.getDriverById(rs.getInt("DriverID")));

				route.setRouteMarkers(routeMarkerDao
						.getAllRouteMarkerByRouteID(routeID));

				listRouteGoodsCategory = routeGoodsCategoryDao
						.getListRouteGoodsCategoryByRouteID(routeID);
				route.setRouteGoodsCategory(listRouteGoodsCategory);

				for (int j = 0; j < listRouteGoodsCategory.size(); j++) {
					goodsCategory = goodsCategoryDao
							.getGoodsCategoryByID(listRouteGoodsCategory.get(j)
									.getGoodsCategoryID());
					listGoodsCategory.add(goodsCategory);
				}

				route.setGoodsCategory(listGoodsCategory);
				return route;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Route table");
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

	public List<Route> getListActiveRouteNotByCategoryID(int goodsCategoryID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM [Route] WHERE Active = 1 AND RouteID NOT IN (SELECT RouteID FROM RouteGoodsCategory WHERE GoodsCategoryID = '"
					+ goodsCategoryID + "')";
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			List<Route> list = new ArrayList<Route>();
			RouteMarkerDAO routeMarkderDAO = new RouteMarkerDAO();
			VehicleDAO vehicleDAO = new VehicleDAO();
			DriverDAO driverDao = new DriverDAO();
			Route route;
			while (rs.next()) {
				route = new Route();

				route.setRouteID(rs.getInt("RouteID"));
				route.setStartingAddress(rs.getString("StartingAddress"));
				route.setDestinationAddress(rs.getString("DestinationAddress"));
				route.setStartTime(rs.getString("StartTime"));
				route.setFinishTime(rs.getString("FinishTime"));
				route.setNotes(rs.getString("Notes"));
				route.setWeight(rs.getInt("Weight"));
				route.setCreateTime(rs.getString("CreateTime"));
				route.setActive(rs.getInt("Active"));

				route.setDriverID(rs.getInt("DriverID"));
				route.setDriver(driverDao.getDriverById(rs.getInt("DriverID")));

				route.setRouteMarkers(routeMarkderDAO
						.getAllRouteMarkerByRouteID(route.getRouteID()));
				route.setVehicles(vehicleDAO.getAllVehicleByRouteID(route
						.getRouteID()));
				list.add(route);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Route table");
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
