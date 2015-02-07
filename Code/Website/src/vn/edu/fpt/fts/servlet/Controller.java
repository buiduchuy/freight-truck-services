package vn.edu.fpt.fts.servlet;

import java.io.IOException;
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
import vn.edu.fpt.fts.dao.AccountDAO;
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.DriverDAO;
import vn.edu.fpt.fts.dao.GoodsCategoryDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.OwnerDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.pojo.Driver;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.GoodsCategory;
import vn.edu.fpt.fts.pojo.Owner;
import vn.edu.fpt.fts.pojo.Route;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		RouteDAO rou = new RouteDAO();
		DealDAO dea = new DealDAO();
		GoodsDAO goodDao= new GoodsDAO();
		String action = request.getParameter("btnAction");
		HttpSession session = request.getSession(true);
		if ("offAccount".equals(action)) {
			session.removeAttribute("account");
			session.removeAttribute("router");
			session.removeAttribute("good");
			session.removeAttribute("price");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
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
		if ("viewDetailRouter".equals(action)) {
			int idRouter = Integer.parseInt(request.getParameter("idRouter"));
			Route router = rou.getRouteById(idRouter);
			session.setAttribute("viewDetailRoute", router);
			RequestDispatcher rd = request
					.getRequestDispatcher("chi-tiet-route.jsp");
			rd.forward(request, response);
		}
		if ("sendSuggest".equals(action)) {
			if (session.getAttribute("viewDetailRoute") != null
					&& session.getAttribute("newGood") != null) {
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				String createTime = dateFormat.format(date);
				Route route = (Route) session.getAttribute("viewDetailRoute");
				Goods good = (Goods) session.getAttribute("newGood");
				Deal deal = new Deal(good.getPrice(), good.getNotes(),
						createTime, 0, "owner", route.getDriverID(),
						good.getGoodsID(), 1);
				if (dea.insertDeal(deal) == 1) {
					session.setAttribute("messageCreateGood",
							"Gửi đề nghị thành công !");
					RequestDispatcher rd = request
							.getRequestDispatcher("goi-y-he-thong.jsp");
					rd.forward(request, response);
				}

			}
		}
		if ("manageGoods".equals(action)) {
			Owner owner=(Owner) session.getAttribute("owner");
			List<Goods> manageGood=goodDao.getListGoodsByOwnerID(owner);
			List<Goods> manageGood1= new ArrayList<Goods>();
			List<Goods> manageGood2= new ArrayList<Goods>();
			for(int i=0;i<manageGood.size();i++){
				if(manageGood.get(i).getActive()==1){
					manageGood1.add(manageGood.get(i));
				}else{
					manageGood2.add(manageGood.get(i));
				}
			}
			Goods[] list1= new Goods[manageGood1.size()];
			manageGood1.toArray(list1);
			Goods[] list2= new Goods[manageGood2.size()];
			manageGood2.toArray(list2);
			session.setAttribute("listGood1", list1);
			session.setAttribute("listGood2", list2);
			RequestDispatcher rd = request
					.getRequestDispatcher("quan-ly-hang.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("btnAction");
		HttpSession session = request.getSession(true);
		GoodsCategoryDAO goodCa = new GoodsCategoryDAO();
		AccountDAO acc = new AccountDAO();
		RouteDAO rou = new RouteDAO();
		OwnerDAO ow = new OwnerDAO();
		DriverDAO dri = new DriverDAO();
		if ("login".equals(action)) {
			String email = request.getParameter("txtEmail");
			String password = request.getParameter("txtPassword");
			session.removeAttribute("errorLogin");
			session.removeAttribute("account");
			if (acc.checkLoginAccount(email, password) != null) {

				Owner owner = ow.getOwnerByEmail(acc.checkLoginAccount(email,
						password));
				List<GoodsCategory> list = goodCa.getAllGoodsCategory();

				GoodsCategory[] typeGoods = new GoodsCategory[list.size()];
				list.toArray(typeGoods);
				session.setAttribute("typeGoods", typeGoods);
				session.setAttribute("owner", owner);
				session.setAttribute("account", owner.getLastName());
				if (session.getAttribute("namePage") != null) {
					String prePage = (String) session.getAttribute("namePage");
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
		if ("next1".equals(action)) {
			String pickupAddress = request.getParameter("txtpickupAddress");
			String pickupTime = request.getParameter("txtpickupTime");
			String deliveryAddress = request.getParameter("txtdeliveryAddress");
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
			String deliveryAddress = request.getParameter("txtdeliveryAddress");
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
			int weight = Integer.parseInt(request.getParameter("txtWeight"));
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
			int weight = Integer.parseInt(request.getParameter("txtWeight"));
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
			Common co = new Common();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String createTime = dateFormat.format(date);
			String pickupAdress = ((Goods) session.getAttribute("router"))
					.getPickupAddress();
			String deliveryAddress = ((Goods) session.getAttribute("router"))
					.getDeliveryAddress();
			String pickupTime = ((Goods) session.getAttribute("router"))
					.getPickupTime();
			String deliveryTime = ((Goods) session.getAttribute("router"))
					.getDeliveryTime();
			int weight = ((Goods) session.getAttribute("good")).getWeight();
			int GoodsCategoryID = ((Goods) session.getAttribute("good"))
					.getGoodsCategoryID();
			String notes = ((Goods) session.getAttribute("good")).getNotes();
			int price = (int) session.getAttribute("price");
			float a = Float.parseFloat("10");
			Owner owner = (Owner) session.getAttribute("owner");
			Goods goo = new Goods(weight, price,
					co.changeFormatDate(pickupTime), pickupAdress,
					co.changeFormatDate(deliveryTime), deliveryAddress, a, a,
					a, a, notes, createTime, 1, owner.getOwnerID(),
					GoodsCategoryID);
			GoodsDAO goodDao = new GoodsDAO();
			List<Route> list = rou.getAllRoute();
			Route[] listRou = new Route[list.size()];
			list.toArray(listRou);
			List<Driver> listDriver = dri.getAllDriver();
			Driver[] listDri = new Driver[listDriver.size()];
			listDriver.toArray(listDri);
			if (goodDao.insertGoods(goo) == 1) {
				List<Goods> listGood = goodDao.getListGoodsByOwnerID(owner);
				Goods goolast = listGood.get(listGood.size() - 1);
				session.setAttribute("newGood", goolast);
				session.setAttribute("listRouter", listRou);
				session.setAttribute("listDriver", listDri);
				session.setAttribute("messageCreateGood", "Tạo hàng thành công");
				RequestDispatcher rd = request
						.getRequestDispatcher("goi-y-he-thong.jsp");
				rd.forward(request, response);
			} else {
				session.setAttribute("errorCreateGood",
						"Có lỗi khi tạo hàng. Vui lòng thử lại");
				RequestDispatcher rd = request
						.getRequestDispatcher("tao-hang-4.jsp");
				rd.forward(request, response);
			}
		}

	}

}

