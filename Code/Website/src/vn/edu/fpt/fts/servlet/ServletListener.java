/**
 * 
 */
package vn.edu.fpt.fts.servlet;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import vn.edu.fpt.fts.process.Scheduler;

/**
 * @author Duc Huy
 *
 */
public class ServletListener implements ServletContextListener {
	Scheduler scheduler = new Scheduler();

	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println("Servlet Context is started....");
		TimerTask ftsTimer = new FTSTimerTask();
		Timer timer = new Timer();
		try {
			// Each 15s run task
			timer.schedule(ftsTimer, 1000, (15 * 1000));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		} catch (Throwable e) {
			throw e;
		}
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Servlet Context is destroyed....");
	}

	class FTSTimerTask extends TimerTask {

		@Override
		public void run() {
//			/                                                                                                                                                                                  
			// scheduler.dealScheduler();
			// scheduler.itemsScheduler();
		}
	}

}
