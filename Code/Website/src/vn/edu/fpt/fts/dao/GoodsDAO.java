package vn.edu.fpt.fts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.model.Goods;

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
	
	public List<Goods> loadAllGoods() {
		Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = Common.makeConnection();
            String sql = "SELECT * FROM Goods";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            List list = new ArrayList();

            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                float price = rs.getFloat("Price");
                String category = rs.getString("Category");
                Goods pr = new G
                list.add(pr);
            }

            ProductDTO[] result = new ProductDTO[list.size()];
            list.toArray(result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
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
            }
        }
        return null;
	}
}
