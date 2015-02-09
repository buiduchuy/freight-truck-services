/**
 * 
 */
package vn.edu.fpt.fts.dao;


import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Order;

/**
 * @author Huy
 *
 */
public class MainTestDao {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GoodsDAO goodsDao = new GoodsDAO();

//		Owner owner = new Owner();
//
//		owner.setOwnerID(1);
//
//		List<Goods> goodsList = goodsDao.getListGoodsByOwnerID(owner);
		
		Goods goods = goodsDao.getGoodsByID(11);
		
//		System.out.println(goodsList.get(0).getDeliveryAddress());
		
		System.out.println(goods.getDeliveryAddress());
		
		Order order = new Order();
		
		OrderDAO orderDao = new OrderDAO();
		
		order.setPrice(0);
		order.setDriverDeliveryStatus(false);
		order.setStaffDeliveryStatus(false);
		order.setOwnerDeliveryStatus(false);
		order.setCreateTime("2015-04-03");
		order.setOrderStatusID(1);
		orderDao.insertOrder(order);
	}

}
