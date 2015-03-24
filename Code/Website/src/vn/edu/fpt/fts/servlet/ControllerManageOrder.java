package vn.edu.fpt.fts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.dao.OrderStatusDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Order;
import vn.edu.fpt.fts.pojo.OrderStatus;
import vn.edu.fpt.fts.pojo.Owner;
import vn.edu.fpt.fts.pojo.Route;

/**
 * Servlet implementation class ControllerManageOrder
 */
public class ControllerManageOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllerManageOrder() {
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
			RouteDAO routeDao = new RouteDAO();
			GoodsDAO goodDao = new GoodsDAO();
			DealDAO dealDao = new DealDAO();
			OrderDAO orderDao = new OrderDAO();
			OrderStatusDAO orderStatusDao = new OrderStatusDAO();

			if ("manageOrder".equals(action)) {
				Owner owner = (Owner) session.getAttribute("owner");
				List<Goods> manageGood = goodDao.getListGoodsByOwnerID(owner
						.getOwnerID());
				List<Goods> manageGood1 = new ArrayList<Goods>();
				for (int i = 0; i < manageGood.size(); i++) {
					if (manageGood.get(i).getActive() == 0) {
						manageGood.get(i).setPickupTime(
								Common.changeFormatDate(manageGood.get(i)
										.getPickupTime(),
										"yyyy-MM-dd hh:mm:ss.s", "dd-MM-yyyy"));
						manageGood.get(i).setDeliveryTime(
								Common.changeFormatDate(manageGood.get(i)
										.getDeliveryTime(),
										"yyyy-MM-dd hh:mm:ss.s", "dd-MM-yyyy"));
						manageGood1.add(manageGood.get(i));
					}
				}
				Goods[] list1 = new Goods[manageGood1.size()];
				manageGood1.toArray(list1);
				session.removeAttribute("orderStatus");
				session.setAttribute("listOrder", list1);
				RequestDispatcher rd = request
						.getRequestDispatcher("quan-ly-order.jsp");
				rd.forward(request, response);
			}
			if ("viewDetailOrder".equals(action)) {
				int idGood = Integer.parseInt(request.getParameter("idGood"));
				List<Deal> listDeal = dealDao.getDealByGoodsID(idGood);
				Deal dea = new Deal();
				for (Deal deal : listDeal) {
					if (deal.getDealStatusID() == Common.deal_accept) {
						dea = deal;
					}
				}
				Route r = routeDao.getRouteByID(dea.getRouteID());
				Order order = orderDao.getOrderByGoodsID(idGood);

				try {
					OrderStatus trackingStatus = orderStatusDao
							.getOrderStatusByID(order.getOrderStatusID());
					Goods goodDetail = goodDao.getGoodsByID(idGood);
					goodDetail.setPickupTime(Common.changeFormatDate(
							goodDetail.getPickupTime(),
							"yyyy-MM-dd hh:mm:ss.s", "dd-MM-yyyy"));
					goodDetail.setDeliveryTime(Common.changeFormatDate(
							goodDetail.getDeliveryTime(),
							"yyyy-MM-dd hh:mm:ss.s", "dd-MM-yyyy"));
					session.setAttribute("detailOrder", goodDetail);
					session.setAttribute("orderStatus", trackingStatus);
					session.setAttribute("routeOrder", r);
					session.setAttribute("priceForDriver", dea.getPrice());
					session.setAttribute("priceCreate", Common.priceCreateGood);
					session.setAttribute("priceTotal", dea.getPrice()
							+ Common.priceCreateGood);
					RequestDispatcher rd = request
							.getRequestDispatcher("chi-tiet-order.jsp");
					rd.forward(request, response);
				} catch (Exception ex) {

				}
			}
			if ("lostGood".equals(action)) {
				int idGood = Integer.parseInt(request.getParameter("idGood"));
				Order order = orderDao.getOrderByGoodsID(idGood);
				try {
					order.setOrderStatusID(5);
					if (orderDao.updateOrder(order) == 1) {
						session.setAttribute(
								"messageSuccess",
								"Xin lỗi vì sự cố mất hàng. Hệ thống sẽ kiểm tra và báo lại cho bạn trong thời gian gần nhất!");
						RequestDispatcher rd = request
								.getRequestDispatcher("ControllerManageOrder?btnAction=manageOrder");
						rd.forward(request, response);
					}
				} catch (Exception ex) {
					session.setAttribute("messageError",
							"Có lỗi xảy ra. Xin vui lòng kiểm tra lại hoá đơn trước khi báo mất hàng!");
					RequestDispatcher rd = request
							.getRequestDispatcher("ControllerManageOrder?btnAction=manageOrder");
					rd.forward(request, response);
				}
			}
			if ("confirmOrder".equals(action)) {

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
