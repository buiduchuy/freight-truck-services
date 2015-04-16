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
import vn.edu.fpt.fts.pojo.Payment;

/**
 * @author Duc Huy
 *
 */
public class PaymentDAO {

	private final static String TAG = "PaymentDAO";

	public int insertPayment(Payment payment) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO Payment ( " + "PaypalID," + "CreateTime,"
					+ "Status," + "OrderID" + ") VALUES (" + "?, " + "?, "
					+ "?, " + "?)";
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			stmt.setInt(i++, payment.getPaypalID()); // PaypalID
			stmt.setString(i++, payment.getCreateTime()); // CreateTime
			stmt.setInt(i++, payment.getStatus()); // Status
			stmt.setInt(i++, payment.getOrderID()); // OrderID

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = (int) rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't insert to Payment table");
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

	public Payment getPaymentByPaypalID(int paypalID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM [Payment] WHERE PaypalID=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, paypalID);
			rs = stmt.executeQuery();
			Payment payment;
			while (rs.next()) {
				payment = new Payment();

				payment.setPaymentID(rs.getInt("PaymentID"));
				payment.setPaypalID(rs.getInt("PaypalID"));
				payment.setCreateTime(rs.getString("CreateTime"));
				payment.setStatus(rs.getInt("Status"));
				payment.setOrderID(rs.getInt("OrderID"));

				return payment;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Owner table");
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
	
	public List<Payment> getAllPayment() {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM [Payment]";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			Payment payment;
			List<Payment> list = new ArrayList<Payment>();
			while (rs.next()) {
				payment = new Payment();

				payment.setPaymentID(rs.getInt("PaymentID"));
				payment.setPaypalID(rs.getInt("PaypalID"));
				payment.setCreateTime(rs.getString("CreateTime"));
				payment.setStatus(rs.getInt("Status"));
				payment.setOrderID(rs.getInt("OrderID"));

				list.add(payment);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Owner table");
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

}
