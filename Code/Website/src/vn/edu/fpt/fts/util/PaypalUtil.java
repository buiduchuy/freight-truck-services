/**
 * 
 */
package vn.edu.fpt.fts.util;

import java.util.HashMap;
import java.util.Map;

import com.paypal.api.payments.Payment;
import com.paypal.core.rest.OAuthTokenCredential;

/**
 * @author Duc Huy
 *
 */
public class PaypalUtil {

	public String returnTransactionID(String paymentID) {
		String transactionID = "";
		try {
			Map<String, String> sdkConfig = new HashMap<String, String>();
			sdkConfig.put("mode", "sandbox");

			String accessToken = new OAuthTokenCredential(
					"AUYEywUBcwk_YKlg-Bqmfp2yx-ecX4A7qU6MN-oU12eq3k1xoH1JKnAfDjeFLjmDTIOSNgRBcAB8mwXm",
					"ECMUGhPghn0gH_5H2fr7SHQM4RvDju367xF7s5KBSh_y5cFbWepXlZQExrPp_--7yVmweupj_j-yWZeu",
					sdkConfig).getAccessToken();
			
			Payment payment = Payment.get(accessToken, paymentID);
			transactionID = payment.getTransactions().get(0).getRelatedResources().get(0).getSale().getId();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return transactionID;
	}

}
