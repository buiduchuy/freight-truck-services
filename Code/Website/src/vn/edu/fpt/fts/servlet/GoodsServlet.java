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

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.DriverDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.pojo.Driver;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Owner;
import vn.edu.fpt.fts.pojo.Route;
import vn.edu.fpt.fts.process.LatLng;
import vn.edu.fpt.fts.process.MapsUtils;
import vn.edu.fpt.fts.process.MatchingProcess;

/**
 * Servlet implementation class GoodsServlet
 */
public class GoodsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2830056779242459724L;

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
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			request.setCharacterEncoding("UTF-8");

			response.setCharacterEncoding("UTF-8");

			String action = request.getParameter("btnAction");

			HttpSession session = request.getSession(true);

			GoodsDAO goodsDao = new GoodsDAO();
			RouteDAO routeDao = new RouteDAO();
			MapsUtils mapUtils = new MapsUtils();
			if ("next1".equals(action)) {
				String pickupAddress = request.getParameter("txtpickupAddress");
				String pickupTime = request.getParameter("txtpickupTime");
				String deliveryAddress = request
						.getParameter("txtdeliveryAddress");
				String deliveryTime = request.getParameter("txtdeliveryTime");

				Goods routeInfoOfGoods = new Goods(pickupTime, pickupAddress,
						deliveryTime, deliveryAddress);
				session.setAttribute("router", routeInfoOfGoods);
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

				// Calculate the price suggest
				Goods goods = (Goods) session.getAttribute("router");

				double distance = mapUtils.parseJsonToGetDistance(mapUtils
						.getJSONFromUrl(mapUtils.makeDirectionURL(
								goods.getPickupAddress(),
								goods.getDeliveryAddress())));

				session.setAttribute("priceSuggest",
						Common.calculateGoodsPrice(weight, distance / 1000));

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
					ex.printStackTrace();
				}
				if (price == 0) {
					price = priceSuggest;
				}

				session.setAttribute("price", price);

				if (session.getAttribute("router") == null) {
					request.getRequestDispatcher("tao-hang-1.jsp").forward(
							request, response);
				}
				if (session.getAttribute("good") == null) {
					request.getRequestDispatcher("tao-hang-2.jsp").forward(
							request, response);
				}
				if (session.getAttribute("price") == null) {
					request.getRequestDispatcher("tao-hang-3.jsp").forward(
							request, response);
				} else {
					request.getRequestDispatcher("tao-hang-4.jsp").forward(
							request, response);
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
				// session.setAttribute("priceSuggest", 0.0);

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

				Goods g = new Goods(weight, notes, goodsCategoryID);
				session.setAttribute("good", g);

				// Calculate the price suggest
				Goods goods = (Goods) session.getAttribute("router");

				double distance = mapUtils.parseJsonToGetDistance(mapUtils
						.getJSONFromUrl(mapUtils.makeDirectionURL(
								goods.getPickupAddress(),
								goods.getDeliveryAddress())));

				session.setAttribute("priceSuggest",
						Common.calculateGoodsPrice(weight, distance / 1000));

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

			if (action.equalsIgnoreCase("createGoods")) {
				boolean isCreateGoods = request.getParameterMap().containsKey(
						"isCreateGoods");
				if (!isCreateGoods) {
					request.getRequestDispatcher("tao-hang-1.jsp").forward(
							request, response);
				} else {
					DateFormat dateFormat = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					String createTime = dateFormat.format(date);
					String pickupAdress = ((Goods) session
							.getAttribute("router")).getPickupAddress();
					String deliveryAddress = ((Goods) session
							.getAttribute("router")).getDeliveryAddress();
					String pickupTime = ((Goods) session.getAttribute("router"))
							.getPickupTime();
					String deliveryTime = ((Goods) session
							.getAttribute("router")).getDeliveryTime();
					int weight = ((Goods) session.getAttribute("good"))
							.getWeight();
					int GoodsCategoryID = ((Goods) session.getAttribute("good"))
							.getGoodsCategoryID();
					String notes = ((Goods) session.getAttribute("good"))
							.getNotes();
					Double price = (Double) session.getAttribute("price");

					LatLng latLngPickupAddress = mapUtils.parseJson(mapUtils
							.getJSONFromUrl(mapUtils
									.makeGeoCodeURL(pickupAdress)));

					double lngpickAddress = latLngPickupAddress.getLongitude();
					if (session.getAttribute("lngpickupAddress") != null) {
						lngpickAddress = (double) session
								.getAttribute("lngpickupAddress");
					}
					double latpickAddress = latLngPickupAddress.getLatitude();
					if (session.getAttribute("latpickupAddress") != null) {
						latpickAddress = (double) session
								.getAttribute("latpickupAddress");
					}

					LatLng latLngDeliveryAddress = mapUtils.parseJson(mapUtils
							.getJSONFromUrl(mapUtils
									.makeGeoCodeURL(deliveryAddress)));

					double lngdeliveryAddress = latLngDeliveryAddress
							.getLongitude();
					if (session.getAttribute("lngdeliveryAddress") != null) {
						lngpickAddress = (double) session
								.getAttribute("lngdeliveryAddress");
					}
					double latdeliveryAddress = latLngDeliveryAddress
							.getLatitude();
					if (session.getAttribute("latdeliveryAddress") != null) {
						latpickAddress = (double) session
								.getAttribute("latdeliveryAddress");
					}
					Owner owner = (Owner) session.getAttribute("owner");
					Goods goo = new Goods(weight, price,
							Common.changeFormatDate(pickupTime, "dd-MM-yyyy",
									"MM-dd-yyyy"), pickupAdress,
							Common.changeFormatDate(deliveryTime, "dd-MM-yyyy",
									"MM-dd-yyyy"), deliveryAddress,
							lngpickAddress, latpickAddress, lngdeliveryAddress,
							latdeliveryAddress, notes, createTime,
							Common.activate, owner.getOwnerID(),
							GoodsCategoryID);
					int idnewGood = goodsDao.insertGoods(goo);
					if (idnewGood != -1) {
						session.removeAttribute("router");
						session.removeAttribute("good");
						session.removeAttribute("price");
						session.setAttribute("detailGood1",
								goodsDao.getGoodsByID(idnewGood));
						request.setAttribute("messageSuccess",
								"Tạo hàng thành công. Hệ thống đưa ra những lộ trình thích hợp!");
						request.getRequestDispatcher(
								"ProcessServlet?btnAction=getSuggestionRoute&txtGoodsID="
										+ idnewGood).forward(request, response);
					} else {
						request.setAttribute("messageError",
								"Có lỗi khi tạo hàng. Vui lòng thử lại");
						RequestDispatcher rd = request
								.getRequestDispatcher("tao-hang-4.jsp");
						rd.forward(request, response);
					}
				}
				// if (session.getAttribute("router") == null) {
				// // response.sendRedirect("tao-hang-1.jsp");
				// request.getRequestDispatcher("tao-hang-1.jsp").forward(
				// request, response);
				// }
				// if (session.getAttribute("good") == null) {
				// // response.sendRedirect("tao-hang-2.jsp");
				// request.getRequestDispatcher("tao-hang-2.jsp").forward(
				// request, response);
				// }
				// if (session.getAttribute("price") == null) {
				// // response.sendRedirect("tao-hang-3.jsp");
				// request.getRequestDispatcher("tao-hang-3.jsp").forward(
				// request, response);
				// }
			} else if ("detailroutepickupAddress".equals(action)) {
				Goods good = (Goods) session.getAttribute("router");
				String address = good.getPickupAddress();
				LatLng latLngPickupAddress = mapUtils.parseJson(mapUtils
						.getJSONFromUrl(mapUtils.makeGeoCodeURL(address)));

				session.setAttribute("latpickupAddress",
						latLngPickupAddress.getLatitude());
				session.setAttribute("lngpickupAddress",
						latLngPickupAddress.getLongitude());
				RequestDispatcher rd = request
						.getRequestDispatcher("clearpickupAddress.jsp");
				rd.forward(request, response);

			} else if ("finishClearPickupAddress".equals(action)) {

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
			} else if ("detailroutedeliveryAddress".equals(action)) {

				Goods good = (Goods) session.getAttribute("router");
				String address = good.getDeliveryAddress();
				LatLng latLngDeliveryAddress = mapUtils.parseJson(mapUtils
						.getJSONFromUrl(mapUtils.makeGeoCodeURL(address)));

				session.setAttribute("latdeliveryAddress",
						latLngDeliveryAddress.getLatitude());
				session.setAttribute("lngdeliveryAddress",
						latLngDeliveryAddress.getLongitude());
				RequestDispatcher rd = request
						.getRequestDispatcher("cleardeliveryAddress.jsp");
				rd.forward(request, response);

			} else if ("finishClearDeliveryAddress".equals(action)) {

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
			if (action.equalsIgnoreCase("getSuggestionRoute")) {
				session.removeAttribute("listRouter");
				int goodsID = Integer.valueOf(request
						.getParameter("txtGoodsID"));
				List<Route> listRoute = matchingProcess
						.getSuggestionRoute(goodsID);
				if (listRoute.size() != 0) {
					List<Driver> listDriver = driverDao.getAllDriver();
					request.setAttribute("detailGoods",
							goodsDao.getGoodsByID(goodsID));
					request.setAttribute("listRoute", listRoute);
					request.setAttribute("listDriver", listDriver);
					request.getRequestDispatcher("goi-y-he-thong.jsp").forward(
							request, response);
				} else {
					request.getRequestDispatcher("goi-y-he-thong.jsp").forward(
							request, response);
				}
			} else if (action.equalsIgnoreCase("manageGoods")) {
				Owner owner = (Owner) session.getAttribute("owner");
				if (owner != null) {
					DecimalFormat df = new DecimalFormat("###.#");
					List<Goods> listGoods = goodsDao
							.getListGoodsByOwnerID(owner.getOwnerID());
					session.setAttribute("listGoods", listGoods);

					request.getRequestDispatcher("quan-ly-hang.jsp").forward(
							request, response);
				} else {
					request.getRequestDispatcher("dang-nhap.jsp").forward(
							request, response);
				}

			} else if (action.equalsIgnoreCase("updateGoods")) {

				int goodsID = Integer.valueOf(request
						.getParameter("txtGoodsID"));
				String pickupAddress = request.getParameter("txtpickupAddress");
				String pickupTime = request.getParameter("txtpickupTime");
				String deliveryAddress = request
						.getParameter("txtdeliveryAddress");
				String deliveryTime = request.getParameter("txtdeliveryTime");

				String createTime = request.getParameter("txtCreateTime");
				int ownerID = Integer.valueOf(request
						.getParameter("txtOwnerID"));
				int weight = Integer.valueOf(request.getParameter("txtWeight"));
				int goodsCategoryID = Integer.valueOf(request
						.getParameter("ddlgoodsCategoryID"));
				String notes = "";

				try {
					notes = notes + request.getParameter("txtNotes");
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				double price = Double.parseDouble(request
						.getParameter("txtPrice"));

				Goods goods = new Goods();
				LatLng latLngPickupAddress = mapUtils
						.parseJson(mapUtils.getJSONFromUrl(mapUtils
								.makeGeoCodeURL(pickupAddress)));
				LatLng latLngDeliveryAddress = mapUtils.parseJson(mapUtils
						.getJSONFromUrl(mapUtils
								.makeGeoCodeURL(deliveryAddress)));
				goods = new Goods(goodsID, weight, price,
						Common.changeFormatDate(pickupTime, "dd-MM-yyyy",
								"MM-dd-yyyy"), pickupAddress,
						Common.changeFormatDate(deliveryTime, "dd-MM-yyyy",
								"MM-dd-yyyy"), deliveryAddress,
						latLngPickupAddress.getLongitude(),
						latLngPickupAddress.getLatitude(),
						latLngDeliveryAddress.getLongitude(),
						latLngDeliveryAddress.getLatitude(), notes, createTime,
						Common.activate, ownerID, goodsCategoryID);

				if (routeDao.getListRouteInDealByGoodsID(goodsID).size() > 0) {
					request.setAttribute(
							"messageError",
							"Không thể cập nhật. Món hàng này đã đã được đồng ý hoặc đang trong quá trình thương lượng.");
				} else {
					int ret = goodsDao.updateGoods(goods);
					if (ret == 1) {
						request.setAttribute("messageSuccess",
								"Cập nhật thành công!");
					} else {
						request.setAttribute("messageError",
								"Cập nhật thất bại. Xin vui lòng thử lại sau!");
					}
				}
				request.getRequestDispatcher(
						"ProcessServlet?btnAction=viewDetailGoods&goodsID="
								+ goodsID).forward(request, response);
			} else if (action.equalsIgnoreCase("deleteGoods")) {
				int goodsID = Integer.valueOf(request
						.getParameter("txtGoodsID"));

				if (routeDao
						.getListRouteInDealPendingOrAcceptByGoodsID(goodsID)
						.size() > 0) {
					request.setAttribute("messageError",
							"Không thể xóa. Món hàng này đang trong quá trình thực hiện thương lượng!");
				} else {
					int ret = goodsDao.updateGoodsStatus(goodsID,
							Common.deactivate);
					if (ret == 1) {
						request.setAttribute("messageSuccess",
								"Xoá hàng thành công!");
						request.getRequestDispatcher(
								"ProcessServlet?btnAction=manageGoods")
								.forward(request, response);
					} else {
						request.setAttribute("messageError",
								"Xoá hàng thất bại. Xin vui lòng thử lại sau!");
					}
				}

				request.getRequestDispatcher(
						"ProcessServlet?btnAction=viewDetailGoods&goodsID="
								+ goodsID).forward(request, response);
			} else if ("viewDetailGoods".equals(action)) {
				try {
					int goodsID = Integer.valueOf(request
							.getParameter("goodsID"));
					Goods goods = goodsDao.getGoodsByID(goodsID);

					goods.setDeliveryTime(Common.changeFormatDate(
							goods.getDeliveryTime(), "yyyy-MM-dd HH:mm:ss.SSS",
							"dd-MM-yyyy"));
					goods.setPickupTime(Common.changeFormatDate(
							goods.getPickupTime(), "yyyy-MM-dd HH:mm:ss.SSS",
							"dd-MM-yyyy"));

					request.setAttribute("goodsDetail", goods);

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
				List<Goods> manageGood = goodsDao.getListGoodsByOwnerID(owner
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
		} catch (Exception e) {
			e.printStackTrace();
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
