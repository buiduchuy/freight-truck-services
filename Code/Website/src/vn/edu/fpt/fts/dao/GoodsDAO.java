package vn.edu.fpt.fts.dao;

/**
 * @author Huy
 *
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.model.Goods;

public class GoodsDAO {

	public int insertGoods(Goods bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO Goods ( " + "Weight," + "Price,"
					+ "PickupTime," + "PickupAddress," + "DeliveryTime,"
					+ "DeliveryAddress," + "PickupMarkerLongtitude,"
					+ "PickupMarkerLatidute," + "DeliveryMarkerLongtitude,"
					+ "DeliveryMarkerLatidute," + "Notes," + "CreateTime,"
					+ "Active," + "OwnerID," + "GoodsCategoryID" + ") VALUES ("
					+ "?, " + "?, " + "?, " + "?, " + "?, " + "?, " + "?, "
					+ "?, " + "?, " + "?, " + "?, " + "?, " + "?, " + "?, "
					+ "?)";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setInt(i++, bean.getWeight()); // Weight
			stmt.setDouble(i++, bean.getPrice()); // Price
			stmt.setString(i++, bean.getPickupTime()); // PickupTime
			stmt.setString(i++, bean.getPickupAddress()); // PickupAddress
			stmt.setString(i++, bean.getDeliveryTime()); // DeliveryTime
			stmt.setString(i++, bean.getDeliveryAddress()); // DeliveryAddress
			stmt.setFloat(i++, bean.getPickupMarkerLongtitude()); // PickupMarkerLongtitude
			stmt.setFloat(i++, bean.getPickupMarkerLatidute()); // PickupMarkerLatidute
			stmt.setFloat(i++, bean.getDeliveryMarkerLongtitude()); // DeliveryMarkerLongtitude
			stmt.setFloat(i++, bean.getDeliveryMarkerLatidute()); // DeliveryMarkerLatidute
			stmt.setString(i++, bean.getNotes()); // Notes
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setInt(i++, bean.getActive()); // Active
			stmt.setInt(i++, bean.getOwnerID()); // OwnerID
			stmt.setInt(i++, bean.getGoodsCategoryID()); // GoodsCategoryID

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			ret = -1;
			System.out.println("Can't insert to Goods table");
			e.printStackTrace();
			Logger.getLogger(GoodsDAO.class.getName()).log(Level.SEVERE, null,
					e);

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
				Logger.getLogger(GoodsDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return ret;
	}

	public static int updateGoods(Goods bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE Goods SET " + " Weight = ?," + " Price = ?,"
					+ " PickupTime = ?," + " PickupAddress = ?,"
					+ " DeliveryTime = ?," + " DeliveryAddress = ?,"
					+ " PickupMarkerLongtitude = ?,"
					+ " PickupMarkerLatidute = ?,"
					+ " DeliveryMarkerLongtitude = ?,"
					+ " DeliveryMarkerLatidute = ?," + " Notes = ?,"
					+ " CreateTime = ?," + " Active = ?," + " OwnerID = ?,"
					+ " GoodsCategoryID = ? " + " WHERE GoodsID = '"
					+ bean.getGoodsID() + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setInt(i++, bean.getWeight()); // Weight
			stmt.setDouble(i++, bean.getPrice()); // Price
			stmt.setString(i++, bean.getPickupTime()); // PickupTime
			stmt.setString(i++, bean.getPickupAddress()); // PickupAddress
			stmt.setString(i++, bean.getDeliveryTime()); // DeliveryTime
			stmt.setString(i++, bean.getDeliveryAddress()); // DeliveryAddress
			stmt.setFloat(i++, bean.getPickupMarkerLongtitude()); // PickupMarkerLongtitude
			stmt.setFloat(i++, bean.getPickupMarkerLatidute()); // PickupMarkerLatidute
			stmt.setFloat(i++, bean.getDeliveryMarkerLongtitude()); // DeliveryMarkerLongtitude
			stmt.setFloat(i++, bean.getDeliveryMarkerLatidute()); // DeliveryMarkerLatidute
			stmt.setString(i++, bean.getNotes()); // Notes
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setInt(i++, bean.getActive()); // Active
			stmt.setInt(i++, bean.getOwnerID()); // OwnerID
			stmt.setInt(i++, bean.getGoodsCategoryID()); // GoodsCategoryID

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Can't update to Goods table");
			e.printStackTrace();
			Logger.getLogger(GoodsDAO.class.getName()).log(Level.SEVERE, null,
					e);
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
				Logger.getLogger(GoodsDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return ret;
	}

	public List<Goods> getAllGoods() {
		DateFormat format = new SimpleDateFormat("dd/mm/yyyy");

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Goods";
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			List<Goods> list = new ArrayList<Goods>();
			Goods goods;
			while (rs.next()) {
				goods = new Goods();

				goods.setGoodsID(rs.getInt("GoodsID"));
				goods.setWeight(rs.getInt("Weight"));
				goods.setPrice(rs.getDouble("Price"));
				goods.setPickupTime(rs.getTimestamp("PickupTime").toString());
				goods.setPickupAddress(rs.getString("PickupAddress"));
				goods.setDeliveryTime(rs.getTimestamp("DeliveryTime")
						.toString());
				goods.setDeliveryAddress(rs.getString("DeliveryAddress"));
				goods.setPickupMarkerLongtitude(rs
						.getFloat("PickupMarkerLongtitude"));
				goods.setPickupMarkerLatidute(rs
						.getFloat("PickupMarkerLatidute"));
				goods.setDeliveryMarkerLongtitude(rs
						.getFloat("DeliveryMarkerLongtitude"));
				goods.setDeliveryMarkerLatidute(rs
						.getFloat("DeliveryMarkerLatidute"));
				goods.setNotes(rs.getString("Notes"));
				goods.setCreateTime(rs.getTimestamp("CreateTime").toString());
				goods.setActive(rs.getInt("Active"));
				goods.setOwnerID(rs.getInt("OwnerID"));
				goods.setGoodsCategoryID(rs.getInt("GoodsCategoryID"));

				list.add(goods);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger(GoodsDAO.class.getName()).log(Level.SEVERE, null,
					e);
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
				System.out.println("Can't load data from Goods table");
				Logger.getLogger(GoodsDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return null;
	}
}
