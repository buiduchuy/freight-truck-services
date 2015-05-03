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
import vn.edu.fpt.fts.pojo.Driver;

/**
 * @author Huy
 *
 */
public class DriverDAO {
	private final static String TAG = "DriverDAO";

	public Driver getDriverById(int Id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM [Driver] WHERE DriverID=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, Id);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int driverID = rs.getInt("DriverID");
				String email = rs.getString("Email");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				int gender = rs.getInt("Gender");
				String phone = rs.getString("Phone");
				int Active = rs.getInt("Active");
				String createBy = rs.getString("CreateBy");
				String createTime = rs.getString("CreateTime");
				String updateBy = rs.getString("UpdateBy");
				String updateTime = rs.getString("UpdateTime");
				String dateOfBirth = rs.getString("DateOfBirth");
				String image = rs.getString("Image");
				int point = rs.getInt("Point");
				Driver driver = new Driver(driverID, email, firstName,
						lastName, gender, phone, Active, createBy, createTime,
						updateBy, updateTime, dateOfBirth, image, point);
				return driver;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Driver table");
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
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
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		return null;
	}

	public List<Driver> getAllDriver() {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM [Driver]";
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			List<Driver> list = new ArrayList<Driver>();
			while (rs.next()) {
				int driverID = rs.getInt("DriverID");
				String email = rs.getString("Email");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				int gender = rs.getInt("Gender");
				String phone = rs.getString("Phone");
				int active = rs.getInt("Active");
				String createBy = rs.getString("CreateBy");
				String createTime = rs.getString("CreateTime");
				String updateBy = rs.getString("UpdateBy");
				String updateTime = rs.getString("UpdateTime");
				String dateOfBirth = rs.getString("DateOfBirth");
				String image = rs.getString("Image");
				int point = rs.getInt("Point");
				Driver driver = new Driver(driverID, email, firstName,
						lastName, gender, phone, active, createBy, createTime,
						updateBy, updateTime, dateOfBirth, image, point);

				list.add(driver);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Driver table");
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

	public Driver getDriverByEmail(String s_email) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM [Driver] WHERE Email=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, s_email);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int driverID = rs.getInt("DriverID");
				String email = rs.getString("Email");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				int gender = rs.getInt("Gender");
				String phone = rs.getString("Phone");
				int Active = rs.getInt("Active");
				String createBy = rs.getString("CreateBy");
				String createTime = rs.getString("CreateTime");
				String updateBy = rs.getString("UpdateBy");
				String updateTime = rs.getString("UpdateTime");
				String dateOfBirth = rs.getString("DateOfBirth");
				String image = rs.getString("Image");
				int point = rs.getInt("Point");
				Driver driver = new Driver(driverID, email, firstName,
						lastName, gender, phone, Active, createBy, createTime,
						updateBy, updateTime, dateOfBirth, image, point);
				return driver;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Driver table");
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
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
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		return null;
	}

	public int insertDriver(Driver bean, Connection con) throws SQLException {
		PreparedStatement stmt = null;
		int ret = 0;

		String sql = "INSERT INTO [Driver] ( " + "Email," + "FirstName,"
				+ "LastName," + "Gender," + "Phone," + "Active," + "CreateBy,"
				+ "CreateTime," + "UpdateBy," + "UpdateTime," + "Age,"
				+ "Image," + "Point" + ") VALUES (" + "?, " + "?, " + "?, "
				+ "?, " + "?, " + "?, " + "?, " + "?, " + "?, " + "?, " + "?, "
				+ "?, " + "?)";
		stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		int i = 1;
		stmt.setString(i++, bean.getEmail()); // Email
		stmt.setString(i++, bean.getFirstName()); // FirstName
		stmt.setString(i++, bean.getLastName()); // LastName
		stmt.setInt(i++, bean.getGender()); // Gender
		stmt.setString(i++, bean.getPhone()); // Phone
		stmt.setInt(i++, bean.getActive()); // Active
		stmt.setString(i++, bean.getCreateBy()); // CreateBy
		stmt.setString(i++, bean.getCreateTime()); // CreateTime
		stmt.setString(i++, bean.getUpdateBy()); // UpdateBy
		stmt.setString(i++, bean.getUpdateTime()); // UpdateTime
		stmt.setString(i++, bean.getDateOfBirth()); // DateOfBirth
		stmt.setString(i++, bean.getImage()); // Image
		stmt.setInt(i++, bean.getPoint()); // Point
		stmt.executeUpdate();

		ResultSet rs = stmt.getGeneratedKeys();
		if (rs != null && rs.next()) {
			ret = (int) rs.getLong(1);
		}

		return ret;
	}

	public int updateDriverStatus(String email, int status) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE [Driver] SET " + " Active = ?"
					+ " WHERE Email = '" + email + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setInt(i++, status); // Active

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't update data from Driver table");
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
