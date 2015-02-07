package vn.edu.fpt.fts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.model.Account;
import vn.edu.fpt.fts.model.Owner;

public class OwnerDAO {

	public Owner getOwnerByEmail(Account account) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Owner WHERE Email=?";

			stm = con.prepareStatement(sql);

			stm.setString(1, account.getEmail());

			rs = stm.executeQuery();
			Owner owner = new Owner();
			owner.setOwnerID(Integer.valueOf(rs.getString("OwnerID")));
			owner.setEmail(rs.getString("email"));
			owner.setFirstName(rs.getString("firstName"));
			owner.setLastName(rs.getString("lastName"));
			owner.setGender(Integer.valueOf(rs.getString("gender")));
			owner.setPhone(rs.getString("phone"));
			owner.setAddress(rs.getString("address"));
			owner.setActive(Integer.valueOf(rs.getString("active")));
			owner.setCreateBy(rs.getString("createBy"));
			owner.setCreateTime(rs.getString("createTime"));
			owner.setUpdateBy(rs.getString("updateBy"));
			owner.setUpdateTime(rs.getString("updateTime"));

			if (rs.next()) {
				return owner;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger(OwnerDAO.class.getName()).log(Level.SEVERE, null,
					e);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			Logger.getLogger(OwnerDAO.class.getName()).log(Level.SEVERE, null,
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
				Logger.getLogger(OwnerDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return null;
	}
}
