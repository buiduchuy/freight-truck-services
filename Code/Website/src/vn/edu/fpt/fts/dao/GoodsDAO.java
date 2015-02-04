package vn.edu.fpt.fts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import vn.edu.fpt.fts.common.Common;

public class GoodsDAO {
	
	public boolean insert(int weight, float price, Time pickupTime,
			String pickupAddress, Time deliveryTime, String deliveryAddress,
			String pickupMarkerLocation, String deliveryMarkerLocation,
			String notes, Time createTime, Boolean isActive, int ownerID,
			int goodsCategoryID) {
		Connection con = null;
		PreparedStatement stm = null;

		try {
			con = Common.makeConnection();

			String sql = "INSERT INTO Goods([Weight], Price, PickupTime, "
					+ "PickupAddress, DeliveryTime, DeliveryAddress, "
					+ "PickupMarkerLocation, DeliveryMarkerLocation, Notes, "
					+ "CreateTime, IsActive, OwnerID, GoodsCategoryID) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			stm = con.prepareStatement(sql);
			stm.setInt(1, weight);
			stm.setFloat(2, price);
			stm.setTime(3, pickupTime);
			stm.setString(4, pickupAddress);
			stm.setTime(5, deliveryTime);
			stm.setString(6, deliveryAddress);
			stm.setString(7, pickupMarkerLocation);
			stm.setString(8, deliveryMarkerLocation);
			stm.setString(9, notes);
			stm.setTime(10, createTime);
			stm.setBoolean(11, isActive);
			stm.setInt(12, ownerID);
			stm.setInt(13, goodsCategoryID);

			int row = stm.executeUpdate();

			if (row > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Cannot insert to Goods table");
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}
}
