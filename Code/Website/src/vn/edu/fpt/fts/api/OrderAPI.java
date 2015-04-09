/**
 * 
 */
package vn.edu.fpt.fts.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.pojo.Order;
import vn.edu.fpt.fts.process.NotificationProcess;

/**
 * @author Huy
 *
 */
@Path("Order")
public class OrderAPI {
	private final static String TAG = "OrderAPI";
	OrderDAO orderDao = new OrderDAO();
	NotificationProcess notificationProcess = new NotificationProcess();

	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> JSON() {
		return orderDao.getAllOrder();
	}

	@POST
	@Path("Update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateOrder(MultivaluedMap<String, String> params) {
		Order order;
		int ret = 0;
		try {
			order = new Order();

			order.setPrice(Double.valueOf(params.getFirst("price")));
			order.setCreateTime(params.getFirst("createTime"));
			order.setOrderStatusID(Integer.valueOf(params
					.getFirst("orderStatusID")));
			order.setActive(Common.activate);

			ret = orderDao.updateOrder(order);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}

	@POST
	@Path("getOrderByID")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Order getOrderByID(MultivaluedMap<String, String> params) {
		Order order = new Order();
		try {
			order = orderDao.getOrderByID(Integer.valueOf(params
					.getFirst("orderID")));
			return order;
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return order;
	}

	@POST
	@Path("getOrderByDriverID")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> getOrderByDriverID(MultivaluedMap<String, String> params) {
		List<Order> list = new ArrayList<Order>();
		try {
			int driverID = Integer.valueOf(params.getFirst("driverID"));
			list = orderDao.getOrderByDriverID(driverID);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return list;
	}

	@POST
	@Path("getOrderByOwnerID")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> getOrderByOwnerID(MultivaluedMap<String, String> params) {
		List<Order> list = new ArrayList<Order>();
		try {
			int ownerID = Integer.valueOf(params.getFirst("ownerID"));
			list = orderDao.getOrderByOwnerID(ownerID);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return list;
	}

	@POST
	@Path("ownerNoticeLostGoods")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String ownerNoticeLostGoods(MultivaluedMap<String, String> params) {
		int ret = 0;
		try {
			int orderID = Integer.valueOf(params.getFirst("orderID"));
			Order db_order = orderDao.getOrderByID(orderID);
			if (db_order != null) {
				ret = orderDao.updateOrderStatusID(orderID, Common.order_lost);
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}

}
