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
import vn.edu.fpt.fts.pojo.Owner;
import vn.edu.fpt.fts.pojo.Route;
import vn.edu.fpt.fts.process.DealProcess;

/**
 * Servlet implementation class DealServlet
 */
public class DealServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8407199342277331481L;
	DealProcess dealProcess = new DealProcess();
	RouteDAO routeDao = new RouteDAO();
	GoodsDAO goodsDao = new GoodsDAO();
	DealDAO dealDao = new DealDAO();

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

			if (action.equalsIgnoreCase("routeDetail")) {
				int routeID = Integer.parseInt(request.getParameter("routeID"));
				int goodsID = Integer.parseInt(request.getParameter("goodsID"));
				Route route = routeDao.getRouteByID(routeID);
				route.setStartTime(Common.changeFormatDate(
						route.getStartTime(), "yyyy-MM-dd hh:mm:ss.s",
						"hh:mm dd-MM-yyyy"));
				route.setFinishTime(Common.changeFormatDate(
						route.getFinishTime(), "yyyy-MM-dd hh:mm:ss.s",
						"dd-MM-yyyy"));
				request.setAttribute("goodsID", goodsID);
				session.setAttribute("viewDetailRoute", route);
				RequestDispatcher rd = request
						.getRequestDispatcher("chi-tiet-route.jsp");
				rd.forward(request, response);
			} else if (action.equalsIgnoreCase("createDeal")) {

				int goodsID = Integer.parseInt(request.getParameter("goodsID"));
				int routeID = Integer.parseInt(request.getParameter("routeID"));

				List<Deal> listCurrentDeal = dealDao
						.getListDealByGoodsIDAndRouteID(goodsID, routeID,
								Common.deal_pending);

				if (listCurrentDeal.size() >= Common.maxCounterTime) {
					request.setAttribute(
							"messageError",
							"Bạn không thể thực hiện gửi đề nghị quá "
									+ Common.maxCounterTime
									+ " lần! Xin vui lòng chờ trả lời của tài xế.");
					request.getRequestDispatcher(
							"ProcessServlet?btnAction=manageDeal").forward(
							request, response);

				} else {
					Goods goods = goodsDao.getGoodsByID(goodsID);
					if (goods.getActive() == Common.deactivate) {
						request.setAttribute("messageError",
								"Hàng này đã có hoá đơn. Bạn không thể thực hiện thao tác này!");
						request.getRequestDispatcher(
								"ProcessServlet?btnAction=manageDeal").forward(
								request, response);
					} else {
						if (goods != null) {
							DateFormat dateFormat = new SimpleDateFormat(
									"yyyy/MM/dd HH:mm:ss");
							Date date = new Date();
							String createTime = dateFormat.format(date);

							Deal deal = new Deal();

							deal.setPrice(goods.getPrice());
							deal.setNotes(goods.getNotes());
							deal.setCreateTime(createTime);
							deal.setCreateBy("owner");
							deal.setRouteID(routeID);
							deal.setGoodsID(goodsID);
							deal.setDealStatusID(Common.deal_pending);
							deal.setActive(Common.activate);

							int dealID = dealDao.insertDeal(deal);

							if (dealID != 0) {

								request.setAttribute("messageSuccess",
										"Gửi đề nghị thành công!");
								request.getRequestDispatcher(
										"ProcessServlet?btnAction=viewDetailDeal&dealID="
												+ dealID).forward(request,
										response);
							} else {
								request.setAttribute("messageError",
										"Không thể gửi đề nghị. Xin vui lòng thử lại sau!");
								request.getRequestDispatcher(
										"ProcessSerlvet?btnAction=manageDeal")
										.forward(request, response);
							}
						}
					}
				}
			} else if (action.equalsIgnoreCase("sendDeal")) {
				int idDealFa = ((Deal) session.getAttribute("dealFa"))
						.getDealID();

				Deal dealFa = dealDao.getDealByID(idDealFa);
				int idGood = dealFa.getGoodsID();
				if (goodsDao.getGoodsByID(idGood).getActive() == Common.deactivate) {
					request.setAttribute("messageError",
							"Hàng này đã có hoá đơn. Bạn không thể thực hiện thao tác này!");
					request.getRequestDispatcher(
							"ProcessServlet?btnAction=manageOrder").forward(
							request, response);
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
					if (dealDao.insertDeal(dealFa) != -1) {
						request.setAttribute("messageSuccess",
								"Gửi đề nghị thành công");
						request.getRequestDispatcher(
								"ProcessServlet?btnAction=viewDetailDeal&dealID="
										+ idDealFa).forward(request, response);
					} else {
						request.setAttribute("messageError",
								"Không thể gửi đề nghị. Xin vui lòng thử lại sau!");
						request.getRequestDispatcher(
								"ProcessServlet?btnAction=viewDetailDeal&dealID="
										+ idDealFa).forward(request, response);
					}
				}
			} else if (action.equalsIgnoreCase("acceptDeal")) {
				int dealID = Integer.parseInt(request.getParameter("dealID"));
				int goodsID = dealDao.getDealByID(dealID).getGoodsID();
				if (goodsDao.getGoodsByID(goodsID).getActive() == Common.deactivate) {
					request.setAttribute("messageError",
							"Hàng này đã có hoá đơn. Bạn không thể thực hiện thao tác này!");
					request.getRequestDispatcher(
							"ProcessServlet?btnAction=manageDeal").forward(
							request, response);
				} else {

					Deal acceptDeal = dealDao.getDealByID(dealID);
					int ret = dealProcess.acceptDeal1(acceptDeal);

					if (ret == 1) {
						request.setAttribute("messageSuccess",
								"Đồng ý đề nghị thành công. Xin vui kiểm tra thông tin hóa đơn!");
						request.getRequestDispatcher(
								"ProcessSerlvet?btnAction=manageOrder")
								.forward(request, response);
					} else {
						request.setAttribute("messageError",
								"Không thể đồng ý đề nghị này. Xin vui lòng thử lại sau!");
						request.getRequestDispatcher(
								"ProcessServlet?btnAction=manageDeal").forward(
								request, response);
					}
				}
			} else if (action.equalsIgnoreCase("declineDeal")) {
				int dealID = Integer.parseInt(request.getParameter("dealID"));
				int goodsID = dealDao.getDealByID(dealID).getGoodsID();
				if (goodsDao.getGoodsByID(goodsID).getActive() == Common.deactivate) {
					request.setAttribute("messageError",
							"Hàng này đã có hoá đơn. Bạn không thể thực hiện thao tác này!");

					request.getRequestDispatcher(
							"ProcessSerlvet?btnAction=manageOrder").forward(
							request, response);
				} else {
					Deal declineDeal = dealDao.getDealByID(dealID);
					int ret = dealProcess.declineDeal1(declineDeal);
					if (ret == 1) {
						request.setAttribute("messageSuccess",
								"Từ chối đề nghị thành công!");
						request.getRequestDispatcher(
								"ProcessSerlvet?btnAction=viewDetailDeal&dealID="
										+ dealID).forward(request, response);
					} else {
						request.setAttribute("messageError",
								"Không thể từ chối đề nghị này. Xin vui lòng thử lại sau!");
						request.getRequestDispatcher(
								"ProcessSerlvet?btnAction=viewDetailDeal&dealID="
										+ dealID).forward(request, response);
					}
				}
			} else if (action.equalsIgnoreCase("cancelDeal")) {
				int dealID = Integer.parseInt(request.getParameter("dealID"));
				int goodsID = dealDao.getDealByID(dealID).getGoodsID();
				if (goodsDao.getGoodsByID(goodsID).getActive() == Common.deactivate) {
					request.setAttribute("messageError",
							"Hàng này đã có hoá đơn. Bạn không thể thực hiện thao tác này!");
					request.getRequestDispatcher(
							"ProcessServlet?btnAction=manageDeal").forward(
							request, response);
				} else {
					Deal cancelDeal = dealDao.getDealByID(dealID);

					int ret = dealProcess.cancelDeal1(cancelDeal);

					if (ret == 1) {
						request.setAttribute("messageSuccess",
								"Hủy đề nghị thành công!");
						request.getRequestDispatcher(
								"ProcessServlet?btnAction=viewDetailDeal&dealID="
										+ dealID).forward(request, response);
					} else {
						request.setAttribute("messageError",
								"Không thể hủy đề nghị. Xin vui lòng thử lại sau!");
						request.getRequestDispatcher(
								"ProcessServlet?btnAction=viewDetailDeal&dealID="
										+ dealID).forward(request, response);
					}
				}
			} else if (action.equalsIgnoreCase("manageDeal")) {
				Owner owner = (Owner) session.getAttribute("owner");
				List<Deal> listDeal = dealDao.getDealByOwnerID(owner
						.getOwnerID());
				request.setAttribute("listDeal", listDeal);
				
				request.getRequestDispatcher("manage-deal.jsp").forward(
						request, response);
			} else if (action.equalsIgnoreCase("viewDetailDeal")) {
				int dealID = Integer.parseInt(request.getParameter("dealID"));
				Deal dealFa = dealDao.getDealByID(dealID);
				List<Deal> listDeal = new ArrayList<Deal>();
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
						listDeal.add(listDealByGoodId.get(i));
					}
				}
				session.setAttribute("listDealDetail", listDeal);
				session.setAttribute("sizeHistory", listDeal.size());
				session.setAttribute("dealFa", dealFa);
				request.getRequestDispatcher("chi-tiet-de-nghi.jsp").forward(
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
