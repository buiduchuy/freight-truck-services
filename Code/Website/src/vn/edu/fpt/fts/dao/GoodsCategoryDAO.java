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
import vn.edu.fpt.fts.model.GoodsCategory;

public class GoodsCategoryDAO {

	public List<GoodsCategory> getAllGoodsCategory() {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM GoodsCategory";
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			List<GoodsCategory> list = new ArrayList<GoodsCategory>();
			GoodsCategory goodsCategory;
			while (rs.next()) {
				goodsCategory = new GoodsCategory();

				goodsCategory.setGoodsCategoryId(rs.getInt("GoodsCategoryID"));
				goodsCategory.setName(rs.getString("Name"));

				list.add(goodsCategory);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Can't load data from Route table");
			e.printStackTrace();
			Logger.getLogger(GoodsCategoryDAO.class.getName()).log(
					Level.SEVERE, null, e);
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
				System.out.println("Can't load data from Route table");
				Logger.getLogger(GoodsCategoryDAO.class.getName()).log(
						Level.SEVERE, null, e);
			}
		}
		return null;
	}

}
