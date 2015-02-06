package vn.edu.fpt.fts.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAccess {

	public static Connection makeConnection() {
		try {
			Class.forName(Common.CLASSSQLSERVERDRIVER);
			String url = Common.CONNECTION;
			Connection con = DriverManager.getConnection(url,
					Common.usernamedb, Common.passworddb);
			return con;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

}
