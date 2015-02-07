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

	public int insertOwner(Owner bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO Owner ( " + "Email," + "FirstName,"
					+ "LastName," + "Gender," + "Phone," + "Address,"
					+ "Active," + "CreateBy," + "CreateTime," + "UpdateBy,"
					+ "UpdateTime" + ") VALUES (" + "?, " + "?, " + "?, "
					+ "?, " + "?, " + "?, " + "?, " + "?, " + "?, " + "?, "
					+ "?)";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setString(i++, bean.getEmail()); // Email
			stmt.setString(i++, bean.getFirstName()); // FirstName
			stmt.setString(i++, bean.getLastName()); // LastName
			stmt.setInt(i++, bean.getGender()); // Gender
			stmt.setString(i++, bean.getPhone()); // Phone
			stmt.setString(i++, bean.getAddress()); // Address
			stmt.setInt(i++, bean.getActive()); // Active
			stmt.setString(i++, bean.getCreateBy()); // CreateBy
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setString(i++, bean.getUpdateBy()); // UpdateBy
			stmt.setString(i++, bean.getUpdateTime()); // UpdateTime

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			ret = -1;
			System.out.println("Can't insert to Owner table");
			e.printStackTrace();
			Logger.getLogger(OwnerDAO.class.getName()).log(Level.SEVERE, null,
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
				Logger.getLogger(OwnerDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return ret;
	}

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
			owner.setEmail(rs.getString("Email"));
			owner.setFirstName(rs.getString("FirstName"));
			owner.setLastName(rs.getString("LastName"));
			owner.setGender(Integer.valueOf(rs.getString("Gender")));
			owner.setPhone(rs.getString("Phone"));
			owner.setAddress(rs.getString("Address"));
			owner.setActive(Integer.valueOf(rs.getString("Active")));
			owner.setCreateBy(rs.getString("CreateBy"));
			owner.setCreateTime(rs.getString("CreateTime"));
			owner.setUpdateBy(rs.getString("UpdateBy"));
			owner.setUpdateTime(rs.getString("UpdateTime"));

			if (rs.next()) {
				return owner;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.getLogger(OwnerDAO.class.getName()).log(Level.SEVERE, null,
					e);
		} catch (NumberFormatException e) {
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