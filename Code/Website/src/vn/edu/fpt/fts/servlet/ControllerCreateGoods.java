package vn.edu.fpt.fts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

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
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws XPathExpressionException
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			XPathExpressionException, ParserConfigurationException,
			SAXException {
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
			Common commonDao = new Common();
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
				Goods r = (Goods) session.getAttribute("router");
				DecimalFormat formatPrice = new DecimalFormat("#");
				session.setAttribute("priceSuggest", Double.valueOf(formatPrice
						.format(Common.perKilometer
								* Common.perKilogram
								* weight
								* commonDao.distance(commonDao.latGeoCoding(r
										.getPickupAddress()), commonDao
										.lngGeoCoding(r.getPickupAddress()),
										commonDao.latGeoCoding(r
												.getDeliveryAddress()), commonDao
												.lngGeoCoding(r
														.getDeliveryAddress()),
										"K"))));
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-3.jsp");
				rd.forward(request, response);
			}if ("next3".equals(action)) {
				double priceSuggest = (Double) session.getAttribute("priceSuggest");
				double price=0;
				try {
					price = Double.parseDouble(request.getParameter("txtPrice"));

				} catch (Exception ex) {

				}
				if(price==0){
					price=priceSuggest;
				}
				double total = price + Common.priceCreateGood;
				session.setAttribute("priceCreate", Common.priceCreateGood);
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
			}if ("viewCreate_1".equals(action)) {
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
				DecimalFormat formatPrice = new DecimalFormat("#");
				Goods g = (Goods) session.getAttribute("good");
				Goods r = (Goods) session.getAttribute("router");
				session.setAttribute("priceSuggest", Double.valueOf(formatPrice
						.format(Common.perKilometer
								* Common.perKilogram
								* g.getWeight()
								* commonDao.distance(commonDao.latGeoCoding(r
										.getPickupAddress()), commonDao
										.lngGeoCoding(r.getPickupAddress()),
										commonDao.latGeoCoding(r
												.getDeliveryAddress()), commonDao
												.lngGeoCoding(r
														.getDeliveryAddress()),
										"K"))));
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-3.jsp");
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

				DecimalFormat formatPrice = new DecimalFormat("#");
				Goods g = new Goods(weight, notes, goodsCategoryID);
				session.setAttribute("good", g);
				Goods r = (Goods) session.getAttribute("router");
				session.setAttribute("priceSuggest", Double.valueOf(formatPrice
						.format(Common.perKilometer
								* Common.perKilogram
								* weight
								* commonDao.distance(commonDao.latGeoCoding(r
										.getPickupAddress()), commonDao
										.lngGeoCoding(r.getPickupAddress()),
										commonDao.latGeoCoding(r
												.getDeliveryAddress()), commonDao
												.lngGeoCoding(r
														.getDeliveryAddress()),
										"K"))));
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-2.jsp");
				rd.forward(request, response);
			}
			
			if ("save3".equals(action)) {
				double priceSuggest = (Double) session.getAttribute("priceSuggest");
				double price=0;
				try {
					price = Double.parseDouble(request.getParameter("txtPrice"));

				} catch (Exception ex) {

				}
				if(price==0){
					price=priceSuggest;
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
				Double price = (Double) session.getAttribute("price");
				float lngpickAddress = commonDao.lngGeoCoding(pickupAdress);
				if (session.getAttribute("lngpickupAddress") != null) {
					lngpickAddress = (float) session
							.getAttribute("lngpickupAddress");
				}
				float latpickAddress = commonDao.latGeoCoding(pickupAdress);
				if (session.getAttribute("latpickupAddress") != null) {
					latpickAddress = (float) session
							.getAttribute("latpickupAddress");
				}
				float lngdeliveryAddress = commonDao.lngGeoCoding(deliveryAddress);
				if (session.getAttribute("lngdeliveryAddress") != null) {
					lngpickAddress = (float) session
							.getAttribute("lngdeliveryAddress");
				}
				float latdeliveryAddress = commonDao.latGeoCoding(deliveryAddress);
				if (session.getAttribute("latdeliveryAddress") != null) {
					latpickAddress = (float) session
							.getAttribute("latdeliveryAddress");
				}
				Owner owner = (Owner) session.getAttribute("owner");
				Goods goo = new Goods(weight, price, commonDao.changeFormatDate(
						pickupTime, "dd-MM-yyyy", "MM-dd-yyyy"), pickupAdress,
						commonDao.changeFormatDate(deliveryTime, "dd-MM-yyyy",
								"MM-dd-yyyy"), deliveryAddress, lngpickAddress,
						latpickAddress, lngdeliveryAddress, latdeliveryAddress,
						notes, createTime, Common.activate, owner.getOwnerID(),
						GoodsCategoryID);
				int idnewGood = goodDao.insertGoods(goo);
				if (idnewGood != -1) {
					session.removeAttribute("router");
					session.removeAttribute("good");
					session.removeAttribute("price");
					session.setAttribute("detailGood1", goodDao.getGoodsByID(idnewGood));
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
			
			if ("detailroutepickupAddress".equals(action)) {

				Goods good = (Goods) session.getAttribute("router");
				String address = good.getPickupAddress();
				session.setAttribute("latpickupAddress",
						commonDao.latGeoCoding(address));
				session.setAttribute("lngpickupAddress",
						commonDao.lngGeoCoding(address));
				RequestDispatcher rd = request
						.getRequestDispatcher("clearpickupAddress.jsp");
				rd.forward(request, response);

			}
			if ("finishClearPickupAddress".equals(action)) {

				session.setAttribute("latpickAddress", Float.parseFloat(request
						.getParameter("latpickAddress")));
				session.setAttribute("lngpickAddress", Float.parseFloat(request
						.getParameter("lngpickAddress")));
				System.out.println(Float.parseFloat(request
						.getParameter("latpickAddress")));
				System.out.println(Float.parseFloat(request
						.getParameter("lngpickAddress")));
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-4.jsp");
				rd.forward(request, response);
			}
			if ("detailroutedeliveryAddress".equals(action)) {

				Goods good = (Goods) session.getAttribute("router");
				String address = good.getDeliveryAddress();
				session.setAttribute("latdeliveryAddress",
						commonDao.latGeoCoding(address));
				session.setAttribute("lngdeliveryAddress",
						commonDao.lngGeoCoding(address));
				RequestDispatcher rd = request
						.getRequestDispatcher("cleardeliveryAddress.jsp");
				rd.forward(request, response);

			}
			if ("finishClearDeliveryAddress".equals(action)) {

				session.setAttribute("latdeliveryAddress", Float
						.parseFloat(request.getParameter("latdeliveryAddress")));
				session.setAttribute("lngdeliveryAddress", Float
						.parseFloat(request.getParameter("lngdeliveryAddress")));
				System.out.println(Float.parseFloat(request
						.getParameter("latdeliveryAddress")));
				System.out.println(Float.parseFloat(request
						.getParameter("lngdeliveryAddress")));
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-4.jsp");
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
		try {
			processRequest(request, response);
		} catch (XPathExpressionException | ParserConfigurationException
				| SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			processRequest(request, response);
		} catch (XPathExpressionException | ParserConfigurationException
				| SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
