package vn.edu.fpt.fts.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import vn.edu.fpt.fts.dao.NotificationDAO;
import vn.edu.fpt.fts.pojo.Notification;

/**
 * Servlet implementation class NotificationServlet
 */
public class NotificationServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -997087893153118004L;
	NotificationDAO notificationDao = new NotificationDAO();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NotificationServlet() {
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
		String action = request.getParameter("btnAction");
		if (action.equalsIgnoreCase("getNotification")) {
			String email = request.getParameter("email");
			String jsonString = "EMPTY";
			if (!email.isEmpty()) {
				List<Notification> l_notification = notificationDao
						.getNotificationByEmail(email);

				Gson gson = new Gson();
				jsonString = gson.toJson(l_notification);

				response.setContentType("json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonString);
			} else {
				response.setContentType("json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonString);
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
	}
}
