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
import vn.edu.fpt.fts.pojo.Employee;

/**
 * @author Duc Huy
 *
 */
public class EmployeeDAO {

	private final static String TAG = "EmployeeDAO";

	public Employee getEmployeeByEmail(String email) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Employee employee = new Employee();
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM [Employee] WHERE Email=" + "?";

			stmt = con.prepareStatement(sql);

			int i = 1;
			stmt.setString(i++, email);

			rs = stmt.executeQuery();

			while (rs.next()) {
				employee.setEmployeeID(rs.getInt("employeeID"));
				employee.setEmail(rs.getString("email"));
				employee.setFirstName(rs.getString("firstName"));
				employee.setLastName(rs.getString("lastName"));
				employee.setPhone(rs.getString("phone"));
				employee.setGender(rs.getInt("gender"));
				employee.setImage(rs.getString("image"));
				employee.setActive(rs.getInt("active"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Employee table");
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
		return employee;
	}

}
