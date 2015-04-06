package vn.edu.fpt.fts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.dao.OrderStatusDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Order;
import vn.edu.fpt.fts.pojo.OrderStatus;
import vn.edu.fpt.fts.pojo.Owner;
import vn.edu.fpt.fts.pojo.Route;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2214798247911372640L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			request.setCharacterEncoding("UTF-8");
			String action = request.getParameter("btnAction");
			HttpSession session = request.getSession(true);
			OrderDAO orderDao = new OrderDAO();
			OrderStatusDAO orderStatusDao = new OrderStatusDAO();

			if (action.equalsIgnoreCase("manageOrder")) {
				Owner owner = (Owner) session.getAttribute("owner");
				if (owner != null) {
					List<Order> listOrder = orderDao.getOrderByOwnerID(owner
							.getOwnerID());

					session.removeAttribute("orderStatus");
					session.setAttribute("listOrder", listOrder);
					request.getRequestDispatcher("quan-ly-order.jsp").forward(
							request, response);
				} else {
					request.getRequestDispatcher("dang-nhap.jsp").forward(
							request, response);
				}

			} else if (action.equalsIgnoreCase("lostGood")) {
				int goodsID = Integer.parseInt(request.getParameter("idGood"));
				Order order = orderDao.getOrderByGoodsID(goodsID);
				try {
					order.setOrderStatusID(5);
					if (orderDao.updateOrder(order) == 1) {
						session.setAttribute(
								"messageSuccess",
								"Xin lỗi vì sự cố mất hàng. Hệ thống sẽ kiểm tra và báo lại cho bạn trong thời gian gần nhất!");
						RequestDispatcher rd = request
								.getRequestDispatcher("OrderServlet?btnAction=manageOrder");
						rd.forward(request, response);
					}
				} catch (Exception ex) {
					session.setAttribute("messageError",
							"Có lỗi xảy ra. Xin vui lòng kiểm tra lại hoá đơn trước khi báo mất hàng!");
					RequestDispatcher rd = request
							.getRequestDispatcher("OrderServlet?btnAction=manageOrder");
					rd.forward(request, response);
				}
			} else if (action.equalsIgnoreCase("confirmOrder")) {
				
				request.getRequestDispatcher("chi-tiet-order.jsp").forward(
						request, response);

			} else if (action.equalsIgnoreCase("viewDetailOrder")) {
				int orderID = Integer.valueOf(request.getParameter("orderID"));
				Order order = orderDao.getOrderByID(orderID);

				Goods goods = order.getDeal().getGoods();
				Route route = order.getDeal().getRoute();
				Deal deal = order.getDeal();

				OrderStatus trackingStatus = orderStatusDao
						.getOrderStatusByID(order.getOrderStatusID());

				goods.setPickupTime(Common.changeFormatDate(
						goods.getPickupTime(), "yyyy-MM-dd hh:mm:ss.s",
						"dd-MM-yyyy"));
				goods.setDeliveryTime(Common.changeFormatDate(order.getDeal()
						.getGoods().getDeliveryTime(), "yyyy-MM-dd hh:mm:ss.s",
						"dd-MM-yyyy"));
				request.setAttribute("detailOrder", goods);
				request.setAttribute("orderStatus", trackingStatus);
				request.setAttribute("routeOrder", route);
				request.setAttribute("priceForDriver", deal.getPrice());
				request.setAttribute("priceCreate", Common.priceCreateGood);
				request.setAttribute("priceTotal", deal.getPrice()
						+ Common.priceCreateGood);

				request.getRequestDispatcher("chi-tiet-order.jsp").forward(
						request, response);
			}
		}
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
