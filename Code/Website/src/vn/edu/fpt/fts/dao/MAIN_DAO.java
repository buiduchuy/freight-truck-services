/**
 * 
 */
package vn.edu.fpt.fts.dao;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fts.pojo.Order;

/**
 * @author Huy
 *
 */
public class MAIN_DAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// GoodsDAO goodsDao = new GoodsDAO();

		// Owner owner = new Owner();
		//
		// owner.setOwnerID(1);
		//
		// List<Goods> goodsList = goodsDao.getListGoodsByOwnerID(owner);

		// Goods goods = goodsDao.getGoodsByID(11);

		// System.out.println(goodsList.get(0).getDeliveryAddress());

		// System.out.println(goods.getDeliveryAddress());
		//
		// Order order = new Order();
		//
		OrderDAO orderDao = new OrderDAO();
		//
		// order.setPrice(0);
		// order.setDriverDeliveryStatus(false);
		// order.setStaffDeliveryStatus(false);
		// order.setOwnerDeliveryStatus(false);
		// order.setCreateTime("2015-04-03");
		// order.setOrderStatusID(1);
		// System.out.println(orderDao.insertOrder(order));
		//
		// order = orderDao.getOrderByID(3);
		//
		// System.out.println(order.isDriverDeliveryStatus());
		//
		// List<RouteGoodsCategory> listRouteGoodsCategory = new
		// ArrayList<RouteGoodsCategory>();
		//
		// RouteGoodsCategoryDAO routeGoodsCategoryDao = new
		// RouteGoodsCategoryDAO();
		//
		// listRouteGoodsCategory = routeGoodsCategoryDao
		// .getListRouteGoodsCategoryByRouteID(3);
		//
		// System.out.println("RouteGoodsCategory Size: " +
		// listRouteGoodsCategory.size());

		System.out.println(orderDao.getOrderByGoodsID(11).getCreateTime());

	}

}
