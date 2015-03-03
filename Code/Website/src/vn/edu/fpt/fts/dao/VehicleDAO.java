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
import vn.edu.fpt.fts.pojo.Vehicle;

/**
 * @author Huy
 *
 */
public class VehicleDAO {

	public List<Vehicle> getAllVehicleByRouteID(int routeID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Vehicle WHERE RouteID=?";
			stm = con.prepareStatement(sql);

			stm.setInt(1, routeID);

			rs = stm.executeQuery();
			List<Vehicle> list = new ArrayList<Vehicle>();
			Vehicle vehicle;
			while (rs.next()) {
				vehicle = new Vehicle();

				vehicle.setVehicleID(rs.getInt("VehicleID"));
				vehicle.setIdentNumber(rs.getString("IdentNumber"));
				vehicle.setType(rs.getString("Type"));

				list.add(vehicle);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Vehicle table");
			Logger.getLogger(VehicleDAO.class.getName()).log(Level.SEVERE,
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
				Logger.getLogger(VehicleDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return null;
	}
}
