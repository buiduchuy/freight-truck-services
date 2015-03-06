/**
 * 
 */
package vn.edu.fpt.fts.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.DealNotificationDAO;
import vn.edu.fpt.fts.pojo.DealNotification;
import vn.edu.fpt.fts.process.NotificationProcess;

/**
 * @author Huy
 *
 */
@Path("DealNotification")
public class DealNotificationAPI {
	private final static String TAG = "DealNotificationAPI";
	DealNotificationDAO dealNotiDao = new DealNotificationDAO();
	DealDAO dealDao = new DealDAO();
	NotificationProcess notiProcess = new NotificationProcess();

	@POST
	@Path("getDealNotificationByDriverID")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<DealNotification> getDealNotificationByDealID(
			MultivaluedMap<String, String> params) {
		List<DealNotification> listDealNoti = new ArrayList<DealNotification>();
		try {
			int driverID = Integer.valueOf(params.getFirst("driverID"));
			listDealNoti = notiProcess.getListDealNotiByDriver(driverID);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return listDealNoti;
	}

	@POST
	@Path("getDealNotificationByOwnerID")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<DealNotification> getDealNotificationByOwnerID(
			MultivaluedMap<String, String> params) {
		List<DealNotification> listDealNoti = new ArrayList<DealNotification>();
		try {
			int ownerID = Integer.valueOf(params.getFirst("ownerID"));
			listDealNoti = notiProcess.getListDealNotiByOwner(ownerID);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return listDealNoti;
	}

	@POST
	@Path("deactivateDealNotification")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public int deactivateDealNotification(MultivaluedMap<String, String> params) {
		int ret = 0;
		try {
			ret = dealNotiDao.updateDealNotificationStatus(
					Integer.valueOf(params.getFirst("dealNotificationID")),
					Common.deactivate);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return ret;
	}

}
