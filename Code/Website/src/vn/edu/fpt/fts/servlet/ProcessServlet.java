package vn.edu.fpt.fts.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Controller
 */
public class ProcessServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7724703787274072376L;

	private static final String accountServlet = "AccountServlet";
	private static final String dealServlet = "DealServlet";
	private static final String goodsServlet = "GoodsServlet";
	private static final String orderServlet = "OrderServlet";
	private static final String notificationServlet = "NotificationServlet";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProcessServlet() {
		super();
		// TODO Auto-generated constructor stub

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("btnAction");
		if (action.equalsIgnoreCase("Login")) {
			request.getRequestDispatcher(accountServlet).forward(request,
					response);
		} else if (action.equalsIgnoreCase("createGoods")) {
			request.getRequestDispatcher("tao-hang-1.jsp").forward(request,
					response);
		} else if (action.equalsIgnoreCase("manageGoods")) {
			request.getRequestDispatcher(goodsServlet).forward(request,
					response);
		} else if (action.equalsIgnoreCase("manageDeal")) {
			request.getRequestDispatcher(dealServlet)
					.forward(request, response);
		} else if (action.equalsIgnoreCase("manageOrder")) {
			request.getRequestDispatcher(orderServlet).forward(request,
					response);
		} else if (action.equalsIgnoreCase("Logout")) {
			request.getRequestDispatcher(accountServlet).forward(request,
					response);
		} else if (action.equalsIgnoreCase("Register")) {
			request.getRequestDispatcher(accountServlet).forward(request,
					response);
		} else if (action.equalsIgnoreCase("routeDetail")) {
			request.getRequestDispatcher(dealServlet)
					.forward(request, response);
		} else if (action.equalsIgnoreCase("sendDeal")) {
			request.getRequestDispatcher(dealServlet)
					.forward(request, response);
		} else if (action.equalsIgnoreCase("getNotification")) {
			request.getRequestDispatcher(notificationServlet).forward(request,
					response);
		} else if (action.equalsIgnoreCase("viewDetailOrder")) {
			request.getRequestDispatcher(orderServlet).forward(request,
					response);
		} else if (action.equalsIgnoreCase("viewDetailDeal")) {
			request.getRequestDispatcher(dealServlet)
					.forward(request, response);
		} else if (action.equalsIgnoreCase("loginPage")) {
			request.getRequestDispatcher(accountServlet).forward(request,
					response);
		}else if (action.equalsIgnoreCase("registerPage")) {
			request.getRequestDispatcher(accountServlet).forward(request,
					response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

	}

}
