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

		// ScheduledExecutorService exec = Executors
		// .newSingleThreadScheduledExecutor();
		// exec.scheduleAtFixedRate(new Runnable() {
		// @Override
		// public void run() {
		// // do stuff
		// DateFormat dateFormat = new SimpleDateFormat(
		// "yyyy/MM/dd HH:mm:ss");
		// Date date = new Date();
		// System.out.println("-------" + dateFormat.format(date)
		// + "-------");
		//
		// orderProcess.checkDelivery();
		// }
		// }, 0, 60, TimeUnit.SECONDS);
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Servlet Context is destroyed....");
		// Logger logger = Logger.getLogger("CleanupContextListener");
		// Enumeration<Driver> drivers = DriverManager.getDrivers();
		// while (drivers.hasMoreElements()) {
		// Driver driver = drivers.nextElement();
		// ClassLoader driverclassLoader = driver.getClass().getClassLoader();
		// ClassLoader thisClassLoader = this.getClass().getClassLoader();
		// if (driverclassLoader != null && thisClassLoader != null
		// && driverclassLoader.equals(thisClassLoader)) {
		// try {
		// logger.warning("Deregistering: " + driver);
		// DriverManager.deregisterDriver(driver);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }
		// }
	}

	class FTSTimerTask extends TimerTask {

		@Override
		public void run() {
			orderProcess.checkDelivery();
		}
	}

}
