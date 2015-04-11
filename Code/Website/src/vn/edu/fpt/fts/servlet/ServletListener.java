/**
 * 
 */
package vn.edu.fpt.fts.servlet;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import vn.edu.fpt.fts.process.OrderProcess;

/**
 * @author Duc Huy
 *
 */
public class ServletListener implements ServletContextListener {
	OrderProcess orderProcess = new OrderProcess();

	public void contextInitialized(ServletContextEvent arg0) {
		
		System.out.println("Servlet Context is started....");
		TimerTask ftsTimer = new FTSTimerTask();
		Timer timer = new Timer();
		timer.schedule(ftsTimer, 1000, (15 * 1000));

	}

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Servlet Context is destroyed....");
	}

	class FTSTimerTask extends TimerTask {

		@Override
		public void run() {
			orderProcess.checkDelivery();
		}
	}

}
