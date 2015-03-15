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
import vn.edu.fpt.fts.pojo.City;
import vn.edu.fpt.fts.process.LatLng;

/**
 * @author Huy
 *
 */
public class CityDAO {
	private final static String TAG = "CityDAO";

	public int insertGoods(City bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO City ( " + "CityName," + "Latitude,"
					+ "Longitude" + ") VALUES (" + "?, " + "?, " + "?)";
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			stmt.setString(i++, bean.getCityName()); // CityName
			stmt.setFloat(i++, bean.getLatitude()); // Latitude
			stmt.setFloat(i++, bean.getLongitude()); // Longitude

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = (int) rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't insert to City table");
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

	public List<City> getAllCity() {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM City";
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			List<City> list = new ArrayList<City>();
			City city;
			while (rs.next()) {
				city = new City();

				city.setCityID(rs.getInt("CityID"));
				city.setCityName(rs.getString("CityName"));
				city.setLatitude(rs.getFloat("Latitude"));
				city.setLongitude(rs.getFloat("Longitude"));

				list.add(city);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from City table");
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

	public int updateCity(City bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE City SET " + " CityName = ?,"
					+ " Latitude = ?," + " Longitude = ? "
					+ " WHERE CityID = '" + bean.getCityID() + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setString(i++, bean.getCityName()); // CityName
			stmt.setFloat(i++, bean.getLatitude()); // Latitude
			stmt.setFloat(i++, bean.getLongitude()); // Longitude

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Can't update to City table");
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

	public int updateLocationCity(int cityID, LatLng latLng) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE City SET " + " Latitude = ?,"
					+ " Longitude = ? " + " WHERE CityID = '" + cityID + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setDouble(i++, latLng.getLatitude()); // Latitude
			stmt.setDouble(i++, latLng.getLongitude()); // Longitude

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Can't update to City table");
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
