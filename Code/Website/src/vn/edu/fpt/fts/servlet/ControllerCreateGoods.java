package vn.edu.fpt.fts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.AccountDAO;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.DealOrderDAO;
import vn.edu.fpt.fts.dao.DriverDAO;
import vn.edu.fpt.fts.dao.GoodsCategoryDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.dao.OwnerDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.pojo.Driver;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Owner;
import vn.edu.fpt.fts.pojo.Route;

/**
 * Servlet implementation class ControllerCreateGoods
 */
public class ControllerCreateGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllerCreateGoods() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
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
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			request.setCharacterEncoding("UTF-8");
			String action = request.getParameter("btnAction");
			HttpSession session = request.getSession(true);
			GoodsCategoryDAO goodCa = new GoodsCategoryDAO();
			AccountDAO accountDao = new AccountDAO();
			RouteDAO routeDao = new RouteDAO();
			GoodsDAO goodDao = new GoodsDAO();
			OwnerDAO ownerDao = new OwnerDAO();
			DealDAO dealDao = new DealDAO();
			DriverDAO driverDao = new DriverDAO();
			DealOrderDAO dealOrderDao = new DealOrderDAO();
			OrderDAO orderDao = new OrderDAO();
			Common common = new Common();
			if ("next1".equals(action)) {
				String pickupAddress = request.getParameter("txtpickupAddress");
				String pickupTime = request.getParameter("txtpickupTime");
				String deliveryAddress = request
						.getParameter("txtdeliveryAddress");
				String deliveryTime = request.getParameter("txtdeliveryTime");

				Goods r = new Goods(pickupTime, pickupAddress, deliveryTime,
						deliveryAddress);

				session.setAttribute("router", r);
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-2.jsp");
				rd.forward(request, response);
			}
			if ("save1".equals(action)) {
				String pickupAddress = request.getParameter("txtpickupAddress");
				String pickupTime = request.getParameter("txtpickupTime");
				String deliveryAddress = request
						.getParameter("txtdeliveryAddress");
				String deliveryTime = request.getParameter("txtdeliveryTime");

				Goods r = new Goods(pickupTime, pickupAddress, deliveryTime,
						deliveryAddress);
				session.setAttribute("router", r);
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-1.jsp");
				rd.forward(request, response);

			}
			if ("next2".equals(action)) {
				int goodsCategoryID = Integer.parseInt(request
						.getParameter("ddlgoodsCategoryID"));
				int weight = Integer
						.parseInt(request.getParameter("txtWeight"));
				String notes = "";
				try {
					notes = notes + request.getParameter("txtNotes");
				} catch (Exception ex) {

				}

				Goods g = new Goods(weight, notes, goodsCategoryID);
				session.setAttribute("good", g);
				session.setAttribute("priceSuggest", "10000000");
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-3.jsp");
				rd.forward(request, response);
			}
			if ("save2".equals(action)) {
				int goodsCategoryID = Integer.parseInt(request
						.getParameter("ddlgoodsCategoryID"));
				int weight = Integer
						.parseInt(request.getParameter("txtWeight"));
				String notes = "";
				try {
					notes = notes + request.getParameter("txtNotes");
				} catch (Exception ex) {

				}

				Goods g = new Goods(weight, notes, goodsCategoryID);
				session.setAttribute("good", g);
				session.setAttribute("priceSuggest", "10000000");
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-2.jsp");
				rd.forward(request, response);
			}
			if ("next3".equals(action)) {
				int price = Integer.parseInt((String) session
						.getAttribute("priceSuggest"));

				try {
					price = Integer.parseInt(request.getParameter("txtPrice"));

				} catch (Exception ex) {

				}
				int total = price + 15000;
				session.setAttribute("total", total);
				session.setAttribute("price", price);
				if (session.getAttribute("router") == null) {
					RequestDispatcher rd = request
							.getRequestDispatcher("tao-hang-1.jsp");
					rd.forward(request, response);
				}
				if (session.getAttribute("good") == null) {
					RequestDispatcher rd = request
							.getRequestDispatcher("tao-hang-2.jsp");
					rd.forward(request, response);
				}
				if (session.getAttribute("price") == null) {
					RequestDispatcher rd = request
							.getRequestDispatcher("tao-hang-3.jsp");
					rd.forward(request, response);
				} else {
					RequestDispatcher rd = request
							.getRequestDispatcher("tao-hang-4.jsp");
					rd.forward(request, response);
				}
			}
			if ("save3".equals(action)) {
				int price = Integer.parseInt((String) session
						.getAttribute("priceSuggest"));
				try {
					price = Integer.parseInt(request.getParameter("txtPrice"));

				} catch (Exception ex) {

				}
				session.setAttribute("price", price);
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-3.jsp");
				rd.forward(request, response);
			}

			if ("createGood".equals(action)) {
				if (session.getAttribute("router") == null) {
					RequestDispatcher rd = request
							.getRequestDispatcher("tao-hang-1.jsp");
					rd.forward(request, response);
				}
				if (session.getAttribute("good") == null) {
					RequestDispatcher rd = request
							.getRequestDispatcher("tao-hang-2.jsp");
					rd.forward(request, response);
				}
				if (session.getAttribute("price") == null) {
					RequestDispatcher rd = request
							.getRequestDispatcher("tao-hang-3.jsp");
					rd.forward(request, response);
				}

				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				String createTime = dateFormat.format(date);
				String pickupAdress = ((Goods) session.getAttribute("router"))
						.getPickupAddress();
				String deliveryAddress = ((Goods) session
						.getAttribute("router")).getDeliveryAddress();
				String pickupTime = ((Goods) session.getAttribute("router"))
						.getPickupTime();
				String deliveryTime = ((Goods) session.getAttribute("router"))
						.getDeliveryTime();
				int weight = ((Goods) session.getAttribute("good")).getWeight();
				int GoodsCategoryID = ((Goods) session.getAttribute("good"))
						.getGoodsCategoryID();
				String notes = ((Goods) session.getAttribute("good"))
						.getNotes();
				int price = (int) session.getAttribute("price");
				float a = Float.parseFloat("10");
				Owner owner = (Owner) session.getAttribute("owner");
				Goods goo = new Goods(weight, price, common.changeFormatDate(
						pickupTime, "dd-MM-yyyy", "MM-dd-yyyy"), pickupAdress,
						common.changeFormatDate(deliveryTime, "dd-MM-yyyy",
								"MM-dd-yyyy"), deliveryAddress, a, a, a, a,
						notes, createTime, 1, owner.getOwnerID(),
						GoodsCategoryID);

				List<Route> list = routeDao.getAllRoute();
				Route[] listRou = new Route[list.size()];
				list.toArray(listRou);
				List<Driver> listDriver = driverDao.getAllDriver();
				Driver[] listDri = new Driver[listDriver.size()];
				listDriver.toArray(listDri);
				int idnewGood = goodDao.insertGoods(goo);
				if (idnewGood != -1) {
					session.setAttribute("messageSuccess",
							"Tạo hàng thành công. Hệ thống đưa ra những lộ trình thích hợp!");

					RequestDispatcher rd = request
							.getRequestDispatcher("ControllerManageGoods?btnAction=suggestFromSystem&txtIdGood="
									+ idnewGood);
					rd.forward(request, response);
				} else {
					session.setAttribute("messageError",
							"Có lỗi khi tạo hàng. Vui lòng thử lại");
					RequestDispatcher rd = request
							.getRequestDispatcher("tao-hang-4.jsp");
					rd.forward(request, response);
				}
			}
			if ("viewCreate_1".equals(action)) {
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-1.jsp");
				rd.forward(request, response);
			}
			if ("viewCreate_2".equals(action)) {
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-2.jsp");
				rd.forward(request, response);
			}
			if ("viewCreate_3".equals(action)) {
				session.setAttribute("priceSuggest", "20000000");
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-3.jsp");
				rd.forward(request, response);
			}
			// out.println("<!DOCTYPE html>");
			// out.println("<html>");
			// out.println("<head>");
			// out.println("<title>Servlet Controller</title>");
			// out.println("</head>");
			// out.println("<body>");
			// out.println("<h1>Servlet Controller at " +
			// request.getContextPath() + "</h1>");
			// out.println("</body>");
			// out.println("</html>");
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
