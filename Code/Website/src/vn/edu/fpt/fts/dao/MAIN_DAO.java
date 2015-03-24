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

	public static void main(String[] args) {
		DealDAO dealDao = new DealDAO();
		System.out.println(dealDao
				.getDealByDriverID(1, Common.deal_pending, "owner").get(0)
				.getPrice());
	}

}
