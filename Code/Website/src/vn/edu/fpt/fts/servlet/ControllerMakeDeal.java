package vn.edu.fpt.fts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.DealOrder;
import vn.edu.fpt.fts.pojo.Driver;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Order;
import vn.edu.fpt.fts.pojo.Owner;
import vn.edu.fpt.fts.pojo.Route;

/**
 * Servlet implementation class ControllerMakeDeal
 */
public class ControllerMakeDeal extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllerMakeDeal() {
		super();
		// TODO Auto-generated constructor stub
	}

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
			if ("viewDetailRouter".equals(action)) {
				int idRouter = Integer.parseInt(request
						.getParameter("idRouter"));
				Route router = routeDao.getRouteById(idRouter);
				router.setStartTime(common.changeFormatDate(
						router.getStartTime(), "yyyy-MM-dd hh:mm:ss.s",
						"hh:mm dd-MM-yyyy"));
				router.setFinishTime(common.changeFormatDate(
						router.getFinishTime(), "yyyy-MM-dd hh:mm:ss.s",
						"dd-MM-yyyy"));
				session.setAttribute("viewDetailRoute", router);
				RequestDispatcher rd = request
						.getRequestDispatcher("chi-tiet-route.jsp");
				rd.forward(request, response);
			}
			if ("sendSuggest".equals(action)) {
				session.getAttribute("detailGood1");
				int idRoute = Integer.parseInt(request.getParameter("routeID"));
				if (session.getAttribute("newGood") != null) {
					Goods good = (Goods) session.getAttribute("newGood");
					Route route = routeDao.getRouteById(idRoute);
					DateFormat dateFormat = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					String createTime = dateFormat.format(date);
					List<Deal> listDealByGoodID = dealDao.getDealByGoodsID(good
							.getGoodsID());
					int idDealFa = 0;
					if (listDealByGoodID.size() != 0) {

						for (int i = 0; i < listDealByGoodID.size(); i++) {
							if (listDealByGoodID.get(i).getRouteID() == idRoute
									&& listDealByGoodID.get(i).getRefDealID() == 0) {
								idDealFa = listDealByGoodID.get(i).getDealID();
							}
						}
					}
					if (idDealFa == 0) {
						Deal newDeal = new Deal(good.getPrice(),
								good.getNotes(), createTime, "Owner",
								route.getRouteID(), good.getGoodsID(), 0, 1, 1);

						if (dealDao.insertDeal(newDeal) != -1) {
							Route[] listRouter = (Route[]) session
									.getAttribute("listRouter");
							List<Route> list = new ArrayList<Route>(
									Arrays.asList(listRouter));
							for (int i = 0; i < list.size(); i++) {
								if (list.get(i).getRouteID() == route
										.getRouteID()) {
									list.remove(i);
								}
							}
							Route[] listRou = new Route[list.size()];
							list.toArray(listRou);
							session.setAttribute("listRouter", listRou);
							session.setAttribute("messageSuccess",
									"Gửi đề nghị thành công");
							RequestDispatcher rd = request
									.getRequestDispatcher("goi-y-he-thong.jsp");
							rd.forward(request, response);
						} else {
							session.setAttribute("messageError",
									"Gửi đề nghị không được gửi thành công. Vui lòng thử lại!");
							RequestDispatcher rd = request
									.getRequestDispatcher("ControllerMakeDeal?btnAction=sendSuggest&routeID="
											+ idRoute);
							rd.forward(request, response);
						}
					} else {
						Deal newDeal = new Deal(good.getPrice(),
								good.getNotes(), createTime, "Owner",
								route.getRouteID(), good.getGoodsID(),
								idDealFa, 1, 1);

						if (dealDao.insertDeal(newDeal) != -1) {
							Route[] listRouter = (Route[]) session
									.getAttribute("listRouter");
							List<Route> list = new ArrayList<Route>(
									Arrays.asList(listRouter));
							for (int i = 0; i < list.size(); i++) {
								if (list.get(i).getRouteID() == route
										.getRouteID()) {
									list.remove(i);
								}
							}
							Route[] listRou = new Route[list.size()];
							list.toArray(listRou);
							session.setAttribute("listRouter", listRou);
							session.setAttribute("messageSuccess",
									"Gửi đề nghị thành công");
							RequestDispatcher rd = request
									.getRequestDispatcher("goi-y-he-thong.jsp");
							rd.forward(request, response);
						} else {
							session.setAttribute("messageError",
									"Gửi đề nghị không được gửi thành công. Vui lòng thử lại!");
							RequestDispatcher rd = request
									.getRequestDispatcher("ControllerMakeDeal?btnAction=sendSuggest&routeID="
											+ idRoute);
							rd.forward(request, response);
						}
					}

				}
			}
			if ("viewSuggest".equals(action)) {
				List<Deal> list = new ArrayList<Deal>();
				int idGood = Integer
						.parseInt(request.getParameter("txtIdGood"));
				List<Deal> listDealById = dealDao.getDealByGoodsID(idGood);
				for (int i = 0; i < listDealById.size(); i++) {
					if (listDealById.get(i).getRefDealID() == 0) {
						list.add(listDealById.get(i));
					}
				}
				Deal[] listDeal = new Deal[list.size()];
				list.toArray(listDeal);
				session.setAttribute("listDeal", listDeal);
				if (listDeal.length != 0) {
					RequestDispatcher rd = request
							.getRequestDispatcher("danh-sach-de-nghi.jsp");
					rd.forward(request, response);
				} else {
					session.setAttribute("messageError",
							"Hàng của bạn chưa có đề nghị. Vui lòng chọn 1 lộ trình phù hợp nhé!");
					RequestDispatcher rd = request
							.getRequestDispatcher("ControllerManageGoods?btnAction=suggestFromSystem&txtIdGood="
									+ idGood);
					rd.forward(request, response);
				}
			}
			if ("viewDetailDeal".equals(action)) {
				int idDeal = Integer.parseInt(request.getParameter("dealID"));
				Deal dealFa = dealDao.getDealByID(idDeal);
				List<Deal> list = new ArrayList<Deal>();
				List<Deal> listDealByGoodId = dealDao.getDealByGoodsID(dealFa
						.getGoodsID());
				for (int i = 0; i < listDealByGoodId.size(); i++) {
					if (listDealByGoodId.get(i).getRouteID() == dealFa
							.getRouteID()) {
						listDealByGoodId.get(i).setCreateTime(
								common.changeFormatDate(listDealByGoodId.get(i)
										.getCreateTime(),
										"yyyy-MM-dd hh:mm:ss.s",
										"hh:mm dd-MM-yyyy"));
						list.add(listDealByGoodId.get(i));
					}
				}
				Deal[] listDeal = new Deal[list.size()];
				list.toArray(listDeal);
				session.setAttribute("listDealDetail", listDeal);
				session.setAttribute("dealFa", dealFa);
				RequestDispatcher rd = request
						.getRequestDispatcher("chi-tiet-de-nghi.jsp");
				rd.forward(request, response);
			}
			if ("sendOffer".equals(action)) {
				int idDealFa = ((Deal) session.getAttribute("dealFa"))
						.getDealID();

				Deal dealFa = dealDao.getDealByID(idDealFa);
				int price = Integer.parseInt(request.getParameter("txtPrice"));
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				String createTime = dateFormat.format(date);
				String notes = "";
				try {
					notes = notes + request.getParameter("txtNotes");
				} catch (Exception ex) {

				}
				dealFa.setDealStatusID(1);
				dealFa.setPrice(price);
				dealFa.setNotes(notes);
				dealFa.setCreateTime(createTime);
				dealFa.setCreateBy("Owner");
				dealFa.setRefDealID(idDealFa);
				if (dealDao.insertDeal(dealFa) != -1) {
					session.setAttribute("messageSuccess",
							"Gửi đề nghị thành công");
					RequestDispatcher rd = request
							.getRequestDispatcher("ControllerMakeDeal?btnAction=viewSuggest&txtIdGood="
									+ dealFa.getGoodsID());
					rd.forward(request, response);
				} else {
					session.setAttribute("messageError",
							"Không thể gửi đề nghị. Vui lòng thử lại nhé!");
					RequestDispatcher rd = request
							.getRequestDispatcher("ControllerMakeDeal?btnAction=viewDetailDeal&dealID="
									+ idDealFa);
					rd.forward(request, response);
				}

			}
			if ("confirmDeal".equals(action)) {
				int idDeal = Integer.parseInt(request.getParameter("idDeal"));
				try {
					Deal dealConfirm = dealDao.getDealByID(idDeal);
					List<Deal> listDealByID = dealDao
							.getDealByGoodsID(dealConfirm.getGoodsID());
					for (int i = 0; i < listDealByID.size(); i++) {
						if (listDealByID.get(i).getDealID() != idDeal) {
							listDealByID.get(i).setActive(0);
							dealDao.updateDeal(listDealByID.get(i));
						}
					}
					DateFormat dateFormat = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					String createTime = dateFormat.format(date);
					Order newOrder = new Order(dealConfirm.getPrice(), false,
							false, false, createTime, 1);
					
					DealOrder newDealOrder= new DealOrder(idDeal, orderDao.insertOrder(newOrder));
					dealOrderDao.insertDealOrder(newDealOrder);
					Goods goodChangeStatus = goodDao.getGoodsByID(dealConfirm
							.getGoodsID());
					goodChangeStatus.setActive(2);
					goodDao.updateGoods(goodChangeStatus);
					session.setAttribute("messageSuccess",
							"Hoàn thành hoá đơn!");
					RequestDispatcher rd = request
							.getRequestDispatcher("ControllerManageOrder?btnAction=manageOrder");
					rd.forward(request, response);
				} catch (Exception ex) {
					session.setAttribute("messageError",
							"Không thể gửi đề nghị. Vui lòng thử lại nhé!");

				}
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
