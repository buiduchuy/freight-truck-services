package vn.edu.fpt.fts.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.edu.fpt.fts.model.Driver;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("btnAction");
        HttpSession session = request.getSession(true);
        ArrayList <Driver>list = new ArrayList();
        if ("login".equals(action)) {
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            session.removeAttribute("errorLogin");
            session.removeAttribute("account");
            if ("abc@abc.com".equals(email) && "admin".equals(password)) {
                session.setAttribute("account", "KHUONGNGUYEN");
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            } else {
                session.setAttribute("errorLogin", "Invalid email or password! Please try again");
                RequestDispatcher rd = request.getRequestDispatcher("dang-nhap.jsp");
                rd.forward(request, response);
            }
        }
        if ("nTaohang1".equals(action)) {
            RequestDispatcher rd = request.getRequestDispatcher("tao-hang-2.jsp");
            rd.forward(request, response);
        }
        if ("nTaohang2".equals(action)) {
            RequestDispatcher rd = request.getRequestDispatcher("tao-hang-3.jsp");
            rd.forward(request, response);
        }
        if ("pTaohang2".equals(action)) {
            RequestDispatcher rd = request.getRequestDispatcher("tao-hang-1.jsp");
            rd.forward(request, response);
        }
        if ("nTaohang3".equals(action)) {
            RequestDispatcher rd = request.getRequestDispatcher("tao-hang-4.jsp");
            rd.forward(request, response);
        }
        if ("pTaohang3".equals(action)) {
            RequestDispatcher rd = request.getRequestDispatcher("tao-hang-2.jsp");
            rd.forward(request, response);
        }
        if ("pTaohang4".equals(action)) {
            RequestDispatcher rd = request.getRequestDispatcher("tao-hang-3.jsp");
            rd.forward(request, response);
        }
         if ("test".equals(action)) {
            RequestDispatcher rd = request.getRequestDispatcher("tao-hang-3.jsp");
            rd.forward(request, response);
        }
        if ("Taohang".equals(action)) {
            session.removeAttribute("errorTaohang");
//             session.setAttribute("errorTaohang", "Hệ thống không thể thêm hàng hóa");
//            Driver A = new Driver(1, "TpHCM", "11h 12/1/2015", "An Giang", "11h 13/1/2015", "20.000.000", "100", "11", "Cá, Rau");
//            Driver B = new Driver(2, "TpHCM", "11h 12/1/2015", "Hà Nội", "11h 13/1/2015", "20.000.000", "99", "11", "Cá, Rau");
//            Driver C = new Driver(3, "TpHCM", "11h 12/1/2015", "Hải Phòng", "11h 13/1/2015", "20.000.000", "98", "11", "Cá, Rau");
//            Driver D = new Driver(4, "TpHCM", "11h 12/1/2015", "Đà Nẵng", "11h 13/1/2015", "20.000.000", "97", "11", "Cá, Rau");
//            Driver E = new Driver(5, "TpHCM", "11h 12/1/2015", "Huế", "11h 13/1/2015", "20.000.000", "96", "11", "Cá, Rau");
//            Driver F = new Driver(6, "TpHCM", "11h 12/1/2015", "Long An", "11h 13/1/2015", "20.000.000", "95", "11", "Cá, Rau");
//            Driver H = new Driver(7, "TpHCM", "11h 12/1/2015", "Tây Ninh", "11h 13/1/2015", "20.000.000", "94", "11", "Cá, Rau");
//            Driver G = new Driver(8, "TpHCM", "11h 12/1/2015", "Nha Trang", "11h 13/1/2015", "20.000.000", "93", "11", "Cá, Rau");
//            Driver K = new Driver(9, "TpHCM", "11h 12/1/2015", "Đà Lạt", "11h 13/1/2015", "20.000.000", "92", "11", "Cá, Rau");
//            Driver L = new Driver(10, "TpHCM", "11h 12/1/2015", "Hội An", "11h 13/1/2015", "20.000.000", "91", "11", "Cá, Rau");
//            Driver M = new Driver(11, "TpHCM", "11h 12/1/2015", "Hội An", "11h 13/1/2015", "20.000.000", "91", "11", "Cá, Rau");
//            Driver N = new Driver(12, "TpHCM", "11h 12/1/2015", "Hội An", "11h 13/1/2015", "20.000.000", "91", "11", "Cá, Rau");
//            Driver O = new Driver(13, "TpHCM", "11h 12/1/2015", "Hội An", "11h 13/1/2015", "20.000.000", "91", "11", "Cá, Rau");
//            Driver P = new Driver(14, "TpHCM", "11h 12/1/2015", "Hội An", "11h 13/1/2015", "20.000.000", "91", "11", "Cá, Rau");
//            Driver Q = new Driver(15, "TpHCM", "11h 12/1/2015", "Hội An", "11h 13/1/2015", "20.000.000", "91", "11", "Cá, Rau");
//            Driver R = new Driver(16, "TpHCM", "11h 12/1/2015", "Hội An", "11h 13/1/2015", "20.000.000", "91", "11", "Cá, Rau");


//            list.add(A);
//            list.add(B);
//            list.add(C);
//            list.add(D);
//            list.add(E);
//            list.add(F);
//            list.add(H);
//            list.add(G);
//            list.add(K);
//            list.add(L);
//            list.add(M);
//            list.add(N);
//            list.add(O);
//            list.add(P);
//            list.add(Q);
//            list.add(R);
            session.setAttribute("TaiXe", list);
            RequestDispatcher rd = request.getRequestDispatcher("goi-y-he-thong.jsp");
            rd.forward(request, response);
        }
        if ("xemCT".equals(action)) {
            
            RequestDispatcher rd = request.getRequestDispatcher("chi-tiet-tai-xe.jsp");
            rd.forward(request, response);
        }
        if("offAccount".equals(action)){
            session.removeAttribute("account");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
	}

}
