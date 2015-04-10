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

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.common.DBAccess;
import vn.edu.fpt.fts.pojo.Deal;

/**
 * @author Huy
 *
 */
public class DealDAO {

	private final static String TAG = "DealDAO";
	GoodsDAO goodsDao = new GoodsDAO();
	RouteDAO routeDao = new RouteDAO();
	DealStatusDAO dealStatusDao = new DealStatusDAO();

	public int insertDeal(Deal bean) {
		ResultSet rs = null;
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
			stmt.setInt(i++, bean.getActive()); // Active

			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = (int) rs.getLong(1);
			}
			return ret;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't insert to Deal table");
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
				deal.setDealStatusID(rs.getInt("DealStatusID"));
				deal.setActive(rs.getInt("Active"));

				deal.setGoods(goodsDao.getGoodsByID(rs.getInt("GoodsID")));
				deal.setRoute(routeDao.getRouteByID(rs.getInt("RouteID")));
				deal.setDealStatus(dealStatusDao.getDealStatusByID(rs
						.getInt("DealStatusID")));

				list.add(deal);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Deal table");
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
				deal.setDealStatusID(rs.getInt("DealStatusID"));
				deal.setActive(rs.getInt("Active"));

				list.add(deal);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Deal table");
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

			while (rs.next()) {
				Deal deal = new Deal();

				deal.setDealID(rs.getInt("DealID"));
				deal.setPrice(rs.getDouble("Price"));
				deal.setNotes(rs.getString("Notes"));
				deal.setCreateTime(rs.getString("CreateTime"));
				deal.setCreateBy(rs.getString("CreateBy"));
				deal.setRouteID(rs.getInt("RouteID"));
				deal.setGoodsID(rs.getInt("GoodsID"));
				deal.setDealStatusID(rs.getInt("DealStatusID"));
				deal.setActive(rs.getInt("Active"));

				deal.setGoods(goodsDao.getGoodsByID(rs.getInt("GoodsID")));
				deal.setRoute(routeDao.getRouteByID(rs.getInt("RouteID")));
				deal.setDealStatus(dealStatusDao.getDealStatusByID(rs
						.getInt("DealStatusID")));
				return deal;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Deal table");
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

	public List<Deal> getAllDeal() {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM Deal ORDER BY DealID DESC";

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
				deal.setDealStatusID(rs.getInt("DealStatusID"));
				deal.setActive(rs.getInt("Active"));

				list.add(deal);

			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Deal table");
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

	public List<Deal> getDealByDriverID(int driverID, int dealStatusID,
			String createBy) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = " SELECT * FROM Deal WHERE RouteID IN "
					+ "(SELECT RouteID FROM [Route] WHERE DriverID = ?) "
					+ "AND GoodsID IN (SELECT GoodsID FROM Goods WHERE Active = ?) "
					+ "AND DealStatusID = ? AND CreateBy = ? ORDER BY DealID DESC";

			stm = con.prepareStatement(sql);

			int i = 1;
			stm.setInt(i++, driverID);
			stm.setInt(i++, Common.activate);
			stm.setInt(i++, dealStatusID);
			stm.setString(i++, createBy);

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
				deal.setDealStatusID(rs.getInt("DealStatusID"));
				deal.setActive(rs.getInt("Active"));

				deal.setGoods(goodsDao.getGoodsByID(rs.getInt("GoodsID")));
				deal.setRoute(routeDao.getRouteByID(rs.getInt("RouteID")));
				deal.setDealStatus(dealStatusDao.getDealStatusByID(rs
						.getInt("DealStatusID")));
				list.add(deal);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Deal table");
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

	public List<Deal> getDealByDriverIDForHistory(int driverID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM Deal WHERE RouteID IN "
					+ "(SELECT RouteID FROM [Route] WHERE DriverID=?) ORDER BY DealID DESC";

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
				deal.setDealStatusID(rs.getInt("DealStatusID"));
				deal.setActive(rs.getInt("Active"));

				deal.setGoods(goodsDao.getGoodsByID(rs.getInt("GoodsID")));
				deal.setRoute(routeDao.getRouteByID(rs.getInt("RouteID")));
				deal.setDealStatus(dealStatusDao.getDealStatusByID(rs
						.getInt("DealStatusID")));
				list.add(deal);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Deal table");
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

	public List<Deal> getDealByOwnerID(int ownerID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM Deal WHERE GoodsID IN "
					+ "(SELECT GoodsID FROM Goods WHERE OwnerID=?) ORDER BY CreateTime DESC";

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
				deal.setDealStatusID(rs.getInt("DealStatusID"));
				deal.setActive(rs.getInt("Active"));

				deal.setGoods(goodsDao.getGoodsByID(rs.getInt("GoodsID")));
				deal.setRoute(routeDao.getRouteByID(rs.getInt("RouteID")));
				deal.setDealStatus(dealStatusDao.getDealStatusByID(rs
						.getInt("DealStatusID")));
				list.add(deal);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Deal table");
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

	public int updateDeal(Deal bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE Deal SET " + " Price = ?," + " Notes = ?,"
					+ " CreateTime = ?," + " CreateBy = ?," + " RouteID = ?,"
					+ " GoodsID = ?," + " DealStatusID = ?,"
					+ " RefDealID = ?," + " Active = ? " + " WHERE DealID = '"
					+ bean.getDealID() + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;

			stmt.setDouble(i++, bean.getPrice()); // Price
			stmt.setString(i++, bean.getNotes()); // Notes
			stmt.setString(i++, bean.getCreateTime()); // CreateTime
			stmt.setString(i++, bean.getCreateBy()); // CreateBy
			stmt.setInt(i++, bean.getRouteID()); // RouteID
			stmt.setInt(i++, bean.getGoodsID()); // GoodsID
			stmt.setInt(i++, bean.getDealStatusID()); // DealStatusID
			stmt.setInt(i++, bean.getActive()); // Active

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't update to Deal table");
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

	public int updateDealStatus(int dealID, int dealStatus) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE Deal SET " + " Active = ? "
					+ " WHERE DealID = '" + dealID + "' ";
			stmt = con.prepareStatement(sql);
			int i = 1;

			stmt.setInt(i++, dealStatus); // Active

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Can't update DealStatus to Deal table");
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

	public int getNumberOfDealParent(int goodsID, int routeID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		int cnt = 0;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT RouteID, GoodsID, COUNT(1) as [Count] "
					+ "FROM Deal WHERE RefDealID IS NULL " + "AND RouteID = "
					+ routeID + "AND GoodsID = " + goodsID
					+ "AND DealStatusID <> 4 " + "GROUP BY RouteID, GoodsID";

			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();

			while (rs.next()) {
				cnt = rs.getInt("Count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Deal table");
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
		return cnt;
	}

	public int updateStatusOfOtherDeal(int dealStatusID, int goodsID,
			int remainingWeightOfRoute, int routeID) {
		Connection con = null;
		PreparedStatement stmt = null;
		int ret = 0;
		try {
			con = DBAccess.makeConnection();
			String sql = "UPDATE Deal SET DealStatusID = ? WHERE GoodsID=? AND GoodsID IN (SELECT GoodsID FROM Goods WHERE Weight >= ?) AND RouteID != ?";
			stmt = con.prepareStatement(sql);
			int i = 1;

			stmt.setInt(i++, dealStatusID);
			stmt.setInt(i++, goodsID);
			stmt.setInt(i++, remainingWeightOfRoute);
			stmt.setInt(i++, routeID);

			ret = stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Can't update to Deal table");
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

	public Deal getLastDealByGoodsAndRouteID(int routeID, int goodsID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT TOP 1 * FROM Deal WHERE RouteID = " + routeID
					+ " AND GoodsID = " + goodsID + " ORDER BY DealID DESC";

			stm = con.prepareStatement(sql);

			rs = stm.executeQuery();
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
				deal.setDealStatusID(rs.getInt("DealStatusID"));
				deal.setActive(rs.getInt("Active"));

				deal.setGoods(goodsDao.getGoodsByID(rs.getInt("GoodsID")));
				deal.setRoute(routeDao.getRouteByID(rs.getInt("RouteID")));
				deal.setDealStatus(dealStatusDao.getDealStatusByID(rs
						.getInt("DealStatusID")));
				return deal;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Deal table");
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

	public List<Deal> getListDealByGoodsIDAndRouteID(int goodsID, int routeID,
			int dealStatusID) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			con = DBAccess.makeConnection();

			String sql = "SELECT * FROM Deal WHERE GoodsID=? AND RouteID=? AND DealStatusID=?";

			stm = con.prepareStatement(sql);

			int i = 1;
			stm.setInt(i++, goodsID);
			stm.setInt(i++, routeID);
			stm.setInt(i++, dealStatusID);

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
				deal.setDealStatusID(rs.getInt("DealStatusID"));
				deal.setActive(rs.getInt("Active"));

				deal.setGoods(goodsDao.getGoodsByID(rs.getInt("GoodsID")));
				deal.setRoute(routeDao.getRouteByID(rs.getInt("RouteID")));
				deal.setDealStatus(dealStatusDao.getDealStatusByID(rs
						.getInt("DealStatusID")));

				list.add(deal);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't load data from Deal table");
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
