/**
 * 
 */
package vn.edu.fpt.fts.process;

import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.DealNotificationDAO;

/**
 * @author Huy
 *
 */
public class NotificationProcess {
	
	DealNotificationDAO dealNotiDao = new DealNotificationDAO();
	DealDAO dealDao = new DealDAO();

	public int insertSendDeal() {
		int ret = 0;
		return ret;
	}

	public int insertAcceptDeal() {
		int ret = 0;
		return ret;
	}

	public int insertDeclineDeal() {
		int ret = 0;
		return ret;
	}

	public int insertCancelDeal() {
		int ret = 0;
		return ret;
	}

}
