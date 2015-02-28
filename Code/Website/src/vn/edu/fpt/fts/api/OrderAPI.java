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

import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.pojo.Order;

/**
 * @author Huy
 *
 */
@Path("Order")
public class OrderAPI {
	private final static String TAG = "GoodsCategoryAPI";
	OrderDAO orderDao = new OrderDAO();

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
		try {
			order = new Order();

			order.setPrice(Double.valueOf(params.getFirst("price")));
			order.setStaffDeliveryStatus(Boolean.valueOf(params
					.getFirst("staffDeliveryStatus")));
			order.setDriverDeliveryStatus(Boolean.valueOf(params
					.getFirst("driverDeliveryStatus")));
			order.setOwnerDeliveryStatus(Boolean.valueOf(params
					.getFirst("ownerDeliveryStatus")));
			order.setCreateTime(params.getFirst("createTime"));
			order.setOrderStatusID(Integer.valueOf(params
					.getFirst("orderStatusID")));

			int ret = orderDao.updateOrder(order);
			if (ret <= 0) {
				return "Fail";
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return "Success";
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

		return null;
	}

	@POST
	@Path("getOrderByDriverID")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> getOrderByDriverID(MultivaluedMap<String, String> params) {
		List<Order> list = new ArrayList<Order>();
		List<Order> listOrderByDriver = new ArrayList<Order>();

		list = orderDao.getAllOrder();

		int driverID = Integer.valueOf(params.getFirst("driverID"));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getDeal() != null) {
				if (list.get(i).getDeal().getRoute() != null) {
					if (list.get(i).getDeal().getRoute().getDriverID() == driverID) {
						listOrderByDriver.add(list.get(i));
					}
				}
			}
		}
		return listOrderByDriver;
	}
	
	

}
