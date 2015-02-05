/**
 * 
 */
package vn.edu.fpt.fts.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Huy
 *
 */
public final class Common {
	
	public static final String usernamedb = "sa";
	public static final String passworddb = "123456";
	
	public static final String CLASSSQLSERVERDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String CONNECTION = "jdbc:sqlserver://localhost:1433;databaseName=FTS";
	
	public static Connection makeConnection() {
        try {
            Class.forName(CLASSSQLSERVERDRIVER);
            String url = CONNECTION;
            Connection con = DriverManager.getConnection(url, usernamedb, passworddb);
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException sqle) {
        	sqle.printStackTrace();
        }
        return null;
    }

}
