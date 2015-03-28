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
import vn.edu.fpt.fts.pojo.Notification;

/**
 * @author Huy
 *
 */
public class NotificationDAO {
	private final static String TAG = "NotificationDAO";

	public List<Notification> getNotificationByEmail(String email) {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<Notification> l_notification = new ArrayList<Notification>();

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Notification WHERE Email=? ORDER BY NotificationID DESC";
			stm = con.prepareStatement(sql);

			stm.setString(1, email);

			rs = stm.executeQuery();
			Notification notification;
			while (rs.next()) {
				notification = new Notification();

				notification.setNotificationID(rs.getInt("NotificationID"));
				notification.setMessage(rs.getString("Message"));
				notification.setActive(rs.getInt("Active"));
				notification.setCreateTime(rs.getString("CreateTime"));
				notification.setType(rs.getString("Type"));
				notification.setEmail(rs.getString("Email"));
				notification.setIdOfType(rs.getInt("IdOfType"));
				notification.setStatusOfType(rs.getInt("StatusOfType"));

				l_notification.add(notification);
			}
			return l_notification;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Notification table");
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
		return l_notification;
	}

	public int insertNotification(Notification bean) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO Notification ( " + "Message," + "Active,"
					+ "CreateTime," + "Type," + "Email," + "IdOfType,"
					+ "StatusOfType" + ") VALUES (" + "?, " + "?, " + "?, "
					+ "?, " + "?, " + "?, " + "?)";
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int i = 1;

			stmt.setString(i++, bean.getMessage()); // Message
			stmt.setInt(i++, bean.getActive()); // Active
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setString(i++, bean.getType()); // Type
			stmt.setString(i++, bean.getEmail()); // Email
			stmt.setInt(i++, bean.getIdOfType()); // IdOfType
			stmt.setInt(i++, bean.getStatusOfType()); // StatusOfType

			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = (int) rs.getLong(1);
			}
			return ret;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't insert to Notification table");
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
		return ret;
	}

	public int updateNotificationStatus(int notificationID,
			int notificationStatus) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE Notification SET " + " Active = ? "
					+ " WHERE NotificationID = '" + notificationID + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;

			stmt.setInt(i++, notificationStatus); // Active

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't update Active to Notification table");
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

	public List<Notification> getAllNotification() {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<Notification> l_notification = new ArrayList<Notification>();

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Notification ORDER BY CreateTime DESC";
			stm = con.prepareStatement(sql);

			rs = stm.executeQuery();
			Notification notification;
			while (rs.next()) {
				notification = new Notification();

				notification.setNotificationID(rs.getInt("NotificationID"));
				notification.setMessage(rs.getString("Message"));
				notification.setActive(rs.getInt("Active"));
				notification.setCreateTime(rs.getString("CreateTime"));
				notification.setType(rs.getString("Type"));
				notification.setEmail(rs.getString("Email"));
				notification.setIdOfType(rs.getInt("IdOfType"));
				notification.setStatusOfType(rs.getInt("StatusOfType"));

				l_notification.add(notification);
			}
			return l_notification;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Notification table");
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
		return l_notification;
	}
}
