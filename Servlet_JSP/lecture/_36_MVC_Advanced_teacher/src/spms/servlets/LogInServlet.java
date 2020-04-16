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
import javax.servlet.http.HttpSession;

import spms.dao.MemberDao;
import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 경로로 들어왔을 때 로그인 화면을 보여주자
		RequestDispatcher rd = req.getRequestDispatcher
						("/auth/LogInForm.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
		      ServletContext sc = this.getServletContext();
		      Connection conn = (Connection) sc.getAttribute("conn"); 

		      MemberDao memberDao = new MemberDao();
		      memberDao.setConnection(conn);
		      
		      Member member = memberDao.exist(
		    		  req.getParameter("email"), 
		    		  req.getParameter("password"));
		      if (member != null) {
		        HttpSession session = req.getSession();
		        session.setAttribute("member", member);
		        resp.sendRedirect("../member/list");

		      } else {
		        RequestDispatcher rd = req.getRequestDispatcher(
		            "/auth/LogInFail.jsp");
		        rd.forward(req, resp);
		      }
		}catch(Exception e) {
			throw new ServletException(e);
		}finally {
	
		}
	}
}










