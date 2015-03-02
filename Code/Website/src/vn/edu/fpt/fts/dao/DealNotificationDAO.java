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
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.DealNotification;

/**
 * @author Huy
 *
 */
public class DealNotificationDAO {
	private final static String TAG = "DealNotificationDAO";

	public List<DealNotification> getDealNotificationByDealID(int dealID) {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM DealNotification WHERE DealID=?";
			stm = con.prepareStatement(sql);

			stm.setInt(1, dealID);

			rs = stm.executeQuery();
			List<DealNotification> listDealNoti = new ArrayList<DealNotification>();
			DealNotification dealNoti;
			while (rs.next()) {
				dealNoti = new DealNotification();

				dealNoti.setDealID(dealID);
				dealNoti.setNotificationID(rs.getInt("NotificationID"));
				dealNoti.setMessage(rs.getString("Message"));
				dealNoti.setActive(rs.getInt("Active"));
				dealNoti.setCreateTime(rs.getString("CreateTime"));
				dealNoti.setDealID(rs.getInt("DealID"));

				listDealNoti.add(dealNoti);
			}
			return listDealNoti;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from DealNotification table");
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

	public int insertDealNotification(DealNotification bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();

			String sql = "INSERT INTO DealNotification ( " + "Message,"
					+ "Active," + "CreateTime," + "DealID" + ") VALUES ("
					+ "?, " + "?, " + "?, " + "?)";
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int i = 1;

			stmt.setString(i++, bean.getMessage()); // Message
			stmt.setInt(i++, bean.getActive()); // Active
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setInt(i++, bean.getDealID()); // DealID

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = (int) rs.getLong(1);
			}
			return ret;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't insert to DealNotification table");
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

	public int updateDealNotificationStatus(int dealNotiID, int dealNotiStatus) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE DealNotification SET " + " Active = ? "
					+ " WHERE DealNotificationID = '" + dealNotiID + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;

			stmt.setInt(i++, dealNotiStatus); // Active

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't update Active to DealNotification table");
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

	public List<DealNotification> getDealNotificationByOwnerID(int ownerID) {

		List<DealNotification> listDealNoti = new ArrayList<DealNotification>();
		List<Deal> listDeal = new ArrayList<Deal>();
		DealDAO dealDao = new DealDAO();
		DealNotificationDAO dealNotiDao = new DealNotificationDAO();
		try {
			listDeal = dealDao.getDealByOwnerID(ownerID);
			for (int i = 0; i < listDeal.size(); i++) {
				List<DealNotification> listDealNotiOfEachDeal = new ArrayList<DealNotification>();
				listDealNotiOfEachDeal = dealNotiDao
						.getDealNotificationByDealID(listDeal.get(i)
								.getDealID());
				for (int j = 0; j < listDealNotiOfEachDeal.size(); j++) {
					listDealNoti.add(listDealNotiOfEachDeal.get(j));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listDealNoti;
	}
}
