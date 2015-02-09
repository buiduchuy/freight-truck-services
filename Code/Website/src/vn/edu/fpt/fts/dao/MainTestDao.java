/**
 * 
 */
package vn.edu.fpt.fts.dao;


import vn.edu.fpt.fts.pojo.Goods;

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
	}

}
