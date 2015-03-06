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
import vn.edu.fpt.fts.pojo.DealOrder;
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

			String sql = "INSERT INTO Order ( " + "Price," + "CreateTime,"
					+ "OrderStatusID," + "Active" + ") VALUES (" + "?, "
					+ "?, " + "?, " + "?)";
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			stmt.setDouble(i++, bean.getPrice()); // Price
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setInt(i++, bean.getOrderStatusID()); // OrderStatusID
			stmt.setInt(i++, bean.getActive()); // Active

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = (int) rs.getLong(1);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't insert to Order table");
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
					+ " CreateTime = ?," + " OrderStatusID = ?,"
					+ " Active = ? " + " WHERE OrderID = '" + bean.getOrderID()
					+ "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;

			stmt.setDouble(i++, bean.getPrice()); // Price
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setInt(i++, bean.getOrderStatusID()); // OrderStatusID
			stmt.setInt(i++, bean.getActive()); // Active

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't update to Order table");
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

	public int updateOrderStatusID(int orderID, int orderStatusID) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE Order SET " + " OrderStatusID = ?"
					+ " WHERE OrderID = '" + orderID + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;

			stmt.setInt(i++, orderStatusID); // OrderStatusID

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't update OrderStatusID to Order table");
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

	public Order getOrderByID(int orderID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM [Order] WHERE OrderID=?";

			stm = con.prepareStatement(sql);

			int i = 1;
			stm.setInt(i++, orderID);

			rs = stm.executeQuery();
			DealOrderDAO dealOrderDao = new DealOrderDAO();
			DealDAO dealDao = new DealDAO();
			DealOrder dealOrder = new DealOrder();
			Order order;
			while (rs.next()) {
				order = new Order();

				order.setOrderID(rs.getInt("OrderID"));
				order.setPrice(rs.getDouble("Price"));
				order.setCreateTime(rs.getString("CreateTime"));
				order.setOrderStatusID(rs.getInt("OrderStatusID"));
				order.setActive(rs.getInt("Active"));
				dealOrder = dealOrderDao.getDealOrderByOrderID(rs
						.getInt("OrderID"));
				order.setDeal(dealDao.getDealByID(dealOrder.getDealID()));

				return order;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Order table");
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

	public List<Order> getAllOrder() {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM [Order]";

			stm = con.prepareStatement(sql);

			rs = stm.executeQuery();
			DealOrderDAO dealOrderDao = new DealOrderDAO();
			DealDAO dealDao = new DealDAO();
			DealOrder dealOrder = new DealOrder();
			Order order;
			List<Order> list = new ArrayList<Order>();
			while (rs.next()) {
				order = new Order();

				order.setOrderID(rs.getInt("OrderID"));
				order.setPrice(rs.getDouble("Price"));
				order.setCreateTime(rs.getString("CreateTime"));
				order.setOrderStatusID(rs.getInt("OrderStatusID"));
				order.setActive(rs.getInt("Active"));

				dealOrder = dealOrderDao.getDealOrderByOrderID(rs
						.getInt("OrderID"));
				if (dealOrder != null) {
					order.setDeal(dealDao.getDealByID(dealOrder.getDealID()));
				}

				list.add(order);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Order table");
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

	public Order getOrderByGoodsID(int goodsID) {
		OrderDAO orderDao = new OrderDAO();
		List<Order> list = new ArrayList<Order>();
		Order order = new Order();

		list = orderDao.getAllOrder();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getDeal() != null) {
				if (list.get(i).getDeal().getGoodsID() == goodsID)
					order = list.get(i);
			}
		}
		return order;
	}
}
