/**
 * 
 */
package vn.edu.fpt.fts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.model.Driver;

/**
 * @author Huy
 *
 */
public class DriverDAO {
	public Driver getDriverById(int Id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM [Driver] WHERE DriverId=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, Id);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int driverID = rs.getInt("DriverID");
				String email = rs.getString("Email");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				int sex = rs.getInt("Sex");
				String phone = rs.getString("Phone");
				boolean isActive = rs.getBoolean("IsActive");
				String createBy = rs.getString("CreateBy");
				String createTime = rs.getString("CreateTime");
				String updateBy = rs.getString("UpdateBy");
				String updateTime = rs.getString("UpdateTime");
				int age = rs.getInt("Age");
				String image = rs.getString("Image");
				int point = rs.getInt("Point");
				Driver driver = new Driver(driverID, email, firstName,
						lastName, sex, phone, isActive, createBy, createTime,
						updateBy, updateTime, age, image, point);
				return driver;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger(OwnerDAO.class.getName()).log(Level.SEVERE, null,
					e);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Columns with Integer type are null");
			Logger.getLogger(OwnerDAO.class.getName()).log(Level.SEVERE, null,
					e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				Logger.getLogger(OwnerDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return null;
	}
}
