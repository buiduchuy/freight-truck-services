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

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.NotificationDAO;
import vn.edu.fpt.fts.pojo.Notification;
import vn.edu.fpt.fts.process.NotificationProcess;

/**
 * @author Huy
 *
 */
@Path("Notification")
public class NotificationAPI {
	private final static String TAG = "NotificationAPI";
	NotificationDAO notificationDao = new NotificationDAO();
	NotificationProcess notificationProcess = new NotificationProcess();

	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Notification> JSON() {
		List<Notification> l_notification = notificationDao
				.getAllNotification();
		return l_notification;
	}

	@POST
	@Path("getNotificationByEmail")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Notification> getNotificationByEmail(
			MultivaluedMap<String, String> params) {
		List<Notification> listDealNoti = new ArrayList<Notification>();
		try {
			String email = String.valueOf(params.getFirst("email"));
			listDealNoti = notificationDao.getNotificationByEmail(email);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return listDealNoti;
	}

	@POST
	@Path("getNewestNotificationByEmail")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Notification> getNewestNotificationByEmail(
			MultivaluedMap<String, String> params) {
		List<Notification> listDealNoti = new ArrayList<Notification>();
		try {
			String email = params.getFirst("email");
			int lastedID = Integer.valueOf(params.getFirst("lastedID"));
			listDealNoti = notificationDao.getNewestNotificationByEmail(email,
					lastedID);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return listDealNoti;
	}

	@POST
	@Path("deactivateNotification")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String deactivateNotification(MultivaluedMap<String, String> params) {
		int ret = 0;
		try {
			ret = notificationDao.updateNotificationStatus(
					Integer.valueOf(params.getFirst("notificationID")),
					Common.deactivate);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}

}
