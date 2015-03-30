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
import vn.edu.fpt.fts.dao.AccountDAO;
import vn.edu.fpt.fts.dao.GoodsCategoryDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.OwnerDAO;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.GoodsCategory;
import vn.edu.fpt.fts.pojo.Owner;

/**
 * Servlet implementation class AccountServlet
 */
public class AccountServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7417093355444344728L;
	GoodsCategoryDAO goodsCategory = new GoodsCategoryDAO();
	AccountDAO accountDao = new AccountDAO();
	GoodsDAO goodsDao = new GoodsDAO();
	OwnerDAO ownerDao = new OwnerDAO();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountServlet() {
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
			request.setCharacterEncoding("UTF-8");
			String action = request.getParameter("btnAction");
			HttpSession session = request.getSession(true);
			
			if (action.equalsIgnoreCase("login")) {
				String email = request.getParameter("txtEmail");
				String password = request.getParameter("txtPassword");
				
				session.removeAttribute("errorLogin");
				session.removeAttribute("account");
				
				if (accountDao.checkLoginAccount(email, password) != null) {
					Owner owner = ownerDao.getOwnerByEmail(accountDao
							.checkLoginAccount(email, password).getEmail());
					List<GoodsCategory> l_goodsCategory = goodsCategory
							.getAllGoodsCategory();

					session.setAttribute("typeGoods", l_goodsCategory);
					session.setAttribute("owner", owner);
					session.setAttribute("account", owner.getLastName());
					
					List<Goods> manageGood = goodsDao
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
					Goods[] list1 = new Goods[manageGood1.size()];
					manageGood1.toArray(list1);
					session.setAttribute("listGood1", list1);
					if (session.getAttribute("namePage") != null) {
						String prePage = (String) session
								.getAttribute("namePage");
						RequestDispatcher rd = request
								.getRequestDispatcher(prePage);
						rd.forward(request, response);
					} else {
						RequestDispatcher rd = request
								.getRequestDispatcher("index.jsp");
						rd.forward(request, response);
					}
				} else {
					session.setAttribute("errorLogin",
							"Email hoặc mật khẩu không đúng. Xin đăng nhập lại !");
					RequestDispatcher rd = request
							.getRequestDispatcher("dang-nhap.jsp");
					rd.forward(request, response);
				}
			}
			if (action.equalsIgnoreCase("logout")) {
				session.removeAttribute("account");
				session.removeAttribute("router");
				session.removeAttribute("good");
				session.removeAttribute("price");
				session.removeAttribute("typeGoods");
				RequestDispatcher rd = request
						.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
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
