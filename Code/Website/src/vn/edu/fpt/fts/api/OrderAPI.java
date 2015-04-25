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
import vn.edu.fpt.fts.dao.PaymentDAO;
import vn.edu.fpt.fts.pojo.Order;
import vn.edu.fpt.fts.pojo.Payment;
import vn.edu.fpt.fts.process.NotificationProcess;
import vn.edu.fpt.fts.process.OrderProcess;

/**
 * @author Huy
 *
 */
@Path("Order")
public class OrderAPI {
	private final static String TAG = "OrderAPI";
	OrderDAO orderDao = new OrderDAO();
	OrderProcess orderProcess = new OrderProcess();
	NotificationProcess notificationProcess = new NotificationProcess();
	PaymentDAO paymentDao = new PaymentDAO();

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
	@Path("ownerReportOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String ownerNoticeLostGoods(MultivaluedMap<String, String> params) {
		int ret = 1;
		try {
			int orderID = Integer.valueOf(params.getFirst("orderID"));
			Order db_order = orderDao.getOrderByID(orderID);

			notificationProcess.insertOwnerReportOrder(db_order);

		} catch (NumberFormatException e) {
			// TODO: handle exception
			ret = 0;
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}

	@POST
	@Path("ownerCancelOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String ownerCancelOrder(MultivaluedMap<String, String> params) {
		int ret = 0;
		try {
			int orderID = Integer.valueOf(params.getFirst("orderID"));
			Order db_order = orderDao.getOrderByID(orderID);
			if (db_order != null) {
				ret = orderProcess.cancelOrder(orderID);
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}

	@POST
	@Path("ownerPayOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String ownerPay(MultivaluedMap<String, String> params) {
		int ret = 0;
		try {

			String paypalID = params.getFirst("paypalID");
			String paypalAccount = params.getFirst("paypalAccount");
			String description = params.getFirst("description");
			int orderID = Integer.valueOf(params.getFirst("orderID"));

			Payment payment = new Payment();

			payment.setPaypalID(paypalID);
			payment.setPaypalAccount(paypalAccount);
			payment.setDescription(description);
			payment.setCreateTime(Common.getCreateTime());
			payment.setOrderID(orderID);

			ret = orderDao.updateOrderStatusID(orderID, Common.order_paid);
			paymentDao.insertPayment(payment);

		} catch (Exception e) {
			// TODO: handle exception
			ret = 0;
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}
}
