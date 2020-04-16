package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

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
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		try {
			// ServletContext에 있는 Connection 객체를 사용하겠다
			ServletContext sc = this.getServletContext();
			Connection conn = (Connection)sc.getAttribute("conn");
			
			// Dao에 위임
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection(conn);
			List<Member> members = memberDao.selectList();
			req.setAttribute("members", members);

			resp.setContentType("text/html; charset=UTF-8");
			
			// JSP로 출력을 위임한다
			RequestDispatcher rd = req.getRequestDispatcher(
						"/member/MemberList.jsp");
			rd.include(req, resp);
			
			/*
			jsp로 위임하는 방식 2가지
			1) forward : 제어권을 아예 넘겨준다
			2) include : 실행이 끝나면 제어권을 넘겨받는다
			*/
			
			
		}catch(Exception e) {
			//throw new ServletException(e);
			// 필요하면 예외정보를 출력하기 위해 req에 저장한다
			req.setAttribute("error", e);
			
			// 포워딩
			RequestDispatcher rd = 
					req.getRequestDispatcher("/Error.jsp");
			rd.forward(req, resp);
			
		}finally {
		}
	}
}






