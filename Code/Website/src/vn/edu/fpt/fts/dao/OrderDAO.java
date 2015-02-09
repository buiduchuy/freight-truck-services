/**
 * 
 */
package vn.edu.fpt.fts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.pojo.Order;

/**
 * @author Huy
 *
 */
public class OrderDAO {
	private final static String TAG = "OrderDAO";

	public int insertOrder(Order bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO [Order] (" + "Price,"
					+ "StaffDeliveryStatus," + "DriverDeliveryStatus,"
					+ "OwnerDeliveryStatus," + "CreateTime," + "OrderStatusID"
					+ ") VALUES (" + "?, " + "?, " + "?, " + "?, " + "?, "
					+ "?)";
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			stmt.setDouble(i++, bean.getPrice()); // Price
			stmt.setBoolean(i++, bean.isStaffDeliveryStatus()); // StaffDeliveryStatus
			stmt.setBoolean(i++, bean.isDriverDeliveryStatus()); // DriverDeliveryStatus
			stmt.setBoolean(i++, bean.isOwnerDeliveryStatus()); // OwnerDeliveryStatus
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setInt(i++, bean.getOrderStatusID()); // OrderStatusID

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = (int) rs.getLong(1);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			ret = -1;
			System.out.println("Can't insert to Order table");
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

	public int updateOrder(Order bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE Order SET " + " Price = ?,"
					+ " StaffDeliveryStatus = ?,"
					+ " DriverDeliveryStatus = ?,"
					+ " OwnerDeliveryStatus = ?," + " CreateTime = ?,"
					+ " OrderStatusID = ? " + " WHERE OrderID = '"
					+ bean.getOrderID() + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;

			stmt.setDouble(i++, bean.getPrice()); // Price
			stmt.setBoolean(i++, bean.isStaffDeliveryStatus()); // StaffDeliveryStatus
			stmt.setBoolean(i++, bean.isDriverDeliveryStatus()); // DriverDeliveryStatus
			stmt.setBoolean(i++, bean.isOwnerDeliveryStatus()); // OwnerDeliveryStatus
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setInt(i++, bean.getOrderStatusID()); // OrderStatusID

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Can't update to Deal table");
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
