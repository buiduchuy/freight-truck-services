package vn.edu.fpt.fts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
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
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Route;
import vn.edu.fpt.fts.process.DealProcess;

/**
 * Servlet implementation class DealServlet
 */
public class DealServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DealServlet() {
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
			DealProcess dealProcess = new DealProcess();
			RouteDAO routeDao = new RouteDAO();
			GoodsDAO goodDao = new GoodsDAO();
			DealDAO dealDao = new DealDAO();
			if ("viewDetailRouter".equals(action)) {
				int idRouter = Integer.parseInt(request
						.getParameter("idRouter"));
				Route router = routeDao.getRouteByID(idRouter);
				router.setStartTime(Common.changeFormatDate(
						router.getStartTime(), "yyyy-MM-dd hh:mm:ss.s",
						"hh:mm dd-MM-yyyy"));
				router.setFinishTime(Common.changeFormatDate(
						router.getFinishTime(), "yyyy-MM-dd hh:mm:ss.s",
						"dd-MM-yyyy"));
				session.setAttribute("viewDetailRoute", router);
				RequestDispatcher rd = request
						.getRequestDispatcher("chi-tiet-route.jsp");
				rd.forward(request, response);
			}
			if ("sendSuggest".equals(action)) {
				session.getAttribute("detailGood1");
				int idGood = Integer
						.parseInt(request.getParameter("txtgoodID"));
				if (goodDao.getGoodsByID(idGood).getActive() == Common.deactivate) {
					session.setAttribute("messageError",
							"Hàng đã được chuyển thành hoá đơn không thể thực hiện thao tác!");
					RequestDispatcher rd = request
							.getRequestDispatcher("OrderServlet?btnAction=manageOrder");
					rd.forward(request, response);
				} else {
					int idRoute = Integer.parseInt(request
							.getParameter("txtrouteID"));
					if (goodDao.getGoodsByID(idGood) != null) {
						Goods good = goodDao.getGoodsByID(idGood);
						Route route = routeDao.getRouteByID(idRoute);
						DateFormat dateFormat = new SimpleDateFormat(
								"yyyy/MM/dd HH:mm:ss");
						Date date = new Date();
						String createTime = dateFormat.format(date);
						List<Deal> listDealByGoodID = dealDao
								.getDealByGoodsID(good.getGoodsID());
						int idDealFa = 0;
						if (listDealByGoodID.size() != 0) {

							for (int i = 0; i < listDealByGoodID.size(); i++) {
								if (listDealByGoodID.get(i).getRouteID() == idRoute
										&& listDealByGoodID.get(i)
												.getRefDealID() == 0) {
									idDealFa = listDealByGoodID.get(i)
											.getDealID();
								}
							}
						}
						if (idDealFa == 0) {
							Deal newDeal = new Deal(good.getPrice(),
									good.getNotes(), createTime, "owner",
									route.getRouteID(), good.getGoodsID(), 0,
									1, 1);

							if (dealDao.insertDeal(newDeal) != -1) {

								session.setAttribute("messageSuccess",
										"Gửi đề nghị thành công");
								RequestDispatcher rd = request
										.getRequestDispatcher("ControllerManageGoods?btnAction=suggestFromSystem&txtIdGood="
												+ idGood);
								rd.forward(request, response);
							} else {
								session.setAttribute("messageError",
										"Gửi đề nghị không được gửi thành công. Vui lòng thử lại!");
								RequestDispatcher rd = request
										.getRequestDispatcher("DealServlet?btnAction=sendSuggest&routeID="
												+ idRoute);
								rd.forward(request, response);
							}
						} else {
							Deal newDeal = new Deal(good.getPrice(),
									good.getNotes(), createTime, "owner",
									route.getRouteID(), good.getGoodsID(),
									idDealFa, 1, 1);

							if (dealDao.insertDeal(newDeal) != -1) {

								session.setAttribute("messageSuccess",
										"Gửi đề nghị thành công");
								RequestDispatcher rd = request
										.getRequestDispatcher("ControllerManageGoods?btnAction=suggestFromSystem&txtIdGood="
												+ idGood);
								rd.forward(request, response);
							} else {
								session.setAttribute("messageError",
										"Gửi đề nghị không được gửi thành công. Vui lòng thử lại!");
								RequestDispatcher rd = request
										.getRequestDispatcher("DealServlet?btnAction=sendSuggest&routeID="
												+ idRoute);
								rd.forward(request, response);
							}
						}

					}
				}
			}
			if ("viewSuggest".equals(action)) {
				int idGood = Integer
						.parseInt(request.getParameter("txtIdGood"));
				List<Deal> listDealByGoodId = dealDao.getDealByGoodsID(idGood);
				List<Deal> listDealFa = new ArrayList<Deal>();
				if (listDealByGoodId.size() == 0) {
					session.setAttribute("messageError",
							"Hàng của bạn chưa có đề nghị. Vui lòng chọn 1 lộ trình phù hợp nhé!");
					RequestDispatcher rd = request
							.getRequestDispatcher("ControllerManageGoods?btnAction=suggestFromSystem&txtIdGood="
									+ idGood);
					rd.forward(request, response);
				} else {

					for (Deal deal : listDealByGoodId) {
						if (deal.getRefDealID() == 0) {
							listDealFa.add(deal);
						}
					}
					if (listDealFa.size() != 0) {
						session.removeAttribute("listDeal");
						Deal[] listFa = new Deal[listDealFa.size()];
						listDealFa.toArray(listFa);
						session.setAttribute("listRoute",
								routeDao.getAllRoute());
						session.setAttribute("listDeal", listFa);
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
								Common.changeFormatDate(listDealByGoodId.get(i)
										.getCreateTime(),
										"yyyy-MM-dd hh:mm:ss.s",
										"hh:mm dd-MM-yyyy"));
						list.add(listDealByGoodId.get(i));
					}
				}
				Deal[] listDeal = new Deal[list.size()];
				list.toArray(listDeal);
				session.setAttribute("listDealDetail", listDeal);
				session.setAttribute("sizeHistory", listDeal.length);
				session.setAttribute("dealFa", dealFa);
				RequestDispatcher rd = request
						.getRequestDispatcher("chi-tiet-de-nghi.jsp");
				rd.forward(request, response);
			}
			if ("sendOffer".equals(action)) {
				int idDealFa = ((Deal) session.getAttribute("dealFa"))
						.getDealID();

				Deal dealFa = dealDao.getDealByID(idDealFa);
				int idGood = dealFa.getGoodsID();
				if (goodDao.getGoodsByID(idGood).getActive() == Common.deactivate) {
					session.setAttribute("messageError",
							"Hàng đã được chuyển thành hoá đơn không thể thực hiện thao tác!");
					RequestDispatcher rd = request
							.getRequestDispatcher("OrderServlet?btnAction=manageOrder");
					rd.forward(request, response);
				} else {
					int price = Integer.parseInt(request
							.getParameter("txtPrice"));
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
					dealFa.setCreateBy("owner");
					dealFa.setRefDealID(idDealFa);
					if (dealDao.insertDeal(dealFa) != -1) {
						session.setAttribute("messageSuccess",
								"Gửi đề nghị thành công");
						RequestDispatcher rd = request
								.getRequestDispatcher("DealServlet?btnAction=viewDetailDeal&dealID="
										+ idDealFa);
						rd.forward(request, response);
					} else {
						session.setAttribute("messageError",
								"Không thể gửi đề nghị. Vui lòng thử lại nhé!");
						RequestDispatcher rd = request
								.getRequestDispatcher("DealServlet?btnAction=viewDetailDeal&dealID="
										+ idDealFa);
						rd.forward(request, response);
					}
				}
			}
			if ("confirmDeal".equals(action)) {
				int idDeal = Integer.parseInt(request.getParameter("idDeal"));
				int idGood = dealDao.getDealByID(idDeal).getGoodsID();
				if (goodDao.getGoodsByID(idGood).getActive() == Common.deactivate) {
					session.setAttribute("messageError",
							"Hàng đã được chuyển thành hoá đơn không thể thực hiện thao tác!");
					RequestDispatcher rd = request
							.getRequestDispatcher("OrderServlet?btnAction=manageOrder");
					rd.forward(request, response);
				} else {
					if (dealProcess.acceptDeal1(dealDao.getDealByID(idDeal)) != 0) {
						session.setAttribute("messageSuccess",
								"Hoàn thành hoá đơn!");
						RequestDispatcher rd = request
								.getRequestDispatcher("OrderServlet?btnAction=manageOrder");
						rd.forward(request, response);
					} else {
						session.setAttribute("messageError",
								"Không thể gửi đề nghị. Vui lòng thử lại nhé!");

					}
				}
			}
			if ("declineDeal".equals(action)) {
				int idDeal = Integer.parseInt(request.getParameter("idDeal"));
				int idGood = dealDao.getDealByID(idDeal).getGoodsID();
				if (goodDao.getGoodsByID(idGood).getActive() == Common.deactivate) {
					session.setAttribute("messageError",
							"Hàng đã được chuyển thành hoá đơn không thể thực hiện thao tác!");
					RequestDispatcher rd = request
							.getRequestDispatcher("OrderServlet?btnAction=manageOrder");
					rd.forward(request, response);
				} else {
					Deal declineDeal = dealDao.getDealByID(idDeal);
					declineDeal.setDealStatusID(3);
					if (dealProcess.declineDeal1(declineDeal) != 0) {
						RequestDispatcher rd = request
								.getRequestDispatcher("DealServlet?btnAction=viewDetailDeal&dealID="
										+ dealDao.getDealByID(idDeal)
												.getRefDealID());
						rd.forward(request, response);
					}
				}
			}
			if ("cancelDeal".equals(action)) {
				int idDeal = Integer.parseInt(request.getParameter("idDeal"));
				int idGood = dealDao.getDealByID(idDeal).getGoodsID();
				if (goodDao.getGoodsByID(idGood).getActive() == Common.deactivate) {
					session.setAttribute("messageError",
							"Hàng đã được chuyển thành hoá đơn không thể thực hiện thao tác!");
					RequestDispatcher rd = request
							.getRequestDispatcher("OrderServlet?btnAction=manageOrder");
					rd.forward(request, response);
				} else {
					session.removeAttribute("listDeal");
					Deal cancelDeal = dealDao.getDealByID(idDeal);
					if (dealProcess.cancelDeal1(cancelDeal) != 0) {
						cancelDeal.setDealStatusID(4);
						RequestDispatcher rd = request
								.getRequestDispatcher("DealServlet?btnAction=viewDetailDeal&dealID="
										+ dealDao.getDealByID(idDeal)
												.getRefDealID());
						rd.forward(request, response);
					}
				}
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
