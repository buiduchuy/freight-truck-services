package vn.edu.fpt.fts.dao;

/**
 * @author Huy
 *
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.process.LatLng;

public class GoodsDAO {
	private final static String TAG = "GoodsDAO";

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
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = (int) rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't insert to Goods table");
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

	public int updateLocationGoods(int goodsID, LatLng latLngSrc,
			LatLng latLngDes) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE Goods SET " + " PickupMarkerLatidute = ?"
					+ " ,PickupMarkerLongtitude = ?"
					+ " ,DeliveryMarkerLatidute = ?"
					+ " ,DeliveryMarkerLongtitude = ?" + " WHERE GoodsID = '"
					+ goodsID + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setDouble(i++, latLngSrc.getLatitude());
			stmt.setDouble(i++, latLngSrc.getLongitude());
			stmt.setDouble(i++, latLngDes.getLatitude());
			stmt.setDouble(i++, latLngDes.getLongitude());

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out
					.println("Can't update Location (4 columns) to Goods table");
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

	public int updateGoodsStatus(int goodsID, int status) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE Goods SET " + " Active = ?"
					+ " WHERE GoodsID = '" + goodsID + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setInt(i++, status); // Active

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't update Status to Goods table");
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

	public int updateGoods(Goods bean) {
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

	public List<Goods> getAllGoods() {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Goods ORDER BY CreateTime DESC";
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			List<Goods> list = new ArrayList<Goods>();
			GoodsCategoryDAO goodsCategoryDao = new GoodsCategoryDAO();
			OwnerDAO ownerDao = new OwnerDAO();
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
				goods.setOwner(ownerDao.getOwnerById(rs.getInt("OwnerID")));

				goods.setGoodsCategoryID(rs.getInt("GoodsCategoryID"));
				goods.setGoodsCategory(goodsCategoryDao.getGoodsCategoryByID(rs
						.getInt("GoodsCategoryID")));

				list.add(goods);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Goods table");
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

	public List<Goods> getListGoodsByOwnerID(int ownerId) {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM [Goods] WHERE OwnerID=? ORDER BY CreateTime DESC";
			stm = con.prepareStatement(sql);

			int i = 1;
			stm.setInt(i++, ownerId);

			rs = stm.executeQuery();
			GoodsCategoryDAO goodsCategoryDao = new GoodsCategoryDAO();
			OwnerDAO ownerDao = new OwnerDAO();
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
				goods.setOwner(ownerDao.getOwnerById(rs.getInt("OwnerID")));

				goods.setGoodsCategoryID(rs.getInt("GoodsCategoryID"));
				goods.setGoodsCategory(goodsCategoryDao.getGoodsCategoryByID(rs
						.getInt("GoodsCategoryID")));

				list.add(goods);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Goods table");
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
				Logger.getLogger(GoodsDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return null;
	}

	public List<Goods> getListGoodsByCreateTime(int ownerid, String createTime) {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Goods WHERE OwnerID=? AND createTime=?";
			stm = con.prepareStatement(sql);

			int i = 1;
			stm.setInt(i++, ownerid);
			stm.setString(i++, createTime);

			rs = stm.executeQuery();
			GoodsCategoryDAO goodsCategoryDao = new GoodsCategoryDAO();
			OwnerDAO ownerDao = new OwnerDAO();
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
				goods.setOwner(ownerDao.getOwnerById(rs.getInt("OwnerID")));

				goods.setGoodsCategoryID(rs.getInt("GoodsCategoryID"));
				goods.setGoodsCategory(goodsCategoryDao.getGoodsCategoryByID(rs
						.getInt("GoodsCategoryID")));

				list.add(goods);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Goods table");
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

	public Goods getGoodsByID(int goodsID) {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Goods WHERE GoodsID=?";

			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setInt(i++, goodsID);

			rs = stmt.executeQuery();
			GoodsCategoryDAO goodsCategoryDao = new GoodsCategoryDAO();
			OwnerDAO ownerDao = new OwnerDAO();
			while (rs.next()) {
				Goods goods = new Goods();

				goods.setGoodsID(goodsID);
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
				goods.setOwner(ownerDao.getOwnerById(rs.getInt("OwnerID")));

				goods.setGoodsCategoryID(rs.getInt("GoodsCategoryID"));
				goods.setGoodsCategory(goodsCategoryDao.getGoodsCategoryByID(rs
						.getInt("GoodsCategoryID")));

				return goods;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Goods table");
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		return null;
	}

	public List<Goods> getListActiveGoods() {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Goods WHERE Active=? ORDER BY CreateTime DESC";
			stm = con.prepareStatement(sql);
			int i = 1;
			stm.setInt(i++, Common.activate);
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
			System.out.println("Can't load data from Goods table");
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

	public Goods getActiveGoodsByID(int goodsID) {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Goods WHERE GoodsID=? AND Active=?";

			stmt = con.prepareStatement(sql);
			int i = 1;
			stmt.setInt(i++, goodsID);
			stmt.setInt(i++, Common.activate);

			rs = stmt.executeQuery();
			OwnerDAO ownerDao = new OwnerDAO();

			while (rs.next()) {
				Goods goods = new Goods();

				goods.setGoodsID(goodsID);
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
				goods.setOwner(ownerDao.getOwnerById(rs.getInt("OwnerID")));

				goods.setGoodsCategoryID(rs.getInt("GoodsCategoryID"));
				return goods;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Goods table");
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		return null;
	}

	public List<Goods> getListActiveGoodsNotByRoute(int routeID) {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Goods WHERE Active = 1 AND GoodsCategoryID NOT IN (SELECT GoodsCategoryID FROM RouteGoodsCategory WHERE RouteID = '"
					+ routeID + "')";
			stm = con.prepareStatement(sql);

			rs = stm.executeQuery();
			GoodsCategoryDAO goodsCategoryDao = new GoodsCategoryDAO();
			OwnerDAO ownerDao = new OwnerDAO();
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
				goods.setOwner(ownerDao.getOwnerById(rs.getInt("OwnerID")));

				goods.setGoodsCategoryID(rs.getInt("GoodsCategoryID"));
				goods.setGoodsCategory(goodsCategoryDao.getGoodsCategoryByID(rs
						.getInt("GoodsCategoryID")));

				list.add(goods);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Goods table");
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
				Logger.getLogger(GoodsDAO.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}
		return null;
	}

	public int getRemainingWeightByRouteID(int routeID) {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT Weight - (SELECT SUM(Weight) "
					+ "AS TotalGoodsWeight FROM Goods WHERE GoodsID IN "
					+ "(SELECT GoodsID FROM Deal WHERE RouteID = '" + routeID
					+ "' " + "AND DealStatusID = '" + Common.deal_accept
					+ "')) AS RemainingWeight FROM Route WHERE RouteID = '"
					+ routeID + "'";

			stmt = con.prepareStatement(sql);

			rs = stmt.executeQuery();
			while (rs.next()) {
				ret = rs.getInt("RemainingWeight");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Goods table");
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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

	public int getTotalWeightByRouteID(int routeID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int ret = 0;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT SUM(Weight) AS TotalGoodsWeight FROM Goods"
					+ " WHERE GoodsID IN (SELECT GoodsID FROM Deal WHERE RouteID = '"
					+ routeID + "' AND DealStatusID = '" + Common.deal_accept
					+ "')";

			stmt = con.prepareStatement(sql);

			rs = stmt.executeQuery();
			while (rs.next()) {
				ret = rs.getInt("TotalGoodsWeight");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Goods table");
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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

	public List<Goods> getListGoodsByDealPendingOrAcceptAndRouteID(int routeID) {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		try {
			con = DBAccess.makeConnection();
			String sql = "SELECT * FROM Goods WHERE Active = ? AND GoodsID IN "
					+ "(SELECT GoodsID FROM Deal WHERE RouteID=? "
					+ "AND DealStatusID=? OR DealStatusID=?)";
			stm = con.prepareStatement(sql);

			int i = 1;
			stm.setInt(i++, Common.activate);
			stm.setInt(i++, routeID);
			stm.setInt(i++, Common.deal_pending);
			stm.setInt(i++, Common.deal_accept);

			rs = stm.executeQuery();
			GoodsCategoryDAO goodsCategoryDao = new GoodsCategoryDAO();
			OwnerDAO ownerDao = new OwnerDAO();
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
				goods.setOwner(ownerDao.getOwnerById(rs.getInt("OwnerID")));

				goods.setGoodsCategoryID(rs.getInt("GoodsCategoryID"));
				goods.setGoodsCategory(goodsCategoryDao.getGoodsCategoryByID(rs
						.getInt("GoodsCategoryID")));

				list.add(goods);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Goods table");
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
}
