/**
 * 
 */
package vn.edu.fpt.fts.dao;

import vn.edu.fpt.fts.common.Common;

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
		DealDAO dealDao = new DealDAO();

		int n = dealDao.changeStatusOfOtherDeal(Common.deal_cancel,
				11, 5);
		System.out.println("Co " + n
				+ " deal da thay doi trang thai la cancel");
	}

}
