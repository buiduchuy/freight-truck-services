/**
 * 
 */
package vn.edu.fpt.fts.dao;

import java.util.List;

import vn.edu.fpt.fts.model.Goods;
import vn.edu.fpt.fts.model.Owner;

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

		Owner owner = new Owner();

		owner.setOwnerID(1);

		List<Goods> goodsList = goodsDao.getListGoodsByOwnerID(owner);
		
		
		System.out.println(goodsList.get(0).getDeliveryAddress());
	}

}
