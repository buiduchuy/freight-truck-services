package vn.edu.fpt.fts.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.JsonObject;
import com.paypal.api.openidconnect.Session;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalResource;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;

/**
 * Servlet implementation class Controller
 */
public class PaypalServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7724703787274072376L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaypalServlet() {
		super();
		// TODO Auto-generated constructor stub

	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			PayPalRESTException, com.paypal.base.rest.PayPalRESTException,
			JSONException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try (PrintWriter out = response.getWriter()) {
			
			Map<String, String> sdkConfig = new HashMap<String, String>();
			sdkConfig.put("mode", "sandbox");
			
			String accessToken = new OAuthTokenCredential(
					"AUYEywUBcwk_YKlg-Bqmfp2yx-ecX4A7qU6MN-oU12eq3k1xoH1JKnAfDjeFLjmDTIOSNgRBcAB8mwXm",
					"ECMUGhPghn0gH_5H2fr7SHQM4RvDju367xF7s5KBSh_y5cFbWepXlZQExrPp_--7yVmweupj_j-yWZeu",
					sdkConfig).getAccessToken();

			APIContext apiContext = new APIContext(accessToken);
			apiContext.setConfigurationMap(sdkConfig);
			
			String action = request.getParameter("btnAction");
			if (action.equalsIgnoreCase("pay")) {

				Amount amount = new Amount();
				amount.setCurrency("USD");
				double price = Double.parseDouble(request
						.getParameter("amount")) / 21;
				DecimalFormat df = new DecimalFormat("#.##");
				price = Double.valueOf(df.format(price));
				amount.setTotal(String.valueOf(price));

				Transaction transaction = new Transaction();
				transaction.setAmount(amount);
				transaction.setDescription("Hóa đơn mới");

				ItemList itemList = new ItemList();

				Item item = new Item();
				item.setPrice(String.valueOf(price));
				item.setName("Thanh toán hóa đơn chở hàng");
				item.setCurrency("USD");
				item.setQuantity("1");
				List<Item> list = new ArrayList<Item>();
				list.add(item);
				itemList.setItems(list);

				List<Transaction> transactions = new ArrayList<Transaction>();
				transactions.add(transaction);
				transaction.setItemList(itemList);

				Payer payer = new Payer();
				payer.setPaymentMethod("paypal");

				Payment payment = new Payment();
				payment.setIntent("sale");
				payment.setPayer(payer);
				payment.setTransactions(transactions);
				RedirectUrls redirectUrls = new RedirectUrls();
				redirectUrls.setCancelUrl(request.getHeader("referer"));
				HttpSession session = request.getSession();
				session.setAttribute("url", request.getHeader("referer"));
				redirectUrls
						.setReturnUrl("http://localhost:8080/FTS/PaypalServlet?btnAction=return");
				payment.setRedirectUrls(redirectUrls);

				Payment createdPayment = payment.create(apiContext);
				List<Links> links = createdPayment.getLinks();
				for (Links li : links) {
					if (li.getRel().equals("approval_url")) {
						response.sendRedirect(li.getHref());
					}
				}
			} else if (action.equalsIgnoreCase("return")) {
				String paymentID = request.getParameter("paymentId");
				String payerID = request.getParameter("PayerID");
				Payment payment = new Payment();
				payment.setId(paymentID);
				PaymentExecution paymentExecute = new PaymentExecution();
				paymentExecute.setPayerId(payerID);
				Payment createdPayment = payment.execute(apiContext, paymentExecute);
				
				HttpSession session = request.getSession();
				String paymentURL = session.getAttribute("url").toString();
				session.removeAttribute("url");
				
				
				if(createdPayment.getState().equals("approved")) {
					response.sendRedirect(paymentURL + "&message=success");
				}
				else {
					response.sendRedirect(paymentURL + "&message=fail");
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (com.paypal.base.rest.PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (com.paypal.base.rest.PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
