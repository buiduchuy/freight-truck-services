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
import vn.edu.fpt.fts.pojo.DealOrder;

/**
 * @author Huy
 *
 */
public class DealOrderDAO {

	private final static String TAG = "DealOrderDAO";

	public int insertDealOrder(DealOrder bean) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO DealOrder ( " + "DealID," + "OrderID"
					+ ") VALUES (" + "?, " + "?)";
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			stmt.setInt(i++, bean.getDealID()); // DealID
			stmt.setInt(i++, bean.getOrderID()); // OrderID

			stmt.executeUpdate();

			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = (int) rs.getLong(1);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't insert to DealOrder table");
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

	public DealOrder getDealOrderByID(int dealOrderID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM DealOrder WHERE DealOrderID=?";
			stm = con.prepareStatement(sql);

			stm.setInt(1, dealOrderID);

			rs = stm.executeQuery();

			DealOrder dealOrder;
			while (rs.next()) {
				dealOrder = new DealOrder();

				dealOrder.setDealOrderID(rs.getInt("DealOrderID"));
				dealOrder.setDealID(rs.getInt("DealID"));
				dealOrder.setOrderID(rs.getInt("OrderID"));

				return dealOrder;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from DealOrder table");
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

	public DealOrder getDealOrderByDealID(int dealID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM DealOrder WHERE DealID=?";
			stm = con.prepareStatement(sql);

			stm.setInt(1, dealID);

			rs = stm.executeQuery();

			DealOrder dealOrder;
			while (rs.next()) {
				dealOrder = new DealOrder();

				dealOrder.setDealOrderID(rs.getInt("DealOrderID"));
				dealOrder.setDealID(rs.getInt("DealID"));
				dealOrder.setOrderID(rs.getInt("OrderID"));

				return dealOrder;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from DealOrder table");
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

	public DealOrder getDealOrderByOrderID(int orderID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM DealOrder WHERE OrderID=?";
			stm = con.prepareStatement(sql);

			stm.setInt(1, orderID);

			rs = stm.executeQuery();

			DealOrder dealOrder;
			while (rs.next()) {
				dealOrder = new DealOrder();

				dealOrder.setDealOrderID(rs.getInt("DealOrderID"));
				dealOrder.setDealID(rs.getInt("DealID"));
				dealOrder.setOrderID(rs.getInt("OrderID"));

				return dealOrder;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from DealOrder table");
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

}
