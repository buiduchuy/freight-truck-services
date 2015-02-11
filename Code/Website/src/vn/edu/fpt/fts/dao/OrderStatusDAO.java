/**
 * 
 */
package vn.edu.fpt.fts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.pojo.DealStatus;
import vn.edu.fpt.fts.pojo.OrderStatus;

/**
 * @author Huy
 *
 */
public class OrderStatusDAO {
	private final static String TAG = "OrderStatusDAO";

	public List<OrderStatus> getAllOrderStatus() {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM OrderStatus";
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			List<OrderStatus> list = new ArrayList<OrderStatus>();
			OrderStatus orderStatus;
			while (rs.next()) {
				orderStatus = new OrderStatus();

				orderStatus.setOrderStatusID(rs.getInt("OrderStatusID"));
				orderStatus.setOrderStatusName(rs.getInt("OrderStatusName"));

				list.add(orderStatus);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
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
				System.out.println("Can't load data from OrderStatus table");
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		return null;
	}

	public OrderStatus getOrderStatusByID(int orderStatusID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM OrderStatus WHERE OrderStatusID=?";
			stm = con.prepareStatement(sql);

			stm.setInt(1, orderStatusID);

			rs = stm.executeQuery();

			OrderStatus orderStatus;
			while (rs.next()) {
				orderStatus = new OrderStatus();

				orderStatus.setOrderStatusID(orderStatusID);
				orderStatus.setOrderStatusName(Integer.valueOf(rs
						.getString("OrderStatusName")));

				return dealStatus;
			}

		} catch (SQLException e) {
			System.out.println("Can't load data from OrderStatus table");
			e.printStackTrace();
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
				System.out.println("Can't load data from OrderStatus table");
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		return null;
	}
}
