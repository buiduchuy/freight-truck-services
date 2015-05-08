/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Refund;
import com.paypal.api.payments.Sale;
import com.paypal.core.rest.OAuthTokenCredential;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.dao.PaymentDAO;
import vn.edu.fpt.fts.pojo.Order;

/**
 * @author Huy
 *
 */
public class OrderProcess {
	private final static String TAG = "OrderProcess";

	OrderDAO orderDao = new OrderDAO();
	GoodsDAO goodsDao = new GoodsDAO();
	NotificationProcess notificationProcess = new NotificationProcess();

	public int cancelOrder(int orderID) {
		int ret = 0;
		Order order = orderDao.getOrderByID(orderID);
		int orderStatusID = order.getOrderStatusID();
		if (orderStatusID == Common.order_unpaid) {
			// Update to cancelled and create notification
			ret = orderDao.updateOrderStatusID(orderID, Common.order_cancelled);
			notificationProcess.insertOwnerCancelOrder(order);

			// Activate goods
			goodsDao.updateGoodsStatus(order.getDeal().getGoodsID(),
					Common.activate);

		} else if (orderStatusID == Common.order_paid) {
			// Update to cancelled and create notification penalty
			// Tiếp tục xử lí refund tiền
			try {
				Map<String, String> sdkConfig = new HashMap<String, String>();
				sdkConfig.put("mode", "sandbox");

				String accessToken = new OAuthTokenCredential(
						"AUYEywUBcwk_YKlg-Bqmfp2yx-ecX4A7qU6MN-oU12eq3k1xoH1JKnAfDjeFLjmDTIOSNgRBcAB8mwXm",
						"ECMUGhPghn0gH_5H2fr7SHQM4RvDju367xF7s5KBSh_y5cFbWepXlZQExrPp_--7yVmweupj_j-yWZeu",
						sdkConfig).getAccessToken();

				int refundPercentage = 90;

				PaymentDAO paymentDao = new PaymentDAO();
				OrderDAO orderDao = new OrderDAO();
				String transactionID = paymentDao
						.getListPaymentByOrderID(orderID).get(0).getPaypalID();

				Sale sale = Sale.get(accessToken, transactionID);

				Amount amount = new Amount();
				amount.setCurrency("USD");
				double price = (orderDao.getOrderByID(orderID).getPrice() / 21)
						/ 100 * refundPercentage;
				DecimalFormat df = new DecimalFormat("#.00");
				df.setRoundingMode(RoundingMode.DOWN);
				amount.setTotal(df.format(price));

				Refund refund = new Refund();
				refund.setAmount(amount);
				refund.setDescription("Hoàn tiền");

				Refund newRefund = sale.refund(accessToken, refund);
				if (newRefund.getState().equals("completed")) {
					orderDao.updateOrderStatusID(orderID, Common.order_refund);
					notificationProcess.insertStaffRefundForOwner(
							orderDao.getOrderByID(orderID), (int) price);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}

			// Update to cancelled and create notification
			ret = orderDao.updateOrderStatusID(orderID, Common.order_cancelled);
			notificationProcess.insertOwnerCancelOrder(order);

			// Activate goods
			goodsDao.updateGoodsStatus(order.getDeal().getGoodsID(),
					Common.activate);
		}
		return ret;
	}
}
