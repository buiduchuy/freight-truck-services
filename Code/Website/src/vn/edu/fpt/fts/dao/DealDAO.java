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
import vn.edu.fpt.fts.pojo.Deal;

/**
 * @author Huy
 *
 */
public class DealDAO {

	private final static String TAG = "DealDAO";

	public int insertDeal(Deal bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO Deal ( " + "Price," + "Notes,"
					+ "CreateTime," + "Sender," + "RouteID," + "GoodsID,"
					+ "DealStatusID," + "Active" + ") VALUES (" + "?, " + "?, "
					+ "?, " + "?, " + "?, " + "?, " + "?, " + "?)";
			stmt = con.prepareStatement(sql);
			int i = 1;

			stmt.setDouble(i++, bean.getPrice()); // Price
			stmt.setString(i++, bean.getNotes()); // Notes
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setString(i++, bean.getSender()); // Sender
			stmt.setInt(i++, bean.getRouteID()); // RouteID
			stmt.setInt(i++, bean.getGoodsID()); // GoodsID
			stmt.setInt(i++, bean.getDealStatusID()); // DealStatusID
			stmt.setInt(i++, bean.getActive()); // Active

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			ret = -1;
			System.out.println("Can't insert to Deal table");
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

	public List<Deal> getDealByGoodsID(int goodsId) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM Deal WHERE GoodsID=?";

			stm = con.prepareStatement(sql);

			int i = 1;
			stm.setInt(i++, goodsId);

			rs = stm.executeQuery();
			List<Deal> list = new ArrayList<Deal>();
			Deal deal;

			while (rs.next()) {
				deal = new Deal();

				deal.setDealID(rs.getInt("DealID"));
				deal.setPrice(rs.getDouble("Price"));
				deal.setNotes(rs.getString("Notes"));
				deal.setCreateTime(rs.getString("CreateTime"));
				deal.setSender(rs.getString("Sender"));
				deal.setRouteID(rs.getInt("RouteID"));
				deal.setGoodsID(rs.getInt("GoodsID"));
				deal.setDealStatusID(rs.getInt("DealStatusID"));
				deal.setActive(rs.getInt("Active"));

				list.add(deal);
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
				System.out.println("Can't load data from Deal table");
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		return null;
	}

	public List<Deal> getDealByRouteID(int routeID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM Deal WHERE RouteID=?";

			stm = con.prepareStatement(sql);

			int i = 1;
			stm.setInt(i++, routeID);

			rs = stm.executeQuery();
			List<Deal> list = new ArrayList<Deal>();
			Deal deal;

			while (rs.next()) {
				deal = new Deal();

				deal.setDealID(rs.getInt("DealID"));
				deal.setPrice(rs.getDouble("Price"));
				deal.setNotes(rs.getString("Notes"));
				deal.setCreateTime(rs.getString("CreateTime"));
				deal.setSender(rs.getString("Sender"));
				deal.setRouteID(rs.getInt("RouteID"));
				deal.setGoodsID(rs.getInt("GoodsID"));
				deal.setDealStatusID(rs.getInt("DealStatusID"));
				deal.setActive(rs.getInt("Active"));

				list.add(deal);
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
				System.out.println("Can't load data from Deal table");
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		return null;
	}

}
