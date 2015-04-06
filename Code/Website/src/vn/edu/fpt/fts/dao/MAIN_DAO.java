/**
 * 
 */
package vn.edu.fpt.fts.dao;

import java.util.List;

import vn.edu.fpt.fts.pojo.Order;

/**
 * @author Huy
 *
 */
public class MAIN_DAO {

	public static void main(String[] args) {
		OrderDAO orderDao = new OrderDAO();
		List<Order> list = orderDao.getAllOrder();
		
		System.out.println(orderDao.getOrderByID(1041).getPrice());
		for (Order order : list) {
			System.out.println(order.getOrderID());
		}
	}
}
