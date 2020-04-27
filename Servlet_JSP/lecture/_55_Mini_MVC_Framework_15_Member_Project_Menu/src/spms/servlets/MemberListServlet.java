package spms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			// ServletContext에 있는 Connection 객체를 사용하겠다
			ServletContext sc = this.getServletContext();
			MySqlMemberDao memberDao = (MySqlMemberDao)sc.getAttribute("memberDao");
			
			List<Member> members = memberDao.selectList();
			request.setAttribute("members", members);
			
			request.setAttribute("viewUrl", "/member/MemberList.jsp");
			
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}
}






