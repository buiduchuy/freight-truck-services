package vn.edu.fpt.fts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.AccountDAO;
import vn.edu.fpt.fts.dao.EmployeeDAO;
import vn.edu.fpt.fts.dao.GoodsCategoryDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.OwnerDAO;
import vn.edu.fpt.fts.pojo.Account;
import vn.edu.fpt.fts.pojo.Employee;
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
	EmployeeDAO employeeDao = new EmployeeDAO();

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

				Account account = accountDao.checkLoginAccount(email, password);

				if (account != null) {
					if (account.getRoleID() == Common.role_owner) {

						Owner owner = ownerDao.getOwnerByEmail(account
								.getEmail());
						List<GoodsCategory> l_goodsCategory = goodsCategory
								.getAllGoodsCategory();

						session.setAttribute("typeGoods", l_goodsCategory);
						session.setAttribute("owner", owner);
						session.setAttribute("account", owner.getLastName() + " " + owner.getFirstName());

						List<Goods> listGoodsByOwner = goodsDao
								.getListGoodsByOwnerID(owner.getOwnerID());
						List<Goods> listGoods = new ArrayList<Goods>();
						for (int i = 0; i < listGoodsByOwner.size(); i++) {
							if (listGoodsByOwner.get(i).getActive() == 1) {
								listGoodsByOwner.get(i).setPickupTime(
										Common.changeFormatDate(
												listGoodsByOwner.get(i)
														.getPickupTime(),
												"yyyy-MM-dd hh:mm:ss.s",
												"dd-MM-yyyy"));
								listGoodsByOwner.get(i).setDeliveryTime(
										Common.changeFormatDate(
												listGoodsByOwner.get(i)
														.getDeliveryTime(),
												"yyyy-MM-dd hh:mm:ss.s",
												"dd-MM-yyyy"));
								listGoods.add(listGoodsByOwner.get(i));
							}
						}
						session.setAttribute("listGoods", listGoods);

						String returnUrl = request.getParameter("ReturnUrl");

						if (!returnUrl.isEmpty()) {
							if (returnUrl.contains("loginPage")) {
								response.sendRedirect("ProcessServlet");
							} else {
								response.sendRedirect(returnUrl);
							}
						} else {
							request.getRequestDispatcher("ProcessServlet")
									.forward(request, response);
						}
					} else if (account.getRoleID() == Common.role_staff) {
						Employee employee = employeeDao
								.getEmployeeByEmail(account.getEmail());
						List<GoodsCategory> l_goodsCategory = goodsCategory
								.getAllGoodsCategory();

						session.setAttribute("typeGoods", l_goodsCategory);
						session.setAttribute("employee", employee);

						request.getRequestDispatcher("admin/index.jsp")
								.forward(request, response);

					}

				} else {
					session.setAttribute("errorLogin",
							"Email hoặc mật khẩu không đúng. Xin đăng nhập lại !");
					request.getRequestDispatcher("dang-nhap.jsp").forward(
							request, response);
				}

			} else if (action.equalsIgnoreCase("logout")) {
				session.invalidate();
				request.getRequestDispatcher("index.jsp").forward(request,
						response);
			} else if (action.equalsIgnoreCase("loginPage")) {
				request.getRequestDispatcher("dang-nhap.jsp").forward(request,
						response);
			} else if (action.equalsIgnoreCase("registerPage")) {
				request.getRequestDispatcher("dang-ky.jsp").forward(request,
						response);
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
