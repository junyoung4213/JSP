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
import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		MemberDao memberDao = new MemberDao();
		
		try {
			ServletContext sc = this.getServletContext();
			Connection conn = (Connection) sc.getAttribute("conn");
			memberDao.setConnection(conn);
			Member member = memberDao.selectOne(Integer.parseInt(req.getParameter("no")));
			
			req.setAttribute("member", member);
			
			RequestDispatcher rd = req.getRequestDispatcher("/member/MemberUpdateForm.jsp");
			rd.forward(req, resp);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		MemberDao memberDao = new MemberDao();
		
		try {
			ServletContext sc = this.getServletContext();
			// ServletContext에 있는 Connection 객체를 사용하겠다
			Connection conn = (Connection) sc.getAttribute("conn");
			
			Member member = new Member().setNo(Integer.parseInt(req.getParameter("no"))).setName(req.getParameter("name")).setEmail(req.getParameter("email"));
			memberDao.setConnection(conn);
			
			memberDao.update(member);
			
			resp.sendRedirect("list");
		}  catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", e);
			RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
			rd.forward(req, resp);
		} finally {
	
		}
		

	}
}
