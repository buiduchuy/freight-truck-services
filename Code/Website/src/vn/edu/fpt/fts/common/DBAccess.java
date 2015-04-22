package vn.edu.fpt.fts.common;

/**
 * @author Huy
 *
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBAccess {

	public static Connection makeConnection() {
		try {
			Class.forName(Common.CLASSSQLSERVERDRIVER);
			String url = Common.CONNECTION;
			// Connection con = DriverManager.getConnection(url);
			Connection con = DriverManager.getConnection(url,
					Common.usernamedb, Common.passworddb);
			return con;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null,
					e);
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null,
					e);
		}
		return null;
	}

	// public static Connection makeConnection() {
	// Connection con = null;
	// try {
	// ResourceBundle rb = ResourceBundle.getBundle("config");
	// Class.forName(rb.getString("driver"));
	// con = DriverManager.getConnection(rb.getString("connection"),
	// rb.getString("username"), rb.getString("password"));
	//
	// } catch (SQLException e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null,
	// e);
	// }
	//
	// return con;
	// }

}
