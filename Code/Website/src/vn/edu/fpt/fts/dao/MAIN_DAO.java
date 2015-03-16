/**
 * 
 */
package vn.edu.fpt.fts.dao;

import java.util.List;

import vn.edu.fpt.fts.pojo.Notification;

/**
 * @author Huy
 *
 */
public class MAIN_DAO {

	public static void main(String[] args) {
		NotificationDAO notificationDAO = new NotificationDAO();
		List<Notification> l_notification = notificationDAO.getAllNotification();
		for (int i = 0; i < l_notification.size(); i++) {
			System.out.println(l_notification.get(i).getMessage());
		}
	}

}
