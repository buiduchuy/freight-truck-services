/**
 * 
 */
package vn.edu.fpt.fts.servlet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;

import vn.edu.fpt.fts.process.OrderProcess;

/**
 * @author Duc Huy
 *
 */
public class ServletListener implements javax.servlet.ServletContextListener {

	public void contextInitialized(ServletContextEvent arg0) {

		ScheduledExecutorService exec = Executors
				.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				// do stuff
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				System.out.println("-------" + dateFormat.format(date)
						+ "-------");
				OrderProcess orderProcess = new OrderProcess();
				orderProcess.checkDelivery();
			}
		}, 0, 15, TimeUnit.SECONDS);
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Servlet Context is destroyed....");
	}

}
