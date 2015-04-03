package vn.edu.fpt.fts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import vn.edu.fpt.fts.dao.DriverDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.pojo.Driver;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Owner;
import vn.edu.fpt.fts.pojo.Route;
import vn.edu.fpt.fts.process.MatchingProcess;

/**
 * Servlet implementation class GoodsServlet
 */
public class GoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GoodsServlet() {
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
			GoodsDAO goodDao = new GoodsDAO();
			if ("next1".equals(action)) {
				String pickupAddress = request.getParameter("txtpickupAddress");
				String pickupTime = request.getParameter("txtpickupTime");
				String deliveryAddress = request
						.getParameter("txtdeliveryAddress");
				String deliveryTime = request.getParameter("txtdeliveryTime");

				Goods r = new Goods(pickupTime, pickupAddress, deliveryTime,
						deliveryAddress);
				session.setAttribute("router", r);
				request.getRequestDispatcher("tao-hang-2.jsp").forward(request,
						response);
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
								* Common.distance(Common.latGeoCoding(r
										.getPickupAddress()), Common
										.lngGeoCoding(r.getPickupAddress()),
										Common.latGeoCoding(r
												.getDeliveryAddress()), Common
												.lngGeoCoding(r
														.getDeliveryAddress()),
										"K"))));
				request.getRequestDispatcher("tao-hang-3.jsp").forward(request,
						response);
			}
			if ("next3".equals(action)) {
				double priceSuggest = (Double) session
						.getAttribute("priceSuggest");
				double price = 0;
				try {
					price = Double
							.parseDouble(request.getParameter("txtPrice"));

				} catch (Exception ex) {

				}
				if (price == 0) {
					price = priceSuggest;
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
				DecimalFormat formatPrice = new DecimalFormat("#");
				Goods g = (Goods) session.getAttribute("good");
				Goods r = (Goods) session.getAttribute("router");
				session.setAttribute("priceSuggest", Double.valueOf(formatPrice
						.format(Common.perKilometer
								* Common.perKilogram
								* g.getWeight()
								* Common.distance(Common.latGeoCoding(r
										.getPickupAddress()), Common
										.lngGeoCoding(r.getPickupAddress()),
										Common.latGeoCoding(r
												.getDeliveryAddress()), Common
												.lngGeoCoding(r
														.getDeliveryAddress()),
										"K"))));

				request.getRequestDispatcher("tao-hang-3.jsp").forward(request,
						response);
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
				request.getRequestDispatcher("tao-hang-1.jsp").forward(request,
						response);
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
								* Common.distance(Common.latGeoCoding(r
										.getPickupAddress()), Common
										.lngGeoCoding(r.getPickupAddress()),
										Common.latGeoCoding(r
												.getDeliveryAddress()), Common
												.lngGeoCoding(r
														.getDeliveryAddress()),
										"K"))));
				request.getRequestDispatcher("tao-hang-2.jsp").forward(request,
						response);
			}

			if ("save3".equals(action)) {
				double priceSuggest = (Double) session
						.getAttribute("priceSuggest");
				double price = 0;
				try {
					price = Double
							.parseDouble(request.getParameter("txtPrice"));

				} catch (Exception ex) {

				}
				if (price == 0) {
					price = priceSuggest;
				}
				session.setAttribute("price", price);
				request.getRequestDispatcher("tao-hang-3.jsp").forward(request,
						response);
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
				float lngpickAddress = Common.lngGeoCoding(pickupAdress);
				if (session.getAttribute("lngpickupAddress") != null) {
					lngpickAddress = (float) session
							.getAttribute("lngpickupAddress");
				}
				float latpickAddress = Common.latGeoCoding(pickupAdress);
				if (session.getAttribute("latpickupAddress") != null) {
					latpickAddress = (float) session
							.getAttribute("latpickupAddress");
				}
				float lngdeliveryAddress = Common.lngGeoCoding(deliveryAddress);
				if (session.getAttribute("lngdeliveryAddress") != null) {
					lngpickAddress = (float) session
							.getAttribute("lngdeliveryAddress");
				}
				float latdeliveryAddress = Common.latGeoCoding(deliveryAddress);
				if (session.getAttribute("latdeliveryAddress") != null) {
					latpickAddress = (float) session
							.getAttribute("latdeliveryAddress");
				}
				Owner owner = (Owner) session.getAttribute("owner");
				Goods goo = new Goods(weight, price, Common.changeFormatDate(
						pickupTime, "dd-MM-yyyy", "MM-dd-yyyy"), pickupAdress,
						Common.changeFormatDate(deliveryTime, "dd-MM-yyyy",
								"MM-dd-yyyy"), deliveryAddress, lngpickAddress,
						latpickAddress, lngdeliveryAddress, latdeliveryAddress,
						notes, createTime, Common.activate, owner.getOwnerID(),
						GoodsCategoryID);
				int idnewGood = goodDao.insertGoods(goo);
				if (idnewGood != -1) {
					session.removeAttribute("router");
					session.removeAttribute("good");
					session.removeAttribute("price");
					session.setAttribute("detailGood1",
							goodDao.getGoodsByID(idnewGood));
					session.setAttribute("messageSuccess",
							"Tạo hàng thành công. Hệ thống đưa ra những lộ trình thích hợp!");
					RequestDispatcher rd = request
							.getRequestDispatcher("GoodsServlet?btnAction=suggestFromSystem&txtIdGood="
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
						Common.latGeoCoding(address));
				session.setAttribute("lngpickupAddress",
						Common.lngGeoCoding(address));
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
						Common.latGeoCoding(address));
				session.setAttribute("lngdeliveryAddress",
						Common.lngGeoCoding(address));
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

			DriverDAO driverDao = new DriverDAO();
			MatchingProcess matchingProcess = new MatchingProcess();
			if ("suggestFromSystem".equals(action)) {
				session.removeAttribute("listRouter");
				Goods goods = (Goods) session.getAttribute("detailGood1");
				int IdGood = goods.getGoodsID();
				// int IdGood = Integer
				// .parseInt(request.getParameter("txtIdGood"));
				List<Route> list = matchingProcess.getSuggestionRoute(IdGood);
				if (list.size() != 0) {
					Route[] listRou = new Route[list.size()];
					list.toArray(listRou);
					List<Driver> listDriver = driverDao.getAllDriver();
					Driver[] listDri = new Driver[listDriver.size()];
					listDriver.toArray(listDri);
					session.setAttribute("detailGood",
							goodDao.getGoodsByID(IdGood));
					session.setAttribute("listRouter", listRou);
					session.setAttribute("listDriver", listDri);
					request.getRequestDispatcher("goi-y-he-thong.jsp").forward(
							request, response);
				} else {
					request.getRequestDispatcher("goi-y-he-thong.jsp").forward(
							request, response);
				}
			} else if (action.equalsIgnoreCase("manageGoods")) {
				Owner owner = (Owner) session.getAttribute("owner");
				if (owner != null) {
					List<Goods> manageGood = goodDao
							.getListGoodsByOwnerID(owner.getOwnerID());
					List<Goods> manageGood1 = new ArrayList<Goods>();
					for (int i = 0; i < manageGood.size(); i++) {
						if (manageGood.get(i).getActive() == 1) {
							manageGood.get(i).setPickupTime(
									Common.changeFormatDate(manageGood.get(i)
											.getPickupTime(),
											"yyyy-MM-dd hh:mm:ss.s",
											"dd-MM-yyyy"));
							manageGood.get(i).setDeliveryTime(
									Common.changeFormatDate(manageGood.get(i)
											.getDeliveryTime(),
											"yyyy-MM-dd hh:mm:ss.s",
											"dd-MM-yyyy"));
							manageGood1.add(manageGood.get(i));
						}
					}
					session.setAttribute("listGoods", manageGood1);

					request.getRequestDispatcher("quan-ly-hang.jsp").forward(
							request, response);
				} else {
					request.getRequestDispatcher("dang-nhap.jsp").forward(
							request, response);
				}

			} else if (action.equalsIgnoreCase("viewDetailGood1")) {
				try {
					int goodsID = Integer.parseInt(request
							.getParameter("idGood"));
					Goods goods = goodDao.getGoodsByID(goodsID);
					goods.setPickupTime(Common.changeFormatDate(
							goods.getPickupTime(), "yyyy-MM-dd hh:mm:ss.s",
							"dd-MM-yyyy"));
					goods.setDeliveryTime(Common.changeFormatDate(
							goods.getDeliveryTime(), "yyyy-MM-dd hh:mm:ss.s",
							"dd-MM-yyyy"));
					session.setAttribute("detailGood1", goods);
					session.setAttribute("priceCreateGood",
							Common.priceCreateGood);
					session.setAttribute("priceTotal", goods.getPrice()
							+ Common.priceCreateGood);
					request.getRequestDispatcher("chi-tiet-hang.jsp").forward(
							request, response);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			} else if ("viewDetailGood2".equals(action)) {
				try {
					int goodsID = Integer.parseInt(request
							.getParameter("idGood"));
					Goods goods = goodDao.getGoodsByID(goodsID);
					session.setAttribute("detailGood2", goods);
					RequestDispatcher rd = request
							.getRequestDispatcher("chi-tiet-order.jsp");
					rd.forward(request, response);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			if ("updateGood".equals(action)) {
				Goods go = (Goods) session.getAttribute("detailGood1");
				String pickupAddress = request.getParameter("txtpickupAddress");
				String pickupTime = request.getParameter("txtpickupTime");
				String deliveryAddress = request
						.getParameter("txtdeliveryAddress");
				String deliveryTime = request.getParameter("txtdeliveryTime");
				int weight = Integer
						.parseInt(request.getParameter("txtWeight"));
				int goodsCategoryID = Integer.parseInt(request
						.getParameter("ddlgoodsCategoryID"));
				String notes = "";
				try {
					notes = notes + request.getParameter("txtNotes");
				} catch (Exception ex) {

				}

				double price = Double.parseDouble(request
						.getParameter("txtPrice"));
				if (price == 0) {
					DecimalFormat formatPrice = new DecimalFormat("#");
					try {
						price = Double
								.valueOf(formatPrice.format(Common.perKilometer
										* Common.perKilogram
										* weight
										* Common.distance(
												Common.latGeoCoding(pickupAddress),
												Common.lngGeoCoding(pickupAddress),
												Common.latGeoCoding(deliveryAddress),
												Common.lngGeoCoding(deliveryAddress),
												"K")));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (XPathExpressionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Goods good = new Goods();
				try {
					good = new Goods(go.getGoodsID(), weight, price,
							Common.changeFormatDate(pickupTime, "dd-MM-yyyy",
									"MM-dd-yyyy"), pickupAddress,
							Common.changeFormatDate(deliveryTime, "dd-MM-yyyy",
									"MM-dd-yyyy"), deliveryAddress,
							Common.lngGeoCoding(pickupAddress),
							Common.latGeoCoding(pickupAddress),
							Common.lngGeoCoding(deliveryAddress),
							Common.latGeoCoding(deliveryAddress), notes, go
									.getCreateTime().toString(),
							Common.activate, go.getOwnerID(), goodsCategoryID);
				} catch (XPathExpressionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (goodDao.updateGoods(good) == 1) {
					session.setAttribute("messageSuccess",
							"Cập nhật thành công");
					RequestDispatcher rd = request
							.getRequestDispatcher("GoodsServlet?btnAction=viewDetailGood1&idGood="
									+ go.getGoodsID());
					rd.forward(request, response);
				} else {
					session.setAttribute("messageError",
							"Cập nhật thất bại. Xin vui lòng thử lại sau!");
					RequestDispatcher rd = request
							.getRequestDispatcher("GoodsServlet?btnAction=viewDetailGood1&idGood="
									+ go.getGoodsID());
					rd.forward(request, response);
				}
			} else if ("deleteGood".equals(action)) {
				int goodsID = Integer.parseInt(request
						.getParameter("txtIdGood"));
				Goods goodsDelete = goodDao.getGoodsByID(goodsID);
				goodsDelete.setActive(10);
				if (goodDao.updateGoods(goodsDelete) == 1) {
					session.setAttribute("messageSuccess",
							"Xoá hàng thành công!");
					RequestDispatcher rd = request
							.getRequestDispatcher("ProcessServlet?btnAction=manageGoods");
					rd.forward(request, response);
				} else {
					session.setAttribute("messageError",
							"Xoá hàng thất bại. Xin vui lòng thử lại sau!");
					RequestDispatcher rd = request
							.getRequestDispatcher("GoodsServlet?btnAction=viewDetailGood1&idGood="
									+ goodsID);
					rd.forward(request, response);
				}
			} else if ("viewDetailGood1".equals(action)) {
				try {
					int idGood = Integer.parseInt(request
							.getParameter("idGood"));
					Goods good = goodDao.getGoodsByID(idGood);
					session.setAttribute("detailGood1", good);
					request.getRequestDispatcher("chi-tiet-hang.jsp").forward(
							request, response);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			} else if ("filter".equals(action)) {
				// try {
				// String startDate = request.getParameter("txtstartdate");
				// } catch (Exception ex) {
				//
				// }
				// try {
				// String endDate = request.getParameter("txtenddate");
				// } catch (Exception ex) {
				//
				// }
				Owner owner = (Owner) session.getAttribute("owner");
				List<Goods> manageGood = goodDao.getListGoodsByOwnerID(owner
						.getOwnerID());
				List<Goods> manageGood1 = new ArrayList<Goods>();
				for (int i = 0; i < manageGood.size(); i++) {
					if (manageGood.get(i).getActive() == 1) {
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
				session.setAttribute("listGoods", manageGood1);
				request.getRequestDispatcher("quan-ly-hang.jsp").forward(
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
