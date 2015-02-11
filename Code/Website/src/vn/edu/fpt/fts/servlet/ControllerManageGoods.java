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
import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.DealOrderDAO;
import vn.edu.fpt.fts.dao.DriverDAO;
import vn.edu.fpt.fts.dao.GoodsCategoryDAO;
import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.dao.OwnerDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.pojo.Goods;
import vn.edu.fpt.fts.pojo.Owner;

/**
 * Servlet implementation class ControllerManageGoods
 */
public class ControllerManageGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerManageGoods() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
			Common common= new Common();
			if ("manageGoods".equals(action)) {
				Owner owner = (Owner) session.getAttribute("owner");
				List<Goods> manageGood = goodDao.getListGoodsByOwnerID(owner.getOwnerID());
				List<Goods> manageGood1 = new ArrayList<Goods>();
				List<Goods> manageGood2 = new ArrayList<Goods>();
				for (int i = 0; i < manageGood.size(); i++) {
					if (manageGood.get(i).getActive() == 1) {
						manageGood1.add(manageGood.get(i));
					}
					if (manageGood.get(i).getActive() == 2) {
						manageGood2.add(manageGood.get(i));
					}
				}
				Goods[] list1 = new Goods[manageGood1.size()];
				manageGood1.toArray(list1);
				Goods[] list2 = new Goods[manageGood2.size()];
				manageGood2.toArray(list2);
				session.setAttribute("listGood1", list1);
				session.setAttribute("listGood2", list2);
				RequestDispatcher rd = request
						.getRequestDispatcher("quan-ly-hang.jsp");
				rd.forward(request, response);
			}
			if ("viewDetailGood1".equals(action)) {
				try {
					int idGood = Integer.parseInt(request.getParameter("idGood"));
					Goods good = goodDao.getGoodsByID(idGood);
					session.setAttribute("detailGood1", good);
					RequestDispatcher rd = request
							.getRequestDispatcher("chi-tiet-hang.jsp");
					rd.forward(request, response);
				} catch (Exception ex) {

				}
			}if ("viewDetailGood2".equals(action)) {
				try {
					int idGood = Integer.parseInt(request.getParameter("idGood"));
					Goods good = goodDao.getGoodsByID(idGood);
					session.setAttribute("detailGood2", good);
					RequestDispatcher rd = request
							.getRequestDispatcher("chi-tiet-order.jsp");
					rd.forward(request, response);
				} catch (Exception ex) {

				}
			}if ("updateGood".equals(action)) {
				Goods go = (Goods) session.getAttribute("detailGood1");
				String pickupAddress = request.getParameter("txtpickupAddress");
				String pickupTime = request.getParameter("txtpickupTime");
				String deliveryAddress = request.getParameter("txtdeliveryAddress");
				String deliveryTime = request.getParameter("txtdeliveryTime");
				int weight = Integer.parseInt(request.getParameter("txtWeight"));
				int goodsCategoryID = Integer.parseInt(request
						.getParameter("ddlgoodsCategoryID"));
				String notes = "";
				try {
					notes = notes + request.getParameter("txtNotes");
				} catch (Exception ex) {

				}
				float a = Float.parseFloat("10");
				double price = Double.parseDouble(request.getParameter("txtPrice"));
				Goods good = new Goods(go.getGoodsID(), weight, price, pickupTime,
						pickupAddress, deliveryTime, deliveryAddress, a, a, a, a,
						notes, go.getCreateTime().toString(), 1, go.getOwnerID(),
						goodsCategoryID);

				if (goodDao.updateGoods(good) == 1) {

					session.setAttribute("messageUpdateGood", "Cập nhật thành công");
					RequestDispatcher rd = request
							.getRequestDispatcher("ControllerManageGoods?btnAction=viewDetailGood1&idGood="
									+ go.getGoodsID());
					rd.forward(request, response);
				} else {

				}
			}
			if ("deleteGood".equals(action)) {
				Goods go = (Goods) session.getAttribute("detailGood1");
				go.setActive(0);
				if (goodDao.updateGoods(go) == 1) {
					Owner owner = (Owner) session.getAttribute("owner");
					List<Goods> manageGood = goodDao.getListGoodsByOwnerID(owner.getOwnerID());
					List<Goods> manageGood1 = new ArrayList<Goods>();
					List<Goods> manageGood2 = new ArrayList<Goods>();
					for (int i = 0; i < manageGood.size(); i++) {
						if (manageGood.get(i).getActive() == 1) {
							manageGood1.add(manageGood.get(i));
						}
						if (manageGood.get(i).getActive() == 2) {
							manageGood2.add(manageGood.get(i));
						}
					}
					Goods[] list1 = new Goods[manageGood1.size()];
					manageGood1.toArray(list1);
					Goods[] list2 = new Goods[manageGood2.size()];
					manageGood2.toArray(list2);
					session.setAttribute("listGood1", list1);
					session.setAttribute("listGood2", list2);
					RequestDispatcher rd = request
							.getRequestDispatcher("quan-ly-hang.jsp");
					rd.forward(request, response);
				} else {

				}
			}
			if ("viewDetailGood1".equals(action)) {
				try {
					int idGood = Integer.parseInt(request.getParameter("idGood"));
					Goods good = goodDao.getGoodsByID(idGood);
					session.setAttribute("detailGood1", good);
					RequestDispatcher rd = request
							.getRequestDispatcher("chi-tiet-hang.jsp");
					rd.forward(request, response);
				} catch (Exception ex) {

				}
			}
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet Controller</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet Controller at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
