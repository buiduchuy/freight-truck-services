package vn.edu.fpt.fts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.dao.PaymentDAO;
import vn.edu.fpt.fts.pojo.Employee;
import vn.edu.fpt.fts.pojo.Order;
import vn.edu.fpt.fts.process.NotificationProcess;

import com.google.gson.JsonObject;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Refund;
import com.paypal.api.payments.Sale;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.PayRequest;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.Receiver;
import com.paypal.svcs.types.ap.ReceiverList;
import com.paypal.svcs.types.common.RequestEnvelope;

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
				// redirectUrls
				// .setReturnUrl("http://localhost:8080/FTS/PaypalServlet?btnAction=return&orderID="
				// + request.getParameter("orderID"));

				String uri = request.getScheme()
						+ "://"
						+ request.getServerName()
						+ ("http".equals(request.getScheme())
								&& request.getServerPort() == 80
								|| "https".equals(request.getScheme())
								&& request.getServerPort() == 443 ? "" : ":"
								+ request.getServerPort())
						+ request.getRequestURI();
				System.out.println(uri);
				redirectUrls.setReturnUrl(uri + "?btnAction=return&orderID="
						+ request.getParameter("orderID"));
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
				String orderID = request.getParameter("orderID");
				Payment payment = new Payment();
				payment.setId(paymentID);
				PaymentExecution paymentExecute = new PaymentExecution();
				paymentExecute.setPayerId(payerID);
				Payment createdPayment = payment.execute(apiContext,
						paymentExecute);

				JSONObject createdJSON = new JSONObject(createdPayment.toJSON());
				String saleID = createdJSON.getJSONArray("transactions")
						.getJSONObject(0).getJSONArray("related_resources")
						.getJSONObject(0).getJSONObject("sale").getString("id");

				HttpSession session = request.getSession();
				String paymentURL = session.getAttribute("url").toString();
				session.removeAttribute("url");

				if (createdPayment.getState().equals("approved")) {
					PaymentDAO paymentDao = new PaymentDAO();
					OrderDAO orderDao = new OrderDAO();
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm");
					String createTime = format.format(Calendar.getInstance()
							.getTime());
					vn.edu.fpt.fts.pojo.Payment pmnt = new vn.edu.fpt.fts.pojo.Payment(
							0, saleID, createdPayment.getPayer()
									.getPayerInfo().getEmail(), "", createTime,
							Integer.parseInt(orderID));
					paymentDao.insertPayment(pmnt);
					orderDao.updateOrderStatusID(Integer.parseInt(orderID),
							Common.order_paid);
					NotificationProcess notificationProcess = new NotificationProcess();
					Order orderNoti = orderDao.getOrderByID(Integer
							.parseInt(orderID));
					notificationProcess.insertOwnerPayOrder(orderNoti);
					response.sendRedirect(paymentURL + "&message=success");
				} else {
					response.sendRedirect(paymentURL + "&message=fail");
				}
			} else if (action.equalsIgnoreCase("employeePay")) {
				
				int orderID = Integer.parseInt(request.getParameter("txtOrderID"));
				Amount amount = new Amount();
				amount.setCurrency("USD");
				double price = Double.parseDouble(request
						.getParameter("txtPrice")) / 21;
				DecimalFormat df = new DecimalFormat("#.##");
				price = Double.valueOf(df.format(price));
				amount.setTotal(String.valueOf(price));
				
				OrderDAO orderDao = new OrderDAO();
				Order order = orderDao.getOrderByID(orderID);
				
				PayRequest payRequest = new PayRequest();
   			  
				List<Receiver> receivers = new ArrayList<Receiver>();
				Receiver receiver = new Receiver();
				receiver.setAmount(price);
				receiver.setEmail("ftsdriver@gmail.com");
				receivers.add(receiver);
				ReceiverList receiverList = new ReceiverList(receivers);

				payRequest.setReceiverList(receiverList);

				RequestEnvelope requestEnvelope = new RequestEnvelope("en_US");
				payRequest.setRequestEnvelope(requestEnvelope); 
				payRequest.setActionType("PAY");
				payRequest.setCancelUrl("http://localhost:8080/FTS/ProcessServlet?btnAction=employeeManageOrder");
				payRequest.setReturnUrl("http://localhost:8080/FTS/ProcessServlet?btnAction=employeeManageOrder");
				payRequest.setCurrencyCode("USD");

				sdkConfig.put("acct1.UserName", "ftswebsite_api1.gmail.com");
				sdkConfig.put("acct1.Password", "WNGDNTT5TMD52VZR");
				sdkConfig.put("acct1.Signature", "AFcWxV21C7fd0v3bYYYRCpSSRl31AOr-ik0fJN5Px8srU-8l3lY4lKA5");
				sdkConfig.put("acct1.AppId","APP-80W284485P519543T");

				AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(sdkConfig);
				
				try {
					PayResponse payResponse = adaptivePaymentsService.pay(payRequest);
					response.sendRedirect("https://www.sandbox.paypal.com/webscr?cmd=_ap-payment&paykey=" + payResponse.getPayKey());
				} catch (SSLConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidCredentialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HttpErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidResponseDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientActionRequiredException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MissingCredentialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OAuthException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (action.equalsIgnoreCase("employeeRefund")) {
				int orderID = Integer.parseInt(request.getParameter("txtOrderID"));
				PaymentDAO paymentDao = new PaymentDAO();
				OrderDAO orderDao = new OrderDAO();
				String transactionID = paymentDao.getListPaymentByOrderID(orderID).get(0).getPaypalID();
				
				Sale sale = Sale.get(accessToken, transactionID);
				
				Amount amount = new Amount();
				amount.setCurrency("USD");
				double price = Double.parseDouble(request
						.getParameter("txtPrice")) / 21;
				DecimalFormat df = new DecimalFormat("#.##");
				price = Double.valueOf(df.format(price));
				amount.setTotal(String.valueOf(price));
				
				Refund refund = new Refund();
				refund.setAmount(amount);
				refund.setDescription("Hoàn tiền");

				Refund newRefund = sale.refund(accessToken, refund);
				if(newRefund.getState().equals("completed")) {
					orderDao.updateOrderStatusID(orderID, Common.order_refund);
					response.sendRedirect(request.getHeader("referer"));
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest requsest, HttpServletResponse
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
