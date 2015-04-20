/**
 * 
 */
package vn.edu.fpt.fts.api;

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
import vn.edu.fpt.fts.dao.PaymentDAO;
import vn.edu.fpt.fts.pojo.Payment;

/**
 * @author Duc Huy
 *
 */
@Path("Payment")
public class PaymentAPI {
	private final static String TAG = "PaymentAPI";
	PaymentDAO paymentDao = new PaymentDAO();

	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Payment> JSON() {
		return paymentDao.getAllPayment();
	}

	@POST
	@Path("Create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String createPayment(MultivaluedMap<String, String> params) {
		int ret = 0;
		try {
			String paypalID = params.getFirst("paypalID");
			String paypalAccount = params.getFirst("paypalAccount");
			String description = params.getFirst("description");
			int orderID = Integer.valueOf(params.getFirst("orderID"));

			Payment payment = new Payment();

			payment.setPaypalID(paypalID);
			payment.setPaypalAccount(paypalAccount);
			payment.setDescription(description);
			payment.setCreateTime(Common.getCreateTime());
			payment.setOrderID(orderID);
			ret = paymentDao.insertPayment(payment);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}

}
