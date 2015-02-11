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
					+ "CreateTime," + "CreateBy," + "RouteID," + "GoodsID,"
					+ "DealStatusID," + "RefDealID," + "Active" + ") VALUES ("
					+ "?, " + "?, " + "?, " + "?, " + "?, " + "?, " + "?, "
					+ "?, " + "?)";
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int i = 1;

			stmt.setDouble(i++, bean.getPrice()); // Price
			stmt.setString(i++, bean.getNotes()); // Notes
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setString(i++, bean.getCreateBy()); // CreateBy
			stmt.setInt(i++, bean.getRouteID()); // RouteID
			stmt.setInt(i++, bean.getGoodsID()); // GoodsID
			stmt.setInt(i++, bean.getDealStatusID()); // DealStatusID
			stmt.setInt(i++, bean.getRefDealID()); // RefDealID
			stmt.setInt(i++, bean.getActive()); // Active

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = (int) rs.getLong(1);
			}
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
				deal.setCreateBy(rs.getString("CreateBy"));
				deal.setRouteID(rs.getInt("RouteID"));
				deal.setGoodsID(rs.getInt("GoodsID"));
				deal.setRefDealID(rs.getInt("RefDealID"));
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
				deal.setCreateBy(rs.getString("CreateBy"));
				deal.setRouteID(rs.getInt("RouteID"));
				deal.setGoodsID(rs.getInt("GoodsID"));
				deal.setRefDealID(rs.getInt("RefDealID"));
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

	public Deal getDealByID(int dealId) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM Deal WHERE DealID=?";

			stm = con.prepareStatement(sql);

			int i = 1;
			stm.setInt(i++, dealId);

			rs = stm.executeQuery();

			GoodsDAO goodsDao = new GoodsDAO();
			RouteDAO routeDao = new RouteDAO();
			DealStatusDAO dealStatusDao = new DealStatusDAO();

			while (rs.next()) {
				Deal deal = new Deal();

				deal.setDealID(rs.getInt("DealID"));
				deal.setPrice(rs.getDouble("Price"));
				deal.setNotes(rs.getString("Notes"));
				deal.setCreateTime(rs.getString("CreateTime"));
				deal.setCreateBy(rs.getString("CreateBy"));
				deal.setRouteID(rs.getInt("RouteID"));
				deal.setGoodsID(rs.getInt("GoodsID"));
				deal.setRefDealID(rs.getInt("RefDealID"));
				deal.setDealStatusID(rs.getInt("DealStatusID"));
				deal.setActive(rs.getInt("Active"));

				deal.setGoods(goodsDao.getGoodsByID(rs.getInt("GoodsID")));
				deal.setRoute(routeDao.getRouteById(rs.getInt("RouteID")));
				deal.setDealStatus(dealStatusDao.getDealStatusByID(rs
						.getInt("DealStatusID")));
				return deal;
			}

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

	public List<Deal> getAllDeal() {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM Deal";

			stm = con.prepareStatement(sql);

			rs = stm.executeQuery();
			List<Deal> list = new ArrayList<Deal>();
			while (rs.next()) {
				Deal deal = new Deal();

				deal.setDealID(rs.getInt("DealID"));
				deal.setPrice(rs.getDouble("Price"));
				deal.setNotes(rs.getString("Notes"));
				deal.setCreateTime(rs.getString("CreateTime"));
				deal.setCreateBy(rs.getString("CreateBy"));
				deal.setRouteID(rs.getInt("RouteID"));
				deal.setGoodsID(rs.getInt("GoodsID"));
				deal.setRefDealID(rs.getInt("RefDealID"));
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

	public List<Deal> getDealByDriverID(int driverID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM Deal WHERE RouteID IN "
					+ "(SELECT RouteID FROM [Route] WHERE DriverID=?)";

			stm = con.prepareStatement(sql);

			int i = 1;
			stm.setInt(i++, driverID);

			rs = stm.executeQuery();
			List<Deal> list = new ArrayList<Deal>();
			Deal deal;

			while (rs.next()) {
				deal = new Deal();

				deal.setDealID(rs.getInt("DealID"));
				deal.setPrice(rs.getDouble("Price"));
				deal.setNotes(rs.getString("Notes"));
				deal.setCreateTime(rs.getString("CreateTime"));
				deal.setCreateBy(rs.getString("CreateBy"));
				deal.setRouteID(rs.getInt("RouteID"));
				deal.setGoodsID(rs.getInt("GoodsID"));
				deal.setRefDealID(rs.getInt("RefDealID"));
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

	public List<Deal> getDealByOwnerID(int ownerID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM Deal WHERE GoodsID IN "
					+ "(SELECT GoodsID FROM Goods WHERE OwnerID=?)";

			stm = con.prepareStatement(sql);

			int i = 1;
			stm.setInt(i++, ownerID);

			rs = stm.executeQuery();
			List<Deal> list = new ArrayList<Deal>();
			Deal deal;

			while (rs.next()) {
				deal = new Deal();

				deal.setDealID(rs.getInt("DealID"));
				deal.setPrice(rs.getDouble("Price"));
				deal.setNotes(rs.getString("Notes"));
				deal.setCreateTime(rs.getString("CreateTime"));
				deal.setCreateBy(rs.getString("CreateBy"));
				deal.setRouteID(rs.getInt("RouteID"));
				deal.setGoodsID(rs.getInt("GoodsID"));
				deal.setRefDealID(rs.getInt("RefDealID"));
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

	public static int updateDeal(Deal bean) {
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
			// stmt.setInt(i++, bean.getWeight()); // Weight
			// stmt.setDouble(i++, bean.getPrice()); // Price
			// stmt.setString(i++, bean.getPickupTime()); // PickupTime
			// stmt.setString(i++, bean.getPickupAddress()); // PickupAddress
			// stmt.setString(i++, bean.getDeliveryTime()); // DeliveryTime
			// stmt.setString(i++, bean.getDeliveryAddress()); //
			// DeliveryAddress
			// stmt.setFloat(i++, bean.getPickupMarkerLongtitude()); //
			// PickupMarkerLongtitude
			// stmt.setFloat(i++, bean.getPickupMarkerLatidute()); //
			// PickupMarkerLatidute
			// stmt.setFloat(i++, bean.getDeliveryMarkerLongtitude()); //
			// DeliveryMarkerLongtitude
			// stmt.setFloat(i++, bean.getDeliveryMarkerLatidute()); //
			// DeliveryMarkerLatidute
			// stmt.setString(i++, bean.getNotes()); // Notes
			// stmt.setString(i++, bean.getCreateTime()); // CreateTime
			// stmt.setInt(i++, bean.getActive()); // Active
			// stmt.setInt(i++, bean.getOwnerID()); // OwnerID
			// stmt.setInt(i++, bean.getGoodsCategoryID()); // GoodsCategoryID

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Can't update to Goods table");
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

}
