package spms.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;

@SuppressWarnings("serial")
@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		MemberDao memberDao = new MemberDao();

		try {
			ServletContext sc = this.getServletContext();

			// ServletContext에 있는 Connection 객체를 사용하겠다
			Connection conn = (Connection) sc.getAttribute("conn");

			memberDao.setConnection(conn);
			memberDao.delete(Integer.parseInt(request.getParameter("no")));
			
			response.sendRedirect("list");

		} catch (Exception e) {
			// throw new ServletException(e);
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);

		} finally {
			
		}

	}
}
